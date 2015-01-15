package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmNetwork;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by us8610 on 12/3/2014.
 */
@Repository
public interface IAtmNetworksDAO {
    List<AtmNetwork> getNetworksList();
    AtmNetwork getNetwork(int id);
    public AtmNetwork saveNetwork(AtmNetwork network);
    boolean deleteNetwork(int network_id);
}
