package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.AtmNetworksDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.service.ParserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Olavin on 21.11.2014.
 */
@Controller
public class AdminBanksController {
    private static org.apache.log4j.Logger log = Logger.getLogger(AdminBanksController.class);

    @Autowired
    IBanksDAO banksDAO;
    @Autowired
    AtmNetworksDAO networksDAO;
    @Autowired
    ParserService parserService;

    /**
     *  Show page with list of Banks and ATM Networks
     */
    @RequestMapping(value = "/adminBanks")
    public String banksList(ModelMap modelMap) {
        log.debug("AdminBanksController.banksList()");
        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);

        List<Bank> banks = banksDAO.getBanksList();
        modelMap.addAttribute("banks", banks);
        modelMap.addAttribute("active","adminBanks");

        return "adminBanks";
    }

    /**
     *  Show Bank information page for edit
     */
    @RequestMapping(value = "/adminBankEdit", method = RequestMethod.GET)
    public String bankEdit(/*@ModelAttribute("bank") Bank bank,*/
                           @RequestParam(value = "bank_id", required = true) int bank_id,
                           ModelMap modelMap) {
        log.debug("AdminBanksController.bankEdit():GET");
        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);
        Bank bank = banksDAO.getBank(bank_id);
        modelMap.addAttribute("bank", bank);
        modelMap.addAttribute("active","adminBanks");

        return "adminBankEdit";
    }

    /**
     *  Show create Bank page for edit
     */
    @RequestMapping(value = "/adminBankCreateNew", method = RequestMethod.GET)
    public String bankCreateNew(ModelMap modelMap) {
        log.debug("AdminBanksController.bankCreateNew():GET");
        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);
        Bank bank = banksDAO.newBank();
        modelMap.addAttribute("bank", bank);
        modelMap.addAttribute("active","adminBanks");

        return "adminBankEdit";
    }

    /**
     *  Update Bank information or create new entry if ID=0
     */
    @RequestMapping(value = "/adminBankEdit", method = RequestMethod.POST)
    public String bankSave(@ModelAttribute("bank") Bank bank,
                           @RequestParam(value = "network_id", required = true) int network_id,
                           ModelMap modelMap)
    {
        log.debug("AdminBanksController.bankSave():POST");

        AtmNetwork network = networksDAO.getNetwork(network_id);
        bank.setNetwork(network);
        Bank savedBank = banksDAO.saveBank(bank); // TODO: check for save error

        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);
        //modelMap.addAttribute("bank", savedBank);

        // TODO: show status
        // TODO: disable "delete" button in "create" mode before save
        modelMap.addAttribute("bank", savedBank);
        //modelMap.addAttribute("bank_id", savedBank.getId());
        modelMap.addAttribute("active","adminBanks");

        return "adminBankEdit";
    }


    /**
     *  Delete Bank
     */
    @RequestMapping(value = "/adminBankDelete", method = RequestMethod.POST)
    public String bankDelete(@ModelAttribute("bank") Bank bank,
                           /*@RequestParam(value = "network_id", required = true) int network_id,*/
                           ModelMap modelMap)
    {
        log.debug("AdminBanksController.bankDelete():POST");

        modelMap.addAttribute("bank_name", bank.getName());

        banksDAO.deleteBank(bank.getId()); // TODO: check for error

        modelMap.addAttribute("status","deleted");
        modelMap.addAttribute("active","adminBanks");

        return "adminBankDeleted";
    }
    @RequestMapping(value ="/setBanksFromNbu")
    public String saveAllBank(){
        parserService.updateAllBanks();

        return "main";
    }

}
