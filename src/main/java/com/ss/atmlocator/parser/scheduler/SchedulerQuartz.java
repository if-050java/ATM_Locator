package com.ss.atmlocator.parser.scheduler;


import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulerQuartz {

    Scheduler scheduler;

    public void addJob(JobTrigerHolder jobTrigerHolder) throws SchedulerException{
        scheduler.scheduleJob(jobTrigerHolder.getJob(),jobTrigerHolder.getTrigger());
    }

    public void removeJob(JobTemplate jobTemplate) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobTemplate.getJobName(),
                jobTemplate.getJobGroup()));
    }

    public List<JobTemplate> getJobs() throws SchedulerException{
        for (String groupName : scheduler.getJobGroupNames()) {

            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();
                String jobClass = jobKey.getClass().toString();

                Map<String,String> dataMap;

                JobDataMap jobMap = scheduler.getJobDetail(new JobKey(jobName, jobGroup)).getJobDataMap();

                if(jobMap != null && jobMap.size()>0){
                    String[] jobMapKeys =  jobMap.getKeys();
                    dataMap = new HashMap(jobMapKeys.length);
                    for (int i=0 ; i < jobMapKeys.length ; i++){
                        dataMap.put(jobMapKeys[i], jobMap.getString(jobMapKeys[i]));
                    }
                }

                List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                for(Trigger trigger : triggers){
                    trigger.getKey();
                }

                //get job's trigger

                Date nextFireTime = triggers.get(0).getNextFireTime();
                System.out.println("[jobName] : " + jobName + " [groupName] : "
                        + jobGroup + " - " + nextFireTime);
            }
        }

        return null;
    }

}
