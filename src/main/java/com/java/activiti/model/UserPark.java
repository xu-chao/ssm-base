package com.java.activiti.model;

import java.io.Serializable;

public class UserPark implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userID;

    private int parkID;

    private User user;

    private Park park;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getParkID() {
        return parkID;
    }

    public void setParkID(int parkID) {
        this.parkID = parkID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    @Override
    public String toString() {
        return "UserPark{" +
                "userID='" + userID + '\'' +
                ", parkID='" + parkID + '\'' +
                ", user=" + user +
                ", park=" + park +
                '}';
    }
}
