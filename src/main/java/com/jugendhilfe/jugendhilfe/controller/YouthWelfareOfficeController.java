package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.YouthWelfareOffice;
import com.jugendhilfe.jugendhilfe.service.YouthWelfareOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/behoerde")
public class YouthWelfareOfficeController {

    @Autowired
    YouthWelfareOfficeService youthWelfareOfficeService;

    @GetMapping("/behoerdenliste")
    public String viewHomePageBH(Model model, YouthWelfareOffice youthWelfareOffice, String keyword) {
        List<YouthWelfareOffice> listYouthWelfareOffice = youthWelfareOfficeService.findAll();

        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if ( listYouthWelfareOffice.isEmpty()){
            youthWelfareOfficeService.addTestYWO();
            listYouthWelfareOffice = youthWelfareOfficeService.findAll();
            model.addAttribute("listYouthWelfareOffice", listYouthWelfareOffice);
        } // end testdaten

        if (keyword != null) {
            model.addAttribute("listYouthWelfareOffice", youthWelfareOfficeService.findByKeyword(keyword));
        } else {
            model.addAttribute("listYouthWelfareOffice", listYouthWelfareOffice);
        }
        return "behoerdenliste";

    }

    @GetMapping("/neuebehoerde")
    public String newYouthWelfareOffice(YouthWelfareOffice youthWelfareOffice, Model model) {
        model.addAttribute("youthWelfareOffice", new YouthWelfareOffice());
        return "/neuebehoerde";
    }

    @PostMapping("/savebehoerde")
    public String saveYouthWelfareOffice(YouthWelfareOffice youthWelfareOffice) {
        youthWelfareOfficeService.save(youthWelfareOffice);
        //return "savebehoerde";
        return "redirect:/behoerde/neuebehoerde";
    }

    @RequestMapping("/editbehoerde/{youthWelfareOfficeId}")
    public String editYouthWelfareOffice(@PathVariable(name = "youthWelfareOfficeId") Long youthWelfareOfficeId, YouthWelfareOffice youthWelfareOffice, Model model) {
        model.addAttribute("youthWelfareOffice", youthWelfareOfficeService.getByYouthWelfareOfficeId(youthWelfareOfficeId));
        return "neuebehoerde";
    }

    @RequestMapping("/deletebehoerde/{youthWelfareOfficeId}")
    public String deleteYouthWelfareOffice(@PathVariable(name = "youthWelfareOfficeId") Long youthWelfareOfficeId) {
        youthWelfareOfficeService.delete(youthWelfareOfficeId);
        return "redirect:/behoerde/behoerdenliste";
    }

    @GetMapping("/showbehoerde/{youthWelfareOfficeId}")
    public String showYouthWelfareOffice(@PathVariable(name = "youthWelfareOfficeId") Long youthWelfareOfficeId, Model model, YouthWelfareOffice youthWelfareOffice) {
        model.addAttribute("youthWelfareOffice", youthWelfareOfficeService.getByYouthWelfareOfficeId(youthWelfareOfficeId));
        return "showbehoerde";
    }
}
