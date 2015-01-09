package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maks on 09.01.2015.
 */
public class AvalParser2 {
    Map<String, String> parameters = new HashMap<>();
    private static  final String  ADDRESS = "address";
    private static  final String  CITY = "Івано-Франківськ";
    private static  final String  USERAGENT = "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52";
    private String bankUrl;
    private String officeUrl;
    Document doc = null;
    static List<AtmOffice> listAtms = new ArrayList<AtmOffice>();
    private boolean atmOffice = false;
    final static Logger logger = LoggerFactory.getLogger(AvalParser2.class);

    private void parceAtm(String url, boolean isOffice) throws IOException {
        try {

            doc = Jsoup.connect(url).userAgent(USERAGENT).get();

            Elements addreses = doc.getElementsByClass(ADDRESS);

            for (Element adres : addreses) {

                AtmOffice tempAtm = new AtmOffice();


                if (isOffice) {
                    tempAtm.setAddress(adres.text());
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM_OFFICE);
                    tempAtm.setAddress(trimFirsnaber(tempAtm.getAddress()));
                }else{
                    tempAtm.setAddress(CITY + adres.text());
                    tempAtm.setType(AtmOffice.AtmType.IS_ATM);
                }
                listAtms.add(tempAtm);
            }

        } catch (IOException ioe) {
            logger.error("[PARSER] Parser cen`t connect to URL"+ioe.getMessage(),ioe);
            throw ioe;
        }
    }

    private String trimFirsnaber(String address) {


        return address.replaceFirst("\\d+,"," ");
    }
    public Map<String, String> parceTesr(){
//        String URL = "http://aval.ua/ru/finlokator/";
        String URL = "http://api.finlocator.com/features/?filters=&theme=aval&section=atm&city=317&lang=ru&filters_atm=&filters_branch=&filters_all=,&_=1420804304902";
        Map<String, String> city=null;
        try {
            doc = Jsoup.connect(URL).userAgent(USERAGENT).get();
            Elements addreses = doc.getElementsByClass("chzn-select");
            Elements addres = addreses.select("option");
             city = new HashMap<>();
            for(Element adr: addres){
                System.out.println(adr.val()+"===="+adr.text());
                city.put( adr.text(), adr.val());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
     return city;
    }

    public static void main(String[] args) throws IOException {
        AvalParser2 avalParser2=new AvalParser2();
        String cityCod;
        Map<String, String > map =avalParser2.parceTesr();
        cityCod = map.get("Харьков");
        String Url = "http://api.finlocator.com/features/?filters=&theme=aval&section=atm&city="+cityCod+"&lang=ru&filters_atm=&filters_branch=&filters_all=,&_=1420804304902";
        avalParser2.parceAtm(Url, true);
        for (AtmOffice a:listAtms ){
            System.out.println(a);
        }

    }
}
