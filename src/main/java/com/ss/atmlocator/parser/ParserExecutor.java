package com.ss.atmlocator.parser;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.service.DbParserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by maks on 15.12.2014.
 */
@Configurable
public abstract class ParserExecutor implements Job {
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(ParserExecutor.class);

    private DbParserService parserService;

    protected IParser parser;

    public  void execute(JobExecutionContext executionContext){
        setParser();
        parserService = (DbParserService)initAppContext(executionContext).getBean("dbParserService");
        Map parameters = executionContext.getJobDetail().getJobDataMap();
        parser.setParameter(parameters);
        int bankId = Integer.valueOf((String)parameters.get("bankid"));
        List<AtmOffice> atms = null;
        try {
            atms = parser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        parserService.update(atms, bankId);
    }

    protected  abstract void setParser() ;// Set there  implementation of IParser

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
