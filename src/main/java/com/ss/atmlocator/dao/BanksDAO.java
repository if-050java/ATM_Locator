package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.utils.TimeUtil;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;;
import java.util.List;
import java.util.Set;

/**
 * Created by Olavin on 22.11.2014.
 */
@Repository
public class BanksDAO implements IBanksDAO {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BanksDAO.class);
    private static final int UNASSIGNED_NETWORK = -1;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IAtmsDAO atmsDAO;

    @Override
    public List<Bank> getBanksList() {
        List<Bank> banks;
        TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank AS b ORDER BY b.name", Bank.class);
        banks = query.getResultList();
        return banks;
    }

    @Override
    @Transactional
    public Bank newBank() {
        Bank bank = new Bank();
        AtmNetwork network = entityManager.find(AtmNetwork.class, UNASSIGNED_NETWORK);
        if (network != null) {
            bank.setNetwork(network);
        } else {
            LOGGER.error("Failed to fetch UNASSIGNED_NETWORK from database");
            // TODO: throw exception
        }
        return bank;
    }

    @Override
    public Bank getBank(final int id) {
        return (Bank) entityManager.find(Bank.class, id);
    }

    @Override
    public List<Bank> getBanksByNetworkId(final int networkId) {
        if (networkId == 0) {
            return getBanksList();
        }
        TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM Bank AS b "
                + "where b.network.id=:network_id ORDER BY b.name", Bank.class);
        query.setParameter("network_id", networkId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Bank saveBank(final Bank bank) {
        bank.setLastUpdated(TimeUtil.currentTimestamp());
        Bank saved = entityManager.merge(bank);
        LOGGER.debug("Saved Bank '" + saved.getName() + "' #" + saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public boolean deleteBank(final int bankId) {
        try {
            Bank bank = entityManager.find(Bank.class, bankId);
            if (bank == null) {
                return false;
            }
            String delName = bank.getName(); //get name before it will be deleted

            int deletedAtms = atmsDAO.deleteBanksAtms(bankId);
            LOGGER.debug(String.format("Deleted Bank '%s' #%d ATMs: %d", delName, bankId, deletedAtms));

            entityManager.remove(bank);
            LOGGER.debug("Deleted Bank '" + delName + "' #" + bankId);

        } catch (PersistenceException e) {
            LOGGER.error("Failed to delete bank #" + bankId);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public AtmNetwork getUnassignedNetwork() {
        return entityManager.find(AtmNetwork.class, UNASSIGNED_NETWORK);
    }

    @Override
    @Transactional
    public void saveAllBankNBU(final List<Bank> banks) {
        Bank tempBank;
        AtmNetwork unassigned = entityManager.find(AtmNetwork.class, UNASSIGNED_NETWORK);

        for (Bank bank : banks) {
            int mfoCode = bank.getMfoCode();
            try {
                TypedQuery<Bank> query = entityManager.createQuery(
                        "SELECT b FROM Bank AS b WHERE b.mfoCode=:mfoCode", Bank.class);
                query.setParameter("mfoCode", mfoCode);

                tempBank = query.getSingleResult();
                if (bank.getMfoCode() == tempBank.getMfoCode()) {
                    System.out.println("------I'm in continue-----");
                    continue;
                }
            } catch (NoResultException nre) {
                LOGGER.warn("he bank with mfo code not found" + nre.getMessage());
            }
            bank.setLastUpdated(TimeUtil.currentTimestamp());
            bank.setNetwork(unassigned);
            entityManager.merge(bank);
        }
    }

    @Override
    @Transactional
    public void saveAllBanksUbank(final List<Bank> banks) {
        int cntBanksUpdated = 0;
        int cntNewAtms = 0;
        LOGGER.debug("Parsed " + banks.size() + " banks from Ubanks.com.ua");
        for (Bank bank : banks) {

            boolean bankUpdated = false;

            try {
                TypedQuery<Bank> query = entityManager.createQuery(
                        "SELECT b FROM Bank AS b WHERE b.name=:bankName", Bank.class);
                query.setParameter("bankName", bank.getName());

                Bank bankOther = query.getSingleResult();

                if (bank.getName().equals(bankOther.getName())) {
                    Set<AtmOffice> persistedAtms = bankOther.getAtmOfficeSet();
                    for (AtmOffice atmOffice : bank.getAtmOfficeSet()) {

                        if (!persistedAtms.contains(atmOffice)) {
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
                }
            } catch (NoResultException nre) {
                System.out.println();
            }


            if (bankUpdated) {
                cntBanksUpdated++;
            }

        }
        LOGGER.debug("Added " + cntNewAtms + " new ATMs and bank offices, for " + cntBanksUpdated + " banks.");

    }


}
