package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.AtmNetworksDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.service.ParserService;
import com.ss.atmlocator.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olavin on 21.11.2014.
 */
@Controller
public class AdminBanksController {
    private final org.apache.log4j.Logger log = Logger.getLogger(AdminBanksController.class);

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
        log.debug("GET: banks page");
        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);

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
        log.debug("GET: list of banks");
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
        log.debug("GET: bank #"+bank_id);
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
        log.debug("GET: create new bank");
        List<AtmNetwork> networks = networksDAO.getNetworksList();
        modelMap.addAttribute("networks", networks);
        Bank bank = banksDAO.newBank();
        modelMap.addAttribute("bank", bank);
        modelMap.addAttribute("active","adminBanks");

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
                           HttpServletRequest request,
                           ModelMap modelMap)
    {
        log.debug("AJAX: save bank "+bank.getName()+" #"+bank.getId());
        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();

        AtmNetwork network = networksDAO.getNetwork(network_id);
        bank.setNetwork(network);

        String newname = null;
        newname = UploadFileUtils.saveBankImage(imageLogo, "bank_logo", bank.getId(), request);
        if(newname != null) bank.setLogo(newname);
        newname = UploadFileUtils.saveBankImage(iconAtmFile, "bank_atm", bank.getId(), request);
        if(newname != null) bank.setIconAtm(newname);
        newname = UploadFileUtils.saveBankImage(iconOfficeFile, "bank_off", bank.getId(), request);
        if(newname != null) bank.setIconOffice(newname);

        Bank savedBank = banksDAO.saveBank(bank); // TODO: check for save error
        if (savedBank != null && savedBank.getId() > 0){
            response.setStatus(Constants.SUCCESS);
        } else {
            response.setStatus(Constants.ERROR);
        }

        response.setErrorMessageList(errorMesages);
        return response;
    }

    /**
     *  Delete Bank by Ajax request
     */
    @RequestMapping(value = "/adminBankDeleteAjax", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse bankDeleteAjax(@RequestParam int bank_id, HttpServletRequest request) {
        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();

        log.debug("AJAX: delete bank #"+bank_id);

        if (banksDAO.deleteBank(bank_id)){
            response.setStatus(Constants.SUCCESS);
        } else {
            response.setStatus(Constants.ERROR);
        }
        response.setErrorMessageList(errorMesages);
        return response;
    }


    /**
     * update all banks (name and mfo) from NBU site
     * */
    @RequestMapping(value ="/updateBanksFromNbu")
    public String saveAllBank(){
        parserService.updateAllBanks();

        return "adminBanks";
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
