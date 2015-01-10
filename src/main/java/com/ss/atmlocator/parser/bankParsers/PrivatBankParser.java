package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.GeoPosition;
import com.ss.atmlocator.parser.IParser;
import com.ss.atmlocator.utils.AtmItem;
import com.ss.atmlocator.utils.PrivatBankApiResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;
import static com.ss.atmlocator.entity.AtmState.NORMAL;


public class PrivatBankParser implements IParser {

    private Properties propertiesFromFile = new Properties();

    private Properties parserProperties = new Properties();

    private static final String DATA_CONTAINER = "body";

    private List<AtmOffice> atmList = new ArrayList<>();

    final static Logger logger = LoggerFactory.getLogger(PrivatBankParser.class);

    @Override
    public void setParameter(Map<String, String> parameters){
        loadProperties();
        for(String paramName : propertiesFromFile.stringPropertyNames()){
            if(parameters.containsKey(paramName)){
                parserProperties.put(paramName, parameters.get(paramName));
            }else {
                parserProperties.put(paramName, propertiesFromFile.get(paramName));
            }
        }
    }

    @Override
    public List<AtmOffice> parse(){
        try {
            parseAtms();
            parseOffices();
            logger.info("Parsing is done. Were parsed " + atmList.size());
            return atmList;
        }catch(IOException ioe){
            logger.error("Parsing failed by followed exception");
            logger.error(ioe.getMessage(), ioe);
            return Collections.EMPTY_LIST;
        }
    }


    private void parseAtms() throws IOException {
        int timeout = Integer.parseInt(parserProperties.getProperty("reading.timeout"));
        logger.info("Try to parse ATMs");
        Document document = Jsoup.connect(parserProperties.getProperty("url.data"))
                .userAgent(parserProperties.getProperty("user.agent"))
                .data(getPostParameters(IS_ATM))
                .timeout(timeout)
                .post();
        logger.info("Page " + parserProperties.getProperty("url.data") + " loaded");
        logger.info("Try to get ATMs from response");
        String jsonResponse = document.getElementsByTag(DATA_CONTAINER).text();
        ObjectMapper objectMapper = new ObjectMapper();
        PrivatBankApiResponse privatBankApiResponse = objectMapper.readValue(jsonResponse, PrivatBankApiResponse.class);
        logger.info("Loaded " + privatBankApiResponse.getItems().length + " ATMs");
        for(AtmItem atmItem : privatBankApiResponse.getItems()){
            if(atmItem.getIs_active() == 1) {
                String rawAddress = getAddress(atmItem.getId());
                AtmOffice atm = new AtmOffice();
                atm.setAddress(prepareAddress(rawAddress));
                atm.setType(IS_ATM);
                atm.setGeoPosition(new GeoPosition(atmItem.getLng(), atmItem.getLat()));
                atm.setLastUpdated(new Timestamp(new Date().getTime()));
                atm.setState(NORMAL);
                atmList.add(atm);
            }
        }
    }

    private void parseOffices() throws IOException {
        int timeout = Integer.parseInt(parserProperties.getProperty("reading.timeout"));
        logger.info("Try to parse offices");
        Document document = Jsoup.connect(parserProperties.getProperty("url.data"))
                .userAgent(parserProperties.getProperty("user.agent"))
                .data(getPostParameters(IS_OFFICE))
                .timeout(timeout)
                .post();
        logger.info("Page " + parserProperties.getProperty("url.data") + " loaded");
        logger.info("Try to load offices from response");
        String jsonResponse = document.getElementsByTag(DATA_CONTAINER).text();
        ObjectMapper objectMapper = new ObjectMapper();
        PrivatBankApiResponse privatBankApiResponse = objectMapper.readValue(jsonResponse, PrivatBankApiResponse.class);
        logger.info("Loaded " + privatBankApiResponse.getItems().length + " offices");
        for(AtmItem atmItem : privatBankApiResponse.getItems()){
            if(atmItem.getIs_active() == 1) {
                String address = prepareAddress(getAddress(atmItem.getId()));
                if(isAtmAndOffice(address)){
                    continue;
                }
                AtmOffice office = new AtmOffice();
                office.setAddress(prepareAddress(address));
                office.setType(IS_OFFICE);
                office.setGeoPosition(new GeoPosition(atmItem.getLng(), atmItem.getLat()));
                office.setLastUpdated(new Timestamp(new Date().getTime()));
                office.setState(NORMAL);
                atmList.add(office);
            }
        }
    }

    /**
     * Load properties from file
     * @throws IOException if can't load
     */
    private void loadProperties(){
        try {
            String dirPath = new ClassPathResource("parserProperties").getURI().getPath();
            String filePath = dirPath + "/privatBankParser.properties";
            logger.info("Try to load properties from file " + filePath);
            InputStream propFile = new FileInputStream(filePath);
            propertiesFromFile.load(propFile);
            logger.info("File successfully loaded.");
        }catch (IOException ioe){
            logger.error("Loading file failed.");
        }
    }

    /**
     *
     * @param type (atm or office)
     * @return map that define request parameters
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
     *
     * @param id
     * @return address of atm or office that is defined by id
     * @throws IOException if cant load page
     */
    private String getAddress(String id) throws IOException {
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
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        IParser privatParser = new PrivatBankParser();
        privatParser.setParameter(Collections.EMPTY_MAP);
        privatParser.parse();
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
    /**
     * @return true and mark atm as ATM_OFFICE if already has same @param address
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


