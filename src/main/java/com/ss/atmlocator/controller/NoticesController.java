package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.*;
import com.ss.atmlocator.service.LogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by Olavin on 22.12.2014.
 */
@Controller
public class NoticesController {
    private static final Logger logger = LoggerFactory.getLogger(NoticesController.class);

    @Autowired
    private LogsService logsService;

    /**
     *  Show page with list of logged event messages
     */
    @RequestMapping(value = "/adminNotices")
    public String banksList(ModelMap modelMap, Principal user) {
        logger.debug("GET: notices page");
        modelMap.addAttribute("active", "adminNotices");
        modelMap.addAttribute("userName", user.getName());
        return "adminNotices";
    }

    /**
     *  Returns list object of logged event messages on AJAX request
     */
    @RequestMapping(value = "/getNotices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DataTableResponse getNotices(@ModelAttribute DataTableCriteria criteria) {
        if (logger.isDebugEnabled()) {
            int start = criteria.getStart();
            int length = criteria.getLength();
            String orderColumn = criteria.getOrder().get(0).get(DataTableCriteria.OrderCriteria.column);
            String orderDirect = criteria.getOrder().get(0).get(DataTableCriteria.OrderCriteria.dir);
            String filter = criteria.getSearch().get(DataTableCriteria.SearchCriteria.value);
            logger.debug(String.format("POST: Notices list, offset %d, count %d, order %s %s, filter {%s}",
                    start, length, orderColumn, orderDirect, filter));
        }

        return logsService.getLogList(criteria);
    }

}
