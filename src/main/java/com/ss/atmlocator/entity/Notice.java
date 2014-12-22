package com.ss.atmlocator.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Olavin on 12.12.2014.
 */
@Entity
@Table(name="notices")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private Timestamp time;

    @Enumerated(EnumType.ORDINAL)
    private Type type;

    public enum Type { INFO, WARN, ERROR, FATAL }

    @Column
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getTimeString() {
        return String.format("%1$TD %1$TT", time);
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
