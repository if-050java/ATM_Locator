package com.ss.atmlocator.parser.scheduler;


import java.util.Map;

public class JobTemplateBuilder {

    private JobTemplate jobTemplate;

    public JobTemplate build(){return jobTemplate;}

    public void newJob(){jobTemplate =  new JobTemplate();}

    public void withJobName(String jobName){
        jobTemplate.setJobName(jobName);
    }

    public void withJobGroup(String jobGroupName){
        jobTemplate.setJobGroup(jobGroupName);
    }

    public void withTriggerName(String triggerName){
        jobTemplate.setTriggerName(triggerName);
    }

    public void withTriggerGroup(String triggerGroup){
        jobTemplate.setTriggerName(triggerGroup);
    }

    public void withCronSched(String cronSched){
        jobTemplate.setCronSched(cronSched);
    }

    public void withMap(Map<String,String> map){
        jobTemplate.setMap(map);
    }
}
