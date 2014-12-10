package com.ss.atmlocator.parser.parserNBU;

import com.ss.atmlocator.parser.IParser;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ss.atmlocator.entity.Bank;

/**
 * Created by maks on 18.11.2014.
 */
public class NbuParser implements IParser {

    final Logger log = Logger.getLogger(NbuParser.class.getName());
    private List<Bank> banks = new ArrayList<Bank>();// Is it may by Set?
    private String url;
    private static String NAMEXPATH ;// ="table.col_title_t>tbody>tr:gt(0)>td:eq(0)>a";
    private static String MFOXPATH ;//="table.col_title_t>tbody>tr:gt(0)>td:eq(2)" ;
    private static String PAGINATORPATH ;

    public NbuParser() {
    }

    public NbuParser(String url) {
        this.url = url;
    }

    @Override
    public void setParameter(Map<String, String> parameters) {
        if(parameters.containsKey("url")){//TODO my by this can block the excess
            url =parameters.get("url");
        }if(parameters.containsKey("NAMEXPATH")){
            NAMEXPATH = parameters.get("NAMEXPATH");
        }if(parameters.containsKey("MFOXPATH")){
            MFOXPATH = parameters.get("MFOXPATH");
        } if(parameters.containsKey("PAGINATORPATH")){
            PAGINATORPATH = parameters.get("PAGINATORPATH");
        }

    }

    @Override
    public List<Bank> parse() {
        log.info("Start parser");
        parceBanks(url);
        List<String> links = parseLink(url);
        for(String link: links){
            parceBanks(link);
        }
        log.info("End parser");
        return banks;
    }

    /**
     *
     * @param url String where is list of banks in table
     */
    public void parceBanks(String url){     //MAKE IT A MULTITHREADED
        log.info("Begin parse url - "+url);
         Document doc;
        try {
            doc =  Jsoup.connect(url).get(); // create connect to url
            Elements ElementsNames = doc.select(NAMEXPATH); // select all names of banks by Xpath
            Elements ElementsMFO =doc.select(MFOXPATH); // selsect mfo of banks by Xpath
            for (int i = 0; i < ElementsMFO.size(); i++) {                  //
                Bank bank = new Bank();
                bank.setName(deleteLastChar(getName(ElementsNames.get(i).text())).toUpperCase());
                bank.setMfoCode(Integer.parseInt(ElementsMFO.get(i).text()));
                banks.add(bank);   // filling a list of banks
            }
        } catch (IOException e) {
            log.error("problem with  connect", e);
        }
        log.info("end parse ");
    }
    private String deleteLastChar(String str){

        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length()-1);
    }


    private String getName(String str) {

        String res = str;
        Pattern pattern = Pattern.compile("[^\"]+\"+$");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            res = matcher.group();
        }
        return res;
    }

    /**
     *
     * This methiod return List links on the other banks in this site
     *
     * @exception java.io.IOException when not have connection
     * @param url
     * @return List Strings
     */
    private List<String> parseLink(String url){
        log.info("begin parse ");
        ArrayList<String> links=null;
        try {
            Document doc = Jsoup.connect(url).get(); // create connect to url
            links = new ArrayList<String>();
            Elements elementsLinks = doc.select(PAGINATORPATH); // select all Links to other pages
            for(Element link: elementsLinks){
                links.add(link.attr("abs:href"));
            }

        } catch (IOException e) {
            log.error("problem with connection "+e);

        }
        return links;
    }
    /**
     * This methos return banks List size size
     * @return int
     * */
    public int size(){
        return banks.size();
    }
}
