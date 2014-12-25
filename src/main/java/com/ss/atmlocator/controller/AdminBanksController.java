package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.service.AtmNetworksService;
import com.ss.atmlocator.service.BanksService;
import com.ss.atmlocator.service.ParserService;
import com.ss.atmlocator.utils.OutResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by Olavin on 21.11.2014.
 */
@Controller
public final class AdminBanksController {
    private final org.apache.log4j.Logger log = Logger.getLogger(AdminBanksController.class);

    @Autowired
    private IBanksDAO banksDAO;
    @Autowired
    private BanksService banksService;
    //@Autowired
    //AtmNetworksDAO networksDAO;
    @Autowired
    private ParserService parserService;
    @Autowired
    private AtmNetworksService atmNetworksService;


    /**
     *  Show page with list of Banks and ATM Networks.
     */
    @RequestMapping(value = "/adminBanks")
    public String banksList(ModelMap modelMap, Principal user) {
        log.debug("GET: banks page");
        modelMap.addAttribute("networks", atmNetworksService.getNetworksList());
        modelMap.addAttribute("active","adminBanks");
        modelMap.addAttribute("userName", user.getName());
        return "adminBanks";
    }

    /**
     *  Get list of ATM networks to AJAX request.
     */
    @RequestMapping(value = "/networksListAjax", method = RequestMethod.GET)
    @ResponseBody
    public List<AtmNetwork> networksListAjax() {
        log.debug("GET: list of ATM networks");
        return atmNetworksService.getNetworksList();
    }

    /**
     *  Get list of Banks to AJAX request.
     */
    @RequestMapping(value = "/banksListAjax", method = RequestMethod.GET)
    @ResponseBody
    public List<Bank> banksListAjax() {
        log.debug("GET: list of banks");
        return banksService.getBanksList();
    }


    /**
     *  Show Bank information page for edit.
     */
    @RequestMapping(value = "/adminBankEdit", method = RequestMethod.GET)
    public String bankEdit(/*@ModelAttribute("bank") Bank bank,*/
                           @RequestParam(value = "bank_id", required = true) int bank_id,
                           ModelMap modelMap, Principal user) {
        log.debug("GET: bank #"+bank_id);
        modelMap.addAttribute("networks", atmNetworksService.getNetworksList());
        modelMap.addAttribute("bank", banksService.getBank(bank_id));
        modelMap.addAttribute("active","adminBanks");
        modelMap.addAttribute("userName", user.getName());
        return "adminBankEdit";
    }

    /**
     *  Show create Bank page for edit
     */
    @RequestMapping(value = "/adminBankCreateNew", method = RequestMethod.GET)
    public String bankCreateNew(final ModelMap modelMap) {
        log.debug("GET: create new bank");
        modelMap.addAttribute("networks", atmNetworksService.getNetworksList());
        Bank bank = banksDAO.newBank();
        modelMap.addAttribute("bank", bank);
        modelMap.addAttribute("active", "adminBanks");

        return "adminBankEdit";
    }

    /**
     *  Update Bank information or create new entry if ID=0 by AJAX POST
     */
    @RequestMapping(value = "/adminBankSaveAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse bankSaveAjax(@ModelAttribute("bank") Bank bank,
                           @RequestParam(value = "network_id", required = true) int network_id,
                           @RequestParam(value = "imageLogo", required = false) MultipartFile imageLogo,
                           @RequestParam(value = "iconAtmFile", required = false) MultipartFile iconAtmFile,
                           @RequestParam(value = "iconOfficeFile", required = false) MultipartFile iconOfficeFile,
                           final HttpServletRequest request)
    {
        log.debug("AJAX: save bank " + bank.getName() + " #" + bank.getId());

        bank.setNetwork(atmNetworksService.getNetwork(network_id));

        return banksService.saveBank(bank, imageLogo, iconAtmFile, iconOfficeFile, request);

    }

    /**
     *  Update ATM Network information or create new entry if ID=0 by AJAX POST.
     */
    @RequestMapping(value = "/adminNetworkSaveAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse networkSaveAjax(@ModelAttribute("network") AtmNetwork network, HttpServletRequest request) {
        log.debug("AJAX request: save network " + network.getName() + " #" + network.getId());
        return atmNetworksService.saveNetwork(network);
    }


    /**
     *  Delete Bank by Ajax request.
     */
    @RequestMapping(value = "/adminBankDeleteAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse bankDeleteAjax(@RequestParam final int id) {
        return banksService.deleteBank(id);
    }

    /**
     *  Delete ATM Network by Ajax request.
     */
    @RequestMapping(value = "/adminNetworkDeleteAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse networkDeleteAjax(@RequestParam final int id) {
        return atmNetworksService.deleteNetwork(id);
    }


    /**
     * update all banks (name and mfo) from NBU site
     * */
    @RequestMapping(value = "/updateBanksFromNbu")
    public String saveAllBank() {
        parserService.updateAllBanks();
        return "adminBanks";
    }

    /**
     *  Show Bank's ATM and office list.
     */
    @RequestMapping(value = "/adminBankAtmList", method = RequestMethod.POST)
    public String adminBankAtmList(@ModelAttribute("bank") final Bank bank,
                                   final ModelMap modelMap, final Principal user) {
        log.debug("AdminBanksController.adminBankAtmList():GET");

        modelMap.addAttribute("bank", banksService.getBank(bank.getId()));
        modelMap.addAttribute("atms",banksService.getBankAtms(bank.getId()));
        modelMap.addAttribute("active","adminBanks");
        modelMap.addAttribute("userName", user.getName());

        return "adminBankAtmList";
    }

}
