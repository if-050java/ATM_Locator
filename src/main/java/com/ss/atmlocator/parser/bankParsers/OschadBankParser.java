package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.IParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;

/**
 * Created by Olavin on 14.12.2014.
 * Parse OschadBank web-site to gel list of branches and ATMs
 */
public final class OschadBankParser implements IParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(OschadBankParser.class);

    private static final String USER_AGENT_PARAM =
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0";
    private static final String LAST_PAGE_SELECTOR = "font.text > a:nth-last-child(1)";
    //private static final String SET_FILTER_VALUE = "\u0428\u0443\u043A\u0430\u0442\u0438"; //"Шукати"
    private static final String SET_FILTER_VALUE = "Шукати";
    private static final String SET_FILTER_PARAM = "set_filter";
    private static final String REGION_PARAM = "arrFilter_pf[RegionName]";
    private static final String PAGE_PARAM = "PAGEN_1";
    private static final int ADDRESS_COLUMN_BRANCH = 1;
    private static final int VIDDIL_COLUMN_BRANCH = 0;
    private static final int ADDRESS_COLUMN_ATM = 0;
    private static final int VIDDIL_COLUMN_ATM = 2;
    private static final int MAX_ROWS_AT_PAGE = 20;
    private static final String REGION_SEPARATOR = ", ";
    private static final String VIDDIL_PATTERN = "(\\d{5})\\s*/+0*(\\d+)";
    private static final String LOCALITY_PATTERN = "(м\\.|с\\.|смт)\\s+(.+?),";
    private static final String ADDRESS_PATTERN = "Ів\\.-.*Франківськ";
    private static final String ADDRESS_SUBSTITUTE = "Івано-Франківськ";

    private Map<String, String> parameters;

    @Override
    public void setParameter(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<AtmOffice> parse() {
        List<AtmOffice> atms = new ArrayList<>();

        String branchPage = parameters.get("base_url") + parameters.get("branch_page");
        String branchSelector = parameters.get("branch_selector");

        String atmPage = parameters.get("base_url") + parameters.get("atm_page");
        String atmSelector = parameters.get("atm_selector");

        List<String> regions = new ArrayList<>();

        String testRegion = parameters.get("region");
        // if empty - parse all regions subsequently
        if (testRegion.equals("")) {
            try {
                // get Branch Page before fetching regions list to satisfy the bank's web server
                Jsoup.connect(parameters.get("base_url")).execute();
                regions = getRegionList();
            } catch (IOException e) {
                //e.printStackTrace();
                LOGGER.error("Cannot fetch regions list: " + e.getMessage(), e);
            }
        } else {
            regions.add(testRegion);
        }

        RegionParser branchParser =
                new RegionParser(branchPage, branchSelector, ADDRESS_COLUMN_BRANCH, VIDDIL_COLUMN_BRANCH);
        RegionParser atmParser =
                new RegionParser(atmPage, atmSelector, ADDRESS_COLUMN_ATM, VIDDIL_COLUMN_ATM);

        for (int i = 0; i < regions.size(); i++) {
            String region = regions.get(i);
            LOGGER.info(String.format("Begin parse region: #%d %s", i, region));
            try {
                LinkedList<OschadBankItem> branchList = branchParser.parseRegion(region);
                LinkedList<OschadBankItem> atmList = atmParser.parseRegion(region);
                atms.addAll(mergeOschadBankItems(region, branchList, atmList));
            } catch (IOException e) {
                //e.printStackTrace();
                LOGGER.error("Connection error: " + e.getMessage(), e);
            }
            LOGGER.info("End parse region");
        }

        return atms;
    }

    private boolean equalLocality(@NotNull final OschadBankItem item1, @NotNull final OschadBankItem item2) {
        Pattern p = Pattern.compile(LOCALITY_PATTERN);
        Matcher m1 = p.matcher(item1.getAddress());

        if (m1.find()) {
            String locality1 = m1.group(2);
            Matcher m2 = p.matcher(item2.getAddress());
            if (locality1 != null && m2.find()) {
                String locality2 = m2.group(2);
                return locality1.equals(locality2);
            }
        }
        return false;
    }

    List<AtmOffice> mergeOschadBankItems(@NotNull final String regionName,
                                         @NotNull final LinkedList<OschadBankItem> branchList,
                                         @NotNull final LinkedList<OschadBankItem> atmList) {
        List<AtmOffice> atms = new ArrayList<>();
        int branchAndAtmCount = 0;

        for (OschadBankItem branchItem : branchList) {
            if (branchItem != null) {
                AtmOffice atmOffice = new AtmOffice();
                atmOffice.setAddress(regionName + REGION_SEPARATOR + branchItem.getAddress());
                int index = atmList.indexOf(branchItem);
                if (index >= 0 && equalLocality(branchItem, atmList.get(index))) {
                    LOGGER.debug(String.format("Found branch and ATM at same address: %s and %s",
                            branchItem.toString(), atmList.get(index)));
                    atmOffice.setType(IS_ATM_OFFICE);
                    atmList.remove(index);
                    branchAndAtmCount++;
                } else {
                    atmOffice.setType(IS_OFFICE);
                }
                atms.add(atmOffice);
            }
        }
        LOGGER.info(String.format("%s: %d branches added (including %d with ATM)",
                regionName, atms.size(), branchAndAtmCount));

        for (OschadBankItem atmItem : atmList) {
            if (atmItem != null) {
                AtmOffice atmOffice = new AtmOffice();
                atmOffice.setAddress(regionName + REGION_SEPARATOR + atmItem.getAddress());
                atmOffice.setType(IS_ATM);
                atms.add(atmOffice);
            }
        }
        LOGGER.info(String.format("%s: %d ATMs added", regionName, atmList.size()));
        LOGGER.info(String.format("%s: %d total branches and ATMs added", regionName, atms.size()));

        return atms;
    }

    class RegionParser {
        private String pageUrl;
        private String selector;
        private int addressColumn;
        private int viddilColumn;

        RegionParser(final String pageUrl, final String selector, final int addressColumn, final int viddilColumn) {
            this.pageUrl = pageUrl;
            this.selector = selector;
            this.addressColumn = addressColumn;
            this.viddilColumn = viddilColumn;
        }

        Connection createJsoupConnection(final String regionName, final int pageNum) {
            return Jsoup.connect(pageUrl)
                    .data(REGION_PARAM, regionName)
                    .data("ib", "atms_ua")
                    .data(SET_FILTER_PARAM, SET_FILTER_VALUE)
                    .data(PAGE_PARAM, (pageNum > 1) ? String.valueOf(pageNum) : "");
        }

        private LinkedList<OschadBankItem> parseRegion(@NotNull final String regionName) throws IOException {
            LinkedList<OschadBankItem> addressList = new LinkedList<>();
            // get count of pages from first page
            int pageCount = 0;
            try {
                pageCount = getPageCount(createJsoupConnection(regionName, 1));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            } catch (ParseException e) {
                LOGGER.warn(e.getMessage());
            }
            for (int page = 1; page <= pageCount; page++) {
                LOGGER.debug(String.format("Region: {%s} page %d/%d", regionName, page, pageCount));
                Connection connection = createJsoupConnection(regionName, page);
                addressList.addAll(parseTable(connection));
            }
            return addressList;
        }

        private List<OschadBankItem> parseTable(final Connection connection) throws IOException {
            List<OschadBankItem> addressList = new ArrayList<>(MAX_ROWS_AT_PAGE);
            Connection.Response response = connection.execute();
            LOGGER.trace("Request URL: " + connection.request().url());
            Elements rows = response.parse().select(selector);
            for (Element row : rows) {
                String address = prepareAddress(row.child(addressColumn).text());
                String viddil = prepareViddil(row.child(viddilColumn).text());
                OschadBankItem item = new OschadBankItem(address, viddil);
                addressList.add(item);
                LOGGER.trace("OschadBank item: " + item.toString());
            }
            LOGGER.debug(String.format("Parsed %d items", addressList.size()));
            return addressList;
        }

        private String prepareAddress(final String address) {
            return (address == null) ? null : address.replaceFirst(ADDRESS_PATTERN, ADDRESS_SUBSTITUTE).trim();
        }

        @Nullable
        private String prepareViddil(final String viddil) {
            Matcher m = Pattern.compile(VIDDIL_PATTERN).matcher(viddil);
            return m.find() ? m.group(1) + "/" + m.group(2) : null;
        }

        private int getPageCount(final Connection connection) throws ParseException, IOException {
            Connection.Response response = connection.execute();
            Elements elems = response.parse().select(LAST_PAGE_SELECTOR);
            if (elems.size() == 0) {
                throw new ParseException("Can't parse last page number", 0);
            }
            String lastPageUrl = elems.get(0).attr("href");
            int index = lastPageUrl.lastIndexOf('=');
            int lastPageNum = Integer.parseInt(lastPageUrl.substring(index + 1));
            return (lastPageNum > 0 ? lastPageNum : 1);
        }

    }

    private List<String> getRegionList() throws IOException {
        Set<String> regions = new HashSet<>();
        Connection.Response response = createRegionListRequest().execute();
        Document document = Jsoup.parse(response.parse().outerHtml());

        JSONObject jsonObj = (JSONObject) JSONValue.parse(document.text());
        if (jsonObj != null) {
            JSONArray jsonArray = (JSONArray) jsonObj.get("results");
            if (jsonArray != null) {
                for (Object aJsonArray : jsonArray) {
                    JSONObject jsonItem = (JSONObject) aJsonArray;
                    String regionStr = (String) jsonItem.get("id");
                    regions.add(regionStr.trim());
                }
            }
        }
        ArrayList<String> regionList = new ArrayList<>(regions.size());
        regionList.addAll(regions);
        Collections.sort(regionList);
        return regionList;
    }

    private Connection createRegionListRequest() {
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

    public static void main(final String[] args) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("base_url", "http://www.oschadnybank.com/");
        parameters.put("branch_page", "ua/branches_atms/branches/");
        parameters.put("atm_page", "ua/branches_atms/atms/");
        parameters.put("branch_selector", ".table_block > table:nth-child(1) > tbody:nth-child(1) > .news-item");
        parameters.put("atm_selector", ".table_block > table:nth-child(1) > tbody:nth-child(1) > .news-item");

        //parameters.put("region","");
        //parameters.put("region", "Івано-Франківська область");
        //parameters.put("region","АР Крим");
        parameters.put("region", "Донецька область");

        OschadBankParser bankParser = new OschadBankParser();
        bankParser.setParameter(parameters);

        List<AtmOffice> atms = bankParser.parse();
        Collections.sort(atms, new Comparator<AtmOffice>() {
            public int compare(final AtmOffice a1, final AtmOffice a2) {
                return a1.getAddress().compareTo(a2.getAddress());
            }
        });

        for (AtmOffice atm : atms) {
            System.out.printf("%d - %s\n", atm.getType().ordinal(), atm.getAddress());
        }

    }

}
