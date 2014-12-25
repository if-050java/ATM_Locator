package com.ss.atmlocator.entity;


import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name="job_logs")
public class JobLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private String jobName;

    @Column
    private Date lastRun;

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

    public Date getLastRun() {
        return lastRun;
    }

    public void setLastRun(Date lastRun) {
        this.lastRun = lastRun;
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
