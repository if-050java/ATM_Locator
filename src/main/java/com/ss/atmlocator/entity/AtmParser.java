package com.ss.atmlocator.entity;

import com.ss.atmlocator.entity.enums.Bank;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Olavin on 18.11.2014.
 */
@Entity
@Table(name="parsers")
public class AtmParser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private String name;

    @Column
    private int state; //todo: substitute with enum

    @Column
    private int type; //todo: substitute with enum

    @Column
    private Timestamp lastRun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    Bank bank;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atmParser")
    private Set<AtmParserParam> paramSet;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getLastRun() {
        return lastRun;
    }

    public void setLastRun(Timestamp lastRun) {
        this.lastRun = lastRun;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Set<AtmParserParam> getParamSet() {
        return paramSet;
    }

    public void setParamSet(Set<AtmParserParam> paramSet) {
        this.paramSet = paramSet;
    }
}
