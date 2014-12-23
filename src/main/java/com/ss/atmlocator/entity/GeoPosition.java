package com.ss.atmlocator.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Olavin on 17.11.2014.
 */
@Embeddable
public class GeoPosition {
    static final double MAX_LONGITUDE = 180.0;
    static final double MAX_LATITUDE = 180.0;
    static final double HALF_CIRCLE = 180.0;

    @Column
    private double longitude = 0;
    @Column
    private double latitude = 0;

    public GeoPosition() {
    }

    public GeoPosition(final double longitude, final double latitude) {

        if (longitude > MAX_LONGITUDE || longitude < -MAX_LONGITUDE) {
            throw new IllegalArgumentException("longitude must be +/- 180 degree");
        }
        if (latitude > MAX_LATITUDE || latitude < -MAX_LATITUDE) {
            throw new IllegalArgumentException("latitude must be +/- 90 degree");
        }
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public final double getLongitude() {
        return longitude;
    }

    public final void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public final double getLatitude() {
        return latitude;
    }

    public final void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public final double getLatInRad() {
        return latitude / HALF_CIRCLE * Math.PI;
    }

    public final double getLngInRad() {
        return longitude / HALF_CIRCLE * Math.PI;
    }
}
