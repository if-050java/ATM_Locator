package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.utils.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olavin on 22.11.2014.
 */
@Repository
public class AtmNetworksDAO implements IAtmNetworksDAO {
    private final org.apache.log4j.Logger log = Logger.getLogger(AtmNetworksDAO.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private IBanksDAO banksDAO;

    public List<AtmNetwork> getNetworksList(){
        List<AtmNetwork> networks;
        TypedQuery<AtmNetwork> query = entityManager.createQuery("SELECT n FROM AtmNetwork AS n",AtmNetwork.class);
        networks = query.getResultList();
        return networks;
    }

    public AtmNetwork getNetwork(int id){
        AtmNetwork network = (AtmNetwork)entityManager.find(AtmNetwork.class, id);
        return network;
    }

    @Override
    @Transactional
    public AtmNetwork saveNetwork(AtmNetwork network) {
        AtmNetwork saved = (AtmNetwork) entityManager.merge(network);
        log.debug("Saved ATM Network '"+saved.getName()+"' #"+saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public boolean deleteNetwork(int network_id) {
        try {
            AtmNetwork network = (AtmNetwork) entityManager.find(AtmNetwork.class, network_id);
            if(network == null) return false;
            final String delName = network.getName(); //get name before it will be deleted

            // remove network assignment for all related banks
            List<Bank> banks = banksDAO.getBanksByNetworkId(network_id);
            final AtmNetwork unassigned = banksDAO.getUnassignedNetwork();
            for(Bank bank : banks){
                bank.setNetwork(unassigned);
            }
            entityManager.merge(banks);

            entityManager.remove(network);

            log.debug("Deleted ATM Network '"+delName+"' #"+network_id);
            return true;

        } catch (PersistenceException e) {
            log.error("Failed to delete ATM Network #"+network_id);
            e.printStackTrace();
            return false;
        }
    }

}
