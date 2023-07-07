package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.Wohngruppe;
import com.jugendhilfe.jugendhilfe.repository.KindRepository;
import com.jugendhilfe.jugendhilfe.repository.MitarbeiterRepository;
import com.jugendhilfe.jugendhilfe.repository.WohngruppenRepository;
import com.jugendhilfe.jugendhilfe.service.KindService;
import com.jugendhilfe.jugendhilfe.service.MitarbeiterService;
import com.jugendhilfe.jugendhilfe.service.WohngruppenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/wohngruppe")
public class WohngruppenController {
    @Autowired
    private WohngruppenService wohngruppenService;

    @Autowired
    private MitarbeiterService mitarbeiterService;

    @Autowired
    private KindService kindService;

    @Autowired
    private WohngruppenRepository wohngruppenRepository;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    private KindRepository kindRepository;

    @GetMapping("/neuewohngruppe")
    public String addWohngruppe(Wohngruppe wohngruppe, Model model) {
        model.addAttribute("wohngruppe", new Wohngruppe());
        return "neuewohngruppe";
    }

    @PostMapping("/savewohngruppe")
    public String saveWohngruppe(@Valid Wohngruppe wohngruppe, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().stream().forEach(System.out::println);
//            model.addAttribute("wohngruppe", wohngruppe);
//            return "neuewohngruppe";
//        }
        List<Wohngruppe> listWohngruppe = wohngruppenService.findAll();

        if (!listWohngruppe.isEmpty()) {
            for (Wohngruppe wg : listWohngruppe) {
                boolean isSame = wg.equals(wohngruppe);

                if (wg.getWohngruppenId() != null) {
                    wohngruppenService.save(wohngruppe);
                    return "redirect:/wohngruppe/neuewohngruppe";
                }
                if (isSame) {
                    model.addAttribute("isSame", isSame);
                    model.addAttribute("error", "Eine Wohngruppe mit den selben Details(Name, Strasse, Ort) existiert bereits.");
                    return "neuewohngruppe";
                }
            }
        }
        wohngruppenService.save(wohngruppe);
        return "redirect:/wohngruppe/neuewohngruppe";
    }

    @GetMapping("/wohngruppenliste")
    public String viewHomePage(Model model, String keyword, String sortBy, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
//        if (page < 0) {
//            page = 0;
//        }
        Pageable pageable = PageRequest.of(page, size);
//        List<Wohngruppe> wohngruppePage;
        Page<Wohngruppe> wohngruppePage;

        if (keyword != null) {
            wohngruppePage = (Page<Wohngruppe>) wohngruppenService.findByKeyword(keyword, pageable);
        } else if (sortBy != null) {
            wohngruppePage = (Page<Wohngruppe>) wohngruppenService.findAllSorted(sortBy, pageable);
        } else {
            wohngruppePage = (Page<Wohngruppe>) (Page<Wohngruppe>) wohngruppenService.findAll(pageable);
        }

        model.addAttribute("listWohngruppe", wohngruppePage.getContent());
        model.addAttribute("currentPage", wohngruppePage.getNumber());
        model.addAttribute("totalPages", wohngruppePage.getTotalPages());
        model.addAttribute("totalItems", wohngruppePage.getTotalElements());
        return "wohngruppenliste";
    }


//    @GetMapping("/wohngruppenliste")
//    public String viewHomePage(Model model, String keyword, String sortBy) {
//        List<Wohngruppe> listWohngruppe;
//
//        if (keyword != null) {
//            listWohngruppe = wohngruppenService.findByKeyword(keyword);
//        } else if (sortBy != null) {
//            listWohngruppe = wohngruppenService.findAllSorted(sortBy);
//        } else {
//            listWohngruppe = wohngruppenService.findAll();
//        }
//
//        model.addAttribute("listWohngruppe", listWohngruppe);
//        return "wohngruppenliste";
//    }

