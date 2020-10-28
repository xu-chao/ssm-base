package com.java.activiti.model;

import java.io.Serializable;

public class SubProject implements Serializable {

    private static final long serialVersionUID = 1L;

    private int subProjectID;//subProjectID

    private String subProjectName;//ProjectName

    private Park park;//ParkID

    private City city;

    public int getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(int subProjectID) {
        this.subProjectID = subProjectID;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SubProject{" +
                "subProjectID=" + subProjectID +
                ", subProjectName='" + subProjectName + '\'' +
                ", park=" + park +
                ", city=" + city +
                '}';
    }
}
