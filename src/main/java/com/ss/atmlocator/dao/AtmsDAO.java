package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.utils.TimeUtil;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;;
import java.util.Collections;
import java.util.List;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM_OFFICE;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_OFFICE;
import static java.util.Arrays.asList;

/**
 * Created by Olavin on 10.12.2014.
 */
@Repository
public class AtmsDAO implements IAtmsDAO {
    final static org.slf4j.Logger log = LoggerFactory.getLogger(AtmsDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(Integer network_id, Integer bank_id, boolean showAtms, boolean showOffices) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AtmOffice> criteria = builder.createQuery(AtmOffice.class);
        Root<AtmOffice> atmOfficeRoot = criteria.from(AtmOffice.class);
        criteria.select(atmOfficeRoot);
        Predicate where = builder.conjunction();

        if (network_id != null)
            where = builder.and(where,builder.equal(atmOfficeRoot.join("bank").join("network").get("id"), network_id));
        if (bank_id != null)
            where = builder.and(where,builder.equal(atmOfficeRoot.join("bank").get("id"), bank_id));
        if (showAtms && !showOffices)
            where = builder.and(where,atmOfficeRoot.get("type").in(asList(IS_ATM,IS_ATM_OFFICE)));
        if (showOffices && !showAtms)
            where = builder.and(where,atmOfficeRoot.get("type").in(asList(IS_OFFICE, IS_ATM_OFFICE)));
        if (!showAtms && !showOffices) return Collections.emptyList();
        return entityManager.createQuery(criteria.where(where)).getResultList();
    }

    public AtmOffice getAtmById(int id) {
        return entityManager.find(AtmOffice.class, id);
    }
    /**
     * @return List<AtmOffice>
     * @param  bank_id this is id of banks*/
    @Override
    @Transactional
    public List<AtmOffice> getBankAtms(int bank_id){
        TypedQuery<AtmOffice> query = entityManager.createQuery("SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id", AtmOffice.class);
        query.setParameter("bank_id", bank_id);
        return query.getResultList();
    }



    @Override
    public void persist (AtmOffice Atm) {
        entityManager.persist(Atm);
    }
    /**
     * the method updates the array offices or puts the database
     *  @param   atmExistList - List of atmOffices
     * */
    @Override
    @Transactional
    public void update(List<AtmOffice> atmExistList) {
        log.info("[TRANSACTION] update()-- begin transaction");
        for(AtmOffice atm: atmExistList) {
//            atm.setLastUpdated(TimeUtil.currentTimestamp());
            entityManager.merge(atm);
        }
        log.info("[TRANSACTION]update()-- end transaction, updated or persisted --->"+atmExistList.size()+" elements");
    }
    /**
     * the method updates the array offices or puts the database
     *  @param   atmNewList - new List<AtmOffice>
     * */
    @Override
    @Transactional
    public void persist(List<AtmOffice> atmNewList) {
        log.info("[TRANSACTION] persist()-- begin transaction");
        for(AtmOffice atm: atmNewList){
            atm.setLastUpdated(TimeUtil.currentTimestamp());
            entityManager.persist(atm);
            entityManager.refresh(atm);
        }
        log.info("[TRANSACTION] persist()-- end transaction, updated or persisted"+atmNewList.size()+" elements");
    }

}
