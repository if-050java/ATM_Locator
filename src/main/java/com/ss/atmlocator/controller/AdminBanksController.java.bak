package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.AtmNetworksDAO;
import com.ss.atmlocator.dao.BanksDAO;
import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String bankEdit(
                           @ModelAttribute("bank") Bank bank,
                           @RequestParam(value = "bank_id", required = true) int bank_id,
                           ModelMap modelMap) {
        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);
        bank = banksDAO.getBank(bank_id);
        modelMap.addAttribute("bank", bank);
        modelMap.addAttribute("active","adminBanks");

        return "adminBankEdit";
    }

    @RequestMapping(value = "/adminBankEdit", method = RequestMethod.POST)
    public String bankSave(
                           Bank bank,
                           @RequestParam(value = "network_id", required = true) int network_id,
                           ModelMap modelMap)
    {
        AtmNetwork network = networksDAO.getNetwork(network_id);
        bank.setNetwork(network);
        Bank savedBank = banksDAO.saveBank(bank);

        modelMap.addAttribute("bank", savedBank);
        modelMap.addAttribute("bank_id", savedBank.getId());
        modelMap.addAttribute("active","adminBanks");

        return "adminBankEdit";
    }


}
