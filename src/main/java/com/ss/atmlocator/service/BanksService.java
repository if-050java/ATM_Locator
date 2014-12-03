package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmNetworksDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by us8610 on 12/3/2014.
 */
@Service
public class BanksService {
    @Autowired
    private IBanksDAO banksDAO;

    public List<Bank> getBanksByNetworkId(int network_id){
        return banksDAO.getBanksByNetworkId(network_id);
    }

}
