package com.jugendhilfe.jugendhilfe.controller;



import com.jugendhilfe.jugendhilfe.service.MitarbeiterService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@Controller
public class mainController {

    @Autowired
    private MitarbeiterService mitarbeiterService;
 ;

    @GetMapping("/")
    public String viewHomePage(Model model) {
    //    model.addAttribute("listMitarbeiter", mitarbeiterService.listAll());
        return "index";
    }

//    @GetMapping("/home")
//    public String home(Model model) {
//        model.addAttribute("kursnamen", kursService.getAllkursname());
//        return "home";
//    }

   // @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/public")
    public String mitarbeiterlistePublic(Model model) {
        return "redirect:/mitarbeiter/mitarbeiters";
    }



   /* @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index";
    }*/

}

