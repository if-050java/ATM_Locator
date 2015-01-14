package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.utils.TimeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;
import static java.util.Arrays.asList;

/**
 * Data access class for ATM entity
 * Created by Olavin on 10.12.2014.
 */
@Repository
public class AtmsDAO implements IAtmsDAO {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AtmsDAO.class);

    //TODO: get count from parameters file;
    private static final int COUNT_ATMS_AT_PAGE = 20;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(final Integer networkId,
                                       final Integer bankId,
                                       final boolean showAtms,
                                       final boolean showOffices) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AtmOffice> criteria = builder.createQuery(AtmOffice.class);
        Root<AtmOffice> atmOfficeRoot = criteria.from(AtmOffice.class);
        criteria.select(atmOfficeRoot);
        Predicate where = builder.conjunction();

        if (networkId != null) {
            where = builder.and(where, builder.equal(atmOfficeRoot.join("bank").join("network").get("id"), networkId));
        }
        if (bankId != null) {
            where = builder.and(where, builder.equal(atmOfficeRoot.join("bank").get("id"), bankId));
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

    /**
     * @return List<AtmOffice>
     * @param  bankId this is id of banks*/


    @Override
    @Transactional
    public long getBankAtmsCount(final int bankId) {
        TypedQuery<Long> query = entityManager
                .createQuery("SELECT count(a.id) FROM AtmOffice AS a WHERE a.bank.id=:bank_id", Long.class);
        query.setParameter("bank_id", bankId);
        return query.getSingleResult();
    }


    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(final int bankId) {
        TypedQuery<AtmOffice> query = entityManager
                .createQuery("SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id", AtmOffice.class);
        query.setParameter("bank_id", bankId);
        return query.getResultList();
    }


    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(final int bankId, final int start, final int length, final String order, final String filter) {
        String queryString = "SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id";
        if(!filter.isEmpty()) {
            queryString += " and a.address like :filter";
        }

        Query query = entityManager.createQuery(queryString + order, AtmOffice.class);
        query.setParameter("bank_id", bankId);
        if(!filter.isEmpty()) {
            query.setParameter("filter", filter);
        }
        query.setFirstResult(start);
        query.setMaxResults(length);

        return query.getResultList();
    }

    @Override
    @Transactional
    public long getBankAtmsFilteredCount(final int bankId, final String filter) {
        TypedQuery<Long>  query = entityManager.createQuery(
                "SELECT count(a.id) FROM AtmOffice AS a WHERE a.bank.id=:bank_id and a.address like :filter", Long.class);
        query.setParameter("bank_id", bankId);
        query.setParameter("filter", filter);
        return query.getSingleResult();
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
    public void persist(AtmOffice Atm) {
        entityManager.persist(Atm);
    }


    /**
     * the method updates the array offices or puts the database
     *  @param   atmExistList - List of atmOffices
     * */
    @Override
    @Transactional
    public void update(final List<AtmOffice> atmExistList) {

        log.info("[TRANSACTION] update()-- begin transaction");
        for (AtmOffice atm: atmExistList) {
            entityManager.merge(atm);
        }
        log.info("[TRANSACTION]update()-- end transaction, updated or persisted --->" + atmExistList.size() + " elements");
    }

    /**
     * the method updates the array offices or puts the database
     *  @param   atmNewList - new List<AtmOffice>
     * */
    @Override
    @Transactional
    public void persist(final List<AtmOffice> atmNewList) {
        log.info("[TRANSACTION] persist()-- begin transaction");
        for (AtmOffice atm: atmNewList) {

            atm.setLastUpdated(TimeUtil.currentTimestamp());
            entityManager.persist(atm);
            entityManager.refresh(atm);
        }
        log.info("[TRANSACTION] persist()-- end transaction, updated or persisted" + atmNewList.size() + " elements");
    }

}
