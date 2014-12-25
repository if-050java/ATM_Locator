package com.ss.atmlocator.parser.coordEncoder;


import com.ss.atmlocator.dao.CoordConvertDAO;
import com.ss.atmlocator.dao.JobLogDAO;
import com.ss.atmlocator.entity.JobLog;
import com.ss.atmlocator.entity.JobLogBuilder;
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
    private final static String nullContext = "Application context is null ";
    private final static String nullAddress = "Address list is null";
    private final static String addressCount = "Start getting ccordinats. Number of addresses is: ";
    private final static String bedGodeRequest = "Cant geocode request, request status: ";
    private final static String geoGodeCount = "Number of geocoding address: ";
    private final static String beginUpdate = "Start inserting data ";
    private final static String allDone = "End of running job ";
    private final static String emptyAddress = "Address list is empty. Nothing to update ";
    private final static String zeroResult = "Geocoder get zero result. Address: ";
    private final static String okResponse = "OK";
    private final static String zeroResponse = "ZERO_RESULTS";
    private final static String jobErrorCode = "error";
    private final static String jobInfoCode = "info";


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

        String jobName = context.getJobDetail().getKey().getName();

        CoordConvertDAO dao =  (CoordConvertDAO)applicationContext.getBean("coordConvertDAO");
        JobLogDAO jobDAO =  (JobLogDAO)applicationContext.getBean("jobLogDAO");

        StringBuilder jobMessage = new StringBuilder();

        List<String> address = dao.getCoordNames();

        if(address == null){
            logger.error(nullAddress);
            jobMessage.append(nullAddress);
            jobDAO.saveLog(JobLogBuilder.newJobLog()
                    .withJobName(jobName)
                    .withLastRun()
                    .withState(jobErrorCode)
                    .withMessage(nullAddress)
                    .build());
            return;
        }
        if(address.isEmpty()){
            logger.info(emptyAddress);
            jobDAO.saveLog(JobLogBuilder.newJobLog()
                    .withJobName(jobName)
                    .withLastRun()
                    .withState(jobInfoCode)
                    .withMessage(emptyAddress)
                    .build());
            return;
        }

        logger.info(addressCount+address.size());
        List<Coord> coordList = new ArrayList<>(listCount);

        for (String addr : address){
            try{
                logger.debug("Searching address: "+addr);
                GoogleResponse res = AddressWorker.convertToLatLong(addr);
                if(res.getStatus().equals(okResponse)){
                    Result result = res.getResults()[0];
                    coordList.add(new Coord(addr,
                            result.getGeometry().getLocation().getLat(),
                            result.getGeometry().getLocation().getLng()));
                    requestCoord++;
                }
                if(res.getStatus().equals(zeroResponse)){
                    coordList.add(new Coord(addr,
                            String.valueOf(Double.valueOf(0.0)),
                            String.valueOf(Double.valueOf(0.0))) );
                    logger.info(zeroResult+addr);
                    jobMessage.append(zeroResult+addr);
                }
                else{
                    logger.info(bedGodeRequest+res.getStatus()+"["+addr+"]");
                    jobMessage.append(bedGodeRequest+res.getStatus()+"["+addr+"]");
                }
                Thread.sleep(500);
            }
            catch (Exception exp){
               logger.error(exp.getMessage(),exp);
               jobMessage.append(exp.getMessage());
            }

        }
        logger.info(geoGodeCount+requestCoord);
        jobMessage.append(geoGodeCount+requestCoord);

        if(!coordList.isEmpty()){
            logger.info(beginUpdate);
            jobMessage.append(beginUpdate);
            List<String> error = new LinkedList<>();
            dao.updateCoord(coordList,error);

            if (!error.isEmpty()){
                for(String err : error){
                    logger.info(err);
                    jobMessage.append(err);
                }
            }
        }
        jobDAO.saveLog(JobLogBuilder.newJobLog()
                .withJobName(jobName)
                .withLastRun()
                .withState(jobInfoCode)
                .withMessage(jobMessage.toString())
                .build());
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
