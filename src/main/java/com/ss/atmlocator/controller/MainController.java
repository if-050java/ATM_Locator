package com.ss.atmlocator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by roman on 11.11.14.
 */
@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping
    public String indexPage(ModelMap model){
        model.addAttribute("active","main");
        return "main";
    }
}
