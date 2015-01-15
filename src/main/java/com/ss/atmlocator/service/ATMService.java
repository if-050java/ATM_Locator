package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.GeoPosition;
import com.ss.atmlocator.utils.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service for working with ATMs
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
     * @return List<AtmOffice>
     */
    public List<AtmOffice> getATMs(Integer network_id, Integer bank_id,
                                         boolean showAtms, boolean showOffices, GeoPosition userPosition, int radius) {

        List<AtmOffice> result = new ArrayList<AtmOffice>();

        addBankATMsToResult(result, network_id, bank_id, showAtms, showOffices, userPosition, radius);

        return result;
    }

    /**
     * Add ATMs from current bank that is in circle of a given radius
     *
     * @param result       collection with ATMs where to add ATMs
     * @param bank_id
     * @param userPosition coordinates of circle center
     * @param radius
     */
    private void addBankATMsToResult(Collection<AtmOffice> result, Integer network_id, Integer bank_id,
                                     boolean showAtms, boolean showOffices, GeoPosition userPosition, int radius) {
        for (AtmOffice atmOffice : atmsDAO.getBankAtms(network_id, bank_id, showAtms, showOffices)) {
            if (GeoUtil.inRadius(userPosition, atmOffice.getGeoPosition(), radius)) {
                result.add(atmOffice);
            }
        }
    }

    /**
     * Returns ATM with current id
     * @param id Id of ATM office
     * @return
     */
    public AtmOffice getAtmById(int id) {
        return atmsDAO.getAtmById(id);
    }
}
