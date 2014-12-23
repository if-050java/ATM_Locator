package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.LogsDAO;
import com.ss.atmlocator.entity.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by d18-antoshkiv on 22.12.2014.
 */
@Service
public class LogsService {
    @Autowired
    private LogsDAO logsDAO;

    public List<Logs> getLogList(Timestamp newTime) {
        return logsDAO.getLogList(newTime);
    }

    public List<Logs> getLogList() {
        return logsDAO.getLogList();
    }

}
