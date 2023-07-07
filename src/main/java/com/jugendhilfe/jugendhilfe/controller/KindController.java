package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.Sorgeberechtiger;
import com.jugendhilfe.jugendhilfe.domain.YouthWelfareOffice;
import com.jugendhilfe.jugendhilfe.service.MitarbeiterService;
import com.jugendhilfe.jugendhilfe.service.SorgeberechtigerService;
import com.jugendhilfe.jugendhilfe.service.YouthWelfareOfficeService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.el.stream.Optional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.service.KindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/kind")
public class KindController {
    @Autowired
    private KindService kindService;
    @Autowired
    private SorgeberechtigerService sorgeberechtigerService;
    @Autowired
    YouthWelfareOfficeService youthWelfareOfficeService;
    @Autowired
    private MitarbeiterService mitarbeiterService;

    @GetMapping("/")
    public String viewHomepage(Model model, String keyword) {
        List<Kind> listKinder = kindService.listAll();

        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if (listKinder.isEmpty()) {
            kindService.addTestKind();
            listKinder = kindService.listAll();
            model.addAttribute("listKinder", listKinder);
        } // end testdaten

        if (keyword != null) {
            model.addAttribute("listKinder", kindService.findByKeyword(keyword));

        } else {
            model.addAttribute("listKinder", listKinder);
        }
        return "kinder";
    }

    @GetMapping("/neueskind")
    public String add(Kind kind, Model model) {
//        model.addAttribute("kind", new Kind());
        model.addAttribute("sorgeberechtigerSet", kind.getSorgeberechtigerSet());
        model.addAttribute("sorgeberechtigerList", sorgeberechtigerService.listAll());
        model.addAttribute("sorgeberechtiger", new Sorgeberechtiger());

        model.addAttribute("siblingsSet", kind.getSiblings());
        model.addAttribute("kinderList", kindService.listAll());

        List<YouthWelfareOffice> youthWelfareOfficeList = youthWelfareOfficeService.findAll();
        model.addAttribute("youthWelfareOfficeList", youthWelfareOfficeList);

        List<Mitarbeiter> mitarbeiterList = mitarbeiterService.findAll();
        model.addAttribute("mitarbeiterList", mitarbeiterList);
        model.addAttribute("kind", new Kind());
        return "neueskind";
    }

    //todo  : to solve the duplicated kind and the error message
    @PostMapping("/savekind")
    public String saveKind(@ModelAttribute(name = "kind") @Valid Kind kind,
                           @RequestParam(name = "sorgeberechtigerId", required = false) Long sorgeberechtigerId,
                           @RequestParam(name = "siblingId", required = false) Long siblingId,
                           @RequestParam(name = "mitarbeiterId", required = false) Long mitarbeiterId,
                           @RequestParam(required = false) String customOption,
                           @RequestParam("einzug_datum") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate einzug_datum,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // validation errors
//            bindingResult.getAllErrors().stream().forEach(System.out::println);
//            model.addAttribute("kind", kind);
//            model.addAttribute("sorgeberechtigerList", sorgeberechtigerService.listAll());
            return "neueskind";
        }
        if (customOption != null && !customOption.isEmpty()) {
            kind.setReligion(customOption);
        }
        if (sorgeberechtigerId != null) {
            Sorgeberechtiger sorgeberechtiger = sorgeberechtigerService.get(sorgeberechtigerId);

            kind.addSorgeBerechtigter(sorgeberechtiger);
            sorgeberechtiger.addKind(kind);

            sorgeberechtigerService.save(sorgeberechtiger);
//            kindService.save(kind);
        }

        if (siblingId != null) {
            Kind sibling = kindService.get(siblingId);
            kind.addSibling(sibling);
            sibling.addSibling(kind);
            kindService.save(sibling);
//            sibling.getSiblings().addAll(kind.getSiblings());
        }
        if (mitarbeiterId != null) {
            Mitarbeiter mitarbeiter = mitarbeiterService.getByMitarbeiterId(mitarbeiterId);
            if (mitarbeiter.getMitarbeiterId() == null) {
                mitarbeiterService.save(mitarbeiter);
                System.out.println("Mitarbeiter: " + kind.getMitarbeitereinzel().getVorname());
            }

            kind.addMitarbeiterEinzel(mitarbeiter);
            einzug_datum=kind.getEinzug_datum();
            kind.setEinzug_datum(einzug_datum);
            mitarbeiter.addKindEinzel(kind);
            mitarbeiterService.save(mitarbeiter);
            System.out.println("saved Mitarbeitereinzel : " + kind.getMitarbeitereinzel().getVorname());

        }


