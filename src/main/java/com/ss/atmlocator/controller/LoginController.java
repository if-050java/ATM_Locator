package com.ss.atmlocator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by roman on 11.11.14.
 */
@Controller
public class LoginController {


    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) String error, ModelMap model) {
        if (error != null) {
            model.addAttribute("error", "Login error!");
        }
        model.addAttribute("active","login");
        return "login";
    }

}
