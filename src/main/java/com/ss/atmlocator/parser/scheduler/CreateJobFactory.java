package com.ss.atmlocator.parser.scheduler;

import org.quartz.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CreateJobFactory {
    final static Logger logger = LoggerFactory.getLogger(SchcedService.class);
    private CreateJobFactory(){}

    public static JobTrigerHolder createJob(JobTemplate jobTemplate){
        try{
        Map<String,String> map = jobTemplate.getMap();

        JobKey jobKey = new JobKey(jobTemplate.getJobName(), jobTemplate.getJobGroup());
        TriggerKey triggerKey = new TriggerKey(jobTemplate.getTriggerName(), jobTemplate.getTriggerGroup());

        Class  clazz = Class.forName(jobTemplate.getJobClassName());

        JobDetail job = JobBuilder.newJob()
                .ofType(clazz)
                .withIdentity(jobKey)
                .storeDurably(false)
                .build();

        if(map != null && map.size() != 0){
            List<String> keyList = new ArrayList<String>(map.keySet());
            for (String key : keyList){
                job.getJobDataMap().put(key, map.get(key));
            }
        }

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(jobTemplate.getCronSched()))
                .build();

        return new JobTrigerHolder(job,trigger);

    }
        catch (Exception exp){
            logger.error(exp.getMessage(), exp);
            return null;
        }
    }


}
