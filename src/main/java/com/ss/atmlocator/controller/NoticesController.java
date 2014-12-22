package com.ss.atmlocator.controller;

import com.ss.atmlocator.service.LogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Olavin on 22.12.2014.
 */
@Controller
public class NoticesController {
    private final static Logger logger = LoggerFactory.getLogger(NoticesController.class);

    @Autowired
    private LogsService logsService;

    /**
     *  Show page with list of Banks and ATM Networks
     */
    @RequestMapping(value = "/adminNotices")
    public String banksList(ModelMap modelMap) {
        logger.debug("GET: notices page");
        modelMap.addAttribute("notices", logsService.getLogList());
        modelMap.addAttribute("active","adminNotices");
        return "adminNotices";
    }

}
