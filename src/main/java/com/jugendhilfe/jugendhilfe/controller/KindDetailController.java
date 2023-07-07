package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.*;
import com.jugendhilfe.jugendhilfe.service.KindDetailService;
import com.jugendhilfe.jugendhilfe.service.KindService;
import com.jugendhilfe.jugendhilfe.service.WohngruppenService;
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
@RequestMapping("/kinddetail")
public class KindDetailController {
    @Autowired
    KindDetailService kindDetailService;
    @Autowired
    KindService kindService;

    @Autowired
    WohngruppenService wohngruppenService;

    @GetMapping("/neueskinddetail")
    public String neuesKindDetail(Model model) {
        List<Kind> kindList = kindService.listAll();
        List<Wohngruppe> wohngruppenList = wohngruppenService.findAll();
        model.addAttribute("kindList", kindList);
        model.addAttribute("wohngruppenList", wohngruppenList);
        model.addAttribute("kinddetail", new KindDetail());
        return "/neueskinddetail";
    }

    @PostMapping("/savekinddetail")
    public String saveKindDetail(@Valid KindDetail kinddetail, Model model) {
        Long kindId = kinddetail.getKindDetailKind().getKindId();
        if (kindId != null) {
            Kind kind = kindService.findById(kindId);
            if (kind != null) {
                kinddetail.setKindDetailKind(kind);
            } else {
                model.addAttribute("error", "Kind nicht gefunden");
                return "neueskinddetail";
            }
        } else {
            model.addAttribute("error", "Ungültige Kind-ID");
            return "neueskinddetail";
        }

        Long wohngruppenId = kinddetail.getKindDetailWohngruppe().getWohngruppenId();
        if (wohngruppenId != null) {
            Wohngruppe wohngruppe = wohngruppenService.findById(wohngruppenId);
            if (wohngruppe != null) {
                kinddetail.setKindDetailWohngruppe(wohngruppe);
            } else {
                model.addAttribute("error", "Wohngruppe nicht gefunden");
                return "neueskinddetail";
            }
        } else {
            model.addAttribute("error", "Ungültige Wohngruppen-ID");
            return "neueskinddetail";
        }

        LocalDate auszug = kinddetail.getAuszug();
        if (auszug == null || auszug.isAfter(LocalDate.now()) || auszug.isEqual(LocalDate.now())) {
            kinddetail.setAktiv(true);
        } else {
            kinddetail.setAktiv(false);
        }

        kindDetailService.save(kinddetail);
        return "redirect:/kinddetail/neueskinddetail";
    }

    @GetMapping("/kinddetailliste")
    public String viewHomePage(Model model, String keyword) {
//        kindDetailService.updateAktivField(); // Hier wird die Methode updateAktivField() aufgerufen
//        List<KindDetail> listmitarbeiterDetail = mitarbeiterDetailService.findAll();

        kindDetailService.updateAktivField();
        List<KindDetail> listkindDetail = kindDetailService.findAllAktiveKind();

        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if ( listkindDetail.isEmpty()){
            kindDetailService.addTestKindDetail();
            listkindDetail = kindDetailService.findAllAktiveKind();
            model.addAttribute("listkindDetail", listkindDetail);
        } // end testdaten

        if (keyword != null) {
            model.addAttribute("listkindDetail", kindDetailService.findByKeyword(keyword));
        } else {
            model.addAttribute("listkindDetail", listkindDetail);
        }
        return "kinddetailliste";
    }
    @RequestMapping("/editkinddetail/{kindDetailId}")
    public String editKindDetail(@PathVariable(name = "kindDetailId") Long kindDetailId, Model model) {
        KindDetail kindDetail = kindDetailService.getByKindDetailId(kindDetailId);
        List<Kind> kindList = kindService.listAll();
        List<Wohngruppe> wohngruppenList = wohngruppenService.findAll();
        model.addAttribute("kinddetail", kindDetail);
        model.addAttribute("kindList", kindList);
        model.addAttribute("wohngruppenList", wohngruppenList);
        return "neueskinddetail";
    }
}
