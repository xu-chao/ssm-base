package com.java.activiti.model;

import java.io.Serializable;

public class UserDept implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userID;

    private String deptID;

    private User user;

    private Dept dept;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "UserDept{" +
                "userID='" + userID + '\'' +
                ", deptID='" + deptID + '\'' +
                ", user=" + user +
                ", dept=" + dept +
                '}';
    }
}
