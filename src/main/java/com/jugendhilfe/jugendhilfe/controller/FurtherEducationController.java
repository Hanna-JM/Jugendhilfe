package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.FurtherEducation;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.repository.MitarbeiterRepository;
import com.jugendhilfe.jugendhilfe.service.FurtherEducationService;
import com.jugendhilfe.jugendhilfe.service.MitarbeiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/fortbildung")
public class FurtherEducationController {

    @Autowired
    FurtherEducationService furtherEducationService;

    @Autowired
    MitarbeiterService mitarbeiterService;
    @Autowired
    MitarbeiterRepository mitarbeiterRepository;

    @GetMapping("/fortbildungliste")
    public String viewHomePageFE(Model model, String keyword){
        List<FurtherEducation> listFurtherEducation = furtherEducationService.findAll();

        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if (listFurtherEducation.isEmpty()){
            furtherEducationService.addTestFurtherEducation();
            listFurtherEducation = furtherEducationService.findAll();
            model.addAttribute("listFurtherEducation", listFurtherEducation);
        } // end testdaten

        if (keyword != null) {
            model.addAttribute("listFurtherEducation", furtherEducationService.findByKeyword(keyword));
        } else {
            model.addAttribute("listFurtherEducation", listFurtherEducation);
        }
        return "fortbildungliste";

    }


    @GetMapping("/neuefortbildung")
    public String addFurtherEducation(FurtherEducation furtherEducation, Model model){

        furtherEducationService.addFurtherEducationCycleOptions(furtherEducation);
        model.addAttribute("furtherEducationCycleOptions", furtherEducation.getFurtherEducationCycleOptions());

        furtherEducationService.addFurtherEducationTypeOptions(furtherEducation);
        model.addAttribute("furtherEducationTypeOptions", furtherEducation.getFurtherEducationTypeOptions());

//        List<Mitarbeiter> mitarbeiterList = mitarbeiterService.findAll();
        List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findByMitarbeiterAktivFirmaTrue();

        model.addAttribute("mitarbeiterList", mitarbeiterList);

        model.addAttribute("furtherEducation", new FurtherEducation());
        return "/neuefortbildung";
    }


    @PostMapping("/savefortbildung")
    public String saveFurtherEducation(@Valid FurtherEducation furtherEducation){
        furtherEducationService.save(furtherEducation);
        return "redirect:/fortbildung/neuefortbildung";
    }

    @RequestMapping("/editfortbildung/{furtherEducationId}")
    public String editFurtherEducation(@PathVariable(name="furtherEducationId") Long furtherEducationId, Model model, FurtherEducation furtherEducation){

        furtherEducationService.addFurtherEducationCycleOptions(furtherEducation);
        model.addAttribute("furtherEducationCycleOptions", furtherEducation.getFurtherEducationCycleOptions());

        furtherEducationService.addFurtherEducationTypeOptions(furtherEducation);
        model.addAttribute("furtherEducationTypeOptions", furtherEducation.getFurtherEducationTypeOptions());


        model.addAttribute("dateCompare", furtherEducationService.datecheck(furtherEducation));


        model.addAttribute("furtherEducation", furtherEducationService.getByFurtherEducationId(furtherEducationId));

        List<Mitarbeiter> mitarbeiterList = mitarbeiterService.findAll();
  //      List<Mitarbeiter> mitarbeiterList = mitarbeiterRepository.findByMitarbeiterAktivFirmaTrue();
        model.addAttribute("mitarbeiterList", mitarbeiterList);

        return "neuefortbildung";
    }

    @RequestMapping("/deletefortbildung/{furtherEducationId}")
    public String deleteFurtherEducation(@PathVariable(name="furtherEducationId") Long furtherEducationId){
        furtherEducationService.delete(furtherEducationId);
        return "redirect:/fortbildung/fortbildungliste";
    }

    @RequestMapping("showfortbildung/{furtherEducationId}")
    public String showFurtherEduation(@PathVariable(name="furtherEducationId") Long furtherEducationId, Model model){

        model.addAttribute("dateCompare", furtherEducationService.datecheck(furtherEducationService.getByFurtherEducationId(furtherEducationId)));

        model.addAttribute("furtherEducation", furtherEducationService.getByFurtherEducationId(furtherEducationId));
        return "showfortbildung";
    }

}
