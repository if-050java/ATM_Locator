package com.ss.atmlocator.parser.scheduler;


import org.quartz.Trigger;

import java.util.Map;

public class JobTemplateBuilder {

    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroup;
    private String jobClassName;
    private String cronSched;
    private Map<String,String> map;
    private String jobStatus;

    protected JobTemplateBuilder(){}

    public JobTemplate build(){
        JobTemplate jobTemplate = new JobTemplate();
        jobTemplate.setJobName(jobName);
        jobTemplate.setJobGroup(jobGroupName);
        jobTemplate.setTriggerName(triggerName);
        jobTemplate.setTriggerGroup(triggerGroup);
        jobTemplate.setJobClassName(jobClassName);
        jobTemplate.setCronSched(cronSched);
        jobTemplate.setJobStatus(jobStatus);
        if(map!=null && map.size()>0){
            jobTemplate.setMap(map);
        }
        return jobTemplate;
    }

    public static JobTemplateBuilder newJob(){return new JobTemplateBuilder();}

    public JobTemplateBuilder withJobName(String jobName){
        this.jobName = jobName;
        return this;
    }

    public JobTemplateBuilder withJobStatus(String jobStatus){
        this.jobStatus = jobStatus;
        return this;
    }
    public JobTemplateBuilder withJobGroup(String jobGroupName){
        this.jobGroupName = jobGroupName;
        return this;
    }

    public JobTemplateBuilder withTriggerName(String triggerName){
        this.triggerName = triggerName;
        return this;
    }

    public JobTemplateBuilder withTriggerGroup(String triggerGroup){
       this.triggerGroup = triggerGroup;
        return this;
    }

    public JobTemplateBuilder withCronSched(String cronSched){
        this.cronSched = cronSched;
        return this;
    }

    public JobTemplateBuilder withJobClass(String jobClass){
        this.jobClassName = jobClass;
        return this;
    }

    public JobTemplateBuilder withMap(Map<String,String> map){
        this.map = map;
        return this;
    }
}