        kindService.save(kind);
        return "redirect:/kind/";
    }

    @GetMapping("/editkind")
    public ModelAndView editKind(@RequestParam(name = "kindId") Long kindId) {
        ModelAndView modelAndView = new ModelAndView("neueskind");
        Kind kind = kindService.get(kindId);
        List<Sorgeberechtiger> sorgeberechtigerList = sorgeberechtigerService.listAll();
        modelAndView.addObject("kind", kind);
        System.out.println("kid: " + kind);
        System.out.println("kid from edit kind: ");
        Set<Sorgeberechtiger> sorgeberechtigerSet = kind.getSorgeberechtigerSet();
        System.out.println(sorgeberechtigerSet);
        modelAndView.addObject("sorgeberechtigerSet", sorgeberechtigerSet);

        modelAndView.addObject("sorgeberechtigerList", sorgeberechtigerList);

        List<YouthWelfareOffice> youthWelfareOfficeList = youthWelfareOfficeService.findAll();
        modelAndView.addObject("youthWelfareOfficeList", youthWelfareOfficeList);

        System.out.println(sorgeberechtigerSet);

        modelAndView.addObject("siblingsSet", kind.getSiblings());

        List<Mitarbeiter> mitarbeiterList = mitarbeiterService.findAll();
        modelAndView.addObject("mitarbeiterList", mitarbeiterList);

        modelAndView.addObject("kinderList", kindService.listAll());
        System.out.println("Siblings: " + kind.getSiblings());
        return modelAndView;
    }


    @RequestMapping("/deletekind/{kindId}")
    public String deleteKind(@PathVariable(name = "kindId") Long kindId, RedirectAttributes redirectAttributes) {
        Kind kind = kindService.get(kindId);

        if (kind.getAuszug_datum() == null || isLessThan20Years(kind.getAuszug_datum())) {
            redirectAttributes.addFlashAttribute("error", "delete.invalid.message");
//                return "redirect:/kind/";
        } else {
            try {
                kindService.delete(kindId);
                redirectAttributes.addFlashAttribute("success", "delete.success.message");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "error.message.auszugDatumTooRecent");
            }
        }

        return "redirect:/kind/";
    }

    private boolean isLessThan20Years(LocalDate auszugDatum) {
        LocalDate currentDate = LocalDate.now();
        LocalDate twentyYearsAgo = currentDate.minusYears(20);

        return auszugDatum.isAfter(twentyYearsAgo);
    }


//    @RequestMapping("/deletekind/{kindId}")
//    public String deleteKind(@PathVariable(name = "kindId") int kindId) {
//        kindService.delete(kindId);
//        return "redirect:/kind/";
//    }

    //    @RequestMapping("/get/{kindId}")
//    public ModelAndView getKindId(@PathVariable Long kindId){
//    }
    @RequestMapping("/showkind/{kindId}")
    public ModelAndView showKind(@PathVariable Long kindId,
                                 @RequestParam(name = "mitarbeiterId", required = false) Long mitarbeiterId) {
        ModelAndView modelAndView = new ModelAndView("showkind");
        Kind kind = kindService.get(kindId);
        modelAndView.addObject("kind", kind);

        Set<Sorgeberechtiger> sorgeberechtigerSet = kind.getSorgeberechtigerSet();
        modelAndView.addObject("sorgeberechtiger", sorgeberechtigerSet);

        modelAndView.addObject("siblingsSet", kind.getSiblings());

        List<Mitarbeiter> mitarbeiterList = mitarbeiterService.findAll();
        modelAndView.addObject("mitarbeiterList", mitarbeiterList);

        Mitarbeiter mitarbeiter = mitarbeiterService.getByMitarbeiterId(mitarbeiterId);
        modelAndView.addObject("mitarbeiter", mitarbeiter);

        modelAndView.addObject("kinderList", kindService.listAll());

        return modelAndView;
    }

    @GetMapping("/kinderauswahl")
    public String kinderauswahl(Model model) {
        List<Kind> listKinder = kindService.listAll();
        model.addAttribute("listKinder", listKinder);
        return "kinderauswahl";
    }


    // GET endpoint to retrieve a Kind by ID
    @GetMapping("/{id}")
    public ResponseEntity<Kind> getKindById(@PathVariable(value = "id") Long kindId) {
        Kind kind = kindService.get(kindId);
        if (kind == null) {
            throw new EntityNotFoundException("Kind not found with id " + kindId);
        }
        return ResponseEntity.ok().body(kind);
    }

    // Exception handler for EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping("/sorgeberechtiger/{kindId}")
    public void addSorgeberechtigerToKind(@PathVariable("kindId") Long id,
                                          @RequestParam("sorgeberechtigerId") Long sorgeberechtigerId,
                                          Model model) {
        System.out.println("addSorgeberechtigerToKind method");
        System.out.println("Kind ID: " + id);
        System.out.println("Sorgeberechtiger ID: " + sorgeberechtigerId);
        Kind kind = kindService.get(id);
        System.out.println(kind);
//        if (sorgeberechtigerId != null) {
        Sorgeberechtiger sorgeberechtiger = sorgeberechtigerService.get(sorgeberechtigerId);

        System.out.println(sorgeberechtiger);
        kind.addSorgeBerechtigter(sorgeberechtiger);
        System.out.println("sorgeberechtigter set : " + kind.getSorgeberechtigerSet());
        kindService.save(kind);
        sorgeberechtigerService.save(sorgeberechtiger);
    }

    //todo to be checked later on
    @PostMapping("/removeSorgeberechtigter/{kindId}")
    public String removeSorgeBerechtigterFromKind(@PathVariable Long kindId,
                                                  @RequestParam Long sorgeberechtigerId) {
        Kind kind = kindService.get(kindId);
        Sorgeberechtiger sorgeberechtiger = sorgeberechtigerService.get(sorgeberechtigerId);
        kind.getSorgeberechtigerSet().remove(sorgeberechtiger);
        kindService.save(kind);
        return "redirect:/kinder";
    }

    //todo to be checked later on
    @PostMapping("/{id}/remove-sorgeberechtiger")
    public String removeSorgeberechtiger(@PathVariable Long id,
                                         @RequestParam Long sorgeberechtigerId) {
        Kind kind = kindService.get(id);
        kindService.removeSorgeberechtigerFromKind(id, sorgeberechtigerId);
        kindService.save(kind);
        return "redirect:/kinder/" + id;
    }
}