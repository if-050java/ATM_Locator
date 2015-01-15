package com.ss.atmlocator.parser.coordEncoder;


import com.ss.atmlocator.entity.AtmState;

public class Coord {

    private String address;
    private String latitude;
    private String longitude;
    private AtmState state;

    public AtmState getState() {
        return state;
    }

    public void setState(AtmState state) {
        this.state = state;
    }

    public Coord(String address,String latitude,String longitude, AtmState state){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
