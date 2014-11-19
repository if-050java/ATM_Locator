package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        return "login";
    }
}
