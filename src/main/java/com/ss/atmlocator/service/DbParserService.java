package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM_OFFICE;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_OFFICE;

/**
 * Created by maks on 13.12.2014.
 */
@Service
public class DbParserService implements IDBParserService {
    @Autowired
    IAtmsDAO atmsDAO;
    @Autowired
    IBanksDAO banksDAO;
    int bankId;

    @Override
    public void update(List<AtmOffice> atms) {


        List<AtmOffice> atmExistList = new ArrayList<>();
        List<AtmOffice> atmNewList = new ArrayList<>();
        bankId = atms.get(0).getBank().getId();     // TODO Bed understand , will changed
        ArrayList<AtmOffice> atmListFomDb = new ArrayList<>(atmsDAO.getBankAtms(bankId));
        for(AtmOffice atmDb: atmListFomDb){
            if(atms.contains(atmDb)){
                atmDb.setLastUpdated(TimeUtil.currentTimestamp());
                atmExistList.add(atmDb);
            }else{
                atmDb.setState(1);
                atmExistList.add(atmDb);
            }
        }
        for (AtmOffice tempAtm : atms) {
            if (!atmListFomDb.contains(tempAtm)) {
                atmNewList.add(tempAtm);

            }
        }

//        atmsDAO.update(atmListFomDb
        atmsDAO.update(atmExistList);
        atmsDAO.persist(atmNewList);
    }


    public void updateWithoutType(List<AtmOffice> atms, int bankId) {
        List<AtmOffice> atmExistList = new ArrayList<>();
        List<AtmOffice> atmNewList = new ArrayList<>();
//        bankId = atms.get(0).getBank().getId();     // TODO Bed understand , will changed
        ArrayList<AtmOffice> atmListFomDb = new ArrayList<>(atmsDAO.getBankAtms(bankId));
        for(AtmOffice atmDb: atmListFomDb){
            if(atms.contains(atmDb)){
                atmDb.setLastUpdated(TimeUtil.currentTimestamp());
                atmExistList.add(atmDb);
            }else{
                atmDb.setState(1);
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


    }
    //-----------------------------------------------------------------------------------

    private boolean compareAtm(AtmOffice atmFromDb, AtmOffice atmNew){
        if(atmFromDb.equals(atmNew)){// equals має бути по адрессі
            AtmOffice.AtmType typeDb =atmFromDb.getType();
            AtmOffice.AtmType typeNew =atmNew.getType();
            if(typeDb.equals(typeNew)){
                return true;
            }else if(typeDb== IS_ATM&&typeNew== IS_OFFICE){
                atmFromDb.setType(IS_OFFICE);

                return true;
            }else if(typeDb== IS_OFFICE&&typeNew== IS_ATM){
                atmFromDb.setType(IS_ATM);

                return true;
            }else if(typeDb==IS_ATM&&typeNew==IS_ATM_OFFICE){
                atmFromDb.setType(IS_ATM_OFFICE);
                return true;
            }else if(typeDb==IS_ATM_OFFICE&&typeNew==IS_ATM){
                atmFromDb.setType(IS_ATM);
                return true;
            }else if(typeDb==IS_OFFICE&&typeNew==IS_ATM_OFFICE){
                atmFromDb.setType(IS_ATM_OFFICE);
                return true;
            }else if(typeDb==IS_ATM_OFFICE&&typeNew==IS_OFFICE){
                atmFromDb.setType(IS_OFFICE);
                return true;
            }

        }
        return false;
    }
    @Override
    public void update(List<AtmOffice> atms, int bankId) {
        List<AtmOffice> atmListFromDb = atmsDAO.getBankAtms(bankId);
        List<AtmOffice> atmResultList = new ArrayList<>();

        Bank currentBank = banksDAO.getBank(bankId);
        for (AtmOffice atm:atms){
            for(AtmOffice atmDb:atmListFromDb){
                compareAtm(atmDb, atm);
                continue;
                /*if(compareAtm(atmDb,atm)) {
                    atmDb.setLastUpdated(TimeUtil.currentTimestamp());
                    atmResultList.add(atmDb);
                }*/
            }
        }
        for(AtmOffice atmDb: atmListFromDb){
            if(atms.contains(atmDb)){
                atmDb.setLastUpdated(TimeUtil.currentTimestamp());
                atmResultList.add(atmDb);
            }else{
                atmResultList.add(atmDb);
            }
        }
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
