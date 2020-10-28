package com.java.activiti.model;

import java.io.Serializable;

public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    private int projectID;//ProjectID

    private String projectName;//ProjectName

    private Park park;//ParkID

    private City city;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
        return "Project{" +
                "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", park=" + park +
                ", city=" + city +
                '}';
    }
}
