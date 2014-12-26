package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.IParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static  final String  ADDRESS = "address";
    private static  final String  CITY = "Івано-Франківськ";
    private static  final String  USERAGENT = "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52";
    private String bankUrl;
    private String officeUrl;
    Document doc = null;
    List<AtmOffice> listAtms = new ArrayList<AtmOffice>();
    private boolean atmOffice = false;
    final static Logger logger = LoggerFactory.getLogger(AvalParser.class);

    @Override
    public void setParameter(Map<String, String> parameters) {
        bankUrl = parameters.get("bankurl");
        officeUrl = parameters.get("officeurl");
    }

    private void parceAtm(String url, boolean isOffice) throws IOException {
        try {

            doc = Jsoup.connect(url).userAgent(USERAGENT).get();

            Elements addreses = doc.getElementsByClass(ADDRESS);

            for (Element adres : addreses) {

                AtmOffice tempAtm = new AtmOffice();


                if (isOffice) {
                    tempAtm.setAddress(adres.text());
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM_OFFICE);
                    tempAtm.setAddress(trimFirsnaber(tempAtm.getAddress()));
                }else{
                    tempAtm.setAddress(CITY + adres.text());
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM);
                }
                listAtms.add(tempAtm);
            }

        } catch (IOException ioe) {
            logger.error("[PARSER] Parser cen`t connect to URL"+ioe.getMessage(),ioe);
            throw ioe;
        }
    }

    private String trimFirsnaber(String address) {


        return address.replaceFirst("\\d+,"," ");
    }


    @Override
    public List<AtmOffice> parse() throws IOException {
        parceAtm(bankUrl, atmOffice);
        atmOffice = true;
        parceAtm(officeUrl, atmOffice );
        return listAtms;
    }
}
