package com.java.activiti.model;

import java.io.Serializable;

public class Park implements Serializable {

    private static final long serialVersionUID = 1L;

    private int parkID;//ParkID
    private String parkName;//ParkName
    private String parkAb;//Abbreviation
    private City city;

    public int getParkID() {
        return parkID;
    }

    public void setParkID(int parkID) {
        this.parkID = parkID;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getParkAb() {
        return parkAb;
    }

    public void setParkAb(String parkAb) {
        this.parkAb = parkAb;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Park{" +
                "parkID=" + parkID +
                ", parkName='" + parkName + '\'' +
                ", parkAb='" + parkAb + '\'' +
                ", city=" + city +
                '}';
    }
}
