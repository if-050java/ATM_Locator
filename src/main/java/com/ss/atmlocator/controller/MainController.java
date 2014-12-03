package com.ss.atmlocator.controller;

import com.ss.atmlocator.service.AtmNetworksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by roman on 11.11.14.
 */
@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private AtmNetworksService atmNetworksService;

    @RequestMapping
    public String indexPage(ModelMap model) {

        model.addAttribute("networks", atmNetworksService.getNetworksList());
        model.addAttribute("active", "main");
        return "main";
    }
}
