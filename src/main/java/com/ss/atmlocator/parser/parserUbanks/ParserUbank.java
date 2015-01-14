package com.ss.atmlocator.parser.parserUbanks;


import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by Ivanna Terletska on 26.10.2014.
 */
public class ParserUbank {
    protected String url;
    protected String listSelector;
    protected Bank bank;
    protected Set<AtmOffice> atmsSet;
    private  AtmOffice.AtmType atmType;



// method fot seting parameters
//    url is url to parsing
//    listselector "atm" or "branch"
public void setParam(Map<String, Object> params) {
    if (params.containsKey("url")) {
        url = params.get("url").toString();
        this.listSelector = params.get("listSelector").toString();
        this.bank=(Bank)params.get("bank");

    }
}


    public List<AtmOffice> getItems() {
        atmsSet = new TreeSet<AtmOffice>();
        String atmAdress;

       //System.out.println(listSelector);
        if (listSelector.equals("branch")) {    // selector for bank office
           atmType= AtmOffice.AtmType.IS_OFFICE;
        } else if (listSelector.equals("atm")) {    // selector for atm
            atmType= AtmOffice.AtmType.IS_ATM;
        }
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
                    atmAdress="Івано-Франківськ"+", "+cols.get(0).children().text().trim();
                } else {
                    atmAdress=cols.get(0).text().trim()+", "+cols.get(2).children().text().trim();
                }

              //  System.out.println(atmType.toString());
              //  System.out.println(atmAdress);

                AtmOffice atm = new AtmOffice();

                atm.setBank(bank);
                atm.setAddress(atmAdress);
                atm.setType(atmType);


                atmsSet.add(atm);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(atmsSet);
    }

}
