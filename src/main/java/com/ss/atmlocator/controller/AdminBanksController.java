package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.AtmNetworksDAO;
import com.ss.atmlocator.dao.BanksDAO;
import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

/**
 * Created by Olavin on 21.11.2014.
 */
@Controller
public class AdminBanksController {

    @Autowired
    BanksDAO banksDAO;
    @Autowired
    AtmNetworksDAO networksDAO;

    @RequestMapping(value = "/adminBanks")
    public String banksList(ModelMap modelMap) {

        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);

        List<Bank> banks = banksDAO.getBanksList();
        modelMap.addAttribute("banks", banks);
        modelMap.addAttribute("active","adminBanks");

        return "adminBanks";
    }

    @RequestMapping(value = "/adminBankEdit", method = RequestMethod.GET)
    public String bankEdit(ModelMap modelMap, @RequestParam(value = "bank_id", required = true) int user_id) {
        Bank bank = banksDAO.getBank(user_id);
        modelMap.addAttribute("bank", bank);
        modelMap.addAttribute("active","adminBanks");

        return "adminBankEdit";
    }

}
