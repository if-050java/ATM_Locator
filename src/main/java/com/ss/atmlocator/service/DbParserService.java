package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.utils.TimeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM_OFFICE;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_OFFICE;
import static com.ss.atmlocator.entity.AtmState.*;

/**
 * Created by maks on 13.12.2014.
 */
@SuppressWarnings("ALL")
@Service
public class DbParserService implements IDBParserService {
    final static org.slf4j.Logger log = LoggerFactory.getLogger(IDBParserService.class);
    @Autowired
    private IAtmsDAO atmsDAO;
    @Autowired
    private IBanksDAO banksDAO;
    private int bankId;

 /*   @Override
    public void update( List<AtmOffice> atms) {
        List<AtmOffice> atmExistList = new ArrayList<>();
        List<AtmOffice> atmNewList = new ArrayList<>();
        bankId = atms.get(0).getBank().getId();
        ArrayList<AtmOffice> atmListFomDb = new ArrayList<>(atmsDAO.getBankAtms(bankId));
        for (AtmOffice atmDb: atmListFomDb) {
            if (atms.contains(atmDb)) {
                atmDb.setLastUpdated(TimeUtil.currentTimestamp());
                atmExistList.add(atmDb);
            } else {
                atmDb.setState(NO_LOCATION);
                atmExistList.add(atmDb);
            }
        }
        for (AtmOffice tempAtm : atms) {
            if (!atmListFomDb.contains(tempAtm)) {
                atmNewList.add(tempAtm);

            }
        }

        atmsDAO.update(atmExistList);
        atmsDAO.persist(atmNewList);
    }


    public void updateWithoutType(List<AtmOffice> atms,  int bankId) {
        List<AtmOffice> atmExistList = new ArrayList<>();
        List<AtmOffice> atmNewList = new ArrayList<>();
        ArrayList<AtmOffice> atmListFomDb = new ArrayList<>(atmsDAO.getBankAtms(bankId));
        for (AtmOffice atmDb: atmListFomDb) {
            if (atms.contains(atmDb)) {
                atmDb.setLastUpdated(TimeUtil.currentTimestamp());
                atmExistList.add(atmDb);
            } else {
                atmDb.setState(NO_LOCATION);
                atmExistList.add(atmDb);
            }
        }
        for (AtmOffice tempAtm : atms) {
            if (!atmListFomDb.contains(tempAtm)) {
                Bank curentBank = banksDAO.getBank(bankId);
                tempAtm.setBank(curentBank);
                atmNewList.add(tempAtm);

            }
        }

        atmsDAO.update(atmExistList);

        atmsDAO.persist(atmNewList);


    }*/
    //-----------------------------------------------------------------------------------
    /**
     * The method compare AtmOffices. Change them, if it need, type atm office from db
     * @param  atmFromDb old atm from database, its type change
     * @param  atmNew  new atm from parser, its type not change
     * @return boolean. True if method some changed or types are equals, else return false;
     * */
    private boolean compareAtm(AtmOffice atmFromDb, AtmOffice atmNew) {
        if (atmFromDb.equals(atmNew)) { // equals має бути по адрессі
            AtmOffice.AtmType typeDb = atmFromDb.getType();
            AtmOffice.AtmType typeNew = atmNew.getType();
            if (typeDb.equals(typeNew)) {
                return true;
            } else if (typeDb == IS_ATM && typeNew == IS_OFFICE) {
                atmFromDb.setType(IS_OFFICE);

                return true;
            } else if (typeDb == IS_OFFICE && typeNew == IS_ATM) {
                atmFromDb.setType(IS_ATM);

                return true;
            } else if (typeDb == IS_ATM && typeNew == IS_ATM_OFFICE) {
                atmFromDb.setType(IS_ATM_OFFICE);
                return true;
            } else if (typeDb == IS_ATM_OFFICE && typeNew == IS_ATM) {
                atmFromDb.setType(IS_ATM);
                return true;
            } else if (typeDb == IS_OFFICE && typeNew == IS_ATM_OFFICE) {
                atmFromDb.setType(IS_ATM_OFFICE);
                return true;
            } else if (typeDb == IS_ATM_OFFICE && typeNew == IS_OFFICE) {
                atmFromDb.setType(IS_OFFICE);
                return true;
            }

        }
        return false;
    }
    /**The method prepares an array of ATMs for the correct entries in the database.
     * compares the new ATMs with ATM communication data- base address,
     * and replaces them with the type of office or ATM. Updates as they are recorded.
     * @param atms new list of AtmOfices
     * @param bankId bank id
     * */
    @Override
    public void update( List<AtmOffice> atms,  int bankId) {
        List<AtmOffice> atmListFromDb = atmsDAO.getBankAtms(bankId); // the atms from database
        List<AtmOffice> atmResultList = new ArrayList<>(); // result list for database

        Bank currentBank = banksDAO.getBank(bankId);
        // this compare oll atms and change type
        for (AtmOffice atm:atms) {
            for (AtmOffice atmDb:atmListFromDb) {
                compareAtm(atmDb, atm);
                continue;               // if we found and change some atm we dont need
            }
        }
        // put in resultList old atms and update their time last update.
        for (AtmOffice atmDb: atmListFromDb) {
            if (atms.contains(atmDb)) {
                atmDb.setLastUpdated(TimeUtil.currentTimestamp());
                atmResultList.add(atmDb);
            } else {
                atmResultList.add(atmDb);
            }
        }
        //put in resultList new atms
        for (AtmOffice tempAtm : atms) {
            if (!atmListFromDb.contains(tempAtm)) {
                tempAtm.setLastUpdated(TimeUtil.currentTimestamp());
                tempAtm.setBank(currentBank);
                atmResultList.add(tempAtm);
            }
        }
        atmsDAO.update(atmResultList);

    }


}
