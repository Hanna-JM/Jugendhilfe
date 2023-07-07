package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.*;
import com.jugendhilfe.jugendhilfe.repository.MitarbeiterRepository;
import com.jugendhilfe.jugendhilfe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/mitarbeiter")
public class MitarbeiterController {

    @Autowired
    private MitarbeiterService mitarbeiterService;
    @Autowired
    private MitarbeiterDetailService mitarbeiterDetailService;

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    private FurtherEducationService furtherEducationService;
    @Autowired
    private KindService kindService;

    /**
     * zeigt mitarbeiterliste, ggf. nur die liste passend zu einem suchbegriff
     *
     * @param model
     * @param keyword
     * @return
     */
    @GetMapping("/mitarbeiterliste")
    public String viewHomePage(Model model, String keyword) {
        List<Mitarbeiter> listMitarbeiter = mitarbeiterService.findAll();
        List<Mitarbeiter> listMitarbeiterAktiv = mitarbeiterRepository.findByMitarbeiterAktivFirmaTrue();


        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if (listMitarbeiter.isEmpty()) {
            mitarbeiterService.addTestMitarbeiter();
            listMitarbeiter = mitarbeiterService.findAll();
            model.addAttribute("listMitarbeiter", listMitarbeiter);
        } // end testdaten

        if (keyword != null) {
            model.addAttribute("listMitarbeiter", mitarbeiterService.findByKeyword(keyword));
        } else {
            model.addAttribute("listMitarbeiter", listMitarbeiter);
            //    model.addAttribute("listMitarbeiter", listMitarbeiterAktiv);
        }
        return "mitarbeiterliste";
    }

    /**
     * Model für Liste für Dropdown bei Anrede Mitarbeiter, Model neuer Mitarbeiter
     *
     * @param mitarbeiter
     * @param model
     * @return
     */
    @GetMapping("/neuermitarbeiter")
    public String addMitarbeiter(Mitarbeiter mitarbeiter, Model model) {

        mitarbeiterService.addOptionAnredeMitarbeiter(mitarbeiter);
        model.addAttribute("optionenAnredeMitarbeiter", mitarbeiter.optionenAnredeMitarbeiter);

        List<FurtherEducation> furtherEducationList = furtherEducationService.findAll();
        model.addAttribute("furtherEducationList", furtherEducationList);

        List<Kind> kindList = kindService.listAll();
        model.addAttribute("kindList", kindList);

        model.addAttribute("mitarbeiter", new Mitarbeiter());

        return "neuermitarbeiter";
    }


    @PostMapping("/savemitarbeiter")
    public String saveMitarbeiter(@Valid Mitarbeiter mitarbeiter) {


        mitarbeiterService.checkMitarbeiterAktivFirma(mitarbeiter);

        if (mitarbeiter.getMitarbeiterDayOfBirth().isAfter(LocalDate.now())) {
            return "neuermitarbeiter";
        }
        mitarbeiterService.save(mitarbeiter);
        return "redirect:/mitarbeiter/mitarbeiterliste";
    }


    @RequestMapping("/editmitarbeiter/{mitarbeiterId}")
    public String editmitarbeiter(@PathVariable(name = "mitarbeiterId") Long mitarbeiterID, Model model, Mitarbeiter mitarbeiter) {

        mitarbeiterService.addOptionAnredeMitarbeiter(mitarbeiter);
        model.addAttribute("optionenAnredeMitarbeiter", mitarbeiter.optionenAnredeMitarbeiter);

        List<FurtherEducation> furtherEducationList = furtherEducationService.findAll();
        model.addAttribute("furtherEducationList", furtherEducationList);

        List<Kind> kindList = kindService.listAll();
        model.addAttribute("kindList", kindList);

        model.addAttribute("mitarbeiter", mitarbeiterService.getByMitarbeiterId(mitarbeiterID));

        return "neuermitarbeiter";
    }


    @RequestMapping("/deletemitarbeiter/{mitarbeiterId}")
    public String deleteMitarbeiter(@PathVariable(name = "mitarbeiterId") Long mitarbeiterId) {
        LocalDate today = LocalDate.now();
        if (mitarbeiterService.findById(mitarbeiterId).getMitarbeiterAustritt() != null) {
            LocalDate maAustrittPlusYear20 = mitarbeiterService.findById(mitarbeiterId).getMitarbeiterAustritt().plusYears(20).plusDays(1);
            if (maAustrittPlusYear20.isBefore(today)) {
                mitarbeiterService.delete(mitarbeiterId);
            }
        }
//        mitarbeiterService.delete(mitarbeiterId);
        return "redirect:/mitarbeiter/mitarbeiterliste";
    }


    @RequestMapping("/showmitarbeiter/{mitarbeiterId}")
    public String showmitarbeiter(@PathVariable(name = "mitarbeiterId") Long mitarbeiterId, Model model, Mitarbeiter mitarbeiter) {
        model.addAttribute("dateCompareloeschenMitarbeiter", mitarbeiterService.datecheckLoeschenMitarbeiter(mitarbeiter));

        mitarbeiterService.checkAndSaveMitarbeiterAktivFirma(mitarbeiterService.getByMitarbeiterId(mitarbeiterId));

        model.addAttribute("mitarbeiter", mitarbeiterService.getByMitarbeiterId(mitarbeiterId));

        return "showmitarbeiter";
    }


}