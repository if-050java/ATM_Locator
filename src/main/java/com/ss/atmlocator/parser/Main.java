package com.ss.atmlocator.parser;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by student on 11/26/2014.
 */
public class Main {
    public static void main(String[] args) {

        BankUrlsParserServer parseUrl = new BankUrlsParserServer();
        Map<String, String> map = new HashMap<String, String>();
        map.put("url", "http://ubanks.com.ua/city/ivano-frankivska.php");
        parseUrl.setParameter(map);

        printBanks(parseUrl.parse());
       

    }

    public static void printBanks(List<Bank> banks) {
        for (Bank bank : banks) {
            System.out.println(bank.getName());
            Set<AtmOffice> atmSet = bank.getAtmOfficeSet();
            for (AtmOffice atm : atmSet) {
                System.out.println(" - "  +atm.getBank().getName() + "  " + atm.getAddress() + " " + atm.getType());
            }
            System.out.println();
        }
    }


}
