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

/**
 * Data access class for ATM entity
 */
@Repository
public class AtmsDAO implements IAtmsDAO {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AtmsDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns list of bank atms by filter params
     * @param networkId ID of selected ATM network
     * @param bankId ID of selected bank
     * @param showAtms Show or not atms
     * @param showOffices Show or not offices
     * @return List of found ATMs
     */
    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(Integer networkId,
                                       Integer bankId,
                                       boolean showAtms,
                                       boolean showOffices) {
        if (!showAtms && !showOffices) {
            return Collections.emptyList();
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AtmOffice> criteria = builder.createQuery(AtmOffice.class);
        Root<AtmOffice> atmOfficeRoot = criteria.from(AtmOffice.class);
        criteria.select(atmOfficeRoot);
        Predicate predicate = builder.conjunction();
        predicate = setPredicate(networkId, bankId, showAtms, showOffices, builder, atmOfficeRoot, predicate);
        return entityManager.createQuery(criteria.where(predicate)).getResultList();
    }

    private Predicate setPredicate(Integer networkId, Integer bankId, boolean showAtms, boolean showOffices, CriteriaBuilder builder, Root<AtmOffice> atmOfficeRoot, Predicate predicate) {
        if (networkId != null) {
            predicate = builder.and(predicate, builder.equal(atmOfficeRoot.join("bank").join("network").get("id"), networkId));
        }
        if (bankId != null) {
            predicate = builder.and(predicate, builder.equal(atmOfficeRoot.join("bank").get("id"), bankId));
        }
        if (showAtms && !showOffices) {
            predicate = builder.and(predicate, atmOfficeRoot.get("type").in(asList(IS_ATM, IS_ATM_OFFICE)));
        }
        if  (showOffices && !showAtms) {
            predicate = builder.and(predicate, atmOfficeRoot.get("type").in(asList(IS_OFFICE, IS_ATM_OFFICE)));
        }
        return predicate;
    }

    public AtmOffice getAtmById(final int id) {
        return entityManager.find(AtmOffice.class, id);
    }

    /**
     * Returns list  of ATMs by bank id
     */
    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(int bankId) {
        TypedQuery<AtmOffice> query = entityManager
                .createQuery("SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id", AtmOffice.class);
        query.setParameter("bank_id", bankId);
        return query.getResultList();
    }

    /**
     * Returns list  of ATMs by filter params
     */
    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(int bankId, int start, int length, String order, String filter) {
        String queryString = "SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id";
        if (!filter.isEmpty()) {
            queryString += " and a.address like :filter";
        }

        TypedQuery<AtmOffice> query = entityManager.createQuery(queryString + order, AtmOffice.class);
        query.setParameter("bank_id", bankId);
        if (!filter.isEmpty()) {
            query.setParameter("filter", filter);
        }
        query.setFirstResult(start);
        query.setMaxResults(length);

        return query.getResultList();
    }

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
    public void persist(AtmOffice atm) {
        entityManager.persist(atm);
    }


    /**
     * the method updates the array offices or puts the database
     *  @param   atmExistList - List of atmOffices
     * */
    @Override
    @Transactional
    public void update(final List<AtmOffice> atmExistList) {

        LOGGER.info("[TRANSACTION] update()-- begin transaction");
        for (AtmOffice atm: atmExistList) {
            entityManager.merge(atm);
        }
        LOGGER.info("[TRANSACTION]update()-- end transaction, updated or persisted --->" + atmExistList.size() + " elements");
    }

    /**
     * the method updates the array offices or puts the database
     *  @param   atmNewList - new List<AtmOffice>
     * */
    @Override
    @Transactional
    public void persist(final List<AtmOffice> atmNewList) {
        LOGGER.info("[TRANSACTION] persist()-- begin transaction");
        for (AtmOffice atm: atmNewList) {

            atm.setLastUpdated(TimeUtil.currentTimestamp());
            entityManager.persist(atm);
            entityManager.refresh(atm);
        }
        LOGGER.info("[TRANSACTION] persist()-- end transaction, updated or persisted" + atmNewList.size() + " elements");
    }

}
