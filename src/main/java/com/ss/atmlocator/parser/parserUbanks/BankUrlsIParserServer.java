package com.ss.atmlocator.parser.parserUbanks;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parser.IParsers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ivanna Terletska on 11/19/2014.
 */
public class BankUrlsIParserServer implements IParsers {
    protected String url;
    protected String listSelector = "spoiler";
    protected String bankName;
    protected List<Bank> bankList;
    protected String bankUrl;

    public BankUrlsIParserServer() {}

    public BankUrlsIParserServer(String url) {
        this.url = url;
    }

    public void setParameter(Map<String, String> parameters) {
        this.url = parameters.get("url");
    }

    public List<Bank> parse() {
        final String BRANCH_SELECTOR_EXPR= "відділення";
        final String ATM_SELECTOR_EXPR = "банкомати";

        bankList = new ArrayList<Bank>();
        ParserUbank parser = new ParserUbank();
        Map<String, Object> map = new HashMap<String, Object>();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements banks = doc.getElementsByClass(listSelector);
            for (Element element : banks) {
                bankName = element.ownText().trim().toUpperCase();
               // System.out.println(bankName);
                Elements urls = element.select("div>a");
                int count =2;

                for (Element url : urls) {
                        /* відділення */
                        if (url.text().equals(BRANCH_SELECTOR_EXPR)) {
                            listSelector = "branch";

                        } else
                        /* банкомати */
                        if (url.text().equals(ATM_SELECTOR_EXPR)) {
                            listSelector = "atm";

                        }
                        bankUrl = url.absUrl("href");

                        Bank bank = new Bank();
                        bank.setName(bankName);

                        map.clear();
                        map.put("url", bankUrl);
                        map.put("listSelector", listSelector);
                        map.put("bank", bank);
                        parser.setParam(map);

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
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return bankList;
    }

}


