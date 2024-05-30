package org.example.restaurants.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("activePage", "home");

        return "home";
    }


}
