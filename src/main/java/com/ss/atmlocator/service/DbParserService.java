package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.AtmState;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.utils.TimeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This servise prepare Collections atms from parser, and persiste their in Database
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


    /**
     * The method compare AtmOffices. Change them, if it need, type atm office from db
     * @param  atmFromDb old atm from database, its type change
     * @param  atmNew  new atm from parser, its type not change
     * @return boolean. True if method some changed or types are equals, else return false;
     * */
    protected boolean compareAtm(AtmOffice atmFromDb, AtmOffice atmNew) {// private modificator is changed to protected for test
        if (atmFromDb.equals(atmNew)) { // equals має бути по адрессі

            AtmOffice.AtmType typeNew = atmNew.getType();
            if(typeNew!= null){
                atmFromDb.setType(typeNew);
            }

            if(atmNew.getState() != AtmState.NO_LOCATION){
                atmFromDb.setState(atmNew.getState());
            }

           return true;
        }
        return false;
    }
    /**The method prepares an array of ATMs for the correct entries in the database.
     * compares the new ATMs with ATM communication data- base address,
     * and replaces them with the type of office or ATM. Updates as they are recorded.
     * @param atms new list of AtmOfices
     * @param bankId bank id */
    @Override
    public List<AtmOffice> update(List<AtmOffice> atms, int bankId) {// TODO change return to void ; return list for Junit test
        List<AtmOffice> atmListFromDb = atmsDAO.getBankAtms(bankId); // the atms from database
        List<AtmOffice> atmResultList = new ArrayList<>(); // result list for database

        Bank currentBank = banksDAO.getBank(bankId);
        // this compare oll atms and change type
        for (AtmOffice atm:atms) {
            for (AtmOffice atmDb:atmListFromDb) {
                if(compareAtm(atmDb, atm)) {
                    break;               // if we found and change some atm we dont need
                }
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
        log.info(" All atms put in database. Number of atms "+atmResultList.size());
        return  atmResultList;
    }


}
