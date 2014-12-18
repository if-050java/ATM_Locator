package com.ss.atmlocator.parser.scheduler;


import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class SchcedService {
    final static Logger logger = LoggerFactory.getLogger(SchcedService.class);
    private static final String error = "Internal error. See logs for details";
    @Autowired
    private Scheduler scheduler;

    public String addJob(JobTrigerHolder jobTrigerHolder){
        try{
            if(scheduler.checkExists(jobTrigerHolder.getJob().getKey()) == false){
                    scheduler.scheduleJob(jobTrigerHolder.getJob(),jobTrigerHolder.getTrigger());
            }
        }
        catch (SchedulerException exp){
            logger.error(exp.getMessage(),exp);
            return error;
        }
        return null;
    }


    public String removeJob(JobTemplate jobTemplate){
        try{
            scheduler.deleteJob(new JobKey(jobTemplate.getJobName(),
                    jobTemplate.getJobGroup()));
        }
        catch (SchedulerException exp){
            logger.error(exp.getMessage(),exp);
            return error;
        }
        return null;
    }


    public String runJob(JobTemplate jobTemplate){
        try{
        scheduler.triggerJob(new JobKey(jobTemplate.getJobName(),
                jobTemplate.getJobGroup()));
        }
        catch (SchedulerException exp){
            logger.error(exp.getMessage(),exp);
            return error;
        }
        return null;
    }


    public String pauseJob(JobTemplate jobTemplate){
           try{
                scheduler.pauseTrigger(new TriggerKey(jobTemplate.getTriggerName(),
                        jobTemplate.getTriggerGroup()));
               }
           catch (SchedulerException exp){
            logger.error(exp.getMessage(),exp);
            return error;
        }
        return null;
    }


    public String resumeJob(JobTemplate jobTemplate)throws SchedulerException {
        try{
        scheduler.resumeTrigger(new TriggerKey(jobTemplate.getTriggerName(),
                jobTemplate.getTriggerGroup()));
        }
        catch (SchedulerException exp){
            logger.error(exp.getMessage(),exp);
            return error;
        }
        return null;
    }


    public List<JobTemplate> getJobs() {
        try{
        List<JobTemplate> listJob = new ArrayList<JobTemplate>(15);

        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();
                String jobClass = scheduler.getJobDetail(jobKey).getJobClass().getName();

                String triggerName = null;
                String triggerGrouop = null;
                String cronExpr = null;
                Map<String,String> dataMap = null;
                String jobStatus = null;

                JobDataMap jobMap = scheduler.getJobDetail(jobKey).getJobDataMap();

                if(jobMap != null && !jobMap.isEmpty()){
                    String[] jobMapKeys =  jobMap.getKeys();
                    dataMap = new HashMap(jobMapKeys.length);
                    for (int i=0 ; i < jobMapKeys.length ; i++){
                        dataMap.put(jobMapKeys[i], jobMap.getString(jobMapKeys[i]));
                    }
                }

                List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

                for(Trigger trigger : triggers){

                    triggerName = trigger.getKey().getName();
                    triggerGrouop = trigger.getKey().getGroup();

                    jobStatus =  scheduler.getTriggerState(trigger.getKey()).name().toLowerCase();

                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        cronExpr = cronTrigger.getCronExpression();

                    }
                }

                JobTemplate jobTemplate = JobTemplateBuilder.newJob()
                        .withJobName(jobName)
                        .withJobGroup(jobGroup)
                        .withTriggerName(triggerName)
                        .withTriggerGroup(triggerGrouop)
                        .withJobClass(jobClass)
                        .withMap(dataMap)
                        .withCronSched(cronExpr)
                        .withJobStatus(jobStatus)
                        .build();
                listJob.add(jobTemplate);
            }
        }

        return listJob;
    }

        catch (SchedulerException exp){
            logger.error(exp.getMessage(),exp);

        }
        return null;

    }
}
