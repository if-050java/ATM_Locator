package com.ss.atmlocator.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olavin on 14.01.2015.
 */
public class NoticesTable extends DataTableResponse {
    public void setData(List<Logs> logList) {
        this.data = new ArrayList<>(logList.size());
        for (Logs log : logList) {
            String[] row = {
                    log.getTimeString(),
                    log.getLevel(),
                    log.getLogger(),
                    log.getMessage()
            };
            data.add(row);
        }
    }

}
