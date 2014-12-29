package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmNetworksDAO;
import com.ss.atmlocator.entity.AtmNetwork;


import com.ss.atmlocator.utils.Constants;
import com.ss.atmlocator.utils.ErrorMessage;
import com.ss.atmlocator.utils.OutResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by us8610 on 12/3/2014.
 */
@Service
public final class AtmNetworksService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AtmNetworksService.class);

    @Autowired
    private IAtmNetworksDAO atmNetworksDAO;

    public List<AtmNetwork> getNetworksList() {
        return atmNetworksDAO.getNetworksList();
    }

    public AtmNetwork getNetwork(final int id) {
        return atmNetworksDAO.getNetwork(id);
    }

    public OutResponse saveNetwork(final AtmNetwork network) {

        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMessages = new ArrayList<>();

        AtmNetwork savedNetwork = atmNetworksDAO.saveNetwork(network);
        if (savedNetwork != null && savedNetwork.getId() != 0) {
            response.setStatus(Constants.SUCCESS);
        } else {
            response.setStatus(Constants.ERROR);
        }

        response.setErrorMessageList(errorMessages);
        return response;

    }


    public OutResponse deleteNetwork(final int id) {
        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMessages = new ArrayList<>();

        LOGGER.debug("Delete ATM network #" + id);

        if (atmNetworksDAO.deleteNetwork(id)) {
            response.setStatus(Constants.SUCCESS);
        } else {
            response.setStatus(Constants.ERROR);
        }
        response.setErrorMessageList(errorMessages);

        return response;
    }
}
