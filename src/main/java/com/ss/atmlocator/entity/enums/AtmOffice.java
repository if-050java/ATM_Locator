package com.ss.atmlocator.entity.enums;


import com.ss.atmlocator.entity.AtmComment;
import com.ss.atmlocator.entity.AtmFavorite;
import com.ss.atmlocator.entity.GeoPosition;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Olavin on 17.11.2014.
 */
@Entity
@Table(name="atm")
public class AtmOffice implements Comparable<AtmOffice> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private String address;

    @Embedded
    private GeoPosition geoPosition;

    @Column
    private int state; //todo: substitute with enum

//    @Enumerated(EnumType.ORDINAL)
//    private AtmType type;

//    public enum AtmType { IS_ATM, IS_OFFICE }
    @Column
    private boolean isAtm;



    public boolean isAtm() {
        return isAtm;
    }

    public void setAtm(boolean isAtm) {
        this.isAtm = isAtm;
    }

    @Column

    private boolean isBankOffice;

    @Column
    private Timestamp lastUpdated;

    @Column
    private String photo;  // filename of real street photo

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    Bank bank;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AtmOffice atmOffice = (AtmOffice) o;

        if (Id != atmOffice.Id) return false;

        return true;
    }
    public boolean isBankOffice() {
        return isBankOffice;
    }

    public void setBankOffice(boolean isBankOffice) {
        this.isBankOffice = isBankOffice;
    }

    @Override
    public int hashCode() {
        return Id;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atmOffice", fetch = FetchType.EAGER)
    private Set<AtmComment> atmComments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atmOffice", fetch = FetchType.EAGER)
    private Set<AtmFavorite> atmFavorites;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

//    public AtmType getType() {
//        return type;
//    }
//
//    public void setType(AtmType type) {
//        this.type = type;
//    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<AtmComment> getAtmComments() {
        return atmComments;
    }

    public void setAtmComments(Set<AtmComment> atmComments) {
        this.atmComments = atmComments;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public int compareTo(AtmOffice other){
        return this.getAddress().compareTo(other.getAddress());
    }
}
