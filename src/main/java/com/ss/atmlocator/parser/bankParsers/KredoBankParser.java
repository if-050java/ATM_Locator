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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.ss.atmlocator.entity.AtmOffice.AtmType.*;
import static com.ss.atmlocator.entity.AtmState.NO_LOCATION;

public class KredoBankParser extends ParserExecutor {


    private final Logger logger = Logger.getLogger(KredoBankParser.class);

    private List<String> regions = new ArrayList<String>();

    private List<AtmOffice> atmList = new ArrayList<>();



    /**
     * @return list AtmOffices that was parsed from given URL and is included to given regions
     * @throws IOException if couldn't load given URL(URL is bad or site don't work at this time)
     */
    public List<AtmOffice> parse() throws IOException {
        initProperties();
        try{
            String url = getProp("url.base") + getProp("url.atm");
            logger.info("Try to load start page " + url);
            Document mainPage = Jsoup.connect(url)
                                     .method(Connection.Method.GET)
                                     .userAgent(getProp("user.agent"))
                                     .referrer(getProp("url.base"))
                                     .execute()
                                     .parse();
                                     //.get();
            int parsedRegions = 0;
            Elements regionRows = mainPage.getElementsByClass(getProp("region.element.class"));
            for(Element regionRow : regionRows){
                String regionID = regionRow.id().substring(Integer.parseInt(getProp("region.id.start.position")));
                Element regionDiv = regionRow.child(Integer.parseInt(getProp("region.div.child")));
                Element regionNameElement = regionDiv.child(Integer.parseInt(getProp("region.name.element")));
                if(regions.contains(regionNameElement.text()) || getProp("regions").isEmpty()){
                    logger.info("Try to parse atmList from region " + regionNameElement.text());
                    parseRegion(getProp("url.base") + getProp("url.region")+regionID);
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

    private void initProperties(){
        String[] regionsArray = getProp("regions").split(",");
        for(String region : regionsArray){
            regions.add(region);
        }
    }

    /**
     * Parse region that is defined by @param request to kredobank rest api
     * and get URLs for cities parser
     */
    private void parseRegion(String request){
        logger.info("Try to connect to URL "+request);
        try{
            Document regionXML = Jsoup.connect(request).userAgent(getProp("user.agent"))
                    .referrer(getProp("url.base"))
                    .method(Connection.Method.GET)
                                                       .execute().parse();
            Elements cityItem = regionXML.getElementsByTag(getProp("atm.container.tag"));
            for(Element city : cityItem){
                logger.info("Try to parse atmList from city " + city.child(0).text());
                parseCity(getProp("url.base")+getProp("url.city") + city.child(Integer.parseInt(getProp("city.child"))).text());
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
            Document cityXML = Jsoup.connect(request).userAgent(getProp("user.agent"))
                                                     .referrer(getProp("url.base"))
                                                     .method(Connection.Method.GET)
                                                     .execute().parse();
            Elements atmItems = cityXML.getElementsByTag(getProp("atm.container.tag"));
            for(Element atmItem : atmItems){
                String address = prepareAddress(atmItem.child(Integer.parseInt(getProp("address.child"))).text());
                String[] addressArray = address.split(getProp("separator.address"));
                address = addressArray[Integer.parseInt(getProp("type.child"))]+addressArray[Integer.parseInt(getProp("address.child"))];
                if(isAtmAndOffice(address)){
                    continue;
                }

                AtmOffice atm = new AtmOffice();
                atm.setAddress(address);
                int typeChild = Integer.parseInt(getProp("type.child"));
                String atmTypeName = getProp("atm.type.name");
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
