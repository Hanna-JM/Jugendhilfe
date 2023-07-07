package com.jugendhilfe.jugendhilfe.controller;

import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Sorgeberechtiger;
import com.jugendhilfe.jugendhilfe.service.SorgeberechtigerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/sorgeberechtiger")
public class SorgeberechtigerController {

    @Autowired
    private SorgeberechtigerService sorgeberechtigerService;

    @GetMapping("/")
    public String viewHomepage(Model model, @RequestParam(required = false) String keyword, Sorgeberechtiger sorgeberechtiger) {
        List<Sorgeberechtiger> sorgeberechtigerList = sorgeberechtigerService.listAll();

        // TODO vor Auslieferung testdaten entfernen
        // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
        if (sorgeberechtigerList.isEmpty()) {
            sorgeberechtigerService.addTestSorgeberechtigter();
            sorgeberechtigerList = sorgeberechtigerService.listAll();
            model.addAttribute("sorgeberechtigerList", sorgeberechtigerList);
        } // end testdaten

        if (keyword != null) {
            sorgeberechtigerList = sorgeberechtigerService.findByKeyword(keyword);
        } else {
            sorgeberechtigerList = sorgeberechtigerService.listAll();
        }

        model.addAttribute("sorgeberechtigerList", sorgeberechtigerList);

        sorgeberechtigerService.addOptionAnredeSorgeberechtigter(sorgeberechtiger);
        model.addAttribute("optionenAnredeSorgeberechtigter", sorgeberechtiger.optionenAnredeSorgeberechtigter);

        model.addAttribute("sorgeberechtiger", new Sorgeberechtiger());
        return "sorgeberechtiger";
    }

    @PostMapping("/savesorgeberechtiger")
    public String saveSorgeberechtiger(@ModelAttribute(name = "sorgeberechtiger")
                                       @Valid Sorgeberechtiger sorgeberechtiger,
                                       Model model) {

        List<Sorgeberechtiger> sorgeberechtigerList = sorgeberechtigerService.listAll();
        System.out.println("sorgeberechtigerList" + sorgeberechtigerList);
//        if (!sorgeberechtigerList.isEmpty()) {
        for (Sorgeberechtiger s : sorgeberechtigerList) {
            boolean sorgeberechtigerExists = s.equals(sorgeberechtiger);

            if (s.getSorgeberechtigerId() != null) {
                System.out.println("id: " + s.getSorgeberechtigerId());

                sorgeberechtigerService.addOptionAnredeSorgeberechtigter(sorgeberechtiger);
                model.addAttribute("optionenAnredeSorgeberechtigter", sorgeberechtiger.optionenAnredeSorgeberechtigter);

                sorgeberechtigerService.save(sorgeberechtiger);
                return "redirect:/sorgeberechtiger/";
            }
        }
//        }
        sorgeberechtigerService.save(sorgeberechtiger);
        return "redirect:/sorgeberechtiger/";
    }

    @GetMapping("/editsorgeberechtiger")
    public ModelAndView editSorgeberechtiger(@RequestParam(name = "sorgeberechtigerId") Long sorgeberechtigerId, Model model) {
        ModelAndView modelAndView = new ModelAndView("sorgeberechtiger");
        Sorgeberechtiger sorgeberechtiger = sorgeberechtigerService.get(sorgeberechtigerId);

        sorgeberechtigerService.addOptionAnredeSorgeberechtigter(sorgeberechtiger);
        modelAndView.addObject("optionenAnredeSorgeberechtigter", sorgeberechtiger.optionenAnredeSorgeberechtigter);

        modelAndView.addObject("sorgeberechtiger", sorgeberechtiger);
        modelAndView.addObject("sorgeberechtigerList", sorgeberechtigerService.listAll());
        System.out.println(" I'm in edit");
        return modelAndView;
    }

    @GetMapping("/addsorgeberechtiger")
    public String addSorgeberechtiger(@ModelAttribute(name = "sorgeberechtiger") @Valid Sorgeberechtiger sorgeberechtiger,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Sorgeberechtiger> sorgeberechtigerList = sorgeberechtigerService.listAll();
            model.addAttribute("sorgeberechtigerList", sorgeberechtigerList);
            return "sorgeberechtiger";
        }

        sorgeberechtigerService.addOptionAnredeSorgeberechtigter(sorgeberechtiger);
        model.addAttribute("optionenAnredeSorgeberechtigter", sorgeberechtiger.optionenAnredeSorgeberechtigter);

        sorgeberechtigerService.save(sorgeberechtiger);
        return "redirect:/sorgeberechtiger/";


    }

    @RequestMapping("/deletesorgeberechtiger/{sorgeberechtigerId}")
    public String deleteSorgeberechtiger(@PathVariable(name = "sorgeberechtigerId") Long sorgeberechtigerId,
                                         RedirectAttributes redirectAttributes) {
        Sorgeberechtiger sorgeberechtiger = sorgeberechtigerService.get(sorgeberechtigerId);
        if (!sorgeberechtiger.getKinderSet().isEmpty()) {
            redirectAttributes.addFlashAttribute("s_error", "delete.invalid.message");
        } else {

            try {

                sorgeberechtigerService.delete(sorgeberechtigerId);

                redirectAttributes.addFlashAttribute("success", "delete.success.message");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("s_error", "error.message.deleteSorgeberechtigter");
            }
        }
        return "redirect:/sorgeberechtiger/";
    }

    @GetMapping("/{id}")
    public Sorgeberechtiger getSorgeberechtigerById(@PathVariable Long id) {
        return sorgeberechtigerService.get(id);
    }

    @PostMapping("/updatesorgeberechtiger")
    public String updateSorgeberechtiger(@ModelAttribute(name = "sorgeberechtiger") @Valid Sorgeberechtiger
                                                 sorgeberechtiger) {
        sorgeberechtigerService.save(sorgeberechtiger);
        return "redirect:/sorgeberechtiger/";
    }

    /**
     * Display the set of Kinder for a Sorgeberechtiger in the template.
     */
    @GetMapping("/{sorgeberechtigerId}")
    public ModelAndView viewSorgeBerechtiger(@PathVariable Long sorgeberechtigerId) {
        ModelAndView modelAndView = new ModelAndView("sorgeberechtiger");
        Sorgeberechtiger sorgeberechtiger = sorgeberechtigerService.get(sorgeberechtigerId);
        Set<Kind> kinderSet = sorgeberechtiger.getKinderSet();
        modelAndView.addObject("sorgeberechtiger", sorgeberechtiger);
        modelAndView.addObject("kinderSet", kinderSet);
        return modelAndView;

    }
}


