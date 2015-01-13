package com.ss.atmlocator.entity;

import com.ss.atmlocator.utils.GeoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olavin on 12.01.2015.
 */
public class AtmOfficeTable {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<String[]> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<AtmOffice> atmOfficeList) {
        this.data = new ArrayList<>(atmOfficeList.size());
        for(AtmOffice atm : atmOfficeList){
            String[] row = {
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
