package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.utils.TimeUtil;
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
import java.util.Collections;
import java.util.List;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;
import static java.util.Arrays.asList;

;

/**
 * Created by Olavin on 10.12.2014.
 */
@Repository
public class AtmsDAO implements IAtmsDAO {
    final static org.slf4j.Logger log = LoggerFactory.getLogger(AtmsDAO.class);

    //TODO: get count from parameters file;
    final static int COUNT_ATMS_AT_PAGE = 20;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(final Integer network_id, final Integer bank_id, final boolean showAtms, final boolean showOffices) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AtmOffice> criteria = builder.createQuery(AtmOffice.class);
        Root<AtmOffice> atmOfficeRoot = criteria.from(AtmOffice.class);
        criteria.select(atmOfficeRoot);
        Predicate where = builder.conjunction();

        if (network_id != null) {
            where = builder.and(where, builder.equal(atmOfficeRoot.join("bank").join("network").get("id"), network_id));
        }
        if (bank_id != null) {
            where = builder.and(where, builder.equal(atmOfficeRoot.join("bank").get("id"), bank_id));
        }
        if (showAtms && !showOffices) {
            where = builder.and(where, atmOfficeRoot.get("type").in(asList(IS_ATM, IS_ATM_OFFICE)));
        }
        if (showOffices && !showAtms) {
            where = builder.and(where, atmOfficeRoot.get("type").in(asList(IS_OFFICE, IS_ATM_OFFICE)));
        }
        if (!showAtms && !showOffices) {
            return Collections.emptyList();
        }
        return entityManager.createQuery(criteria.where(where)).getResultList();
    }

    public AtmOffice getAtmById(final int id) {
        return entityManager.find(AtmOffice.class, id);
    }

    @Override
    @Transactional
    public long getBankAtmsCount(final int bankId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT count(a.id) FROM AtmOffice AS a WHERE a.bank.id=:bank_id", Long.class);
        query.setParameter("bank_id", bankId);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public long getBankAtmsPages(final int bankId) {
        long atms = getBankAtmsCount(bankId);
        return  (atms + COUNT_ATMS_AT_PAGE - 1) / COUNT_ATMS_AT_PAGE;
    }

    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(final int bankId) {
        TypedQuery<AtmOffice> query = entityManager.createQuery("SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id", AtmOffice.class);
        query.setParameter("bank_id", bankId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(final int bankId, final int page) {
        int offset = 0;
        if (page > 0) {
            offset = page * COUNT_ATMS_AT_PAGE;
        }
        TypedQuery<AtmOffice> query = entityManager.createQuery("SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id", AtmOffice.class);
        query.setParameter("bank_id", bankId);
        query.setFirstResult(offset);
        query.setMaxResults(COUNT_ATMS_AT_PAGE);
        return query.getResultList();
    }


    @Override
    @Transactional
    public int deleteBanksAtms(final int bankId) {
        List<AtmOffice> atms = getBankAtms(bankId);
        int removed = 0;
        for (AtmOffice atm : atms) {
            entityManager.remove(atm);
            removed++;
        }
        return removed;
    }

    @Override
    public void persiste(AtmOffice Atm) {
        entityManager.persist(Atm);
    }

    @Override
    @Transactional
    public void update(List<AtmOffice> atmExistList) {
        log.info("[TRANSACTION] update() begin transaction");
        for (AtmOffice atm: atmExistList) {
//            atm.setLastUpdated(TimeUtil.currentTimestamp());
            entityManager.merge(atm);
        }
        log.info("[TRANSACTION]update() end transaction");
    }

    @Override
    @Transactional
    public void persist(List<AtmOffice> atmNewList) {
        log.info("[TRANSACTION] persist()---> begin transaction");
        for (AtmOffice atm: atmNewList) {
            atm.setLastUpdated(TimeUtil.currentTimestamp());
            entityManager.persist(atm);
            entityManager.refresh(atm);
        }
        log.info("[TRANSACTION] persist()---> end transaction");
    }

}
