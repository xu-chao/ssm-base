package com.java.activiti.model;

import java.io.Serializable;

public class UserInfo extends User implements Serializable {

    private String deptID;//deptID

    private Dept userDept;

    private int parkID;//deptID

    private Park userPark;

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public Dept getUserDept() {
        return userDept;
    }

    public void setUserDept(Dept userDept) {
        this.userDept = userDept;
    }

    public int getParkID() {
        return parkID;
    }

    public void setParkID(int parkID) {
        this.parkID = parkID;
    }

    public Park getUserPark() {
        return userPark;
    }

    public void setUserPark(Park userPark) {
        this.userPark = userPark;
    }
}
