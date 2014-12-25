package com.ss.atmlocator.dao;


import com.ss.atmlocator.entity.JobLog;
import com.ss.atmlocator.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("jobLogDAO")
public class JobLogDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void saveLog(JobLog log) {
        entityManager.persist(log);

    }

    public List<JobLog> getJobLogs(String jobName){
        TypedQuery<JobLog> query = entityManager.createQuery("SELECT u FROM JobLog AS u WHERE u.jobName=:jobName", JobLog.class);
        query.setParameter("jobName", jobName);
        return query.getResultList();
    }


}
