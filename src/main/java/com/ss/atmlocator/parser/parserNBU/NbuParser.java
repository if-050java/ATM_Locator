package com.ss.atmlocator.parser.parserNBU;

import com.ss.atmlocator.parser.IParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ss.atmlocator.entity.enums.Bank;

/**
 * Created by maks on 18.11.2014.
 */
public class NbuParser implements IParser {

    private List<Bank> banks = new ArrayList<Bank>();// Is it may by Set?
    private String url;
    private static String NAMEXPATH ;// ="table.col_title_t>tbody>tr:gt(0)>td:eq(0)>a";
    private static String MFOXPATH ;//="table.col_title_t>tbody>tr:gt(0)>td:eq(2)" ;

    public NbuParser() {
    }

    public NbuParser(String url) {
        this.url = url;
    }

    @Override
    public void setParameter(Map<String, String> parameters) {
        if(parameters.containsKey("url")){
            url =parameters.get("url");
        }if(parameters.containsKey("NAMEXPATH")){
            NAMEXPATH = parameters.get("NAMEXPATH");
        }if(parameters.containsKey("MFOXPATH")){
            MFOXPATH = parameters.get("MFOXPATH");
        }
    }

    @Override
    public List<Bank> parce() {
        parceBanks(url);
        List<String> links = parceLink(url);
        for(String link: links){
            parceBanks(link);
        }
//        for(Bank bank: banks){
//            System.out.println(bank);
//        }

        return banks;
    }

    /**
     *
     * @param url String where is list of banks in table
     */
    public void parceBanks(String url){     //MAKE IT A MULTITHREADED
         Document doc;
        try {
            doc =  Jsoup.connect(url).get(); // create connect to url
            Elements ElementsNames = doc.select(NAMEXPATH); // select all names of banks by Xpath
            Elements ElementsMFO =doc.select(MFOXPATH); // selsect mfo of banks by Xpath
            for (int i = 0; i < ElementsMFO.size(); i++) {                  //
//                String name = getName(ElementsNames.get(i).text());
//                banks.add(new Bank(ElementsNames.get(i).text() ,ElementsMFO.get(i).text() ));  // filling a list of banks
                Bank bank = new Bank();
//                int a ="asdf".lastIndexOf("\"");
//                bank.setName(ElementsNames.get(i).text());
                bank.setName(deleteLastChar(getName(ElementsNames.get(i).text())));
                bank.setMfoCode(Integer.parseInt(ElementsMFO.get(i).text()));
                banks.add(bank);   // filling a list of banks
//                banks.add(new Bank(name ,ElementsMFO.get(i).text() ));  // filling a list of banks
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String deleteLastChar(String str){

        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length()-1);
    }


    private String getName(String str) {
        String res = str;
//        Pattern pattern = Pattern.compile("\"(.+)\"");
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
    private List<String> parceLink(String url){
        ArrayList<String> links=null;
        try {
            Document doc = Jsoup.connect(url).get(); // create connect to url
            links = new ArrayList<String>();
            Elements elementsLinks = doc.select("div.content>table:eq(5)>tbody>tr>td:eq(1)>a"); // select all Links to other pages
            for(Element link: elementsLinks){
                links.add(link.attr("abs:href"));
            }

        } catch (IOException e) {
            e.printStackTrace();
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
