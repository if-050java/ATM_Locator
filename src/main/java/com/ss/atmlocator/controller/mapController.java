package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.BanksDAO;
import com.ss.atmlocator.entity.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Vasyl Danylyuk on 29.11.2014.
 */

@Controller
public class mapController {
    @Autowired
    BanksDAO banksDAO;

    @RequestMapping(value = "/getBank")
    @ResponseBody
    public Bank getBank(@RequestParam int id){
        return banksDAO.getBank(id);
    }
}
