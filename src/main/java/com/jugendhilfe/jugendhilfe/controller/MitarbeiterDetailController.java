package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.repository.MitarbeiterRepository;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.MitarbeiterDetail;
import com.jugendhilfe.jugendhilfe.domain.Wohngruppe;
import com.jugendhilfe.jugendhilfe.service.MitarbeiterDetailService;
import com.jugendhilfe.jugendhilfe.service.MitarbeiterService;
import com.jugendhilfe.jugendhilfe.service.WohngruppenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/mitarbeiterdetail")
public class MitarbeiterDetailController {
    @Autowired
    MitarbeiterDetailService mitarbeiterDetailService;

    @Autowired
    MitarbeiterService mitarbeiterService;

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    WohngruppenService wohngruppenService;

//    @GetMapping("/neuermitarbeiterdetail")
//    public String neuerMitarbeiterDetail(Model model) {
//        List<Mitarbeiter> mitarbeiterList = mitarbeiterService.findAll();
//        model.addAttribute("mitarbeiterList", mitarbeiterList);
//        model.addAttribute("mitarbeiterdetail", new MitarbeiterDetail());
//        return "/neuermitarbeiterdetail";
//    }


@GetMapping("/neuermitarbeiterdetail")
public String neuerMitarbeiterDetail(Model model) {
    List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findByMitarbeiterAktivFirmaTrue();
    mitarbeiterService.findAll();
    List<Wohngruppe> wohngruppenList = wohngruppenService.findAll();
    // Initialisierung das MitarbeiterDetail-Objekt
    MitarbeiterDetail mitarbeiterdetail = new MitarbeiterDetail();
    model.addAttribute("mitarbeiterList", mitarbeiterList);
    model.addAttribute("wohngruppenList", wohngruppenList);
    model.addAttribute("mitarbeiterdetail", new MitarbeiterDetail());
    return "/neuermitarbeiterdetail";
}


//    @PostMapping("/savemitarbeiterdetail")
//    public String saveMitarbeiterDetail(@Valid MitarbeiterDetail mitarbeiterdetail) {
//        mitarbeiterDetailService.save(mitarbeiterdetail);
//        return "redirect:/mitarbeiterdetail/neuermitarbeiterdetail";
//    }

