package com.ss.atmlocator.parser.scheduler;

import org.quartz.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DisallowConcurrentExecution
public class JobB implements Job {
    final static Logger logger = LoggerFactory.getLogger(SchcedService.class);
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        Date date = new Date();

        System.out.println("JobB is running (" +dateFormat.format(date)+")");
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String message = data.getString("message");
        logger.info(message);


    }

}