package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.GeoPosition;
import com.ss.atmlocator.parser.ParserExecutor;
import com.ss.atmlocator.utils.AtmItem;
import com.ss.atmlocator.utils.PrivatBankApiResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;
import static com.ss.atmlocator.entity.AtmState.*;


public class PrivatBankParser extends ParserExecutor {


    private List<AtmOffice> atmList = new ArrayList<>();

    final static Logger logger = LoggerFactory.getLogger(PrivatBankParser.class);

    /**
     * Load all ATMs and offices and addresses for  them
     * @return list of ATMs and offices that were received from Privatbank site
     */
    @Override
    public List<AtmOffice> parse(){
        try {
            if(Boolean.parseBoolean(parserProperties.getProperty("parse.atms"))){
                atmList.addAll(parseAtms());
            }
            if(Boolean.parseBoolean(parserProperties.getProperty("parse.offices"))) {
                atmList.addAll(parseOffices());
            }
            logger.info("Parsing is done. Were parsed " + atmList.size());
            return atmList;
        }catch(IOException ioe){
            logger.error("Parsing failed by followed exception");
            logger.error(ioe.getMessage(), ioe);
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * loading ATMs from Privatbank site
     * @throws IOException if cant load ATMs page or address page
     */
    private List<AtmOffice> parseAtms() throws IOException {
        List<AtmOffice> atmList = new ArrayList<>();
        int timeout = Integer.parseInt(parserProperties.getProperty("reading.timeout"));
        logger.info("Try to parse ATMs");
        Document document = Jsoup.connect(parserProperties.getProperty("url.data"))
                .userAgent(parserProperties.getProperty("user.agent"))
                .data(getPostParameters(IS_ATM))
                .timeout(timeout)
                .post();
        logger.info("Page " + parserProperties.getProperty("url.data") + " loaded");
        logger.info("Try to get ATMs from response");
        String jsonResponse = document.body().text();
        ObjectMapper objectMapper = new ObjectMapper();
        PrivatBankApiResponse privatBankApiResponse = objectMapper.readValue(jsonResponse, PrivatBankApiResponse.class);
        logger.info("Loaded " + privatBankApiResponse.getItems().length + " ATMs");
        for(AtmItem atmItem : Arrays.copyOfRange(privatBankApiResponse.getItems(),0,20)){
                atmList.add(createAtm(atmItem, IS_ATM));
        }
        return atmList;
    }

    /**
     * loading offices from Privatbank site
     * @throws IOException if cant load offices's page or address page
     */
    private List<AtmOffice> parseOffices() throws IOException {
        List<AtmOffice> atmList = new ArrayList<>();
        int timeout = Integer.parseInt(parserProperties.getProperty("reading.timeout"));
        logger.info("Try to parse offices");
        Document document = Jsoup.connect(parserProperties.getProperty("url.data"))
                .userAgent(parserProperties.getProperty("user.agent"))
                .data(getPostParameters(IS_OFFICE))
                .timeout(timeout)
                .post();
        logger.info("Page " + parserProperties.getProperty("url.data") + " loaded");
        logger.info("Try to load offices from response");
        String jsonResponse = document.body().text();
        ObjectMapper objectMapper = new ObjectMapper();
        PrivatBankApiResponse privatBankApiResponse = objectMapper.readValue(jsonResponse, PrivatBankApiResponse.class);
        logger.info("Loaded " + privatBankApiResponse.getItems().length + " offices");
        for(AtmItem atmItem : Arrays.copyOfRange(privatBankApiResponse.getItems(), 0, 20)){
            AtmOffice office = createAtm(atmItem, IS_OFFICE);
            if(office != null)
                atmList.add(office);
        }
        return atmList;
    }

    /**
     * @param atmItem data about one atm or office received from PrivatBank
     * @param type atm or office
     * @return new atm made from atmItem data
     * @throws IOException if cant receive address from PrivatBank
     */
    private AtmOffice createAtm(AtmItem atmItem, AtmOffice.AtmType type){
        AtmOffice atm = new AtmOffice();
        atm.setType(type);
        atm.setGeoPosition(new GeoPosition(atmItem.getLng(), atmItem.getLat()));
        atm.setLastUpdated(new Timestamp(new Date().getTime()));
        atm.setState(atmItem.getIsActive() == 1 ? NORMAL : DISABLED);
        String address = null;
        try {
            address = prepareAddress(getAddress(atmItem.getId()));
        } catch (IOException ioe) {
            atm.setState(BAD_ADDRESS);
            logger.error("Parser can't receive address for item with id" + atmItem.getId() + "because of followed exception");
            logger.error(ioe.getMessage(), ioe);
        }
        if(address != null && isAtmAndOffice(address)){
            return null;
        }
        atm.setAddress(address);
        return atm;
    }


    /**
     * @param type defines atm or office
     * @return map that define request parameters for loading ATMs or offices
     */
    private Map<String, String> getPostParameters(AtmOffice.AtmType type) throws IOException {
        Map<String, String> postData = new HashMap<>();
        //add params
        for(String paramName : parserProperties.stringPropertyNames()){
            if(paramName.matches("^post\\.parameter\\.name\\..{1,}")){
                String postParamName = parserProperties.getProperty(paramName);
                String postParamValue = parserProperties.getProperty(paramName.replace("name", "value"));
                postData.put(postParamName, postParamValue);
            }
        }
        //Add type parameter
        String typeString;
        if(type == IS_ATM){
            typeString = (String)parserProperties.get("post.parameter.value.type.atm");
        }else {
            typeString = (String)parserProperties.get("post.parameter.value.type.office");
        }
        postData.put(parserProperties.getProperty("post.parameter.name.type"), typeString);
        return postData;
    }

    /**
     * @param id
     * @return address of atm or office that is defined by id
     * @throws IOException if cant load page
     */
    public String getAddress(String id) throws IOException {
        Map<String, String> postData = new HashMap<>();
        postData.put(parserProperties.getProperty("post.parameter.name.distance"), parserProperties.getProperty("post.parameter.value.distance"));
        postData.put("id", id);
        postData.put(parserProperties.getProperty("post.parameter.name.language"), parserProperties.getProperty("post.parameter.value.language"));

        int timeout = Integer.parseInt(parserProperties.getProperty("reading.timeout"));
        System.out.println("Try to get address for " + id);
        Document detailsDocument = Jsoup.connect(parserProperties.getProperty("url.details"))
                .userAgent(parserProperties.getProperty("user.agent"))
                .data(postData)
                .timeout(timeout)
                .post();

        Element addressElement = detailsDocument.select(parserProperties.getProperty("address.element.xpath")).get(0);

        String result = addressElement.child(0).text() + " " + addressElement.child(1).text();
        System.out.println(result);
        try {
            Thread.sleep(200);
        } catch (InterruptedException ie) {
            logger.error(ie.getMessage(), ie);
        }
        return result;
    }

    /**
     * @return formatted address string based on rawAddress
     * parameters for formatting get from properties
     */
    private String  prepareAddress(String rawAddress){
        String result = rawAddress;
        for(String paramName : parserProperties.stringPropertyNames()){
            if(paramName.matches("replace\\.regexp\\..*")){
                String regexp = parserProperties.getProperty(paramName);
                String replaceValue = parserProperties.getProperty(paramName.replace("regexp", "value"));
                result = result.replaceAll(regexp, replaceValue);
            }
        }
        return result.trim();
    }

    public static void main(String[] args) {
        PrivatBankParser privatBankParser = new PrivatBankParser();
        String s = privatBankParser.getClass().getSimpleName();
        String file  =s.substring(0,1).toLowerCase() + s.substring(1) + ".properties";
        System.out.println(file);
    }
    /**
     * @return true and mark atm as ATM_OFFICE if already has same address
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
}


