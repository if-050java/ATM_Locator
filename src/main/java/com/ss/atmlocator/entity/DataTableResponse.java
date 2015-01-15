package com.ss.atmlocator.entity;

import java.util.List;

/**
 * Created by Olavin on 14.01.2015.
 */
public abstract class DataTableResponse {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    protected List<String[]> data;

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
}
