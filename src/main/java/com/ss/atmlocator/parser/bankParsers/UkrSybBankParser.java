package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.ParserExecutor;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;

/**
 * This class is used to parsing ATMs and branches
 * from UkrSybBank official website
 */
public class UkrSybBankParser extends ParserExecutor {
    private final Logger logger = Logger.getLogger(UkrSybBankParser.class);
    private Map<String, String> initSettings = new HashMap<>();

    /**
     * Set params for the next page
     * @param page
     * @return
     */
    private Map<String, String> setPage(String page) {

        initSettings.put(getProp("url.page_offices"), page);
        initSettings.put(getProp("url.page_atms"), page);
        return initSettings;
    }

    /**
     * Init parameters
     */
    private void init() {
        initSettings.put(getProp("url.region.prop"), convertRegion(getProp(("region"))));
        initSettings.put(getProp("url.show_offices"), getProp("url.show_offices.val"));
        initSettings.put(getProp("url.show_atms"), getProp("url.show_atms.val"));
        initSettings.put(getProp("url.type_atms_0"), getProp("url.type_atms_0.val"));
        initSettings.put(getProp("url.type_atms_1"), getProp("url.type_atms_1.val"));
    }

    /**
     * Returns list of parsed ATMs and offices
     * @throws IOException
     */
    @Override
    public List<AtmOffice> parse() throws IOException {
        init();
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
        resultListAtms.addAll(atms);
        logger.debug("Total parsed: " + resultListAtms.size());
        return resultListAtms;
    }

    /**
     * Seperates offices with ATMs
     * @param resultListAtms merged list
     * @param branches list of offices
     * @param atms list of atms
     */
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

    /**
     * Returns list of elements by given selector
     * @param totalPages  total pages on site
     * @param selector html selector
     * @return list of atms and offices
     */
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

    /**
     * Returns total count of paging pages
     * @param countElements
     * @return count of paging pages
     * @throws IOException
     */
    private int getTotalPages(int countElements) throws IOException {

        return (int) Math.ceil(countElements / Double.valueOf(getProp("global.records_per_page")));
    }

    /**
     * Returns total count of elements
     * @param selector html selector
     * @throws IOException
     */
    private int getElementsCount(String selector) throws IOException {
        Document countAtms = getDocument(setPage(getProp("global.first_page")));
        String textCount = countAtms.select(selector).text();
        int count = Integer.parseInt(textCount.replaceAll(getProp("global.parse_number_regex"), ""));
        return count;
    }

    /**
     * Returns instance Document
     * @param params intit params
     * @throws IOException
     */
    private Document getDocument(Map<String, String> params) throws IOException {
        return Jsoup.connect(getProp("global.url"))
                .data(params)
                .userAgent(getProp("global.user_agent"))
                .timeout(Integer.valueOf(getProp("global.connection_timeout")))
                .get();
    }

    /**
     * Converts region name to region id
     * @param region name of region
     * @return
     */
    private String convertRegion(String region){
        Document document;
        try {
            document = Jsoup.connect(getProp("global.url"))
                    .userAgent(getProp("global.user_agent"))
                    .timeout(Integer.valueOf(getProp("global.connection_timeout")))
                    .get();
            Elements elements = document.select(getProp("selector.regions"));
            for(Element element : elements) {
                if(element.text().equalsIgnoreCase(region)) return element.attr("value");
            }
        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        }
        return getProp("region.all");
    }

}
