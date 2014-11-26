package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
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
public class AtmNetworksDAO {
    @PersistenceContext
    private EntityManager entityManager;

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

}
