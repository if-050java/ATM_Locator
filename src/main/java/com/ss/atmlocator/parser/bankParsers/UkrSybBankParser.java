package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.IParser;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM_OFFICE;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_OFFICE;

/**
 * Created by Roman Vintonyak on 05.11.2014.
 */
public class UkrSybBankParser implements IParser {
    private final Logger logger = Logger.getLogger(UkrSybBankParser.class);
    public static final Set<String> requiredParams = new HashSet<>(Arrays.asList("url", "region"));
    public static final String TOTAL_BRANCHES_SELECTOR = "#ref_goggle_tab_branches span";
    public static final String TOTAL_ATMS_SELECTOR = "#ref_goggle_tab_atms span";
    public static final String BRANCHES_SELECTOR = "#goggle_tab_branches .address";
    public static final String ATMS_SELECTOR = "#goggle_tab_atms .address";
    public static final String CITY_PREFIX = "м.";
    public static final int RECORDS_PER_PAGE = 100;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko";
    public static final String REGION_ID = "region_id_13";
    public static final String SHOW_OFFICES = "type_branch_13";
    public static final String PAGE_OFFICES = "page_b_13";
    public static final String SHOW_ATMS = "type_atm_13";
    public static final String TYPE_ATMS_0 = "type_atm_id_13[0]";
    public static final String TYPE_ATMS_1 = "type_atm_id_13[1]";
    public static final String PAGE_ATMS = "page_a_13";
    public static final String FIRST_PAGE = "1";
    public static final String PARSE_NUMBER_REGEX = "\\D+";
    public static final int CONNECTION_TIMEOUT = 5 * 1000;
    private String url = "http://my.ukrsibbank.com/ua/branches_atms/map/";

    private Map<String, String> initSettings = new HashMap<>();

    public UkrSybBankParser() {
        initSettings.put(REGION_ID, "0");
        initSettings.put(SHOW_OFFICES, "1");
        initSettings.put(SHOW_ATMS, "1");
        initSettings.put(TYPE_ATMS_0, "7");
        initSettings.put(TYPE_ATMS_1, "8");
    }

    private Map<String, String> setPage(String page) {
        initSettings.put(PAGE_OFFICES, page);
        initSettings.put(PAGE_ATMS, page);
        return initSettings;
    }

    @Override
    public void setParameter(Map<String, String> parameters) {
        Set<String> keySet = parameters.keySet();
        if(!Collections.disjoint(keySet, requiredParams)){
            String url = parameters.get("url");
            String region_id = parameters.get("region");
            if(url != null){
                this.url = url;
                logger.debug("An url is changed to: " + url);
            };
            if(region_id != null){
                initSettings.put(REGION_ID,region_id);
                logger.debug("A region_id is changed to: " + region_id);
            }
        } else if(keySet.size()>0){
            IllegalArgumentException iae = new IllegalArgumentException("Illegal arguments!");
            logger.error(iae.getMessage(),iae);
            throw iae;
        }
    }

    @Override
    public List<AtmOffice> parse() throws IOException {
        List<AtmOffice> resultListAtms = new ArrayList<>();
        int totalAtms = getElementsCount(TOTAL_ATMS_SELECTOR);
        logger.debug("Found total ATMs : " + totalAtms);
        int totalBranches = getElementsCount(TOTAL_BRANCHES_SELECTOR);
        logger.debug("Found total branches : " + totalBranches);
        int totalAtmPages = getTotalPages(totalAtms);
        int totalBranchPages = getTotalPages(totalBranches);
        List<AtmOffice> branches = getListElements(totalBranchPages, BRANCHES_SELECTOR);
        List<AtmOffice> atms = getListElements(totalAtmPages, ATMS_SELECTOR);
        seperateAtmOffices(resultListAtms, branches, atms);
        logger.debug("Total branches added: " + resultListAtms.size());
        resultListAtms.addAll(atms);
        return resultListAtms;
    }

    private void seperateAtmOffices(List<AtmOffice> resultListAtms, List<AtmOffice> branches, List<AtmOffice> atms) {
        AtmOffice atmOffice;
        for (AtmOffice branch : branches) {
            atmOffice = branch;
            if (atms.contains(branch)) {
                atmOffice.setType(IS_ATM_OFFICE);
                atms.remove(branch);
                logger.debug("Found branch and ATM at same address: " + branch.getAddress());
            } else {
                atmOffice.setType(IS_OFFICE);
            }
            resultListAtms.add(atmOffice);
        }
    }

    private List<AtmOffice> getListElements(int totalPages, String selector) {
        Document document;
        List<AtmOffice> listElements = new ArrayList<>();
        AtmOffice.AtmType atmType = selector.equals(ATMS_SELECTOR) ? IS_ATM : IS_OFFICE;
        for (int page = 1; page <= totalPages; page++) {
            try {
                document = getDocument(setPage(String.valueOf(page)));
            } catch (IOException ioe) {
                logger.error(ioe.getMessage(), ioe);
                return null;
            }
            Elements elements = document.select(selector);
            for (Element element : elements) {
                listElements.add(new AtmOffice(CITY_PREFIX + element.text(), atmType));
            }
        }
        return listElements;
    }

    private int getTotalPages(int countElements) throws IOException {

        return (int) Math.ceil(countElements / (double) RECORDS_PER_PAGE);
    }

    private int getElementsCount(String selector) throws IOException {
        Document countAtms = getDocument(setPage(FIRST_PAGE));
        String textCount = countAtms.select(selector).text();
        int count = Integer.parseInt(textCount.replaceAll(PARSE_NUMBER_REGEX, ""));
        return count;
    }


    private Document getDocument(Map<String, String> params) throws IOException {
        return Jsoup.connect(url)
                .data(params)
                .userAgent(USER_AGENT)
                .timeout(CONNECTION_TIMEOUT)
                .get();
    }

    //It's method is used only for parser testing
    public static void main(String[] args) {
        UkrSybBankParser ukrSybBankParser = new UkrSybBankParser();
        Map<String,String> testParams = new HashMap<>();
        testParams.put("region", "13");
        ukrSybBankParser.setParameter(testParams);
        try {
            System.out.println(ukrSybBankParser.parse());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
