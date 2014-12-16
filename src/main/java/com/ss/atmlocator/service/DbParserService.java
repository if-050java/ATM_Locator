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

    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();


    @Override
    public void update(List<AtmOffice> atms) {


        List<AtmOffice> atmExistList = new ArrayList<>();
        List<AtmOffice> atmNewList = new ArrayList<>();


//        HashSet<AtmOffice> atmsFromDb = new HashSet<>();

        bankId = atms.get(0).getBank().getId();     // TODO Bed understand , will changed
     /*   atmListFomDb=(ArrayList)atmsDAO.getBankAtms(bankId);
        HashMap<AtmOffice, Object > tempMap=new HashMap<>();*/

        ArrayList<AtmOffice> atmListFomDb = new ArrayList<>(atmsDAO.getBankAtms(bankId));
//        for (int i = 0; i < atmListFomDb.size(); i++) {
////           tempMap.put(atmListFomDb.get(i), PRESENT); // filling the array
//
//        }

//        for(AtmOffice tempAtm : atms){
//
//        }



//         atmsFromDb.addAll(atmsDAO.getBankAtms(bankId));


  /*     for (AtmOffice tempAtm : atms) {
         *//*   if (atmsFomDb.contains(tempAtm)) {
                atmsDAO.updateAtmTime(tempAtm);// TODO create new List with changed field lastUpdate and then updated theirs in Db
            } else {
                atmsDAO.persiste(tempAtm);  //TODO create new List with new Atms and then persist theirs in Db
            }*//*

           for(AtmOffice dbAtm:atmListFomDb){
                if(tempAtm.equals(dbAtm)){

                }
           }


          *//*
            if (!atmListFomDb.contains(tempAtm)) {
                atmListFomDb.add(tempAtm);

            }else{

            }*//*

/////////////////////////////////
        }*/
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

//        atmsDAO.update(atmListFomDb);
        atmsDAO.update(atmExistList);
        atmsDAO.persist(atmNewList);


    }

    @Override
    public void update(List<AtmOffice> atms, int bankId) {
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

//        atmsDAO.update(atmListFomDb);
        atmsDAO.update(atmExistList);

        atmsDAO.persist(atmNewList);


    }


}
