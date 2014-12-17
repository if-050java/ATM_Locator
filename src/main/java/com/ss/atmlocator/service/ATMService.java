package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.BanksDAO;
import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.entity.GeoPosition;
import com.ss.atmlocator.utils.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Vasyl Danylyuk on 02.12.2014.
 */

@Service
public class ATMService {

    @Autowired
    private IAtmsDAO atmsDAO;

    /**
     * Add ATMs that is in circle of a given radius from banks with given IDs
     * or empty collection if not exist such ATMs
     *
     * @param network_id
     * @param bank_id
     * @param userPosition
     * @param radius
     * @return Collection<AtmOffice>
     */
    public Collection<AtmOffice> getATMs(Integer network_id, Integer bank_id,
                                         boolean showAtms, boolean showOffices, GeoPosition userPosition,  int radius){

        Collection<AtmOffice> result = new ArrayList<AtmOffice>();

        addBankATMsToResult(result, network_id,  bank_id, showAtms, showOffices, userPosition, radius);

        return result;
    }

    /**
     * Add ATMs from current bank that is in circle of a given radius
     *
     * @param result collection with ATMs where to add ATMs
     * @param bank_id
     * @param userPosition coordinates of circle center
     * @param radius
     */
    private void addBankATMsToResult(Collection<AtmOffice> result,Integer network_id, Integer bank_id,
                                     boolean showAtms, boolean showOffices, GeoPosition userPosition, int radius){
        for(AtmOffice atmOffice : atmsDAO.getBankAtms(network_id, bank_id, showAtms, showOffices)){
            if(GeoUtil.inRadius(userPosition, atmOffice.getGeoPosition(),radius)){
                result.add(atmOffice);
            }
        }
    }

    public AtmOffice getATMById(int id){
        return  atmsDAO.getAtmById(id);
    }
}
