package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.entity.GeoPosition;
import com.ss.atmlocator.service.ATMService;
import com.ss.atmlocator.service.BanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

/**
 * Controller for working with GMap and filtering
 */

@Controller
@RequestMapping("/map")
public class MapController {
    @Autowired
    private ATMService atmService;
    @Autowired
    private BanksService banksService;

    /**
     *
     * @param networkId ID of selected ATM network
     * @param bankId ID of selected bank
     * @param userLat Current user latitude
     * @param userLng Current user longitude
     * @param radius Radius of search
     * @param showAtms Show or not atms
     * @param showOffices Show or not offices
     * @return List of found ATMs
     */
    @RequestMapping(value = "/getATMs")
    @ResponseBody
    public List<AtmOffice> getATMs(@RequestParam(required = false) Integer networkId,
                                         @RequestParam(required = false) Integer bankId,
                                         @RequestParam double userLat,
                                         @RequestParam double userLng,
                                         @RequestParam int radius,
                                         @RequestParam boolean showAtms,
                                         @RequestParam boolean showOffices
    ) {
        GeoPosition userPosition = new GeoPosition(userLng, userLat);
        List<AtmOffice> atmOffices = atmService.getATMs(networkId, bankId, showAtms, showOffices, userPosition, radius);
        return atmOffices;
    }

    /**
     * Returns all banks by network id
     * @param network_id Network id
     * @return List of banks
     */
    @RequestMapping(value = "/getBanksByNetwork")
    @ResponseBody
    public List<Bank> getBanksByNetwork(@RequestParam Integer network_id) {
        return banksService.getBanksByNetworkId(network_id);
    }
}
