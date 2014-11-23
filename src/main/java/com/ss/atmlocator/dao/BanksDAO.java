package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    public Bank getBank(int id){
        Bank bank = (Bank)entityManager.find(Bank.class, id);
        return bank;
    }

}
