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

    private String defaultLogo;
    private String defaultAtm;
    private String defaultOffice;
    private String prefixLogo;
    private String prefixAtm;
    private String prefixOffice;

    @Autowired
    private IBanksDAO banksDAO;

    @Autowired
    private IAtmsDAO atmsDAO;

    public List<Bank> getBanksByNetworkId(int networkId){
        return banksDAO.getBanksByNetworkId(networkId);
    }

    public long getBankAtmsCount(final int bankId) {
        return atmsDAO.getBankAtmsCount(bankId);
    }

    public long getBankAtmsPages(final int bankId) {
        return atmsDAO.getBankAtmsPages(bankId);
    }

    public List<AtmOffice> getBankAtms(int bankId, int page) {
        return atmsDAO.getBankAtms(bankId, page);
    }

    public List<Bank> getBanksList() {
        List<Bank> banks = banksDAO.getBanksList();
        for (Bank bank : banks) {
            setBankDefaultImages(bank);
        }
        return banks;
    }

    public Bank getBank(final int bankId) {
        Bank bank = banksDAO.getBank(bankId);
        setBankDefaultImages(bank);
        return bank;
    }

    private void setBankDefaultImages(final Bank bank) {
        if (bank.getLogo() == null || "".equals(bank.getLogo())) {
            bank.setLogo(defaultLogo);
        }

        if (bank.getIconAtm() == null || "".equals(bank.getIconAtm())) {
            bank.setIconAtm(defaultAtm);
        }

        if (bank.getIconOffice() == null || "".equals(bank.getIconOffice())) {
            bank.setIconOffice(defaultOffice);
        }
    }

    public Bank newBank() {
        Bank bank = banksDAO.newBank();
        setBankDefaultImages(bank);
        bank.setLastUpdated(TimeUtil.currentTimestamp());

        return bank;
    }

    public OutResponse deleteBank(final int bankId, final HttpServletRequest request) {
        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

        logger.debug("Delete bank #" + bankId);
        Bank bankToDel = banksDAO.getBank(bankId);

        if (banksDAO.deleteBank(bankId)) {
            deleteBankImages(bankToDel, request);
            response.setStatus(Constants.SUCCESS);
        } else {
            logger.error("Failed to delete bank #" + bankId);
            response.setStatus(Constants.ERROR);
        }
        response.setErrorMessageList(errorMessages);

        return response;
    }

    private void deleteBankImages(final Bank bankToDel, final HttpServletRequest request) {
        String logo = bankToDel.getLogo();
        if (logo != null && !logo.equals(defaultLogo)) {
            UploadedFile.deleteResourceFile(logo, request);
        }

        String iconAtm = bankToDel.getIconAtm();
        if (iconAtm != null && !iconAtm.equals(defaultAtm)) {
            UploadedFile.deleteResourceFile(iconAtm, request);
        }

        String iconOffice = bankToDel.getIconOffice();
        if (iconOffice != null && !iconOffice.equals(defaultOffice)) {
            UploadedFile.deleteResourceFile(iconOffice, request);
        }
    }

    public OutResponse saveBank(Bank bank,
                         MultipartFile imageLogo,
                         MultipartFile iconAtmFile,
                         MultipartFile iconOfficeFile,
                         HttpServletRequest request) {

        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

        // save bank and obtain its ID
        Bank savedBank = banksDAO.saveBank(bank);

        final String FAIL_SAVE_MSG = "Failed to save image: ";

        if (imageLogo != null) {
            try {
                String newLogo = UploadedFile.saveImage(imageLogo, prefixLogo, savedBank.getId(), request);
                savedBank.setLogo(newLogo);
            } catch (IOException e) {
                logger.error(FAIL_SAVE_MSG + imageLogo.getOriginalFilename(), e);
            }
        }

        if (iconAtmFile != null) {
            try {
                String newIconAtm = UploadedFile.saveImage(iconAtmFile, prefixAtm, savedBank.getId(), request);
                savedBank.setIconAtm(newIconAtm);
            } catch (IOException e) {
                logger.error(FAIL_SAVE_MSG + iconAtmFile.getOriginalFilename(), e);
            }
        }

        if (iconOfficeFile != null) {
            try {
                String newIconOffice = UploadedFile.saveImage(iconOfficeFile, prefixOffice, savedBank.getId(), request);
                savedBank.setIconOffice(newIconOffice);
            } catch (IOException e) {
                logger.error(FAIL_SAVE_MSG + iconOfficeFile.getOriginalFilename(), e);
            }
        }

        // save Bank entity again to update images
        banksDAO.saveBank(savedBank);

        if (savedBank != null && savedBank.getId() != 0) {
            response.setStatus(Constants.SUCCESS);
        } else {
            response.setStatus(Constants.ERROR);
        }
        response.setErrorMessageList(errorMessages);

        return response;

    }

    public void setPrefixLogo(String prefixLogo) {
        this.prefixLogo = prefixLogo;
    }

    public void setPrefixAtm(String prefixAtm) {
        this.prefixAtm = prefixAtm;
    }

    public void setPrefixOffice(String prefixOff) {
        this.prefixOffice = prefixOff;
    }

    public void setDefaultLogo(String defaultLogo) {
        this.defaultLogo = defaultLogo;
    }

    public void setDefaultAtm(String defaultAtm) {
        this.defaultAtm = defaultAtm;
    }

    public void setDefaultOffice(String defaultOffice) {
        this.defaultOffice = defaultOffice;
    }


}
