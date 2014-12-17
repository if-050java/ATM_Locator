package com.ss.atmlocator.parser.parserAval;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parser.IParser;
import com.ss.atmlocator.parser.testParser.ParserExecutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maks on 17.12.2014.
 */

public class AvalParser implements IParser {
    Map<String, String> parameters = new HashMap<>();
    private String bankUrl;
    private String officeUrl;
    Document doc = null;
    List<AtmOffice> listAtms = new ArrayList<AtmOffice>();
    private boolean atmOffice = false;

    @Override
    public void setParameter(Map<String, String> parameters) {

        bankUrl = parameters.get("bankUrl");
        officeUrl = parameters.get("officeUrl");
    }

    private void parceAtm(String url, boolean isOffice) {
        try {

            doc = Jsoup.connect(url).userAgent("Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52").get();

            Elements addreses = doc.getElementsByClass("address");

            for (Element adres : addreses) {

                AtmOffice tempAtm = new AtmOffice();
                if (isOffice) {
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM_OFFICE);
                }else{
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM);
                }
                tempAtm.setAddress(adres.text());
                listAtms.add(tempAtm);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<AtmOffice> parse() {
        parceAtm(bankUrl, atmOffice);
        atmOffice = true;
        parceAtm(officeUrl, atmOffice );
        return listAtms;
    }
}
