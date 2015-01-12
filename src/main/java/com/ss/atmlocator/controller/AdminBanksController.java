package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.service.ATMService;
import com.ss.atmlocator.service.AtmNetworksService;
import com.ss.atmlocator.service.BanksService;
import com.ss.atmlocator.service.ParserService;
import com.ss.atmlocator.utils.OutResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * Created by Olavin on 21.11.2014.
 */
@Controller
public class AdminBanksController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdminBanksController.class);

    @Autowired
    private BanksService banksService;
    @Autowired
    private ParserService parserService;
    @Autowired
    private AtmNetworksService atmNetworksService;

    /**
     *  Show page with list of Banks and ATM Networks.
     */
    @RequestMapping(value = "/adminBanks")
    public String banksList(ModelMap modelMap, Principal user) {
        LOGGER.debug("GET: banks page");
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
        LOGGER.debug("GET: list of ATM networks");
        return atmNetworksService.getNetworksList();
    }

    /**
     *  Get list of Banks to AJAX request.
     */
    @RequestMapping(value = "/banksListAjax", method = RequestMethod.GET)
    @ResponseBody
    public List<Bank> banksListAjax() {
        LOGGER.debug("GET: list of banks");
        return banksService.getBanksList();
    }

    /**
     *  Show Bank information page for edit.
     */
    @RequestMapping(value = "/adminBankEdit", method = RequestMethod.GET)
    public String bankEdit(@RequestParam(value = "bank_id", required = true) int bankId,
                           ModelMap modelMap, Principal user) {
        LOGGER.debug("GET: bank #" + bankId);
        modelMap.addAttribute("networks", atmNetworksService.getNetworksList());
        modelMap.addAttribute("bank", banksService.getBank(bankId));
        modelMap.addAttribute("atm_count", banksService.getBankAtmsCount(bankId));
        modelMap.addAttribute("active", "adminBanks");
        modelMap.addAttribute("userName", user.getName());
        return "adminBankEdit";
    }

    /**
     *  Show create Bank page for edit
     */
    @RequestMapping(value = "/adminBankCreateNew", method = RequestMethod.GET)
    public String bankCreateNew(final ModelMap modelMap, Principal user) {
        LOGGER.debug("GET: create new bank");
        modelMap.addAttribute("networks", atmNetworksService.getNetworksList());
        Bank bank = banksService.newBank();
        modelMap.addAttribute("bank", bank);
        modelMap.addAttribute("atm_count", 0);
        modelMap.addAttribute("active", "adminBanks");
        modelMap.addAttribute("userName", user.getName());

        return "adminBankEdit";
    }

    /**
     *  Update Bank information or create new entry if ID=0 by AJAX POST
     */
    @RequestMapping(value = "/adminBankSaveAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse bankSaveAjax(@ModelAttribute("bank") Bank bank,
                           @RequestParam(value = "network_id", required = true) final int networkId,
                           @RequestParam(value = "imageLogo", required = false) final MultipartFile imageLogo,
                           @RequestParam(value = "iconAtmFile", required = false) final MultipartFile iconAtmFile,
                           @RequestParam(value = "iconOfficeFile", required = false) final MultipartFile iconOfficeFile,
                           final HttpServletRequest request)
    {
        if (bank.getId() == 0) {
            LOGGER.debug("AJAX: save new bank " + bank.getName());
        } else {
            LOGGER.debug(String.format("AJAX: save bank %s #%d", bank.getName(), bank.getId()));
        }

        bank.setNetwork(atmNetworksService.getNetwork(networkId));

        return banksService.saveBank(bank, imageLogo, iconAtmFile, iconOfficeFile, request);

    }

    /**
     *  Update ATM Network information or create new entry if ID=0 by AJAX POST.
     */
    @RequestMapping(value = "/adminNetworkSaveAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse networkSaveAjax(@ModelAttribute("network") AtmNetwork network, final HttpServletRequest request) {
        LOGGER.debug("AJAX request: save network " + network.getName() + " #" + network.getId());
        return atmNetworksService.saveNetwork(network);
    }


    /**
     *  Delete Bank by Ajax request.
     */
    @RequestMapping(value = "/adminBankDeleteAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse bankDeleteAjax(@RequestParam final int id, final HttpServletRequest request) {
        LOGGER.debug("AJAX: delete bank #" + id);
        return banksService.deleteBank(id, request);
    }

    /**
     *  Delete ATM Network by Ajax request.
     */
    @RequestMapping(value = "/adminNetworkDeleteAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse networkDeleteAjax(@RequestParam final int id) {
        LOGGER.debug("AJAX: delete ATM network #" + id);
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
    @RequestMapping(value = "/adminBankAtmList", method = RequestMethod.GET)
    public String adminBankAtmList(@RequestParam(value = "id", required = true) final int bankId,
                                   @RequestParam(value = "page", required = false) Integer page, // first page #1
                                   final ModelMap modelMap, final Principal user) {
        LOGGER.debug(String.format("GET request: ATMs list, Bank #%d, page %d", bankId, page));

        int pageNum = (page == null) ? 0 : page - 1; // first page will be 0
        long pageCount = banksService.getBankAtmsPages(bankId);

        modelMap.addAttribute("bank", banksService.getBank(bankId));
        modelMap.addAttribute("atms", banksService.getBankAtms(bankId, pageNum));
        modelMap.addAttribute("page", pageNum + 1);
        modelMap.addAttribute("page_count", pageCount);
        modelMap.addAttribute("active", "adminBanks");
        modelMap.addAttribute("userName", user.getName());

        LOGGER.debug(String.format("GET response: ATMs list, Bank #%d, page %d of %d", bankId, pageNum + 1, pageCount));
        return "adminBankAtmList";
    }

    @RequestMapping(value = "/adminBankAtmList2", method = RequestMethod.GET)
    public String adminBankAtmList2(@RequestParam(value = "id", required = true) final int bankId,
                                   final ModelMap modelMap, final Principal user) {
        LOGGER.debug(String.format("GET request: ATMs list, Bank #%d", bankId));

        modelMap.addAttribute("bank", banksService.getBank(bankId));
        modelMap.addAttribute("active", "adminBanks");
        modelMap.addAttribute("userName", user.getName());

        LOGGER.debug(String.format("GET response: ATMs list 2, Bank #%d", bankId));
        return "adminBankAtmList2";
    }


    @RequestMapping(value = "/getBankATMs")
    @ResponseBody
    public List<AtmOffice> getBankATMs(@RequestParam(required = false) Integer bankId,
                                       @RequestParam boolean showAtms,
                                       @RequestParam boolean showOffices
    ) {
        List<AtmOffice> atmOffices = banksService.getBankAtms(bankId, 0);
        return atmOffices;
    }


}
