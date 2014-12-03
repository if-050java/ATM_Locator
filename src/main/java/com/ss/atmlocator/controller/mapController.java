package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.BanksDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.service.BanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Vasyl Danylyuk on 29.11.2014.
 */

@Controller
@RequestMapping("/map")
public class mapController {
    @Autowired
    IBanksDAO banksDAO;

    @Autowired
    private BanksService banksService;

    @RequestMapping(value = "/getBank")
    @ResponseBody
    public Bank getBank(@RequestParam int id) {
        return banksDAO.getBank(id);
    }


    @RequestMapping(value = "/getBanksByNetwork")
    @ResponseBody
    public List<Bank> getBanksByNetwork(@RequestParam Integer network_id) {
        return banksService.getBanksByNetworkId(network_id);
    }
}
