package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parser.IParser;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;

/**
 * Created by Vasyl Danylyuk on 05.11.2014.
 */
public class CredoBankParser implements IParser {

    private enum requiredParameters{
        REGIONS("regions"),
        URL("url");

        private String value;

        requiredParameters(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }

    private static final String REGIONS_SEPARATOR = ",";
    private static final String ADDRESS_SEPARATOR = "<br />";

    private static final String ILLEGAL_ARGUMENT_MESSAGE = "Required parameter not specified or empty: ";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko";
    private static final String ATM_HTML_TAG = "item";

    private static final String ATM_TYPE_ATM = "банкомат";
    private static final String ATM_TYPE_OFFICE = "Відділення";

    private static final int TYPE = 0;
    private static final int ADDRESS = 1;

    private static final int STREET = 0;
    private static final int CITY = 1;

    private final Logger logger = Logger.getLogger(CredoBankParser.class);

    private String startURL;
    private List<String> regions = new ArrayList<String>();
    private Document mainPage;
    private List<AtmOffice> ATMs = new ArrayList<>();
    private int bankId;

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public void parseMainPage(){

        try{
            logger.trace("Завантажуємо початкову сторінку");
            mainPage = Jsoup.connect(startURL).get();
            logger.trace("Початкову сторінку завантажено");
            for(String region : regions){
                Elements regionRows = mainPage.getElementsByClass("white_back");
                for(Element regionRow : regionRows){
                    String regionID = regionRow.id().substring(2);
                    Element regionDiv = regionRow.child(0);
                    Element regionDivLink = regionDiv.children().get(1);
                    if(regionDivLink.text().equals(region)){
                        parseRegion("http://www.kredobank.com.ua/xml/coord_data/s/"+regionID+"/");
                    }
                }
            }

        }catch (IOException IOE){
            logger.error("Сторінку не вдалось завантажити");
        }

    }

    private void parseRegion(String request){
        try{
            Document regionXML = Jsoup.connect(request).userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                                                        .referrer("http://www.kredobank.com.ua/our_coordinates.html#")
                                                        .method(Connection.Method.GET)
                                                        .execute().parse();
            Elements cityItem = regionXML.getElementsByTag("item");
            for(Element city : cityItem){
                parseCity("http://www.kredobank.com.ua/xml/coord_data/a/"+city.child(1).text()+"/");
            }
        }catch(IOException IOE){
            logger.error("Сторінку не вдалось завантажити");
        }
    }

    /**
     *Parse city that is defined by @param request to kredobank rest api
     * and add all ATMs from this city to list
     */
    private void parseCity(String request){
        try{
            Document cityXML = Jsoup.connect(request).userAgent(USER_AGENT)
                                                     .referrer(startURL)
                                                     .method(Connection.Method.GET)
                                                     .execute().parse();
            Elements ATMItems = cityXML.getElementsByTag(ATM_HTML_TAG);
            for(Element ATMItem : ATMItems){
                String[] addressArray = ATMItem.child(ADDRESS).text().split(ADDRESS_SEPARATOR);
                String address = addressArray[CITY]+addressArray[STREET];
                if(isAtmAndOffice(address)){
                    continue;
                }

                AtmOffice ATM = new AtmOffice();
                ATM.setAddress(address);
                ATM.setType(ATMItem.child(TYPE).text().matches(ATM_TYPE_ATM) ? IS_ATM : IS_OFFICE);
                ATM.setLastUpdated(new Timestamp(new Date().getTime()));

                ATMs.add(ATM);
            }

        }catch(IOException IOE){

        }
    }

    /**
     *
     * @return true if already has same @param address
     * it means that this address has both ATM and office
     */
    private boolean isAtmAndOffice(String address){
        for(AtmOffice ATM : ATMs){
            if(ATM.getAddress().equals(address)){
                ATM.setType(IS_ATM_OFFICE);
                return true;
            }
        }
        return false;
    }

    /**
     * Setting @param parameters for parser
     */
    @Override
    public void setParameter(Map<String, String> parameters) {
        checkParameters(parameters);
        String regionsString = parameters.get(requiredParameters.REGIONS);
        for(String region : regionsString.split(REGIONS_SEPARATOR)){
            regions.add(region.trim());
        };
        startURL = parameters.get(requiredParameters.URL);
    }

    /**
     *  Check all @param parameters sanded to parser and  @throws IllegalArgumentException
     *  if any parameters isn't preset, null or empty
     */
    private void checkParameters(Map<String, String> parameters) throws IllegalArgumentException{
        for(requiredParameters parameter : requiredParameters.values()){
            if(! isPreset(parameters, parameter.getValue())){
                IllegalArgumentException iae = new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE + parameter);
                logger.error(iae.getMessage(), iae);
                throw iae;
            };
        }
    }

    /**
     * @return true if @param parameter is in map @param parameters, isn't null and not empty
     */
    private boolean isPreset( Map<String, String> parameters, String parameter){
        if(! parameters.containsKey(parameter)){
            return false;
        }
        if(parameters.get(parameter) == null){
            return false;
        }
        if(parameters.get(parameter).isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public List<Bank> parse() {
        return null;
    }
}