    @PostMapping("/savemitarbeiterdetail")
    public String saveMitarbeiterDetail(@Valid MitarbeiterDetail mitarbeiterdetail, BindingResult bindingResult, Model model) {
        Long mitarbeiterId = mitarbeiterdetail.getMitarbeiterDetailMitarbeiter().getMitarbeiterId();
        if (mitarbeiterId != null) {
            Mitarbeiter mitarbeiter = mitarbeiterService.findById(mitarbeiterId);
            if (mitarbeiter != null) {
                mitarbeiterdetail.setMitarbeiterDetailMitarbeiter(mitarbeiter);
            } else {
                model.addAttribute("error", "Mitarbeiter nicht gefunden");
                return "neuermitarbeiterdetail";
            }
        } else {
            model.addAttribute("error", "Ungültige Mitarbeiter-ID");
            return "neuermitarbeiterdetail";
        }

        Long wohngruppenId = mitarbeiterdetail.getMitarbeiterDetailWohngruppe().getWohngruppenId();
        if (wohngruppenId != null) {
            Wohngruppe wohngruppe = wohngruppenService.findById(wohngruppenId);
            if (wohngruppe != null) {
                mitarbeiterdetail.setMitarbeiterDetailWohngruppe(wohngruppe);
            } else {
                model.addAttribute("error", "Wohngruppe nicht gefunden");
                return "neuermitarbeiterdetail";
            }
        } else {
            model.addAttribute("error", "Ungültige Wohngruppen-ID");
            return "neuermitarbeiterdetail";
        }

        Mitarbeiter mitarbeiter = mitarbeiterService.findById(mitarbeiterId);
        boolean hasActiveEntry = mitarbeiterDetailService.hasActiveEntryForMitarbeiter(mitarbeiter);
        if (hasActiveEntry) {
            model.addAttribute("error", "Es existiert bereits ein aktiver Eintrag für diesen Mitarbeiter.");
            model.addAttribute("mitarbeiterdetail", mitarbeiterdetail);
            model.addAttribute("mitarbeiterList", mitarbeiterService.findAll());
            model.addAttribute("wohngruppenList", wohngruppenService.findAll());
            return "neuermitarbeiterdetail";
        }

        // Überprüfung ob Einsatzende vor Einsatzbeginn
        LocalDate einsatzBeginn = mitarbeiterdetail.getEinsatzBeginn();
        LocalDate einsatzEnde = mitarbeiterdetail.getEinsatzEnde();
        if (einsatzBeginn != null && einsatzEnde != null && einsatzEnde.isBefore(einsatzBeginn)) {
            if (mitarbeiterdetail.getMitarbeiterDetailId() == null) {
                bindingResult.rejectValue("einsatzEnde", "error.mitarbeiterdetail", "Falsche Eingabe. Das Einsatzende muss nach dem Einsatzbeginn liegen..");
            }
        }

        if (bindingResult.hasErrors()) {
            // Hinzufügen des Objekts zum Model
            model.addAttribute("mitarbeiterdetail", mitarbeiterdetail);
            model.addAttribute("mitarbeiterList", mitarbeiterService.findAll());
            model.addAttribute("wohngruppenList", wohngruppenService.findAll());
            // Bei Validierungsfehler die Fehlermeldung ausgeben
            model.addAttribute("error", "Ein Fehler ist aufgetreten. Das Einsatzende muss nach dem Einsatzbeginn liegen.");
            return "/neuermitarbeiterdetail";
        }

//        LocalDate einsatzEnde = mitarbeiterdetail.getEinsatzEnde();
        if (einsatzEnde == null ||
                einsatzEnde.isAfter(LocalDate.now()) ||
                einsatzEnde.isEqual(LocalDate.now())) {
            mitarbeiterdetail.setAktiv(true);
        } else {
            mitarbeiterdetail.setAktiv(false);
        }

        mitarbeiterDetailService.save(mitarbeiterdetail);
        return "redirect:/mitarbeiterdetail/neuermitarbeiterdetail";
    }

    @GetMapping("/mitarbeiterdetailliste")
    public String viewHomePage(Model model, String keyword) {
        mitarbeiterDetailService.updateAktivField();
        List<MitarbeiterDetail> listmitarbeiterDetail = mitarbeiterDetailService.findAllAktiveMitarbeiter();

        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if ( listmitarbeiterDetail.isEmpty()){
            mitarbeiterDetailService.addTestMitarbeiterDetail();
            listmitarbeiterDetail = mitarbeiterDetailService.findAllAktiveMitarbeiter();
            model.addAttribute("listmitarbeiterDetail", listmitarbeiterDetail);
        } // end testdaten

        if (keyword != null) {
            model.addAttribute("listmitarbeiterDetail", mitarbeiterDetailService.findByKeyword(keyword));
        } else {
            model.addAttribute("listmitarbeiterDetail", listmitarbeiterDetail);
        }
        return "mitarbeiterdetailliste";
    }


    @RequestMapping("/editmitarbeiterdetail/{mitarbeiterDetailId}")
    public String editMitarbeiterDetail(@PathVariable(name = "mitarbeiterDetailId") Long mitarbeiterDetailId, Model model) {
        MitarbeiterDetail mitarbeiterDetail = mitarbeiterDetailService.getByMitarbeiterDetailId(mitarbeiterDetailId);
//        List<Mitarbeiter> mitarbeiterList = mitarbeiterService.findAll();
        List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findByMitarbeiterAktivFirmaTrue();
        List<Wohngruppe> wohngruppenList = wohngruppenService.findAll();
        model.addAttribute("mitarbeiterdetail", mitarbeiterDetail);
        model.addAttribute("mitarbeiterList", mitarbeiterList);
        model.addAttribute("wohngruppenList", wohngruppenList);
        return "neuermitarbeiterdetail";
    }
}
