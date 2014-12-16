package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.utils.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;;
import java.util.List;

/**
 * Created by Olavin on 10.12.2014.
 */
@Repository
public class AtmsDAO implements IAtmsDAO {
    private final org.apache.log4j.Logger log = Logger.getLogger(AtmsDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(Integer network_id, Integer bank_id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AtmOffice> criteria = builder.createQuery(AtmOffice.class);
        Root<AtmOffice> atmOfficeRoot = criteria.from(AtmOffice.class);
        criteria.select(atmOfficeRoot);
        if (network_id != null)
            criteria.where(builder.equal(atmOfficeRoot.join("bank").join("network").get("id"), network_id));
        if (bank_id != null) criteria.where(builder.equal(atmOfficeRoot.join("bank").get("id"), bank_id));
        return entityManager.createQuery(criteria).getResultList();
    }

    public AtmOffice getAtmById(int id) {
        return entityManager.find(AtmOffice.class, id);
    }
    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(int bank_id){
        TypedQuery<AtmOffice> query = entityManager.createQuery("SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id", AtmOffice.class);
        query.setParameter("bank_id", bank_id);
        return query.getResultList();
    }

   /* @Override
    public void updateAtmTime(AtmOffice tempAtm) {

    }*/

    @Override
    public void persiste(AtmOffice Atm) {
        entityManager.persist(Atm);
    }

    @Override
    @Transactional
    public void update(List<AtmOffice> atmExistList) {
        for(AtmOffice atm: atmExistList) {
//            atm.setLastUpdated(TimeUtil.currentTimestamp());
            entityManager.merge(atm);
        }
    }

    @Override
    @Transactional
    public void persist(List<AtmOffice> atmNewList) {
        for(AtmOffice atm: atmNewList){
            atm.setLastUpdated(TimeUtil.currentTimestamp());
            entityManager.persist(atm);
            entityManager.refresh(atm);
        }
    }

}
