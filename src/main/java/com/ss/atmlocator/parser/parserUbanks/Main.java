package com.ss.atmlocator.parser.parserUbanks;

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

        BankUrlsIParserServer parseUrl = new BankUrlsIParserServer();
        Map<String, String> map = new HashMap<String, String>();
        map.put("url", "http://ubanks.com.ua/city/ivano-frankivska.php");
        parseUrl.setParameter(map);

        printBanks(parseUrl.parse());


    }

    public static void printBanks(List<Bank> banks) {
        Set<AtmOffice> atmSet;
        for (Bank bank : banks) {
            System.out.println(bank.getName());
            atmSet= bank.getAtmOfficeSet();
            for (AtmOffice atm : atmSet) {
                System.out.println(" - "  + "  " + atm.getAddress() + " " + atm.getType());
            }
            System.out.println();
        }
    }


}
