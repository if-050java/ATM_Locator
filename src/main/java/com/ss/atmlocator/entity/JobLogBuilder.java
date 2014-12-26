package com.ss.atmlocator.entity;

import java.util.Date;
import com.ss.atmlocator.utils.TimeUtil;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JobLogBuilder {

    private DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");

    private String jobName;
    private Timestamp lastRun;
    private String state;
    private String message;


    protected JobLogBuilder() {}

    public static JobLogBuilder newJobLog()  {
        return new JobLogBuilder();
    }

    public JobLogBuilder withJobName(String jobName){
        this.jobName = jobName;
        return this;
    }

    public JobLogBuilder withLastRun() {
        this.lastRun = TimeUtil.currentTimestamp();
        return this;
    }

    public JobLogBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public JobLogBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public JobLog build() {
        JobLog jobLog = new JobLog();
        jobLog.setJobName(this.jobName);
        jobLog.setLastRun(this.lastRun);
        jobLog.setState(this.state);
        jobLog.setMessage(this.message);
        return jobLog;
    }


}
