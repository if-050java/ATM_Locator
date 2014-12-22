package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.Notice;
import com.ss.atmlocator.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.sql.Timestamp;

/**
 * Created by Olavin on 21.12.2014.
 */
@Repository
public class NoticeDAO {
    private final static Logger logger = LoggerFactory.getLogger(NoticeDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Notice> getNoticesList() {
        List<Notice> notices;
        TypedQuery<Notice> query = entityManager.createQuery("SELECT a FROM Notice AS a ORDER BY a.time", Notice.class);
        notices = query.getResultList();
        return notices;
    }

    @Transactional
    public List<Notice> getNoticesList(Timestamp newTime) {
        List<Notice> notices;
        TypedQuery<Notice> query = entityManager.createQuery("SELECT a FROM Notice AS a WHERE a.time>:newtime ORDER BY a.time", Notice.class);
        query.setParameter("newtime", newTime);
        notices = query.getResultList();
        return notices;
/*
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notice> criteria = builder.createQuery(Notice.class);
        Root<Notice> noticeRoot = criteria.from(Notice.class);
        //criteria.select(noticeRoot);
        criteria.where(builder.greaterThan(noticeRoot.get("time"),builder.parameter(Timestamp.class,"newtime")));
        Predicate where = builder.greaterThan();
*/

    }

    @Transactional
    public void saveNotice(Notice notice) {
        notice.setTime(TimeUtil.currentTimestamp());
        Notice saved = (Notice) entityManager.merge(notice);
        logger.debug("Saved Notice #" + saved.getId());
    }


}
