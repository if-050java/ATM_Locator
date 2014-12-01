package com.ss.atmlocator.parser.parsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ivanna Terletska on 26.10.2014.
 */
public class AtmParserServise {
    protected String url;
    protected String listSelector;
    protected Bank bank;
    protected Set<AtmOffice> atmsList;
    boolean isAtm;
    boolean isBankOffice;
    protected int bankOfficceOrAtm;

    public AtmParserServise() {
    }

    public AtmParserServise(Bank bank, int bankOfficceOrAtm) {
        this.url = bank.getWebSite();
        this.bank = bank;
        this.bankOfficceOrAtm = bankOfficceOrAtm;

        if (bankOfficceOrAtm == 1) {    // selector for bank office
            listSelector = "branch";
            isAtm = true;
        } else if (bankOfficceOrAtm == 2) {    // selector for atm
            listSelector = "atm";
            isBankOffice = true;
        }

    }
// method fot seting parameters
// url / ww fdjasfkl;
//    bankOrArm / 1 or 2
    public void setParam(Map<String, String> params) {
        this.url = params.get("url");
        this.bankOfficceOrAtm = Integer.parseInt(params.get("bankOfficceOrAtm"));

    }


    public Set<AtmOffice> getItems() {
        atmsList = new TreeSet<AtmOffice>();
        String atmCity;
        String atmAdress;
        int atmId = 1;

        //System.out.println(listSelector);

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

            // System.out.println(url);

            Elements table = doc.getElementsByClass(listSelector);
            Elements rows = table.select("tr");
            // System.out.println(listSelector);
            //  System.out.println(table);
            for (int i = 0; i < rows.size() - 1; i = i + 2) {
                Element row = rows.get(i);
                Elements cols = row.select("td");
                if (url.equals("http://ubanks.com.ua/adr/privatbank/branches/ivano-frankivska/ivano-frankivsk.php")) {    // in PrivatBank another structure
                    atmCity = "Івано-Франківськ";
                    atmAdress = cols.get(0).text();
                } else {
                    atmCity = cols.get(0).text();
                    atmAdress = cols.get(2).text();
                }
                ;
                //System.out.println(atmCity);
                //System.out.println(atmAdress);
                AtmOffice atm = new AtmOffice(bank, atmCity, atmAdress, isAtm, isBankOffice);
                atmId = atmId + 1;
                atmsList.add(atm);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return atmsList;
    }

}
