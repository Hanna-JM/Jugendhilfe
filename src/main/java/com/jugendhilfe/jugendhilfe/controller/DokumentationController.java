package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.Dokumentation;
import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.repository.KindRepository;
import com.jugendhilfe.jugendhilfe.repository.MitarbeiterRepository;
import com.jugendhilfe.jugendhilfe.service.DokumentationService;
import jakarta.mail.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/dokumentation")
public class DokumentationController {

    @Autowired
    private KindRepository kindRepository;

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;
    private List<Message> message;
    @Autowired
    DokumentationService dokumentationService;

//    @GetMapping("/neuedokumentation")
//    public String addDokumentation(Dokumentation dokumentation, Model model){
//        List<Kind> kinder = kindRepository.findAll();
//        model.addAttribute("kinder", kinder);
//        return "/neuedokumentation";
//    }
@GetMapping("/neuedokumentationkind")
public String addDokumentation(Dokumentation dokumentation, Model model){
    List<Kind> kinder = kindRepository.findAll();
    model.addAttribute("kinder", kinder);
    dokumentationService.addOptionenDokumentationTyp(dokumentation);
    model.addAttribute("optionenDokumentationTyp",dokumentation.optionenDokumentationTyp);
    model.addAttribute("localDateTime", LocalDateTime.now());
    model.addAttribute("dokumentation", new Dokumentation());
    return "/neuedokumentationkind";
}



    @PostMapping("/savedokumentationkind")
    public String savedokumentationkind(@Valid Dokumentation dokumentation, Model model){
        LocalDateTime dokumentationdatum = LocalDateTime.now();
        dokumentation.setDokumentationdatum(dokumentationdatum)  ;
        model.addAttribute("dokumentationdatum", dokumentationdatum);
        dokumentationService.save(dokumentation);
        return "redirect:/dokumentation/neuedokumentationkind";
    }


    @GetMapping("/dokumentationlistekind")
    public String viewHomePageKind(Model model, String keyword) {
        List<Dokumentation> listDokumentationKind = dokumentationService.findAll();


        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if(listDokumentationKind.isEmpty()){
            dokumentationService.addTestDokumentationKind();
            listDokumentationKind = dokumentationService.findAll();
            model.addAttribute("listDokumentation", listDokumentationKind);
        } // end testdaten

        if (keyword != null) {
            model.addAttribute("listDokumentation", dokumentationService.findByKeyword(keyword));
        } else {
            model.addAttribute("listDokumentation", listDokumentationKind);
        }
        return "dokumentationlistekind";
    }

}
