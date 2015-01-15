package com.ss.atmlocator.entity;

import com.ss.atmlocator.utils.GeoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olavin on 12.01.2015.
 */
public class AtmOfficeTable extends DataTableResponse {

    public void setData(List<AtmOffice> atmOfficeList) {
        this.data = new ArrayList<>(atmOfficeList.size());
        for(AtmOffice atm : atmOfficeList){
            String[] row = {
                    String.valueOf(atm.getId()),
                    String.valueOf(atm.getId()),
                    atm.getTypeString(),
                    atm.getStateString(),
                    atm.getAddress(),
                    GeoUtil.geoLocationString(atm.getGeoPosition()),
                    atm.getTimeString()
            };
            data.add(row);
        }
    }

}
