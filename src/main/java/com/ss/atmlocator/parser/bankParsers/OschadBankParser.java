package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.IParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;

/**
 * Created by Olavin on 14.12.2014.
 */
public class OschadBankParser implements IParser {
    private final static Logger logger = LoggerFactory.getLogger(OschadBankParser.class);
    private final static String USER_AGENT_PARAM = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0";
    private final static String LAST_PAGE_SELECTOR = "font.text > a:nth-last-child(1)";
    private final static String SET_FILTER_PARAM = "\u0428\u0443\u043A\u0430\u0442\u0438"; //"Шукати"
    private final static int ADDRESS_COLUMN_BRANCH = 1;
    private final static int VIDDIL_COLUMN_BRANCH = 0;
    private final static int ADDRESS_COLUMN_ATM = 0;
    private final static int VIDDIL_COLUMN_ATM = 2;
    private final static int MAX_ROWS_AT_PAGE = 20;
    private final static String REGION_SEPARATOR = ", ";

    private Map<String,String> parameters;

    @Override
    public void setParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<AtmOffice> parse(){
        List<AtmOffice> atms = new ArrayList<>();

        try {
            String branchPage = parameters.get("base_url")+parameters.get("branch_page");
            String branchSelector = parameters.get("branch_selector");

            String atmPage = parameters.get("base_url")+parameters.get("atm_page");
            String atmSelector = parameters.get("atm_selector");

            logger.debug("Total branch pages: " + getPageCount(Jsoup.connect(branchPage)));
            logger.debug("Total atm pages: " + getPageCount(Jsoup.connect(atmPage)));

            List<String> regions = getRegionList();
            for (int i=0; i < regions.size(); i++){
                logger.debug(String.format("Region: #%d %s",i,regions.get(i)));
            }

            String testRegion = regions.get(24);
            LinkedList<OschadBankItem> branchList = parseRegion(branchPage, testRegion, branchSelector, ADDRESS_COLUMN_BRANCH, VIDDIL_COLUMN_BRANCH);
            LinkedList<OschadBankItem> atmList = parseRegion(atmPage, testRegion, atmSelector, ADDRESS_COLUMN_ATM, VIDDIL_COLUMN_ATM);

            for(OschadBankItem branchItem : branchList){
                AtmOffice atmOffice = new AtmOffice();
                atmOffice.setAddress(branchItem.getAddress());
                int index = atmList.indexOf(branchItem);
                if (index >= 0){
                    logger.debug(String.format("Found branch and ATM at same address: %s and %s", branchItem.toString(), atmList.get(index)));
                    atmOffice.setType(IS_ATM_OFFICE);
                    atmList.remove(branchItem);
                } else {
                    atmOffice.setType(IS_OFFICE);
                }
                atms.add(atmOffice);
            }
            logger.debug("Total branches added: "+atms.size());
            for(OschadBankItem atmItem : atmList){
                AtmOffice atmOffice = new AtmOffice();
                atmOffice.setAddress(atmItem.getAddress());
                atmOffice.setType(IS_ATM);
                atms.add(atmOffice);
            }
            logger.debug("Total branches and ATMs added: "+atms.size());

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return atms;
    }

    Connection createJsoupConnection(String pageUrl, String regionName, int pageNum) {
        return Jsoup.connect(pageUrl)
                .data("arrFilter_pf[RegionName]",regionName)
                .data("ib","atms_ua")
                .data("set_filter",SET_FILTER_PARAM)
                .data("PAGEN_1",(pageNum > 1) ? String.valueOf(pageNum) : "");
    }

    private LinkedList<OschadBankItem> parseRegion(String pageUrl, String regionName, String selector, int addressColumn, int viddilColumn){
        LinkedList<OschadBankItem> addressList = new LinkedList<>();
        try {
            Connection connection;
            int pageCount = getPageCount(createJsoupConnection(pageUrl, regionName, 1));
            for (int page = 1; page <= pageCount; page++){
                logger.debug(String.format("Region: {%s} page %d/%d", regionName, page, pageCount));
                connection = createJsoupConnection(pageUrl, regionName, page);
                addressList.addAll(parseTable(connection, selector, addressColumn, viddilColumn, regionName));
            }

        } catch (IOException e) {
            logger.error("Connection error: "+e.getMessage());
        }
        return addressList;
    }

    private List<OschadBankItem> parseTable(Connection connection, String rowsSelector, int addressColumn, int viddilColumn, String regionName) throws IOException {
        List<OschadBankItem> addressList = new ArrayList<>(MAX_ROWS_AT_PAGE);
        Connection.Response response = connection.execute();
        logger.debug("Request URL: "+connection.request().url());
        Elements rows = response.parse().select(rowsSelector);
        for (Element row : rows) {
            //String address = regionName + REGION_SEPARATOR + replaceAddress(row.child(addressColumn).text());
            OschadBankItem item = new OschadBankItem();
            item.setAddress(row.child(addressColumn).text());
            item.setViddil(row.child(viddilColumn).text());
            addressList.add(item);
            logger.debug("OschadBank: "+item.toString());
        }
        return addressList;
    }

    private String replaceAddress(String source){
        return source.replaceFirst("Ів\\.-.*Франківськ","Івано-Франківськ");
    }

    private int getPageCount(Connection connection) throws IOException {
        Connection.Response response = connection.execute();
        Elements elems = response.parse().select(LAST_PAGE_SELECTOR);
        if (elems.size() == 0){
            throw new IOException();
        }
        String lastPageUrl = elems.get(0).attr("href");
        int index = lastPageUrl.lastIndexOf('=');
        int lastPageNum = Integer.parseInt(lastPageUrl.substring(index + 1));
        return (lastPageNum > 0 ? lastPageNum : 1);
    }

    private static List<String> getRegionList(){
        Set<String> regions = new HashSet<>();
        ArrayList<String> regionList = new ArrayList<>(regions.size());
        try {
            Connection.Response response = createRegionListRequest().execute();
            Document document = Jsoup.parse(response.parse().outerHtml());

            JSONObject JsonObj = (JSONObject)JSONValue.parse(document.text());
            JSONArray jsonArray = (JSONArray)JsonObj.get("results");
            JSONObject JsonItem;
            String regionStr;
            for (int i=0; i<jsonArray.size(); i++){
                JsonItem = (JSONObject) jsonArray.get(i);
                regionStr = (String) JsonItem.get("id");
                regions.add(regionStr.trim());
            }
            regionList = new ArrayList<>(regions.size());
            regionList.addAll(regions);
            Collections.sort(regionList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return regionList;

    }

    private static Connection createRegionListRequest(){
        return Jsoup.connect("http://www.oschadnybank.com/handlers/region1.php")
                .data("contentType", "application/json; charset=utf-8")
                .data("ib", "atms_ua")
                .data("p", "1")
                .data("s", "15")
                .method(Connection.Method.GET)
                .header("Accept", "application/json")
                .header("Host", "www.oschadnybank.com")
                .referrer("http://www.oschadnybank.com/ua/branches_atms/atms/")
                .header("X-Requested-With", "XMLHttpRequest")
                .userAgent(USER_AGENT_PARAM)
                .ignoreContentType(true);
    }

    public static void main(String[] args){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("base_url","http://www.oschadnybank.com/ua/branches_atms/");
        parameters.put("branch_page","branches/");
        parameters.put("atm_page","atms/");
        parameters.put("branch_selector",".table_block > table:nth-child(1) > tbody:nth-child(1) > .news-item");
        parameters.put("atm_selector",".table_block > table:nth-child(1) > tbody:nth-child(1) > .news-item");

        OschadBankParser bankParser = new OschadBankParser();
        bankParser.setParameter(parameters);

        List<AtmOffice> atms = bankParser.parse();
        Collections.sort(atms,new Comparator<AtmOffice>()
        {
            public int compare(AtmOffice a1, AtmOffice a2)
            {
                return a1.getAddress().compareTo(a2.getAddress());
            }
        });

        for(AtmOffice atm : atms){
            System.out.printf("%d - %s\n", atm.getType().ordinal(), atm.getAddress());
        }

    }

}
