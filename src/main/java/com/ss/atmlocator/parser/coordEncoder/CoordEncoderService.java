package com.ss.atmlocator.parser.coordEncoder;


import com.ss.atmlocator.dao.CoordConvertDAO;
import com.ss.atmlocator.dao.JobLogDAO;
import com.ss.atmlocator.entity.AtmState;
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

    private final static String NULL_CONTEXT = "Application context is null ";
    private final static String NULL_ADDRESS = "Address list is null";
    private final static String ADDRESS_COUNT = "Start getting cordinats. Number of addresses is: ";
    private final static String BED_CODE_REQUEST = "Cant geocode request, request status: ";
    private final static String GEO_CODE_COUNT = "Number of geocoding address: ";
    private final static String BEGIN_UPDATE = "Start inserting data ";
    private final static String ALL_DONE = "End of running job ";
    private final static String EMPTY_ADDRESS = "Address list is empty. Nothing to update ";
    private final static String ZERO_RESULT = "Geocoder get zero result. Address: ";
    private final static String OK_RESPONSE = "OK";
    private final static String ZERO_RESPONSE = "ZERO_RESULTS";
    private final static String JOB_ERROR_CODE = "error";
    private final static String JOB_INFO_CODE = "info";


    private final static int listCount = 10;
    private int requestCoord = 0;



    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        ApplicationContext applicationContext = initAppContext(context);

        if(applicationContext == null) {
            logger.error(NULL_CONTEXT);
            return;
        }

        String jobName = context.getJobDetail().getKey().getName();

        CoordConvertDAO dao =  (CoordConvertDAO)applicationContext.getBean("coordConvertDAO");
        JobLogDAO jobDAO =  (JobLogDAO)applicationContext.getBean("jobLogDAO");

        StringBuilder jobMessage = new StringBuilder();

        List<String> address = dao.getCoordNames();

        if(address == null){
            logger.error(NULL_ADDRESS);
            jobMessage.append(NULL_ADDRESS);
            jobDAO.saveLog(JobLogBuilder.newJobLog()
                    .withJobName(jobName)
                    .withLastRun()
                    .withState(JOB_ERROR_CODE)
                    .withMessage(NULL_ADDRESS)
                    .build());
            return;
        }
        if(address.isEmpty()){
            logger.info(EMPTY_ADDRESS);
            jobDAO.saveLog(JobLogBuilder.newJobLog()
                    .withJobName(jobName)
                    .withLastRun()
                    .withState(JOB_INFO_CODE)
                    .withMessage(EMPTY_ADDRESS)
                    .build());
            return;
        }

        logger.info(ADDRESS_COUNT+address.size());
        List<Coord> coordList = new ArrayList<>(listCount);

        for (String addr : address){
            try{
                logger.debug("Searching address: "+addr);
                GoogleResponse res = AddressWorker.convertToLatLong(addr);

                switch (res.getStatus()){
                    case OK_RESPONSE: {
                        Result result = res.getResults()[0];
                        coordList.add(new Coord(addr,
                                result.getGeometry().getLocation().getLat(),
                                result.getGeometry().getLocation().getLng(),
                                AtmState.NORMAL));
                        requestCoord++;
                        break;
                    }

                    case ZERO_RESPONSE:{
                        coordList.add(new Coord(addr,null,null,AtmState.BAD_ADDRESS));
                        logger.info(ZERO_RESULT+addr);
                        jobMessage.append(ZERO_RESULT+addr);
                        break;
                    }

                    default:{
                        logger.info(BED_CODE_REQUEST+res.getStatus()+"["+addr+"]");
                        jobMessage.append(BED_CODE_REQUEST+res.getStatus()+"["+addr+"]");
                    }
                }
                Thread.sleep(500);
            }
            catch (Exception exp){
               logger.error(exp.getMessage(),exp);
               jobMessage.append(exp.getMessage());
            }

        }
        logger.info(GEO_CODE_COUNT+requestCoord);
        jobMessage.append(GEO_CODE_COUNT+requestCoord);

        if(!coordList.isEmpty()){
            logger.info(BEGIN_UPDATE);
            jobMessage.append(BEGIN_UPDATE);
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
                .withState(JOB_INFO_CODE)
                .withMessage(jobMessage.toString())
                .build());
        logger.info(ALL_DONE);

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
