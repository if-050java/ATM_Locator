package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.IParser;
import com.ss.atmlocator.parser.ParserExecutor;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM_OFFICE;
import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_OFFICE;

/**
 * Created by Roman Vintonyak on 05.11.2014.
 */
public class UkrSybBankParser extends ParserExecutor {
    private final Logger logger = Logger.getLogger(UkrSybBankParser.class);
    private Map<String, String> initSettings = new HashMap<>();


    public UkrSybBankParser() {
        setParameter(Collections.EMPTY_MAP);
        initSettings.put(getProp("url.region_id"), getProp("url.region_id.val"));
        initSettings.put(getProp("url.show_offices"), getProp("url.show_offices.val"));
        initSettings.put(getProp("url.show_atms"), getProp("url.show_atms.val"));
        initSettings.put(getProp("url.type_atms_0"), getProp("url.type_atms_0.val"));
        initSettings.put(getProp("url.type_atms_1"), getProp("url.type_atms_1.val"));
    }

    private Map<String, String> setPage(String page) {
        initSettings.put(getProp("url.page_offices"), page);
        initSettings.put(getProp("url.page_atms"), page);
        return initSettings;
    }

    @Override
    public void setParameter(Map<String, String> parameters){
        Properties fromFile = loadProperties("ukrSybBankParser.properties");
        for(String paramName : fromFile.stringPropertyNames()){
            if(parameters.containsKey(paramName)){
                parserProperties.put(paramName, parameters.get(paramName));
                parameters.remove(paramName);
            }else {
                parserProperties.put(paramName, fromFile.get(paramName));
            }
        }
        parserProperties.putAll(parameters);
    }

    @Override
    public List<AtmOffice> parse() throws IOException {
        List<AtmOffice> resultListAtms = new ArrayList<>();
        int totalAtms = getElementsCount(getProp("selector.total_atms"));
        logger.debug("Found total ATMs : " + totalAtms);
        int totalBranches = getElementsCount(getProp("selector.total_branches"));
        logger.debug("Found total branches : " + totalBranches);
        int totalAtmPages = getTotalPages(totalAtms);
        int totalBranchPages = getTotalPages(totalBranches);
        List<AtmOffice> branches = getListElements(totalBranchPages, getProp("selector.branches"));
        List<AtmOffice> atms = getListElements(totalAtmPages, getProp("selector.atms"));
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
        AtmOffice.AtmType atmType = selector.equals(getProp("selector.atms")) ? IS_ATM : IS_OFFICE;
        for (int page = 1; page <= totalPages; page++) {
            try {
                document = getDocument(setPage(String.valueOf(page)));
            } catch (IOException ioe) {
                logger.error(ioe.getMessage(), ioe);
                return null;
            }
            Elements elements = document.select(selector);
            for (Element element : elements) {
                listElements.add(new AtmOffice(element.text(), atmType));
            }
        }
        return listElements;
    }

    private int getTotalPages(int countElements) throws IOException {

        return (int) Math.ceil(countElements / Double.valueOf(getProp("global.records_per_page")));
    }

    private int getElementsCount(String selector) throws IOException {
        Document countAtms = getDocument(setPage(getProp("global.first_page")));
        String textCount = countAtms.select(selector).text();
        int count = Integer.parseInt(textCount.replaceAll(getProp("global.parse_number_regex"), ""));
        return count;
    }


    private Document getDocument(Map<String, String> params) throws IOException {
        return Jsoup.connect(getProp("global.url"))
                .data(params)
                .userAgent(getProp("global.user_agent"))
                .timeout(Integer.valueOf(getProp("global.connection_timeout")))
                .get();
    }
    private String getProp(String prop){
        return (String) parserProperties.get(prop);
    }

    public static void main(String[] args) throws IOException {
        UkrSybBankParser parser = new UkrSybBankParser();
        System.out.println(parser.parse());
    }
}
