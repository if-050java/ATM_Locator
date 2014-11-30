package com.ss.atmlocator.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Olavin on 18.11.2014.
 */
@Entity
@Table(name="atmnetworks")
public class AtmNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private String name;

/*
    @JsonIgnore //Ignoring this field in JSON serializing
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "network", fetch = FetchType.LAZY)
    private Set<Bank> Banks;
*/

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

/*
    public Set<Bank> getBanks() {
        return Banks;
    }

    public void setBanks(Set<Bank> banks) {
        Banks = banks;
    }
*/
}
