package com.ss.atmlocator.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="job_logs")
public class JobLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private String jobName;

    @Column
    private Timestamp lastRun;

    @Column
    private Timestamp lastFinish;

    @Column
    private String state;

    @Column
    private String message;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Timestamp getLastRun() {
        return lastRun;
    }

    public void setLastRun(Timestamp lastRun) {
        this.lastRun = lastRun;
    }

    public Timestamp getLastFinish() {
        return lastFinish;
    }

    public void setLastFinish(Timestamp lastFinish) {
        this.lastFinish = lastFinish;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
