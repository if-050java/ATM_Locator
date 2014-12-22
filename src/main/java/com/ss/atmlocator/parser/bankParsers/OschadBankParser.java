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
    private final static String SET_FILTER_VALUE = "\u0428\u0443\u043A\u0430\u0442\u0438"; //"Шукати"
    private final static String SET_FILTER_PARAM = "set_filter";
    private final static String REGION_PARAM = "arrFilter_pf[RegionName]";
    private final static String PAGE_PARAM = "PAGEN_1";
    private final static int ADDRESS_COLUMN_BRANCH = 1;
    private final static int VIDDIL_COLUMN_BRANCH = 0;
    private final static int ADDRESS_COLUMN_ATM = 0;
    private final static int VIDDIL_COLUMN_ATM = 2;
    private final static int MAX_ROWS_AT_PAGE = 20;
    private final static String REGION_SEPARATOR = ", ";
    private final static String VIDDIL_PATTERN = "(\\d{5})\\s*/+0*(\\d+)";

    private Map<String,String> parameters;

    @Override
    public void setParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<AtmOffice> parse(){
        List<AtmOffice> atms = new ArrayList<>();

        String branchPage = parameters.get("base_url")+parameters.get("branch_page");
        String branchSelector = parameters.get("branch_selector");

        String atmPage = parameters.get("base_url")+parameters.get("atm_page");
        String atmSelector = parameters.get("atm_selector");

        String testRegion = parameters.get("region");
        List<String> regions = new ArrayList<>();

        // if empty - parse all regions subsequently
        if(testRegion.equals("")){
            // get Branch Page before fetching regions list to satisfy the bank's web server
            try {
                Jsoup.connect(parameters.get("base_url")).execute();
                regions = getRegionList();
            } catch (IOException e) {
                //e.printStackTrace();
                logger.error("Cannot fetch regions list: "+e.getMessage());
            }
        } else {
            regions = new ArrayList<>();
            regions.add(testRegion);
        }

        RegionParser branchParser = new RegionParser(branchPage, branchSelector, ADDRESS_COLUMN_BRANCH, VIDDIL_COLUMN_BRANCH);
        RegionParser atmParser = new RegionParser(atmPage, atmSelector, ADDRESS_COLUMN_ATM, VIDDIL_COLUMN_ATM);

        for (int i=0; i < regions.size(); i++){
            String region = regions.get(i);
            logger.info(String.format("Begin parse region: #%d %s", i, region));
            try {
                LinkedList<OschadBankItem> branchList = branchParser.parseRegion(region);
                LinkedList<OschadBankItem> atmList = atmParser.parseRegion(region);
                atms.addAll(mergeOschadBankItems(region, branchList, atmList));
            } catch (IOException e) {
                //e.printStackTrace();
                logger.error("Connection error: " + e.getMessage());
            }
            logger.info("End parse region");
        }

        return atms;
    }

    List<AtmOffice> mergeOschadBankItems(String regionName, LinkedList<OschadBankItem> branchList, LinkedList<OschadBankItem> atmList){
        List<AtmOffice> atms = new ArrayList<>();
        int branchAndAtmCount = 0;

        for(OschadBankItem branchItem : branchList){
            AtmOffice atmOffice = new AtmOffice();
            atmOffice.setAddress(regionName+REGION_SEPARATOR+branchItem.getAddress());
            int index = atmList.indexOf(branchItem);
            if (index >= 0){
                logger.debug(String.format("Found branch and ATM at same address: %s and %s", branchItem.toString(), atmList.get(index)));
                atmOffice.setType(IS_ATM_OFFICE);
                atmList.remove(index);
                branchAndAtmCount++;
            } else {
                atmOffice.setType(IS_OFFICE);
            }
            atms.add(atmOffice);
        }
        logger.info(String.format("%s: %d branches added (including %d with ATM)", regionName, atms.size(), branchAndAtmCount));

        for(OschadBankItem atmItem : atmList){
            AtmOffice atmOffice = new AtmOffice();
            atmOffice.setAddress(regionName+REGION_SEPARATOR+atmItem.getAddress());
            atmOffice.setType(IS_ATM);
            atms.add(atmOffice);
        }
        logger.info(String.format("%s: %d ATMs added", regionName, atmList.size()));
        logger.info(String.format("%s: %d total branches and ATMs added", regionName, atms.size()));

        return atms;
    }

    class RegionParser {
        private String pageUrl;
        private String selector;
        private int addressColumn;
        private int viddilColumn;

        RegionParser(String pageUrl, String selector, int addressColumn, int viddilColumn){
            this.pageUrl = pageUrl;
            this.selector = selector;
            this.addressColumn = addressColumn;
            this.viddilColumn = viddilColumn;
        }

        Connection createJsoupConnection(String regionName, int pageNum) {
            return Jsoup.connect(pageUrl)
                    .data(REGION_PARAM, regionName)
                    .data("ib","atms_ua")
                    .data(SET_FILTER_PARAM, SET_FILTER_VALUE)
                    .data(PAGE_PARAM,(pageNum > 1) ? String.valueOf(pageNum) : "");
        }

        private LinkedList<OschadBankItem> parseRegion(String regionName) throws IOException {
            LinkedList<OschadBankItem> addressList = new LinkedList<>();
            // get count of pages from first page
            int pageCount = getPageCount(createJsoupConnection(regionName, 1));
            for (int page = 1; page <= pageCount; page++){
                logger.debug(String.format("Region: {%s} page %d/%d", regionName, page, pageCount));
                Connection connection = createJsoupConnection(regionName, page);
                addressList.addAll(parseTable(connection));
            }
            return addressList;
        }

        private List<OschadBankItem> parseTable(Connection connection) throws IOException {
            List<OschadBankItem> addressList = new ArrayList<>(MAX_ROWS_AT_PAGE);
            Connection.Response response = connection.execute();
            logger.debug("Request URL: "+connection.request().url());
            Elements rows = response.parse().select(selector);
            for (Element row : rows) {
                String address = prepareAddress(row.child(addressColumn).text()); //regionName + REGION_SEPARATOR +
                String viddil = prepareViddil(row.child(viddilColumn).text());
                OschadBankItem item = new OschadBankItem(address, viddil);
                addressList.add(item);
                logger.debug("OschadBank item: " + item.toString());
            }
            return addressList;
        }

        private String prepareAddress(String address) {
            //TODO: replace with parameters
            return (address == null) ? null : address.replaceFirst("Ів\\.-.*Франківськ", "Івано-Франківськ").trim();
        }
        private String prepareViddil(String viddil){
            Matcher m = Pattern.compile(VIDDIL_PATTERN).matcher(viddil);
            return m.find() ? m.group(1)+"/"+m.group(2) : null;
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

    }

    private List<String> getRegionList() throws IOException {
        Set<String> regions = new HashSet<>();
        ArrayList<String> regionList = new ArrayList<>();

        Connection.Response response = createRegionListRequest().execute();
        Document document = Jsoup.parse(response.parse().outerHtml());

        JSONObject JsonObj = (JSONObject)JSONValue.parse(document.text());
        if(JsonObj != null){
            JSONArray jsonArray = (JSONArray)JsonObj.get("results");
            if(jsonArray != null){
                for (Object aJsonArray : jsonArray) {
                    JSONObject JsonItem = (JSONObject) aJsonArray;
                    String regionStr = (String) JsonItem.get("id");
                    regions.add(regionStr.trim());
                }
            }
        }
        regionList = new ArrayList<>(regions.size());
        regionList.addAll(regions);
        Collections.sort(regionList);

        return regionList;

    }

    private Connection createRegionListRequest(){
        return Jsoup.connect("http://www.oschadnybank.com/handlers/region1.php")
                .data("contentType", "application/json; charset=utf-8")
                //.data("ib", "atms_ua")
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
        parameters.put("base_url","http://www.oschadnybank.com/");
        parameters.put("branch_page","ua/branches_atms/branches/");
        parameters.put("atm_page","ua/branches_atms/atms/");
        parameters.put("branch_selector",".table_block > table:nth-child(1) > tbody:nth-child(1) > .news-item");
        parameters.put("atm_selector",".table_block > table:nth-child(1) > tbody:nth-child(1) > .news-item");

        //parameters.put("region","");
        //parameters.put("region","Івано-Франківська область");
        //parameters.put("region","АР Крим");
        parameters.put("region","Донецька область");

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
