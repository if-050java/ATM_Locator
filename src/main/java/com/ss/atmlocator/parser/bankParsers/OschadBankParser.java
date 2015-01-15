package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.ParserExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;

/**
 * Created by Olavin on 14.12.2014.
 * Parse OschadBank website to gel list of branches and ATMs
 */
public class OschadBankParser extends ParserExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OschadBankParser.class);

    /**
     * main entry point - parse OschadBank web site for ATMs and Branches addresses
     * @return list of AtmOffice entity
     */
    @Override
    public List<AtmOffice> parse() {
        List<String> regions = new ArrayList<>();

        String singleRegion = parserProperties.getProperty("region");
        // if empty - parse all regions subsequently
        if (singleRegion.equals("")) {
            try {
                // get Branch Page before fetching regions list to satisfy the bank's web server
                Jsoup.connect(parserProperties.getProperty("url.base")).execute();
                regions = getRegionList();
            } catch (IOException e) {
                //e.printStackTrace();
                LOGGER.error("Cannot fetch regions list: " + e.getMessage(), e);
            }
        } else {
            regions.add(singleRegion);
        }

        RegionParser branchParser = new RegionParser(
                        parserProperties.getProperty("url.base") + parserProperties.getProperty("page.branch"),
                        parserProperties.getProperty("selector.branch"),
                        Integer.parseInt(parserProperties.getProperty("column.address.branch")),
                        Integer.parseInt(parserProperties.getProperty("column.viddil.branch"))
                );

        RegionParser atmParser = new RegionParser(
                        parserProperties.getProperty("url.base") + parserProperties.getProperty("page.atm"),
                        parserProperties.getProperty("selector.atm"),
                        Integer.parseInt(parserProperties.getProperty("column.address.atm")),
                        Integer.parseInt(parserProperties.getProperty("column.viddil.atm"))
                );

        List<AtmOffice> atms = new ArrayList<>();

        for (int i = 0; i < regions.size(); i++) {
            String region = regions.get(i);
            LOGGER.info(String.format("Begin parse region: #%d %s", i, region));
            try {
                LinkedList<OschadBankItem> branchList = branchParser.parseRegion(region);
                LinkedList<OschadBankItem> atmList = atmParser.parseRegion(region);
                atms.addAll(mergeOschadBankItems(region, branchList, atmList));
            } catch (IOException e) {
                LOGGER.error("Connection error: " + e.getMessage(), e);
            }
            LOGGER.info("End parse region");

            // Sleep only if have more then one region to parse
            if(regions.size() > 1) {
                try {
                    Thread.sleep(Integer.parseInt(parserProperties.getProperty("regions.delay")));
                } catch (InterruptedException ie) {
                    LOGGER.warn(ie.getMessage(), ie);
                }
            }

        }

        return atms;
    }

    /**
     * Compare two Oschadbank item whether are at one location
     * @param item1 - Oschadbank Branch or ATM address item
     * @param item2 - Oschadbank Branch or ATM address item
     * @return true if items have equal location
     */
    private boolean equalLocality(@NotNull final OschadBankItem item1, @NotNull final OschadBankItem item2) {
        Pattern p = Pattern.compile(parserProperties.getProperty("pattern.locality"));
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


    /**
     * Merge two lists of Branches and ATMs to one list
     * @param regionName
     * @param branchList
     * @param atmList
     * @return merged list
     */
    List<AtmOffice> mergeOschadBankItems(@NotNull final String regionName,
                                         @NotNull final LinkedList<OschadBankItem> branchList,
                                         @NotNull final LinkedList<OschadBankItem> atmList) {
        List<AtmOffice> atms = new ArrayList<>();
        int branchAndAtmCount = 0;

        for (OschadBankItem branchItem : branchList) {
            if (branchItem != null) {
                AtmOffice atmOffice = new AtmOffice();
                atmOffice.setAddress(regionName + parserProperties.getProperty("separator.region") + branchItem.getAddress());
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
                atmOffice.setAddress(regionName + parserProperties.getProperty("separator.region") + atmItem.getAddress());
                atmOffice.setType(IS_ATM);
                atms.add(atmOffice);
            }
        }
        LOGGER.info(String.format("%s: %d ATMs added", regionName, atmList.size()));
        LOGGER.info(String.format("%s: %d total branches and ATMs added", regionName, atms.size()));

        return atms;
    }

    /**
     *  Parser for one region
     */
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

        /**
         * Creates Jsoup connection
         * @param regionName
         * @param pageNum
         * @return
         */
        Connection createJsoupConnection(final String regionName, final int pageNum) {
            return Jsoup.connect(pageUrl)
                .data(parserProperties.getProperty("region.param"), regionName)
                .data("ib", "atms_ua")
                .data(parserProperties.getProperty("setfilter.param"), parserProperties.getProperty("setfilter.value"))
                .data(parserProperties.getProperty("page.param"), (pageNum > 1) ? String.valueOf(pageNum) : "")
                .timeout(Integer.parseInt(parserProperties.getProperty("reading.timeout")));
        }

        /**
         * Main mathod to parse one region
         * @throws IOException
         */
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

        /**
         * Parse one page of ATMs or Branched at Oschadbank web-site
         * @param connection JSoup connection
         * @return parsed list of OschadBankItem
         * @throws IOException
         */
        private List<OschadBankItem> parseTable(final Connection connection) throws IOException {
            List<OschadBankItem> addressList
                    = new ArrayList<>(Integer.parseInt(parserProperties.getProperty("page.maxrows")));
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

        /**
         * @return formatted address string based on rawAddress
         * parameters for formatting get from properties
         */
        private String  prepareAddress(String rawAddress) {
            String result = rawAddress;
            for (String paramName : new TreeSet<>(parserProperties.stringPropertyNames())) {
                if (paramName.matches("replace\\.regexp\\..*")) {
                    String regexp = parserProperties.getProperty(paramName);
                    String replaceValue = parserProperties.getProperty(paramName.replace("regexp", "value"));
                    result = result.replaceAll(regexp, replaceValue);
                }
            }
            return result.trim();
        }

        /**
         * Parse Branch or ATMs unique office number
         * @param viddil
         * @return
         */
        @Nullable
        private String prepareViddil(final String viddil) {
            Matcher m = Pattern.compile(parserProperties.getProperty("pattern.viddil")).matcher(viddil);
            return m.find() ? m.group(1) + "/" + m.group(2) : null;
        }

        /**
         * get count of web-pages with addresses for one region
         * @param connection
         * @return number of pages
         * @throws ParseException
         * @throws IOException
         */
        private int getPageCount(final Connection connection) throws ParseException, IOException {
            Connection.Response response = connection.execute();
            Elements elems = response.parse().select(parserProperties.getProperty("selector.lastpage"));
            if (elems.size() == 0) {
                throw new ParseException("Can't parse last page number", 0);
            }
            String lastPageUrl = elems.get(0).attr("href");
            int index = lastPageUrl.lastIndexOf('=');
            int lastPageNum = Integer.parseInt(lastPageUrl.substring(index + 1));
            return (lastPageNum > 0 ? lastPageNum : 1);
        }

    }

    /**
     * get list of region Names from Oschadbank website
     * @return list of region Names
     * @throws IOException
     */
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

    /**
     * Create Jsoup connection for AJAX request of region Names to Oschadbank website
     * @return
     */
    private Connection createRegionListRequest() {
        return Jsoup.connect("http://www.oschadnybank.com/handlers/region1.php")
                .data("contentType", "application/json; charset=utf-8")
                .data("p", "1")
                .data("s", "15")
                .method(Connection.Method.GET)
                .header("Accept", "application/json")
                .header("Host", "www.oschadnybank.com")
                .referrer("http://www.oschadnybank.com/ua/branches_atms/atms/")
                .header("X-Requested-With", "XMLHttpRequest")
                .userAgent(parserProperties.getProperty("user.agent"))
                .ignoreContentType(true);
    }

}
