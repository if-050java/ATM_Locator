package com.ss.atmlocator.controller;

import com.ss.atmlocator.service.AtmNetworksService;
import com.ss.atmlocator.service.BanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;


/**
 * Controller for main page
 */
@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private AtmNetworksService atmNetworksService;
    @Autowired
    private BanksService banksService;

    /**
     * Returns main page
     * @param model Model map
     * @param user Logged user
     * @return
     */
    @RequestMapping
    public String indexPage(ModelMap model, Principal user) {

        model.addAttribute("networks", atmNetworksService.getNetworksList());
        model.addAttribute("banks", banksService.getBanksList());
        model.addAttribute("active", "main");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "main";
    }




}
