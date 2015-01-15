package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.ParserExecutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * This class parses ATMs and branches  Raiffeizen Bank Aval
 */
public class AvalParser extends ParserExecutor{
//    Map<String, String> parameters = new HashMap<>();

    Document doc = null;
    private static final boolean IT_IS_OFFICE = true;
    private static final boolean IT_IS_ATM = false;
    final static Logger logger = LoggerFactory.getLogger(AvalParser.class);
    private Map<String, String> cityCodeName=null;

    public AvalParser(){
        Map<String, String> param = new HashMap<>();
//        param.put("cityName", "Ивано-Франковск");
        setParameter(param);
        cityCodeName = parseCityNameAndKey();
    }

    /**
     * method using jsoup parses branches and ATMs in a particular city
     * @param url - url where are atm`s or branches
     * @param  isOffice - type of atm (atm or branch)
     * @param  city - city name, city where we parse atm`s
     * @return Set of AtmOffice
     * @throws java.io.IOException */
    private Set<AtmOffice> parseAtm(String url, boolean isOffice, String city) throws IOException {
        Set<AtmOffice> atms=new HashSet<>(); // set, because the page may contain duplicate addresses.
        try {
            doc = Jsoup.connect(url).userAgent(parserProperties.getProperty("user.agent")).get();
            Elements addresses = doc.getElementsByClass(parserProperties.getProperty("address"));
            for (Element address : addresses) {
                AtmOffice tempAtm = new AtmOffice();
                if (isOffice) {
                    tempAtm.setAddress(address.text());
                    tempAtm.setType(AtmOffice.AtmType.IS_OFFICE);
                    tempAtm.setAddress(trimFirstNuber(tempAtm.getAddress()));
                }else {
                    tempAtm.setAddress(city+" "+trimFirstNuber(address.text()));
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM);
                }
                atms.add(tempAtm);
            }
            logger.trace(" number of atm - %d", atms.size());
         return atms;

        } catch (IOException ioe) {
            logger.error("[PARSER] Parser cen`t connect to URL %s", ioe.getMessage(),ioe);
            throw ioe;
        }
    }


    private String trimFirstNuber(String address) {
        return address.replaceFirst("\\d+,"," ");
    }
    /**
     * Method create map of parameters where key - city name, value - city key.
     * @return map of parameters where key - city name, value - city key*/
     Map<String, String> parseCityNameAndKey(){
         logger.info("try load city name and code");
        Map<String, String> cityCodeName=null;
        try {
            doc = Jsoup.connect(parserProperties.getProperty("url.for.city.codes")).userAgent(parserProperties.getProperty("address")).get();
            Elements addresses = doc.getElementsByClass("chzn-select");
            Elements address = addresses.select("option");
            cityCodeName = new HashMap<>();
            for(Element adr: address){
                cityCodeName.put(adr.text(), adr.val());
            }
            logger.info(" %d  cities are parsed: name and code  ", cityCodeName.size());
        } catch (IOException ioe) {
            logger.error("creation city name and key - filed", ioe.getMessage());
        }
     return cityCodeName;
    }
    /**
     * @param cityName - it is a name of city
     * @param isOffice - type. if it is atm - true, else branch - false
     * @return method parseCity return list of atm`s by city name*/
    public List<AtmOffice> parseCity(String cityName, boolean isOffice){
        logger.info("try parse atm`s in city "+cityName);
        List<AtmOffice> atms=null;
        try {
        String cityKey=cityCodeName.get(cityName);
        String resultUrl;
        if(isOffice){
            resultUrl = parserProperties.getProperty("url.part1")+parserProperties.getProperty("branch")+parserProperties.getProperty("url.part2")+cityKey+parserProperties.getProperty("url.part3");
        }else{
            resultUrl = parserProperties.getProperty("url.part1")+parserProperties.getProperty("atm")+parserProperties.getProperty("url.part2")+cityKey+parserProperties.getProperty("url.part3");
        }

        atms = new ArrayList<>(parseAtm(resultUrl, isOffice, cityName));

        } catch (IOException ioe) {
           logger.error("parse city [ %s ] error ", cityName, ioe.getMessage());
        }
        logger.info( atms.size()+" - atm`s are parsed");
        return atms;
    }

    /**
     * method parses all the cities on the same type: ATMs or branches
     * @param isAtm - - type. if it is atm - true, else branch - false
     * @return - list of AtmOffice.
     * @throws java.io.IOException
     */

    public  List<AtmOffice> parseAllCity(boolean isAtm) throws IOException {
        String type;
        if(isAtm){
            type = getProp("branch");
        }else{
            type= getProp("atm");
        }
        List<AtmOffice> resultList=new ArrayList<AtmOffice>();
        Set<String> cityNames = cityCodeName.keySet();
        for(String cityName: cityNames){
            String cityKey=cityCodeName.get(cityName);

            String resultUrl =getProp("url.part1")+type+getProp("url.part2")+cityKey+getProp("url.part3");

            resultList.addAll(parseAtm(resultUrl, IT_IS_ATM, cityName));
            try {
                Thread.sleep(30);
            } catch (InterruptedException ioe) {
                logger.info(ioe.getMessage(), ioe);
            }

        }
        return resultList;
    }

    /**
     * method parses all the cities
     * @return  list of AtmOffice.
     * @throws IOException
     */
    public  List<AtmOffice> parseAllCity() throws IOException {
        List<AtmOffice> resultList=new ArrayList<AtmOffice>();
        resultList.addAll(parseAllCity(IT_IS_ATM));
        resultList.addAll(parseAllCity(IT_IS_OFFICE));
        return resultList;
    }


    @Override
    public List<AtmOffice> parse() throws IOException {
        List<AtmOffice> resultList = new ArrayList<>();
        if("true".equals(getProp("all_regions"))) {
            return parseAllCity();
        }else{
            String cityName = getProp("city_name");
            resultList.addAll(parseCity(cityName, IT_IS_ATM));
            resultList.addAll(parseCity(cityName, IT_IS_OFFICE));
            return resultList;
        }
    }
    private String getProp(String prop) {
        return String.valueOf(parserProperties.get(prop));
    }
}
