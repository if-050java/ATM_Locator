package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.utils.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Olavin on 22.11.2014.
 */
@Repository
public class BanksDAO implements IBanksDAO {
    private static final int UNASSIGNED_NETWORK = -1;
    private final org.apache.log4j.Logger log = Logger.getLogger(BanksDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Bank> getBanksList() {
        List<Bank> banks;
        TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank AS b ORDER BY b.name",Bank.class);
        banks = query.getResultList();
        return banks;
    }

    @Override
    @Transactional
    public Bank newBank() {
        Bank bank = new Bank();
        AtmNetwork network = entityManager.find(AtmNetwork.class, UNASSIGNED_NETWORK);
        if(network != null) {
            bank.setNetwork(network);
        } else {
            log.error("Failed to fetch UNASSIGNED_NETWORK from database");
            // TODO: throw exception
        }
        return bank;
    }

    @Override
    public Bank getBank(int id) {
        return (Bank)entityManager.find(Bank.class, id);
    }

    @Override
    public List<Bank> getBanksByNetworkId(int network_id) {
        TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank AS b " +
                "where b.network.id=:network_id ORDER BY b.name",Bank.class);
        query.setParameter("network_id", network_id);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Bank saveBank(Bank bank) {
        bank.setLastUpdated(TimeUtil.currentTimestamp());
        Bank saved = (Bank) entityManager.merge(bank);
        log.debug("Saved Bank '"+saved.getName()+"' #"+saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public boolean deleteBank(int bank_id) {
        try {
            Bank bank = (Bank) entityManager.find(Bank.class, bank_id);
            if(bank == null) return false;
            String delName = bank.getName(); //get name before it will be deleted
            entityManager.remove(bank);
            log.debug("Deleted Bank '"+delName+"' #"+bank_id);
            return true;
        } catch (PersistenceException e) {
            log.error("Failed to delete bank "+bank_id);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AtmNetwork getUnassignedNetwork() {
        return entityManager.find(AtmNetwork.class, UNASSIGNED_NETWORK);
    }

    @Override
    @Transactional
    public void saveAllBankNBU(List<Bank> banks) {
        Bank tempBank;
        AtmNetwork unassigned = entityManager.find(AtmNetwork.class, UNASSIGNED_NETWORK);

        for (Bank bank : banks) {
            int mfoCode = bank.getMfoCode();
            try {
                TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank AS b WHERE b.mfoCode=:mfoCode", Bank.class);
                query.setParameter("mfoCode", mfoCode);

                tempBank = query.getSingleResult();
                if (bank.getMfoCode() == tempBank.getMfoCode()) {
                    System.out.println("------I'm in continue-----");
                    continue;
                }
            } catch (NoResultException nre) {
                log.warn("he bank with mfo code not found"+nre.getMessage());
                //if the bank with mfo code not found , catch this exception
//                continue;
            }
            bank.setLastUpdated(TimeUtil.currentTimestamp());
            bank.setNetwork(unassigned);
            entityManager.merge(bank);
        }
    }

    @Override
     @Transactional
     public void saveAllBanksUbank(List<Bank> banks) {
        int cntBanksUpdated = 0;
        int cntNewAtms = 0;
        log.debug("Parsed "+banks.size()+" banks from Ubanks.com.ua");
        for (Bank bank : banks){

            boolean bankUpdated = false;

            try {
                TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank AS b WHERE b.name=:bankName", Bank.class);
                query.setParameter("bankName", bank.getName());

                Bank bankOther = query.getSingleResult();

                if (bank.getName().equals(bankOther.getName())) {
                    Set<AtmOffice> persistedAtms = bankOther.getAtmOfficeSet();
                    for (AtmOffice atmOffice: bank.getAtmOfficeSet()) {

                        if (!persistedAtms.contains(atmOffice)){
                            atmOffice.setBank(bankOther);
                            atmOffice.setLastUpdated(TimeUtil.currentTimestamp());
                            persistedAtms.add(atmOffice);
                            cntNewAtms++;
                            bankUpdated = true;
                        } else {
                            //TODO:  зробити провірку по типу атм
                        }

                    }
                    entityManager.merge(bankOther);
                    //continue;
                }
            } catch (NoResultException nre) {
                System.out.println();
                //if the bank with mfo code not found , catch this exception
//                continue;
            }


//            for(AtmOffice atmOffice:officeSet){
//                atmOffice.setBank(bank);
//            }
            if(bankUpdated) {
                cntBanksUpdated++;
            }

        }
        log.debug("Added "+cntNewAtms+" new ATMs and bank offices, for "+cntBanksUpdated+" banks.");

    }


}
