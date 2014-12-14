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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Olavin on 14.12.2014.
 */
public class OschadBankParser implements IParser {

    private Map<String,String> parameters;
    String siteEncoding = null;

    @Override
    public void setParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<Bank> parse() {
        return null;
    }

    //@Override
    public Set<AtmOffice> parseAtms(){
        Set<AtmOffice> atms = new HashSet<>();
        List<String> regions = getRegionList();
        for(int i=0; i < regions.size(); i++){
            System.out.println(i+". "+regions.get(i));
        }

        //String ifr2 = "Івано-Франківська область";
        //String ifr = "\u0406\u0432\u0430\u043D\u043E-\u0424\u0440\u0430\u043D\u043A\u0456\u0432\u0441\u044C\u043A\u0430 \u043E\u0431\u043B\u0430\u0441\u0442\u044C";
        //System.out.println("[ "+ifr + "=" + ifr2+" ]");
        String testRegion = regions.get(0);

        String page = parameters.get("base_url")+parameters.get("branch_page");
        String selector = parameters.get("branch_selector");
        List<String> addressList = parseRegion(page, testRegion, selector);

        return atms;
    }

    private Elements getElementsList(String page, String selector) throws IOException {
        return Jsoup.parse(new URL(page).openStream(), siteEncoding, page).select(selector);
    }

    private String getBranchAddress(Element row){
        final int addressColumn = 1;
        String address = row.child(addressColumn).text();
        System.out.println(address);
        return address;
    }

    private List<String> parseTable(String page, String selector) throws IOException {
        List<String> addressList = new ArrayList<>();
        Elements rows = getElementsList(page, selector);
        for (Element element : rows) {
            addressList.add(getBranchAddress(element));
        }
        return addressList;
    }

    String createRequestUrl(String pageUrl, String regionName, int pageNum) {
        String requestUrl = null;
        try {
            String regionParam = "arrFilter_pf%5BRegionName%5D="+URLEncoder.encode(regionName, "UTF-8");
            String otherParams = "&ib=atms_ua";
            String searchParam = "&set_filter=" + URLEncoder.encode("\u0428\u0443\u043A\u0430\u0442\u0438", "UTF-8"); //"Шукати"
            String pageNumParam = (pageNum > 1) ? "&PAGEN_1="+pageNum : "";
            requestUrl = pageUrl + "?" + regionParam + otherParams + searchParam + pageNumParam;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return requestUrl;
    }

    private List<String> parseRegion(String pageUrl, String regionName, String selector){
        List<String> addressList = new ArrayList<>();
        try {

            int pageCount = getPageCount(createRequestUrl(pageUrl, regionName, 1));
            for(int i = 1; i <= pageCount; i++){
                System.out.println("Page "+i);
                addressList.addAll(parseTable(createRequestUrl(pageUrl, regionName, i), selector));
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressList;

    }

    private int getPageCount(String page) throws IOException {
        final String pageCountSelector = "font.text > a:nth-last-child(1)";
        Elements elems = Jsoup.parse(new URL(page).openStream(), siteEncoding, page).select(pageCountSelector);
        String lastPageUrl = elems.get(0).attr("href");
        int index = lastPageUrl.lastIndexOf('=');
        int lastPageNum = Integer.parseInt(lastPageUrl.substring(index+1));
        return (lastPageNum > 0 ? lastPageNum : 1);

    }

    private static List<String> getRegionList(){
        Set<String> regions = new HashSet<>();
        try {
            Connection.Response response = Jsoup.connect("http://www.oschadnybank.com/handlers/region1.php")
                .data("contentType", "application/json; charset=utf-8", "ib", "atms_ua", "p", "1", "s", "15")
                .method(Connection.Method.GET)
                .header("Accept", "application/json")
                .header("Host", "www.oschadnybank.com")
                .header("Referer", "http://www.oschadnybank.com/ua/branches_atms/atms/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0")
                .header("X-Requested-With", "XMLHttpRequest")
                .ignoreContentType(true)
                .execute();
            Document document = Jsoup.parse(response.parse().outerHtml());
            //System.out.println(document.text());
            //String JsonString = ;

            JSONObject JsonObj = (JSONObject)JSONValue.parse(document.text());
            JSONArray jsonArray = (JSONArray)JsonObj.get("results");
            //System.out.println(jsonArray);
            JSONObject JsonItem;
            String regionStr;
            for(int i=0; i<jsonArray.size(); i++){
                JsonItem = (JSONObject) jsonArray.get(i);
                regionStr = (String) JsonItem.get("id");
                regions.add(regionStr.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> regionList = new ArrayList<>(regions.size());
        regionList.addAll(regions);
        Collections.sort(regionList);
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
        bankParser.parseAtms();

    }

}
