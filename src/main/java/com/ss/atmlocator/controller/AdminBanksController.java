package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.AtmNetworksDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;

import com.ss.atmlocator.service.ParserService;

import com.ss.atmlocator.utils.UploadFileUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
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

        /*
        List<Bank> banks = banksDAO.getBanksList();
        modelMap.addAttribute("banks", banks);
        */
        modelMap.addAttribute("active","adminBanks");

        return "adminBanks";
    }

    /**
     *  Get list of Banks to AJAX request
     */
    @RequestMapping(value = "/banksListAjax", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Bank> banksListAjax(){
        log.debug("AdminBanksController.banksListAjax()");
        List<Bank> banks = banksDAO.getBanksList();
        return banks;
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
                           @RequestParam(value = "imageLogo", required = false) MultipartFile imageLogo,
                           @RequestParam(value = "iconAtmFile", required = false) MultipartFile iconAtmFile,
                           @RequestParam(value = "iconOfficeFile", required = false) MultipartFile iconOfficeFile,
                           HttpServletRequest request,
                           ModelMap modelMap)
    {
        log.debug("AdminBanksController.bankSave():POST");

        AtmNetwork network = networksDAO.getNetwork(network_id);
        bank.setNetwork(network);

        String newname = null;

        newname = SaveBankImage(imageLogo, "bank_logo", bank.getId(), request);
        if(newname != null) bank.setLogo(newname);

        newname = SaveBankImage(iconAtmFile, "bank_atm", bank.getId(), request);
        if(newname != null) bank.setIconAtm(newname);

        newname = SaveBankImage(iconOfficeFile, "bank_off", bank.getId(), request);
        if(newname != null) bank.setIconOffice(newname);

        Bank savedBank = banksDAO.saveBank(bank); // TODO: check for save error

        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);
        modelMap.addAttribute("bank", savedBank);
        modelMap.addAttribute("active","adminBanks");
        // TODO: show status
        // TODO: disable "delete" button in "create" mode before save

        return "adminBankEdit";
    }

    /**
     *  Save bank logo or icon image with new filename based on bank_id
     *  to avoid possibility of the same files for different banks
     */
    private String SaveBankImage(MultipartFile image, String prefix, int bank_id, HttpServletRequest request){
        String newname = null;
        try{
            if (image != null && !image.isEmpty()) {
                String filename = image.getOriginalFilename();
                String extension = filename.substring(filename.lastIndexOf('.'));
                newname =  prefix + bank_id + extension;
                UploadFileUtils.save(image, newname, request);
            }
        } catch (IOException e) {
            log.error("AdminBanksController.SaveBankImage(): IO error saving image "+prefix);
            e.printStackTrace();
        }
        return newname;
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
        //TODO: delete associated image files

        modelMap.addAttribute("status","deleted");
        modelMap.addAttribute("active","adminBanks");

        return "adminBankDeleted";
    }
    @RequestMapping(value ="/setBanksFromNbu")
    public String saveAllBank(){
        parserService.updateAllBanks();

        return "main";
    }

    /**
     *  Show Bank's ATM and office list
     */
    @RequestMapping(value = "/adminBankAtmList", method = RequestMethod.POST)
    public String adminBankAtmList(@ModelAttribute("bank") Bank bank,
                                   ModelMap modelMap) {
        log.debug("AdminBanksController.adminBankAtmList():GET");
        Bank getbank = banksDAO.getBank(bank.getId());
        modelMap.addAttribute("bank", getbank);
        modelMap.addAttribute("active","adminBanks");

        //TODO: provide list of ATMs and offices

        return "adminBankAtmList";
    }


}
