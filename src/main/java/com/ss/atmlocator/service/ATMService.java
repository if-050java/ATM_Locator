package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.BanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.entity.GeoPosition;
import com.ss.atmlocator.utils.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vasyl Danylyuk on 02.12.2014.
 */


public class ATMService {

    @Autowired
    private BanksDAO banksDAO;

    /**
     * Add ATMs that is in circle of a given radius from banks with given IDs
     * or empty collection if not exist such ATMs
     *
     * @param bankIDs
     * @param userPosition
     * @param radius
     * @return
     */
    public Collection<AtmOffice> getATMs(Collection<Integer> bankIDs,GeoPosition userPosition,  int radius){

        Collection<AtmOffice> result =new ArrayList<AtmOffice>();

        for (int id : bankIDs){
            addBankATMsToResult(result, banksDAO.getBank(id), userPosition, radius);
        }

        return result;
    }

    /**
     * Add ATMs from current bank that is in circle of a given radius
     *
     * @param result collection with ATMs where to add ATMs
     * @param bank
     * @param userPosition coordinates of circle center
     * @param radius
     */
    private void addBankATMsToResult(Collection<AtmOffice> result, Bank bank, GeoPosition userPosition, int radius){
        for(AtmOffice atmOffice : bank.getAtmOfficeSet()){
            if(GeoUtil.inRadius(userPosition, atmOffice.getGeoPosition(),radius)){
                result.add(atmOffice);
            }
        }
    }
}
