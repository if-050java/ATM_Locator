package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Olavin on 22.11.2014.
 */
@Repository
public final class AtmNetworksDAO implements IAtmNetworksDAO {
    private final org.apache.log4j.Logger log = Logger.getLogger(AtmNetworksDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IBanksDAO banksDAO;

    public List<AtmNetwork> getNetworksList() {
        List<AtmNetwork> networks;
        TypedQuery<AtmNetwork> query = entityManager.createQuery("SELECT n FROM AtmNetwork AS n", AtmNetwork.class);
        networks = query.getResultList();
        return networks;
    }

    public AtmNetwork getNetwork(final int id) {
        return entityManager.find(AtmNetwork.class, id);
    }

    @Override
    @Transactional
    public AtmNetwork saveNetwork(final AtmNetwork network) {
        AtmNetwork saved = (AtmNetwork) entityManager.merge(network);
        log.debug(String.format("Saved ATM Network '%s' %d#", saved.getName(), saved.getId()));
        return saved;
    }

    @Override
    @Transactional
    public boolean deleteNetwork(final int id) {
        try {
            AtmNetwork network = (AtmNetwork) entityManager.find(AtmNetwork.class, id);
            if (network == null) {
                return false;
            }
            final String delName = network.getName(); //get name before it will be deleted

            // remove network assignment for all related banks
            List<Bank> banks = banksDAO.getBanksByNetworkId(id);
            if (!banks.isEmpty()) {
                final AtmNetwork unassigned = banksDAO.getUnassignedNetwork();
                for (Bank bank : banks) {
                    bank.setNetwork(unassigned);
                }
                entityManager.merge(banks);
            }

            entityManager.remove(network);

            log.debug("Deleted ATM Network '" + delName + "' #" + id);
            return true;

        } catch (PersistenceException e) {
            log.error("Failed to delete ATM Network #" + id);
            e.printStackTrace();
            return false;
        }
    }

}
