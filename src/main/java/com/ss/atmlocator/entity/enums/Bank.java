package com.ss.atmlocator.entity.enums;

import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.AtmParser;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Olavin on 18.11.2014.
 */
@Entity
@Table(name="banks")
public class Bank implements Comparable<Bank> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private String name;



    @Transient
    private String url;

    @Column
    private int mfoCode = 0;

    @Column
    private String webSite;

    @Column
    private String logo;

    @Column
    private String iconAtm;

    @Column
    private String iconOffice;

    @Column
    private Timestamp lastUpdated;

    @JsonIgnore //Ignoring this field in JSON serializing
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bank", fetch = FetchType.EAGER)
    private Set<AtmOffice> atmOfficeSet;

    @JsonIgnore //Ignoring this field in JSON serializing
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bank", fetch = FetchType.EAGER)
    private Set<AtmParser> atmParserSet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "network_id")
    private AtmNetwork network;

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

    public int getMfoCode() {
        return mfoCode;
    }

    public void setMfoCode(int mfoCode) {
        this.mfoCode = mfoCode;
    }

    public Set<AtmParser> getAtmParserSet() {
        return atmParserSet;
    }

    public void setAtmParserSet(Set<AtmParser> atmParserSet) {
        this.atmParserSet = atmParserSet;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIconAtm() {
        return iconAtm;
    }

    public void setIconAtm(String iconAtm) {
        this.iconAtm = iconAtm;
    }

    public String getIconOffice() {
        return iconOffice;
    }

    public void setIconOffice(String iconOffice) {
        this.iconOffice = iconOffice;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<AtmOffice> getAtmOfficeSet() {
        return atmOfficeSet;
    }

    public void setAtmOfficeSet(Set<AtmOffice> atmOfficeSet) {
        this.atmOfficeSet = atmOfficeSet;
    }

    public AtmNetwork getNetwork() {
        return network;
    }

    public void setNetwork(AtmNetwork network) {
        this.network = network;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bank(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Bank() {
    }

    @Override
    public String toString() {
        return "Bank{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", mfoCode=" + mfoCode +
                ", webSite='" + webSite + '\'' +
                ", logo='" + logo + '\'' +
                ", iconAtm='" + iconAtm + '\'' +
                ", iconOffice='" + iconOffice + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", network=" + network +
                '}';
    }
    @Override
    public int compareTo(Bank other){
        return this.getName().compareTo(other.getName());
    }
}
