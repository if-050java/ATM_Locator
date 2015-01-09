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

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM_OFFICE;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_OFFICE;
import static com.ss.atmlocator.entity.AtmState.NORMAL;


public class PrivatBankParser implements IParser {

    private enum requiredParameters{
        URL("get_atm_url"),
        GET_DETAILS_URL("get_details_url");

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

    private String url;
    private String getDetailsUrl;
    private static final String ILLEGAL_ARGUMENT_MESSAGE = "Required parameter not specified or empty: ";

    private List<AtmOffice> atmList = new ArrayList<>();

    int i=0;

    final static Logger logger = LoggerFactory.getLogger(PrivatBankParser.class);

    @Override
    public void setParameter(Map<String, String> parameters) {
        checkParameters(parameters);
        url = parameters.get(requiredParameters.URL.getValue());
        getDetailsUrl = parameters.get(requiredParameters.GET_DETAILS_URL.getValue());
    }

    @Override
    public List<AtmOffice> parse() throws IOException {
        parseAtms();
        parseOffices();
        return atmList;
    }

    private void parseAtms() throws IOException {
        Map<String, String> postData = new HashMap<>();
        postData.put("countries", "ua");
        postData.put("day_work", "all");
        postData.put("link_lang", "ua");
        postData.put("search_string", "all");
        postData.put("type", "atm");
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .data(postData)
                .post();
        String jsonResponse = document.getElementsByTag("body").text();
        ObjectMapper objectMapper = new ObjectMapper();
        PrivatBankApiResponse privatBankApiResponse = objectMapper.readValue(jsonResponse, PrivatBankApiResponse.class);
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
        Map<String, String> postData = new HashMap<>();
        postData.put("countries", "ua");
        postData.put("day_work", "all");
        postData.put("link_lang", "ua");
        postData.put("search_string", "all");
        postData.put("type", "branch");
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .data(postData)
                .post();
        String jsonResponse = document.getElementsByTag("body").text();
        ObjectMapper objectMapper = new ObjectMapper();
        PrivatBankApiResponse privatBankApiResponse = objectMapper.readValue(jsonResponse, PrivatBankApiResponse.class);
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

    private String getAddress(String id) throws IOException {
        Map<String, String> postData = new HashMap<>();
        postData.put("distance", "-1");
        postData.put("id", id);
        postData.put("link_lang", "ua");


        System.out.println("send request for " + id + " " + i++);
        Document detailsDocument = Jsoup.connect(getDetailsUrl)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .data(postData)
                .post();

        Element addressElement = detailsDocument.select("html > body > div.adr_box > div.center > div.adr").get(0);

        String result = addressElement.child(0).text() + " " + addressElement.child(1).text();
        return result;
    }

    public static void main(String[] args) throws IOException {
        IParser privatParser = new PrivatBankParser();
        privatParser.parse();
    }

    /**
     * @return formated address string based on @param rawAddress from site
     */
    private String  prepareAddress(String rawAddress){
        String address = rawAddress.replaceAll(",[\\s]*", ", ");
        address = address.replaceFirst(", \\d{5}", "");
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


