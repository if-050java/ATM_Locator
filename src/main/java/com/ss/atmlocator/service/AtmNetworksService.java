package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmNetworksDAO;
import com.ss.atmlocator.entity.AtmNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by us8610 on 12/3/2014.
 */
@Service
public class AtmNetworksService {
    @Autowired
    private IAtmNetworksDAO atmNetworksDAO;

    public List<AtmNetwork> getNetworksList(){
        return atmNetworksDAO.getNetworksList();
    }

    public AtmNetwork getNetwork(int id) {
        return atmNetworksDAO.getNetwork(id);
    }

}
