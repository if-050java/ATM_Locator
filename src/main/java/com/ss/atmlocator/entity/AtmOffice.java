package com.ss.atmlocator.entity;


import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Olavin on 17.11.2014.
 */
@Entity
@Table(name="atm")
public class AtmOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @Column
    private String address;

    @Embedded
    private GeoPosition geoPosition;

    @Column
    private int state;

    @Enumerated(EnumType.ORDINAL)
    private AtmType type;

    public enum AtmType { IS_ATM, IS_OFFICE }

    @Column
    private Timestamp lastUpdated;

    @Column
    private String photo;  // filename of real street photo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    Bank bank;

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

    public AtmType getType() {
        return type;
    }

    public void setType(AtmType type) {
        this.type = type;
    }

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
}

