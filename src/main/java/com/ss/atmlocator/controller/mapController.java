package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.BanksDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.entity.GeoPosition;
import com.ss.atmlocator.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vasyl Danylyuk on 29.11.2014.
 */

@Controller
public class mapController {
    @Autowired
    ATMService atmService;


    @RequestMapping(value = "/getATMs")
    @ResponseBody
    public Collection<AtmOffice> getATMs(@RequestParam int id,
                                         @RequestParam double userLat,
                                         @RequestParam double userLng,
                                         @RequestParam int radius
                                         ){
        GeoPosition userPosition = new GeoPosition(userLng, userLat);
        List<Integer> banks = new ArrayList<Integer>();
        banks.add(id);
        return atmService.getATMs(banks, userPosition, radius);
    }
}
