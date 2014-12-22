package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.Logs;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by d18-antoshkiv on 22.12.2014.
 */
@Repository
public class LogsDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Logs> getLogList() {
        List<Logs> notices;
        TypedQuery<Logs> query = entityManager.createQuery("SELECT a FROM Logs AS a ORDER BY a.dated", Logs.class);
        notices = query.getResultList();
        return notices;
    }

    @Transactional
    public List<Logs> getLogList(Timestamp newTime) {
        List<Logs> logList;
        TypedQuery<Logs> query = entityManager.createQuery("SELECT a FROM Notice AS a WHERE a.time>:newtime ORDER BY a.time", Logs.class);
        query.setParameter("newtime", newTime);
        logList = query.getResultList();
        return logList;
    }

}
