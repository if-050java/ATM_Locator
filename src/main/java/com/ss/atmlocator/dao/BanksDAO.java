package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olavin on 22.11.2014.
 */
@Repository
public class BanksDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Bank> getBanksList(){
        List<Bank> banks;
        TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank AS b",Bank.class);
        banks = query.getResultList();

        return banks;
    }

/*
    public List<Bank> getBanksList(){
        List<Bank> banks;
        //TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank AS b",Bank.class);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bank> q = cb.createQuery(Bank.class);

        //banks = q.getResultList();

        return banks;
    }
*/

    public Bank getBank(int id){
        Bank bank = (Bank)entityManager.find(Bank.class, id);
        return bank;
    }

    @Transactional
    public Bank saveBank(Bank bank){
        return (Bank)entityManager.merge(bank);
    }

}
