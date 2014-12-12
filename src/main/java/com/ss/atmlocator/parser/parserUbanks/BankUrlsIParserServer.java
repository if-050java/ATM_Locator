package com.ss.atmlocator.parser.parserUbanks;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parser.IParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.util.*;

/**
 * Created by Ivanna Terletska on 11/19/2014.
 */
public class BankUrlsIParserServer implements IParser {
    protected String url;
    protected String listSelector = "spoiler";
    protected String bankName;
    protected List<Bank> bankList;
    protected String bankUrl;
    

    public BankUrlsIParserServer() {
    }

    public BankUrlsIParserServer(String url) {
        this.url = url;
    }

    public void setParameter(Map<String, String> parameters) {

        this.url = parameters.get("url");
       
    }

    public List<Bank> parse() {
        final String BRANCH_SELECTOR_EXPR= "відділення";
        final String ATM_SELECTOR_EXPR = "банкомати";

        assert (BRANCH_SELECTOR_EXPR.equals("\u0432\u0456\u0434\u0434\u0456\u043B\u0435\u043D\u043D\u044F"));
        assert (ATM_SELECTOR_EXPR.equals("\u0431\u0430\u043D\u043A\u043E\u043C\u0430\u0442\u0438"));

        bankList = new ArrayList<Bank>();
        AtmParserServise parser = new AtmParserServise();
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

                        //System.out.println(bankUrls.getBankName());
                        //System.out.println(bankUrls.getBankUrl());
                        //System.out.println(bankUrls.getBankOfficceOrAtm());


                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return bankList;
    }

}


