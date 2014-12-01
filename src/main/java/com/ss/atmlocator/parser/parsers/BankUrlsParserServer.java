package com.ss.atmlocator.parser.parsers;

import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parser.parsers.AtmParserServise;
import com.ss.atmlocator.parser.parsers.IParserServise;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ivanna Terletska on 11/19/2014.
 */
public class BankUrlsParserServer implements IParserServise {
    protected String url;
    protected String listSelector = "spoiler";
    protected String bankName;
    protected Set<Bank> bankList;
    protected String bankUrl;
    protected int bankOfficceOrAtm;

    public BankUrlsParserServer() {
    }

    public BankUrlsParserServer(String url) {

        this.url = url;
    }

    public void setParam(Map<String, String> params) {
        this.url = params.get("url");
        // this.listSelector=params.get("listSelector");

    }

    public Set<Bank> getItems() {
        bankList = new TreeSet<Bank>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            int bankId = 1;

            Elements banks = doc.getElementsByClass(listSelector);
            for (Element element : banks) {

                bankName = element.ownText();
                //System.out.println(bankName);
                Elements divs = element.select("div");
                for (Element div : divs) {
                    Elements urls = div.select("a");

                    for (Element url : urls) {
                        if (url.text().equals("відділення")) {
                            bankOfficceOrAtm = 1;
                            //isBankOffice = true;
                        }
                        if (url.text().equals("банкомати")) {
                            bankOfficceOrAtm = 2;
                            //isAtm = true;
                        }
                        bankUrl = url.absUrl("href");

                        Bank bank = new Bank(bankName, bankUrl);

                        bankId = bankId + 1;
                        AtmParserServise parser = new AtmParserServise(bank, bankOfficceOrAtm);


                        bank.setAtmOfficeSet(parser.getItems());
                        bankList.add(bank);


                        //System.out.println(bankUrls.getBankName());
                        //System.out.println(bankUrls.getBankUrl());
                        //System.out.println(bankUrls.getBankOfficceOrAtm());

                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return bankList;
    }

}


