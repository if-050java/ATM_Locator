package com.ss.atmlocator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by roman on 11.11.14.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping
    public String adminPage(){
        return "admin";
    }
}
