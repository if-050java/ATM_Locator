package com.ss.atmlocator.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by d18-antoshkiv on 22.12.2014.
 */
@Entity
@Table(name="logs")
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private Timestamp dated;

    @Column
    private String logger;

    @Column
    private String level;

    @Column
    private String message;

    public String getTimeString() {
        return String.format("%1$TD %1$TT", dated);
    }

    public int getId() {
        return id;
    }

    public Timestamp getDated() {
        return dated;
    }

    public String getLogger() {
        return logger;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message.replaceAll(",",", ");
    }
}
