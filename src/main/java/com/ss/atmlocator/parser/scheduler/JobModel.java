package com.ss.atmlocator.parser.scheduler;

import java.util.Map;

public class JobModel {
    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    private String cronSched;
    private String jobClassName;
    private String params;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }



    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getCronSched() {
        return cronSched;
    }

    public void setCronSched(String cronSched) {
        this.cronSched = cronSched;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

}
