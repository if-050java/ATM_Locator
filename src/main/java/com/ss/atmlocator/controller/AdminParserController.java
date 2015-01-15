package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmParser;
import com.ss.atmlocator.entity.Bank;

import com.ss.atmlocator.service.ParserParamService;
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
 * Created by maks on 02.12.2014.
 */
@Controller
@RequestMapping
public class AdminParserController {
    final Logger log = Logger.getLogger(AdminParserController.class.getName());
    @Autowired
    IBanksDAO banksDAO;
    @Autowired
    ParserService parserService;
    @Autowired
    ParserParamService paramService;

    @RequestMapping(value = "/saveChanges", method = RequestMethod.GET)
    public String saveChanges(@RequestParam(value = "bankId") String bankId,
                              @RequestParam(value="parametrValue") String newValue,
                              @RequestParam(value="parserParamId") String parserParamId,
                              @RequestParam(value="state", required = false) Integer state,
                              @RequestParam(value = "parserId") int parserId,
                              ModelMap modelMap
                              ) {


        Bank bank = banksDAO.getBank(Integer.valueOf(bankId));
        paramService.saveChanges(newValue, parserParamId);
        if (state == null) {
            parserService.changeState(parserId, 1);
            log.trace("parser state changed (disable)");
        } else {
            parserService.changeState(parserId, state);
            log.trace("parser state changed (enable)");
        }

        modelMap.addAttribute("bank_id", bank.getId());


        return "redirect:/adminBankEdit";
    }


}
