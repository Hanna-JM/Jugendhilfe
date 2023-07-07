package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.Dokumentation;
import com.jugendhilfe.jugendhilfe.domain.DokumentationMA;
import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.repository.KindRepository;
import com.jugendhilfe.jugendhilfe.repository.MitarbeiterRepository;
import com.jugendhilfe.jugendhilfe.service.DokumentationMAService;
import com.jugendhilfe.jugendhilfe.service.DokumentationService;
import jakarta.mail.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/dokumentationma")
public class DokumentationMAController {


    @Autowired
    MitarbeiterRepository mitarbeiterRepository;
    private List<Message> message;
    @Autowired
    DokumentationMAService dokumentationMAService;

//    @GetMapping("/neuedokumentation")
//    public String addDokumentation(Dokumentation dokumentation, Model model){
//        List<Kind> kinder = kindRepository.findAll();
//        model.addAttribute("kinder", kinder);
//        return "/neuedokumentation";
//    }


//    @GetMapping("/neuedokumentationmitarbeiter")
//    public String addDokumentationMitarbeiter(DokumentationMA dokumentationma, Model model){
//        List<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findAll();
//        model.addAttribute("mitarbeiter", mitarbeiter);
//        dokumentationMAService.addOptionenDokumentationTyp(dokumentationma);
//        model.addAttribute("optionenDokumentationTyp",dokumentationma.optionenDokumentationTyp);
//        model.addAttribute("localDateTime", LocalDateTime.now());
//        model.addAttribute("dokumentationma", new DokumentationMA());
//        return "/neuedokumentationmitarbeiter";
//    }

    @GetMapping("/neuedokumentationmitarbeiter")
    public String addDokumentationMitarbeiter(Model model){
        List<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findAll();
        DokumentationMA dokumentationma = new DokumentationMA();
        dokumentationMAService.addOptionenDokumentationTyp(dokumentationma); // Optionen hinzufügen
        model.addAttribute("mitarbeiter", mitarbeiter);
        model.addAttribute("optionenDokumentationTyp", dokumentationma.getOptionenDokumentationTyp()); // Optionen dem Modell hinzufügen
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("dokumentationma", dokumentationma);
        System.out.println("dokumentationma: " + dokumentationma);
        return "/neuedokumentationmitarbeiter";
    }




    @PostMapping("/savedokumentationmitarbeiter")
    public String savedokumentation(@Valid DokumentationMA dokumentationma, Model model){
        LocalDateTime dokumentationdatum = LocalDateTime.now();
        dokumentationma.setDokumentationdatum(dokumentationdatum)  ;
        model.addAttribute("dokumentationdatum", dokumentationdatum);
        dokumentationMAService.save(dokumentationma);
        return "redirect:/dokumentationma/neuedokumentationmitarbeiter";
    }


    @GetMapping("/dokumentationlistemitarbeiter")
    public String viewHomePageMitarbeiter(Model model, String keyword) {
        List<DokumentationMA> listDokumentationMitarbeiter = dokumentationMAService.findAll();

        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if(listDokumentationMitarbeiter.isEmpty()){
            dokumentationMAService.addTestDokumentationMitarbeiter();
            listDokumentationMitarbeiter = dokumentationMAService.findAll();
            model.addAttribute("listDokumentation", listDokumentationMitarbeiter);
        } // end testdaten


        if (keyword != null) {
            model.addAttribute("listDokumentation", dokumentationMAService.findByKeyword(keyword));
        } else {
            model.addAttribute("listDokumentation", listDokumentationMitarbeiter);
        }
        return "dokumentationlistemitarbeiter";
    }

}
