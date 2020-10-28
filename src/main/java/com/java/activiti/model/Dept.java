package com.java.activiti.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    private String deptID;//deptID

    private String deptName;//deptName

    private String deptAddress;//deptAddress

    private String deptMaster;//deptMaster

    private String deptPhone;//deptPhone

    private String deptFax;//deptFax

    private String deptEmail;//deptEmail

    private String deptRemarks;//deptRemarks

    private String pid;//pid

    private Integer is_parent;//is_parent

    private List<Dept> depts = new LinkedList<Dept>();

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptAddress() {
        return deptAddress;
    }

    public void setDeptAddress(String deptAddress) {
        this.deptAddress = deptAddress;
    }

    public String getDeptMaster() {
        return deptMaster;
    }

    public void setDeptMaster(String deptMaster) {
        this.deptMaster = deptMaster;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getDeptFax() {
        return deptFax;
    }

    public void setDeptFax(String deptFax) {
        this.deptFax = deptFax;
    }

    public String getDeptEmail() {
        return deptEmail;
    }

    public void setDeptEmail(String deptEmail) {
        this.deptEmail = deptEmail;
    }

    public String getDeptRemarks() {
        return deptRemarks;
    }

    public void setDeptRemarks(String deptRemarks) {
        this.deptRemarks = deptRemarks;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(Integer is_parent) {
        this.is_parent = is_parent;
    }

    public List<Dept> getDepts() {
        return depts;
    }

    public void setDepts(List<Dept> depts) {
        this.depts = depts;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptID='" + deptID + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptAddress='" + deptAddress + '\'' +
                ", deptMaster='" + deptMaster + '\'' +
                ", deptPhone='" + deptPhone + '\'' +
                ", deptFax='" + deptFax + '\'' +
                ", deptEmail='" + deptEmail + '\'' +
                ", deptRemarks='" + deptRemarks + '\'' +
                ", pid='" + pid + '\'' +
                ", is_parent=" + is_parent +
                ", depts=" + depts +
                '}';
    }
}
