package com.ss.atmlocator.parser;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Olavin on 14.12.2014.
 */
public class OschadBankParser implements IParser {
    final String LAST_PAGE_SELECTOR = "font.text > a:nth-last-child(1)";
    //final String CHARACTER_ENCODING = "UTF-8";
    final String SET_FILTER_PARAM = "\u0428\u0443\u043A\u0430\u0442\u0438"; //"Шукати"
    final int ADDRESS_COLUMN_BRANCH = 1;
    final int ADDRESS_COLUMN_ATM = 0;

    private Map<String,String> parameters;

    @Override
    public void setParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<AtmOffice> parse(){
        List<AtmOffice> atms = new ArrayList<>();
        List<String> regions = getRegionList();
        for(int i=0; i < regions.size(); i++){
            System.out.println(i+". "+regions.get(i));
        }

        //String ifr2 = "Івано-Франківська область";
        //String ifr = "\u0406\u0432\u0430\u043D\u043E-\u0424\u0440\u0430\u043D\u043A\u0456\u0432\u0441\u044C\u043A\u0430 \u043E\u0431\u043B\u0430\u0441\u0442\u044C";
        //System.out.println("[ "+ifr + "=" + ifr2+" ]");
        String testRegion = regions.get(2);

        String page = parameters.get("base_url")+parameters.get("branch_page");
        String selector = parameters.get("branch_selector");
        List<String> addressList = parseRegion(page, testRegion, selector);

        return atms;
    }

    private Elements getElementsList(Connection connection, String selector) throws IOException {
        Connection.Response response = connection.execute();
        return response.parse().select(selector);
    }

    private List<String> parseTable(Connection connection, String selector) throws IOException {
        List<String> addressList = new ArrayList<>();
        Connection.Response response = connection.execute();
        Elements rows = response.parse().select(selector);//getElementsList(connection, selector);
        for (Element row : rows) {
            String address = row.child(ADDRESS_COLUMN_BRANCH).text();
            addressList.add(address);
            System.out.println(address);
        }
        return addressList;
    }

    Connection createJsoupConnection(String pageUrl, String regionName, int pageNum) {
        return Jsoup.connect(pageUrl)
                .data("arrFilter_pf[RegionName]",regionName)
                .data("ib","atms_ua")
                .data("set_filter",SET_FILTER_PARAM)
                .data("PAGEN_1",(pageNum > 1) ? String.valueOf(pageNum) : "");
    }

    private List<String> parseRegion(String pageUrl, String regionName, String selector){
        List<String> addressList = new ArrayList<>();
        try {
            int page = 1;
            int pageCount = 1;
            do {
                if(page == 1){
                    pageCount = getPageCount(createJsoupConnection(pageUrl, regionName, page));
                }
                System.out.println("Page "+page);
                addressList.addAll(parseTable(createJsoupConnection(pageUrl, regionName, page), selector));
                System.out.println();
                page++;
            } while(page <= pageCount);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: catch exception
        }
        return addressList;
    }

    private int getPageCount(Connection connection) throws IOException {
        Connection.Response response = connection.execute();
        Elements elems = response.parse().select(LAST_PAGE_SELECTOR);
        if(elems.size() == 0){
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
            Connection.Response response = Jsoup.connect("http://www.oschadnybank.com/handlers/region1.php")
                .data("contentType", "application/json; charset=utf-8", "ib", "atms_ua", "p", "1", "s", "15")
                .method(Connection.Method.GET)
                .header("Accept", "application/json")
                .header("Host", "www.oschadnybank.com")
                .referrer("http://www.oschadnybank.com/ua/branches_atms/atms/")
                .header("X-Requested-With", "XMLHttpRequest")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0")
                .ignoreContentType(true)
                .execute();
            Document document = Jsoup.parse(response.parse().outerHtml());

            JSONObject JsonObj = (JSONObject)JSONValue.parse(document.text());
            JSONArray jsonArray = (JSONArray)JsonObj.get("results");
            JSONObject JsonItem;
            String regionStr;
            for(int i=0; i<jsonArray.size(); i++){
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

    public static void main(String[] args){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("base_url","http://www.oschadnybank.com/ua/branches_atms/");
        parameters.put("branch_page","branches/");
        parameters.put("atm_page","atms/");
        parameters.put("branch_selector",".table_block > table:nth-child(1) > tbody:nth-child(1) > .news-item");
        //відділення - Номер відділення / Адреса / Телефон
        //банкомати - Адреса / Часи роботи / Місце встановлення
        // http://www.oschadnybank.com/ua/branches_atms/branches/?PAGEN_1=253
        // font.text > a:nth-child(7)

        // http://www.oschadnybank.com/ua/branches_atms/atms/?
        // arrFilter_pf[RegionName]=Івано-Франківська+область&amp;ib=atms_ua&amp;set_filter=Шукати
        // &PAGEN_1=2

        OschadBankParser bankParser = new OschadBankParser();
        bankParser.setParameter(parameters);
        bankParser.parse();

    }

}
