package com.ss.atmlocator.parser.parsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;

import java.util.Set;

/**
 * Created by student on 11/26/2014.
 */
public class Main {
    public static void main(String[] args) {

        BankUrlsParserServer parseUrl = new BankUrlsParserServer("http://ubanks.com.ua/city/ivano-frankivska.php");

        BankUrlsParserServer parseUrl2 = new BankUrlsParserServer("http://ubanks.com.ua/city/kyivska.php");

        printBanks(parseUrl.getItems());
        printBanks(parseUrl2.getItems());
        AtmParserServise atmParse = new AtmParserServise();

    }

    public static void printBanks(Set<Bank> banks) {
        for (Bank bank : banks) {
            System.out.println(bank.getName());
            Set<AtmOffice> atmSet = bank.getAtmOfficeSet();
            for (AtmOffice atm : atmSet) {
                System.out.println(" - " + atm.getAtmCity() + "  " + atm.getAddress() + " " + atm.isAtm() + " " + atm.isBankOffice());
            }
            System.out.println();
        }
    }


}