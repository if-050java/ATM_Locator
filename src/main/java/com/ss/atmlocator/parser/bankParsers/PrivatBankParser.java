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

    private String url;
    private String detailsUrl;

    private Properties propertiesFromFile = new Properties();
    private Properties parserProperties = new Properties();

    private static final String URL_PAR_NAME = "url.data";
    private static final String URL_DETAILS_PAR_NAME = "url.details";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko";

    private static final String POST_PARAMETER_NAME_COUNTRIES = "countries";
    private static final String POST_PARAMETER_NAME_DAY_WORK = "day_work";
    private static final String POST_PARAMETER_NAME_LINK_LANG = "link_lang";
    private static final String POST_PARAMETER_NAME_SEARCH = "search_string";
    private static final String POST_PARAMETER_NAME_TYPE = "type";
    private static final String POST_PARAMETER_NAME_DISTANCE = "distance";

    private static final String POST_PARAMETER_COUNTRIES = "ua";
    private static final String POST_PARAMETER_DAY_WORK = "all";
    private static final String POST_PARAMETER_LINK_LANG = "ua";
    private static final String POST_PARAMETER_SEARCH = "all";
    private static final String POST_PARAMETER_TYPE_ATM = "atm";
    private static final String POST_PARAMETER_TYPE_OFFICE = "branch";
    private static final String POST_PARAMETER_DISTANCE = "-1";

    private static final String DATA_CONTAINER = "body";

    private static final String ADDRESS_ELEMENT_XPATH = "html > body > div.adr_box > div.center > div.adr";

    private static final String POSTAL_CODE_REGEXP = ", \\d{5}";
    private static final String REMOVE_ADD_SPACES_REGEXP = ",[\\s]*";


    private List<AtmOffice> atmList = new ArrayList<>();

    int i=0;

    final static Logger logger = LoggerFactory.getLogger(PrivatBankParser.class);


    @Override
    public void setParameter(Map<String, String> parameters) throws IOException {
        loadProperties();
        for(String paramName : propertiesFromFile.stringPropertyNames()){
            if(parameters.containsKey(paramName)){
                parserProperties.put(paramName, parameters.get(paramName));
            }else {
                parserProperties.put(paramName, propertiesFromFile.get(paramName));
            }
        }
        url = parameters.containsKey(URL_PAR_NAME) ? parameters.get(URL_PAR_NAME) : parserProperties.getProperty(URL_PAR_NAME);
        detailsUrl = parameters.containsKey(URL_DETAILS_PAR_NAME) ? parameters.get(URL_DETAILS_PAR_NAME) : parserProperties.getProperty(URL_DETAILS_PAR_NAME);
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
        logger.info("Try to parse ATMs");
        Document document = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .data(getPostParameters(POST_PARAMETER_TYPE_ATM))
                .timeout(100000)
                .post();
        logger.info("Page " + url + " loaded");
        logger.info("Try to get ATMs from response");
        String jsonResponse = document.getElementsByTag(DATA_CONTAINER).text();
        ObjectMapper objectMapper = new ObjectMapper();
        PrivatBankApiResponse privatBankApiResponse = objectMapper.readValue(jsonResponse, PrivatBankApiResponse.class);
        logger.info("Loaded " + privatBankApiResponse.getItems().length + " ATMs");
        for(AtmItem atmItem : Arrays.copyOfRange(privatBankApiResponse.getItems(), 0, 20)){
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
        logger.info("Try to parse offices");
        Document document = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .data(getPostParameters(POST_PARAMETER_TYPE_OFFICE))
                .timeout(100000)
                .post();
        logger.info("Page " + url + " loaded");
        logger.info("Try to load offices from response");
        String jsonResponse = document.getElementsByTag(DATA_CONTAINER).text();
        ObjectMapper objectMapper = new ObjectMapper();
        PrivatBankApiResponse privatBankApiResponse = objectMapper.readValue(jsonResponse, PrivatBankApiResponse.class);
        logger.info("Loaded " + privatBankApiResponse.getItems().length + " offices");
        for(AtmItem atmItem : Arrays.copyOfRange(privatBankApiResponse.getItems(), 0, 20)){
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
    private void loadProperties() throws IOException {
        try {
            String dirPath = new ClassPathResource("parserProperties").getURI().getPath();
            String filePath = dirPath + "/privatBankParser.properties";
            logger.info("Try to load properties from file " + filePath);
            InputStream propFile = new FileInputStream(filePath);
            propertiesFromFile.load(propFile);
            logger.info("File successfully loaded.");
        }catch (IOException ioe){
            logger.error("Loading file failed.");
            throw ioe;
        }
    }

    /**
     *
     * @param type (atm or office)
     * @return map that define request parameters
     */
    private Map<String, String> getPostParameters(String type) throws IOException {
        Map<String, String> postData = new HashMap<>();
        postData.put(POST_PARAMETER_NAME_COUNTRIES, POST_PARAMETER_COUNTRIES);
        postData.put(POST_PARAMETER_NAME_DAY_WORK, POST_PARAMETER_DAY_WORK);
        postData.put(POST_PARAMETER_NAME_LINK_LANG, POST_PARAMETER_LINK_LANG);
        postData.put(POST_PARAMETER_NAME_SEARCH, POST_PARAMETER_SEARCH);
        postData.put(POST_PARAMETER_NAME_TYPE, type);

        for(String paramName : parserProperties.stringPropertyNames()){
            if(paramName.matches("^post\\.parameter\\.name\\..{1,}")){
                postData.put(paramName, (String)parserProperties.get(paramName.replace("name", "value")));
            }
        }
        return postData;
    }

    /**
     *
     * @param id
     * @return address of atm or office that is defined by @param id
     * @throws IOException if cant load page
     */
    private String getAddress(String id) throws IOException {
        Map<String, String> postData = new HashMap<>();
        postData.put(POST_PARAMETER_NAME_DISTANCE, POST_PARAMETER_DISTANCE);
        postData.put("id", id);
        postData.put(POST_PARAMETER_NAME_LINK_LANG, POST_PARAMETER_LINK_LANG);

        System.out.println("Try to get address for " + id);
        Document detailsDocument = Jsoup.connect(detailsUrl)
                .userAgent(USER_AGENT)
                .data(postData)
                .timeout(10000)
                .post();


        Element addressElement = detailsDocument.select(ADDRESS_ELEMENT_XPATH).get(0);

        String result = addressElement.child(0).text() + " " + addressElement.child(1).text();
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) throws IOException {
        IParser privatParser = new PrivatBankParser();
        privatParser.setParameter(Collections.EMPTY_MAP);
        privatParser.parse();
    }

    /**
     * @return formated address string based on @param rawAddress from site
     */
    private String  prepareAddress(String rawAddress){
        String address = rawAddress.replaceAll(REMOVE_ADD_SPACES_REGEXP, ", ");
        address = address.replaceFirst(POSTAL_CODE_REGEXP, "");
        return address.trim();
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


