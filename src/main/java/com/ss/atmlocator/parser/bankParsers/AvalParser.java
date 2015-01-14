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
    Map<String, String> parameters = new HashMap<>();

    Document doc = null;
    private static final boolean   IS_OFFICE = false;
    private static final boolean   IS_ATM = true;
    final static Logger logger = LoggerFactory.getLogger(AvalParser.class);
    private Map<String, String> cityCodeName=null;

    public AvalParser(){
        Map<String, String> param = new HashMap<>();
        param.put("cityName", "Ивано-Франковск");
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
                    tempAtm.setAddress(city+" "+address.text());
                    tempAtm.setType(AtmOffice.AtmType.IS_OFFICE);
                    tempAtm.setAddress(trimFirstNuber(tempAtm.getAddress()));
                }else {

                    tempAtm.setAddress( trimFirstNuber(address.text()));
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
        Map<String, String> cityCodeName=null;
        try {
            doc = Jsoup.connect(parserProperties.getProperty("url.for.city.codes")).userAgent(parserProperties.getProperty("address")).get();
            Elements addresses = doc.getElementsByClass("chzn-select");
            Elements address = addresses.select("option");
            cityCodeName = new HashMap<>();
            for(Element adr: address){
                cityCodeName.put(adr.text(), adr.val());
            }
        } catch (IOException ioe) {
            logger.error("creation city name and key - filed", ioe.getMessage());
        }
     return cityCodeName;
    }
    /**
     * @param cityName - it is a name of city
     * @param isAtm - type. if it is atm - true, else branch - false
     * @return method parseCity return list of atm`s by city name*/
    public List<AtmOffice> parseCity(String cityName, boolean isAtm){
        List<AtmOffice> atms=null;
        try {
        String cityKey=cityCodeName.get(cityName);
        String resultUrl;
        if(isAtm){
            resultUrl = parserProperties.getProperty("url.part1")+parserProperties.getProperty("atm")+parserProperties.getProperty("url.part2")+cityKey+parserProperties.getProperty("url.part3");
        }else{
            resultUrl = parserProperties.getProperty("url.part1")+parserProperties.getProperty("branch")+parserProperties.getProperty("url.part2")+cityKey+parserProperties.getProperty("url.part3");
        }

        atms = new ArrayList<>(parseAtm(resultUrl, isAtm, cityName));

        } catch (IOException ioe) {
           logger.error("parse city [ %s ] error ", cityName, ioe.getMessage());
        }
        return atms;

    }
    public static void main(String[] args) throws IOException {
        AvalParser avalParser=new AvalParser();
        Map<String, String> param = new HashMap<>();
        param.put("cityName", "Ивано-Франковск");
        avalParser.setParameter(param);
        List<AtmOffice> atmOffices =avalParser.parse();
        System.out.println("------------------------------------------------------");
        for (AtmOffice atm: atmOffices){
            System.out.println(atm);
        }
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
            type = parserProperties.getProperty("atm");
        }else{
            type= parserProperties.getProperty("branch");
        }
        List<AtmOffice> resultList=new ArrayList<AtmOffice>();
        Set<String> cityNames = cityCodeName.keySet();
        for(String cityName: cityNames){
            String cityKey=cityCodeName.get(cityName);
            String resultUrl =parserProperties.getProperty("url.part1")+type+parserProperties.getProperty("url.part2")+cityKey+parserProperties.getProperty("url.part3");

            resultList.addAll(parseAtm(resultUrl, IS_ATM, cityName));
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
        resultList.addAll(parseAllCity(IS_ATM));
        resultList.addAll(parseAllCity(IS_OFFICE));
        return resultList;
    }


    @Override
    public List<AtmOffice> parse() throws IOException {
        List<AtmOffice> resultList = new ArrayList<>();
        if(parameters.isEmpty()) {
            return parseAllCity();
        }else{
            String cityName = parameters.get("cityName");
            resultList.addAll(parseCity(cityName, IS_ATM));
            resultList.addAll(parseCity(cityName, IS_OFFICE));
            return resultList;
        }
    }
}
