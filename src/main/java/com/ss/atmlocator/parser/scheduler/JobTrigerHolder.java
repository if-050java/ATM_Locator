package com.ss.atmlocator.parser.scheduler;

import org.quartz.JobDetail;
import org.quartz.Trigger;

public class JobTrigerHolder {

    private JobDetail job;
    private Trigger trigger;

    public JobDetail getJob() {
        return job;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public JobTrigerHolder(JobDetail job, Trigger trigger) {
        this.job = job;
        this.trigger = trigger;

    }
}
