package com.ss.atmlocator.parser.bankParsers;


import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.IParser;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import com.ss.atmlocator.parser.ParserExecutor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;
import static com.ss.atmlocator.entity.AtmState.NO_LOCATION;



public class CrediAgricoleParser extends ParserExecutor {

    private String region = "11";
    private String url = "https://credit-agricole.ua/branches/otdelenija-i-bankomaty/";
    private String bvId = "1";
    private String atmId = "2";

    final static Logger logger = LoggerFactory.getLogger(CrediAgricoleParser.class);

    private static final String REGION_KEY = "region";
    private static final String URL_KEY = "url";
    private static final String BVID_KEY = "bvId";
    private static final String ATM_KEY = "atmId";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko";
    private static final String ELEMENT_FILIALS = "filials";
    private static final String CITY_TAG = "h6";
    private static final String TABLE_TAG = "table";
    private static final String TABLE_DATA_TAG = "td";
    private static final String ATM_BEGIN_CUT_ELEMENT = "Банкомат Credit Agricole ";
    private static final String ATM_END_CUT_ELEMENT = "Примітка:";
    private static final String BV_BEGIN_CUT_ELEMENT = "м. ";
    private static final String BV_ELEMENT = "відділення";

    @Override
    public void setParameter(Map<String, String> parameters) {

        if (parameters.containsKey(REGION_KEY)){
            region = parameters.get(REGION_KEY);
        }

        if (parameters.containsKey(URL_KEY)){
            url = parameters.get(URL_KEY);
        }

        if (parameters.containsKey(BVID_KEY)){
            bvId = parameters.get(BVID_KEY);
        }

        if (parameters.containsKey(ATM_KEY)){
            atmId = parameters.get(ATM_KEY);
        }
    }

    @Override
    public List<AtmOffice> parse() throws IOException {
        logger.info("CrediAgricole start parsing");
        logger.info("Begin fetching data");
        List<String> bvList = getBv();
        List<String> atmList = getAtm();
        logger.info("End fetching data. Number of branches: "+bvList.size()+", number of ATMs: "+atmList.size()+";");
        List<AtmOffice> atmOffices = new LinkedList<>();
        if(!bvList.isEmpty()){
          for(String address : bvList){
              AtmOffice atm = new AtmOffice();
              atm.setAddress(address);
              atm.setType(IS_OFFICE);
              atm.setLastUpdated(new Timestamp(new Date().getTime()));
              atm.setState(NO_LOCATION);
              atmOffices.add(atm);
          }
        }

        if(!atmList.isEmpty()){
            for(String address : atmList){
                AtmOffice atm = new AtmOffice();
                atm.setAddress(address);
                atm.setType(IS_ATM);
                atm.setLastUpdated(new Timestamp(new Date().getTime()));
                atm.setState(NO_LOCATION);
                atmOffices.add(atm);
            }
        }

        logger.info("End parsing data. Number of parsing ATMs and branches is: "+ atmOffices.size());
        return atmOffices;
    }

    private List<String> getBv() throws IOException {
        List<String> bvString  = new ArrayList<>();
        Document mainPage = Jsoup.connect(url+"?type="+bvId+"&show=1&d_id="+region).userAgent(USER_AGENT).get();
        Element element =  mainPage.getElementById(ELEMENT_FILIALS);
        Elements tvbv = element.getElementsByTag(TABLE_TAG);
        for(Element bv : tvbv){
            Elements tableData = bv.getElementsByTag(TABLE_DATA_TAG);
            for(Element atmElement : tableData){
                if(atmElement.text().toLowerCase().contains(BV_ELEMENT)){
                    String tempOut = atmElement.text();
                    tempOut = tempOut.substring(tempOut.indexOf(BV_BEGIN_CUT_ELEMENT), tempOut.indexOf(ATM_END_CUT_ELEMENT));
                    bvString.add(tempOut);
                   }
            }
        }
        return bvString;
    }



    private  List<String> getAtm() throws IOException{
        List<String> atmString  = new ArrayList<>();
        Document mainPage = Jsoup.connect(url+"?type="+atmId+"&show=1&d_id="+region).userAgent(USER_AGENT).get();
        Element element =  mainPage.getElementById(ELEMENT_FILIALS);
        Elements sities = element.getElementsByTag(CITY_TAG);
        Elements atm = element.getElementsByTag(TABLE_TAG);
        for(int i=0 ; i<sities.size(); i++){
            Object[] sityObjects =  sities.toArray();
            Object[] atmObjects = atm.toArray();
            String sityName = ((Element) sityObjects[i]).text();
            Element branch = (Element) atmObjects[i];
            Elements tableElements = branch.getElementsByTag(TABLE_DATA_TAG);
            for(Element tableElement : tableElements){
                if(tableElement.hasText()){
                    String begin = tableElement.text().replaceAll(ATM_BEGIN_CUT_ELEMENT,"");
                    atmString.add(sityName + " " + begin.substring(0, begin.indexOf(ATM_END_CUT_ELEMENT)));
                }
            }
        }
        return atmString;
    }
}
