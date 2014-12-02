package com.ss.atmlocator.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Olavin on 17.11.2014.
 */
@Embeddable
public class GeoPosition {
    @Column
    double longitude = 0;
    @Column
    double latitude = 0;

    public GeoPosition() {
    }

    public GeoPosition(double longitude, double latitude) {
        if (longitude > 180 || longitude < -180)
            throw new IllegalArgumentException("longitude must be +/- 180 degree");
        if (latitude > 90 || latitude < -90)
            throw new IllegalArgumentException("latitude must be +/- 90 degree");
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatInRad(){
        return latitude/180*Math.PI;
    }

    public double getLngInRad(){
        return longitude/180*Math.PI;
    }
}
