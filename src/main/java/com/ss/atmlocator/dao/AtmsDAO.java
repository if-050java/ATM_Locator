package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmOffice;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;;
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
    public List<AtmOffice> getBankAtms(int bank_id){
        TypedQuery<AtmOffice> query = entityManager.createQuery(
                "SELECT a FROM AtmOffice AS a WHERE a.bank.id=:bank_id "
                        +"and a.geoPosition.latitude is not null and a.geoPosition.longitude is not null",
                AtmOffice.class);
        query.setParameter("bank_id", bank_id);
        return query.getResultList();
    }

}
