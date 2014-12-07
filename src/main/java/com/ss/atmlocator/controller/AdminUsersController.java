package com.ss.atmlocator.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("adminUsers")
public class AdminUsersController {

    @RequestMapping(method = RequestMethod.GET)
    public String adminUsers(ModelMap model) {
        model.addAttribute("active", "adminUsers");
        return "adminUsers";
    }
}
