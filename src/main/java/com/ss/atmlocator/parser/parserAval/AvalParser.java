package com.ss.atmlocator.parser.parserAval;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parser.IParser;
import com.ss.atmlocator.parser.testParser.ParserExecutor;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    final static Logger logger = LoggerFactory.getLogger(AvalParser.class);

    @Override
    public void setParameter(Map<String, String> parameters) {

        bankUrl = parameters.get("bankUrl");
        officeUrl = parameters.get("officeUrl");
    }

    private void parceAtm(String url, boolean isOffice) throws IOException {
        try {

            doc = Jsoup.connect(url).userAgent("Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52").get();

            Elements addreses = doc.getElementsByClass("address");

            for (Element adres : addreses) {

                AtmOffice tempAtm = new AtmOffice();

                tempAtm.setAddress(adres.text());
                if (isOffice) {
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM_OFFICE);
                    tempAtm.setAddress(trimFirsnaber(tempAtm.getAddress()));
                }else{
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM);
                }
                listAtms.add(tempAtm);
            }

        } catch (IOException ioe) {
            logger.error(ioe.getMessage(),ioe);
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
