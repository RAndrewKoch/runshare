package com.akproject.runshare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("")
    private String displayIndexPage(Model model){
        model.addAttribute("title", "Home");
        return "index";
    }
}
