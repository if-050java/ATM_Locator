package com.ss.atmlocator.parser.scheduler;

import org.quartz.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

@DisallowConcurrentExecution
public class JobB implements Job {



    final static Logger logger = LoggerFactory.getLogger(SchcedService.class);
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        ApplicationContext applicationContext = initAppContext(context);
        if(applicationContext == null) {
            return;
        }
        MessageSource messages =  (MessageSource)applicationContext.getBean("messageSource");

        System.out.println(messages.getMessage("run.job", null, Locale.ENGLISH));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        Date date = new Date();


        JobDataMap data = context.getJobDetail().getJobDataMap();
        String message = data.getString("message");
        System.out.println("JobB is running (" +dateFormat.format(date)+")" + ";"+message);
        logger.info(message);


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