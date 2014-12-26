package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
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
public class KredoBankParser implements IParser {

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
    private static final String REGION_ELEMENTS_CLASS = "white_back";

    private static final String GET_REGION_URL = "/xml/coord_data/s/";
    private static final String GET_CITY_URL = "/xml/coord_data/a/";
    private static final String ATM_PAGE_URL = "/our_coordinates.html";

    private static final String ATM_TYPE_ATM = "банкомат";
    private static final int CITY_ID_CHILD = 1;


    private static final int TYPE_CHILD = 0;
    private static final int ADDRESS_CHILD = 1;

    private static final int STREET_PART = 0;
    private static final int CITY_PART = 1;

    private static final int REGION_ID_STRING_START_POSITION = 2;
    private static final int REGION_DIV_CHILD = 0;
    private static final int REGION_NAME_ELEMENT = 1;

    private static final String POSTAL_CODE_REGEXP = ", \\d{5}";
    private static final String REMOVE_SPACES_REGEXP = "\\s{1,}";
    private static final String REMOVE_SPACE_AFTER_DOT = "\\. ";

    private final Logger logger = Logger.getLogger(KredoBankParser.class);

    private String bankSite;
    private List<String> regions = new ArrayList<String>();
    private List<AtmOffice> atmList = new ArrayList<>();


    /**
     * @return list AtmOffices that was parsed from given URL and is included to given regions
     * @throws IOException if couldn't load given URL(URL is bad or site don't work at this time)
     */
    public List<AtmOffice> parse() throws IOException {
        try{
            logger.info("Try to load start page " + bankSite + ATM_PAGE_URL);
            Document mainPage = Jsoup.connect(bankSite + ATM_PAGE_URL).get();
            int parsedRegions = 0;
            for(String region : regions){
                Elements regionRows = mainPage.getElementsByClass(REGION_ELEMENTS_CLASS);
                for(Element regionRow : regionRows){
                    String regionID = regionRow.id().substring(REGION_ID_STRING_START_POSITION);
                    Element regionDiv = regionRow.child(REGION_DIV_CHILD);
                    Element regionNameElement = regionDiv.child(REGION_NAME_ELEMENT);
                    if(region.equals(regionNameElement.text())){
                        logger.info("Try to parse atmList from region " + region);
                        parseRegion(bankSite + GET_REGION_URL+regionID);
                        parsedRegions++;
                    }//end if
                }//end for regionRows
            }//end for regions
            logger.info("Parsing is done. Was parsed " + atmList.size() + " atmList and offices");
            if(regions.size() != parsedRegions){
                logger.warn("Regions given " + regions.size() + "regions parsed " + parsedRegions);
            }
            return atmList;
        }catch(IOException ioe){
            logger.error(ioe.getMessage(), ioe);
            throw ioe;
        }
    }

    /**
     * Parse region that is defined by @param request to kredobank rest api
     * and get URLs for cities parser
     */
    private void parseRegion(String request){
        logger.info("Try to connect to URL "+request);
        try{
            Document regionXML = Jsoup.connect(request).userAgent(USER_AGENT)
                                                       .referrer(bankSite)
                                                       .method(Connection.Method.GET)
                                                       .execute().parse();
            Elements cityItem = regionXML.getElementsByTag(ATM_HTML_TAG);
            for(Element city : cityItem){
                logger.info("Try to parse atmList from city " + city.child(0).text());
                parseCity(bankSite+GET_CITY_URL + city.child(CITY_ID_CHILD).text());
            }
        }catch(IOException ioe){
            logger.error(ioe.getMessage(), ioe);
        }
    }

    /**
     *Parse city that is defined by @param request to kredobank rest api
     * and add all atmList from this city to list
     */
    private void parseCity(String request){
        logger.info("Try to connect to URL "+request);
        try{
            Document cityXML = Jsoup.connect(request).userAgent(USER_AGENT)
                                                     .referrer(bankSite)
                                                     .method(Connection.Method.GET)
                                                     .execute().parse();
            Elements atmItems = cityXML.getElementsByTag(ATM_HTML_TAG);
            for(Element atmItem : atmItems){
                String address = prepareAddress(atmItem.child(ADDRESS_CHILD).text());
                if(isAtmAndOffice(address)){
                    continue;
                }

                AtmOffice atm = new AtmOffice();
                atm.setAddress(address);
                atm.setType(atmItem.child(TYPE_CHILD).text().matches(ATM_TYPE_ATM) ? IS_ATM : IS_OFFICE);
                atm.setLastUpdated(new Timestamp(new Date().getTime()));

                atmList.add(atm);
            }

        }catch(IOException ioe){
            logger.error(ioe.getMessage(), ioe);
        }
    }

    /**
     * @return formated address string based on @param rawAddress from site
     */
    private String  prepareAddress(String rawAddress){
        String[] addressArray = rawAddress.split(ADDRESS_SEPARATOR);
        String address = addressArray[STREET_PART]+addressArray[CITY_PART];
        address = address.replaceFirst(POSTAL_CODE_REGEXP,"");
        address = address.replaceAll(REMOVE_SPACE_AFTER_DOT, ".");
        address = address.replaceAll(REMOVE_SPACES_REGEXP," ");
        return address.trim();
    }

    /**
     *
     * @return true if already has same @param address
     * it means that this address has both atm and office
     */
    private boolean isAtmAndOffice(String address){
        for(AtmOffice atm : atmList){
            if(atm.getAddress().equals(address)){
                atm.setType(IS_ATM_OFFICE);
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
        String regionsString = parameters.get(requiredParameters.REGIONS.getValue());
        for(String region : regionsString.split(REGIONS_SEPARATOR)){
            regions.add(region.trim());
        }
        bankSite = parameters.get(requiredParameters.URL.getValue());
    }

    /**
     *  Check all @param parameters sanded to parser and  @throws IllegalArgumentException
     *  if any parameters isn't preset, null or empty
     */
    private void checkParameters(Map<String, String> parameters){
        for(requiredParameters parameter : requiredParameters.values()){
            if(! isPreset(parameters, parameter.getValue())){
                IllegalArgumentException iae = new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE + parameter);
                logger.error(iae.getMessage(), iae);
                throw iae;
            }
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
}
