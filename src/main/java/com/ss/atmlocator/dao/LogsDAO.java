package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.Logs;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    static final String WHERE_EXPRESSION = " WHERE a.level like :filter OR a.logger like :filter OR a.message like :filter";
    static final String COUNT_EXPRESSION = "SELECT count(a.id) FROM Logs AS a";

    @Transactional
    public List<Logs> getLogList(final int start, final int length, final String order, final String filter) {
        String queryString = "SELECT a FROM Logs AS a";
        if (!filter.isEmpty()) {
            queryString += WHERE_EXPRESSION;
        }

        TypedQuery<Logs> query = entityManager.createQuery(queryString + order, Logs.class);
        if (!filter.isEmpty()) {
            query.setParameter("filter", filter);
        }
        query.setFirstResult(start);
        query.setMaxResults(length);

        return query.getResultList();
    }

    @Transactional
    public long getLogsCount() {
        TypedQuery<Long> query = entityManager.createQuery(COUNT_EXPRESSION, Long.class);
        return query.getSingleResult();
    }

    @Transactional
    public long getLogsFilteredCount(final String filter) {
        TypedQuery<Long> query =
                entityManager.createQuery(COUNT_EXPRESSION + WHERE_EXPRESSION, Long.class);
        query.setParameter("filter", filter);
        return query.getSingleResult();
    }

}