    @RequestMapping("/showwohngruppe/{wohngruppenId}")
    public String showwohngruppe(@PathVariable(name = "wohngruppenId") Long wohngruppenID, Model model) {
        Wohngruppe wohngruppe = wohngruppenService.getWohngruppenId(wohngruppenID);
//        Set<Mitarbeiter> mitarbeiterSet = wohngruppe.getWgmitarbeiter();
        model.addAttribute("wohngruppe", wohngruppe);
//        model.addAttribute("mitarbeiterSet", mitarbeiterSet);
        return "showwohngruppe";
    }

    @RequestMapping("/editwohngruppe/{wohngruppenId}")
    public String editwohngruppe(@PathVariable(name = "wohngruppenId") Long wohngruppenID, Model model, Wohngruppe wohngruppe) {
        wohngruppe = wohngruppenService.getWohngruppenId(wohngruppenID);
        model.addAttribute("wohngruppe", wohngruppe);
        return "neuewohngruppe";
    }

    @RequestMapping("/deletewohngruppe/{wohngruppenId}")
    public String deletewohngruppe(@PathVariable(name = "wohngruppenId") Long wohngruppenId) {
        Wohngruppe wohngruppe = wohngruppenService.getWohngruppenId(wohngruppenId);
        wohngruppenService.delete(wohngruppenId);
        return "redirect:/wohngruppe/wohngruppenliste";
    }

    @GetMapping("/wohngruppen")
    public String showWohngruppen(Model model) {
        List<Wohngruppe> wohngruppen = wohngruppenRepository.findAll();
        System.out.println(wohngruppen);
        model.addAttribute("wohngruppen", wohngruppen);
        return "wohngruppen";
    }

    @PostMapping("/wohngruppen/mitarbeiter")
    public String addMitarbeiterToWohngruppe(@RequestParam Long wohngruppenId, @RequestParam Long mitarbeiterId) {
        Wohngruppe wohngruppe = wohngruppenRepository.findById(wohngruppenId).orElseThrow(() -> new IllegalArgumentException("Invalid Wohngruppe Id: " + wohngruppenId));
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findById(mitarbeiterId).orElseThrow(() -> new IllegalArgumentException("Invalid Mitarbeiter Id: " + mitarbeiterId));
//        wohngruppe.getWgmitarbeiter().add(mitarbeiter);
        wohngruppenRepository.save(wohngruppe);
//        return "redirect:/wohngruppe/wohngruppenliste";
        return "redirect:/wohngruppe/showwohngruppe/" + wohngruppenId;
    }

    @RequestMapping("/get/{wohngruppenId}")
    public ModelAndView getWohngruppenId(@PathVariable Long wohngruppenId) {
//        ModelAndView mav = new ModelAndView("addMitarbeiterToWohngruppe");
        ModelAndView mav = new ModelAndView("wohngruppen");
        mav.addObject("wohngruppenId", wohngruppenId);
        mav.addObject("mitarbeiterList", mitarbeiterService.findAll());
        return mav;
    }

    //für kind
    @GetMapping("/addKindToWohngruppe")
    public String showKinder(Model model) {
        List<Kind> kinder = kindRepository.findAll();
        System.out.println(kinder);
        model.addAttribute("kinder", kinder);
        return "addKindToWohngruppe";
    }

    @PostMapping("/addKindToWohngruppe/kind")
    public String addKindToWohngruppe(@RequestParam Long wohngruppenId, @RequestParam Long kindId) {
        Wohngruppe wohngruppe = wohngruppenRepository.findById(wohngruppenId).orElseThrow(() -> new IllegalArgumentException("Invalid Wohngruppe Id: " + wohngruppenId));
        Kind kind = kindRepository.findById(kindId).orElseThrow(() -> new IllegalArgumentException("Invalid Kind Id: " + kindId));
//        wohngruppe.getWgkind().add(kind);
        wohngruppenRepository.save(wohngruppe);
        return "redirect:/wohngruppe/showwohngruppe/" + wohngruppenId;
    }

    @RequestMapping("/getKind/{wohngruppenId}")
    public ModelAndView getWohngruppenIdKind(@PathVariable Long wohngruppenId) {
        ModelAndView mav = new ModelAndView("addKindToWohngruppe");
        mav.addObject("wohngruppenId", wohngruppenId);
        mav.addObject("kindList", kindService.listAll());
        return mav;
    }

}

