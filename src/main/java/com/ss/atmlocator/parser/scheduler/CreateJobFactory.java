package com.ss.atmlocator.parser.scheduler;


import org.quartz.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CreateJobFactory {

    private CreateJobFactory(){}

    public static JobTrigerHolder createJob(JobTemplate jobTemplate)
            throws ClassNotFoundException{

        Map<String,String> map = jobTemplate.getMap();

        JobKey jobKey = new JobKey(jobTemplate.getJobName(), jobTemplate.getTriggerName());
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

}
