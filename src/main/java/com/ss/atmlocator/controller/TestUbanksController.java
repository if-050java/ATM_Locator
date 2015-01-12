package com.ss.atmlocator.controller;

import com.ss.atmlocator.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by student on 12/9/2014.
 */
@Controller
@RequestMapping(value = "/testU")
public class TestUbanksController {

    @Autowired
    private ParserService parserService;


    @RequestMapping
    public String updateAll() {

        parserService.updateUbanks();

        return "adminBanks";
    }


}

