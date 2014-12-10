package com.ss.atmlocator.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Olavin on 18.11.2014.
 */
@Entity
@Table(name="parser_params")
public class AtmParserParam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private String parameter;

    @Column
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parser_id")
    AtmParser atmParser;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AtmParser getAtmParser() {
        return atmParser;
    }

    public void setAtmParser(AtmParser atmParser) {
        this.atmParser = atmParser;
    }
}
