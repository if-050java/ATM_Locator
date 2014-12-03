package com.ss.atmlocator.parser;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parsers.IParserServise;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ivanna Terletska on 11/19/2014.
 */
public class BankUrlsParserServer implements Parser {
    protected String url;
    protected String listSelector = "spoiler";
    protected String bankName;
    protected List<Bank> bankList;
    protected String bankUrl;
    

    public BankUrlsParserServer() {
    }

    public BankUrlsParserServer(String url) {
        this.url = url;
    }

    public void setParametr(Map<String, String> parameters) {

        this.url = parameters.get("url");
       
    }

    public List<Bank> parce() {
        bankList = new ArrayList<Bank>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

            Elements banks = doc.getElementsByClass(listSelector);
            AtmParserServise parser = new AtmParserServise();
            Map<String, String> map = new HashMap<String, String>();

            for (Element element : banks) {

                bankName = element.ownText();

               // System.out.println(bankName);

                Elements divs = element.select("div");
                for (Element div : divs) {
                    Elements urls = div.select("a");

                    for (Element url : urls) {
                        if (url.text().equals("відділення")) {
                            listSelector = "branch";

                        } else if (url.text().equals("банкомати")) {
                            listSelector = "atm";

                        }
                        bankUrl = url.absUrl("href");

                        map.clear();
                        map.put("url", bankUrl);
                        map.put("listSelector", listSelector);
                        parser.setParam(map);
                        Bank bank = new Bank();
                        bank.setName(bankName);

                        Set<AtmOffice> AtmOfficeSetTmp = new TreeSet<AtmOffice>();
                        AtmOfficeSetTmp.addAll(parser.getItems());

                       
                        for (int i=0;i<bankList.size();i++) {
                             if (bankList.get(i).getName().equals(bankName)) {
                                AtmOfficeSetTmp.addAll(bankList.get(i).getAtmOfficeSet());
                               bankList.remove(i);
                                i=i+1;
                            }
                        }

                        bank.setAtmOfficeSet(AtmOfficeSetTmp);
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


