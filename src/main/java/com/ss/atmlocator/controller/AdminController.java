package com.ss.atmlocator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by roman on 11.11.14.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping
    public String adminPage(ModelMap model){
        model.addAttribute("active","admin");
        return "admin";
    }

    @RequestMapping(value = "/users")
    public String adminUsers(ModelMap model) {
        model.addAttribute("active", "adminUsers");
        return "adminUsers";
    }
}
