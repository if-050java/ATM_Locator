package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.utils.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by us8610 on 12/3/2014.
 */
@Service
public class BanksService {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BanksService.class);

    @Autowired
    private IBanksDAO banksDAO;

    @Autowired
    private IAtmsDAO atmsDAO;

    public List<Bank> getBanksByNetworkId(int network_id){
        return banksDAO.getBanksByNetworkId(network_id);
    }

    public long getBankAtmsCount(final int bankId) {
        return atmsDAO.getBankAtmsCount(bankId);
    }

    public long getBankAtmsPages(final int bankId) {
        return atmsDAO.getBankAtmsPages(bankId);
    }

    public List<AtmOffice> getBankAtms(int id) {
        return atmsDAO.getBankAtms(id);
    }

    public List<AtmOffice> getBankAtms(int id, int page) {
        return atmsDAO.getBankAtms(id, page);
    }

    public List<Bank> getBanksList() {
        return banksDAO.getBanksList();
    }

    public Bank getBank(int id){
        return banksDAO.getBank(id);
    }

    public Bank newBank(){
        Bank bank = banksDAO.newBank();
        //TODO: get image names from parameters file
        bank.setLogo("default_logo.png");
        bank.setIconAtm("default_atm.png");
        bank.setIconOffice("default_office.png");
        bank.setLastUpdated(TimeUtil.currentTimestamp());

        return bank;
    }

    public OutResponse deleteBank(int id) {
        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

        logger.debug("Delete bank #" + id);
        //TODO: delete associated image files

        if (banksDAO.deleteBank(id)){
            response.setStatus(Constants.SUCCESS);
        } else {
            //TODO: add error message
            response.setStatus(Constants.ERROR);
        }
        response.setErrorMessageList(errorMessages);

        return response;
    }

    public OutResponse saveBank(Bank bank,
                         MultipartFile imageLogo,
                         MultipartFile iconAtmFile,
                         MultipartFile iconOfficeFile,
                         HttpServletRequest request) {

        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

        String newLogo = null;
        try {
            newLogo = UploadedFile.saveImage(imageLogo, "bank_logo", bank.getId(), request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(newLogo != null){
            bank.setLogo(newLogo);
        }

        String newIconAtm = null;
        try {
            newIconAtm = UploadedFile.saveImage(iconAtmFile, "bank_atm", bank.getId(), request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(newIconAtm != null){
            bank.setIconAtm(newIconAtm);
        }

        String newIconOffice = null;
        try {
            newIconOffice = UploadedFile.saveImage(iconOfficeFile, "bank_off", bank.getId(), request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(newIconOffice != null) {
            bank.setIconOffice(newIconOffice);
        }

        Bank savedBank = banksDAO.saveBank(bank); // TODO: check for save error
        if (savedBank != null && savedBank.getId() != 0){
            response.setStatus(Constants.SUCCESS);
        } else {
            response.setStatus(Constants.ERROR);
        }

        response.setErrorMessageList(errorMessages);

        return response;

    }


}
