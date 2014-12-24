package com.ss.atmlocator.parser.coordEncoder;


import com.ss.atmlocator.dao.CoordConvertDAO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@DisallowConcurrentExecution
public class CoordEncoderService implements Job {
    private final static Logger logger = LoggerFactory.getLogger(CoordEncoderService.class);
    private final static String nullContext = "Application context is null";
    private final static String nullAddress = "Address list is null";
    private final static String addressCount = "Start getting ccordinats. Number of addresses is: ";
    private final static String bedGodeRequest = "Cant geocode request, request status: ";
    private final static String geoGodeCount = "Number of geocoding address: ";
    private final static String beginUpdate = "Start inserting data";
    private final static String allDone = "End of running job";
    private final static String emptyAddress = "Address list is empty. Nothing to update";


    private final static int listCount = 10;
    private int requestCoord = 0;
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        ApplicationContext applicationContext = initAppContext(context);

        if(applicationContext == null) {
            logger.error(nullContext);
            return;
        }

        CoordConvertDAO dao =  (CoordConvertDAO)applicationContext.getBean("coordConvertDAO");


        List<String> address = dao.getCoordNames();

        if(address == null){
            logger.error(nullAddress);
            return;
        }
        if(address.isEmpty()){
            logger.info(emptyAddress);
            return;
        }

        logger.info(addressCount+address.size());
        List<Coord> coordList = new ArrayList<>(listCount);

        for (String addr : address){
            try{

                GoogleResponse res = AddressWorker.convertToLatLong(addr);
                if(res.getStatus().equals("OK")){
                    Result result = res.getResults()[0];
                    coordList.add(new Coord(addr,
                            result.getGeometry().getLocation().getLat(),
                            result.getGeometry().getLocation().getLng()));
                    requestCoord++;
                }
                else{
                    logger.info(bedGodeRequest+res.getStatus());
                }
                Thread.sleep(500);
            }
            catch (Exception exp){
               logger.error(exp.getMessage(),exp);
            }

        }
        logger.info(geoGodeCount+requestCoord);

        if(!coordList.isEmpty()){
            logger.info(beginUpdate);
            List<String> error = new LinkedList<>();
            dao.updateCoord(coordList,error);

            if (!error.isEmpty()){
                for(String err : error){
                    logger.info(err);
                }
            }
        }
        logger.info(allDone);
    }

    private ApplicationContext initAppContext(JobExecutionContext context){

        ApplicationContext applicationContext;

        try{
            applicationContext = (ApplicationContext)context
                    .getScheduler().getContext().get("applicationContext");

        }
        catch(SchedulerException exp){
            logger.error(exp.getMessage(),exp);
            return null;
        }

        return applicationContext;
    }

}
