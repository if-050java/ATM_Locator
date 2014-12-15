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

}
