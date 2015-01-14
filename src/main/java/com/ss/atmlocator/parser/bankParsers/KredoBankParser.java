package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.ParserExecutor;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;
import static com.ss.atmlocator.entity.AtmState.NO_LOCATION;

public class KredoBankParser extends ParserExecutor {


    private final Logger logger = Logger.getLogger(KredoBankParser.class);

    private Properties propertiesFromFile = new Properties();

    private Properties properties = new Properties();

    private String bankSite;
    private List<String> regions = new ArrayList<String>();
    private boolean parseAllRegions;
    private List<AtmOffice> atmList = new ArrayList<>();



    /**
     * @return list AtmOffices that was parsed from given URL and is included to given regions
     * @throws IOException if couldn't load given URL(URL is bad or site don't work at this time)
     */
    public List<AtmOffice> parse() throws IOException {
        try{
            String url = bankSite + properties.getProperty("url.atm");
            logger.info("Try to load start page " + url);
            Document mainPage = Jsoup.connect(url)
                                     .method(Connection.Method.GET)
                                     .userAgent(properties.getProperty("user.agent"))
                                     .referrer(bankSite)
                                     .execute()
                                     .parse();
                                     //.get();
            int parsedRegions = 0;
            Elements regionRows = mainPage.getElementsByClass(properties.getProperty("region.element.class"));
            for(Element regionRow : regionRows){
                String regionID = regionRow.id().substring(Integer.parseInt(properties.getProperty("region.id.start.position")));
                Element regionDiv = regionRow.child(Integer.parseInt(properties.getProperty("region.div.child")));
                Element regionNameElement = regionDiv.child(Integer.parseInt(properties.getProperty("region.name.element")));
                if(regions.contains(regionNameElement.text()) || parseAllRegions){
                    logger.info("Try to parse atmList from region " + regionNameElement.text());
                    parseRegion(bankSite + properties.getProperty("url.region")+regionID);
                    parsedRegions++;
                }//end if
            }//end for regionRows
            logger.info("Parsing is done. Was parsed " + atmList.size() + " atmList and offices");
            if(regions.size() != parsedRegions){
                logger.warn("Regions given " + regions.size() + "regions parsed " + parsedRegions);
            }
            return atmList;
        }catch(IOException ioe){
            logger.error(ioe.getMessage(), ioe);
            throw ioe;
        }
    }

    /**
     * Parse region that is defined by @param request to kredobank rest api
     * and get URLs for cities parser
     */
    private void parseRegion(String request){
        logger.info("Try to connect to URL "+request);
        try{
            Document regionXML = Jsoup.connect(request).userAgent(properties.getProperty("user.agent"))
                    .referrer(bankSite)
                    .method(Connection.Method.GET)
                                                       .execute().parse();
            Elements cityItem = regionXML.getElementsByTag(properties.getProperty("atm.container.tag"));
            for(Element city : cityItem){
                logger.info("Try to parse atmList from city " + city.child(0).text());
                parseCity(bankSite+properties.getProperty("url.city") + city.child(Integer.parseInt(properties.getProperty("city.child"))).text());
            }
        }catch(IOException ioe){
            logger.error(ioe.getMessage(), ioe);
        }
    }

    /**
     *Parse city that is defined by @param request to kredobank rest api
     * and add all atmList from this city to list
     */
    private void parseCity(String request){
        logger.info("Try to connect to URL "+request);
        try{
            Document cityXML = Jsoup.connect(request).userAgent(properties.getProperty("user.agent"))
                                                     .referrer(bankSite)
                                                     .method(Connection.Method.GET)
                                                     .execute().parse();
            Elements atmItems = cityXML.getElementsByTag(properties.getProperty("atm.container.tag"));
            for(Element atmItem : atmItems){
                String address = prepareAddress(atmItem.child(Integer.parseInt(properties.getProperty("address.child"))).text());
                if(isAtmAndOffice(address)){
                    continue;
                }

                AtmOffice atm = new AtmOffice();
                atm.setAddress(address);
                int typeChild = Integer.parseInt(properties.getProperty("type.child"));
                String atmTypeName = properties.getProperty("atm.type.name");
                atm.setType(atmItem.child(typeChild).text().matches(atmTypeName) ? IS_ATM : IS_OFFICE);
                atm.setLastUpdated(new Timestamp(new Date().getTime()));
                atm.setState(NO_LOCATION);

                atmList.add(atm);
            }

        }catch(IOException ioe){
            logger.error(ioe.getMessage(), ioe);
        }
    }

    /**
     * @return formated address string based on @param rawAddress from site
     */
    private String  prepareAddress(String rawAddress){
        String[] addressArray = rawAddress.split(properties.getProperty("separator.address"));
        String address = addressArray[0]+addressArray[1];
        for(String paramName : properties.stringPropertyNames()){
            if(paramName.matches("replace\\.regexp\\..*")){
                String regexp = properties.getProperty(paramName);
                String replaceValue = properties.getProperty(paramName.replace("regexp", "value"));
                address = address.replaceAll(regexp, replaceValue);
            }
        }
        return address.trim();
    }

    /**
     * @return true if already has same @param address
     * it means that this address has both atm and office
     */
    private boolean isAtmAndOffice(String address){
        for(AtmOffice atm : atmList){
            if(atm.getAddress().equals(address)){
                atm.setType(IS_ATM_OFFICE);
                return true;
            }
        }
        return false;
    }
    }
