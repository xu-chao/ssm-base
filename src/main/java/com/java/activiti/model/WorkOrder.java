package com.java.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class WorkOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private User user;

    private String woStartDate;

    private String woType;

    private String materielID;

    private String materielType;

    private String repairedPerson;

    private String repairedHelper;

    private String repairedProcess;

    private String repairedImageID;

    private Images images;

    private String repairedFileID;

    private File file;

    private Date woEndDate;

    private String state;

    private Repair repair;

    private String processInstanceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWoStartDate() {
        return woStartDate;
    }

    public void setWoStartDate(String woStartDate) {
        this.woStartDate = woStartDate;
    }

    public String getWoType() {
        return woType;
    }

    public void setWoType(String woType) {
        this.woType = woType;
    }

    public String getMaterielID() {
        return materielID;
    }

    public void setMaterielID(String materielID) {
        this.materielID = materielID;
    }

    public String getMaterielType() {
        return materielType;
    }

    public void setMaterielType(String materielType) {
        this.materielType = materielType;
    }

    public String getRepairedPerson() {
        return repairedPerson;
    }

    public void setRepairedPerson(String repairedPerson) {
        this.repairedPerson = repairedPerson;
    }

    public String getRepairedHelper() {
        return repairedHelper;
    }

    public void setRepairedHelper(String repairedHelper) {
        this.repairedHelper = repairedHelper;
    }

    public String getRepairedProcess() {
        return repairedProcess;
    }

    public void setRepairedProcess(String repairedProcess) {
        this.repairedProcess = repairedProcess;
    }

    public String getRepairedImageID() {
        return repairedImageID;
    }

    public void setRepairedImageID(String repairedImageID) {
        this.repairedImageID = repairedImageID;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getRepairedFileID() {
        return repairedFileID;
    }

    public void setRepairedFileID(String repairedFileID) {
        this.repairedFileID = repairedFileID;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Date getWoEndDate() {
        return woEndDate;
    }

    public void setWoEndDate(Date woEndDate) {
        this.woEndDate = woEndDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Repair getRepair() {
        return repair;
    }

    public void setRepair(Repair repair) {
        this.repair = repair;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", woStartDate='" + woStartDate + '\'' +
                ", woType='" + woType + '\'' +
                ", materielID='" + materielID + '\'' +
                ", materielType='" + materielType + '\'' +
                ", repairedPerson='" + repairedPerson + '\'' +
                ", repairedHelper='" + repairedHelper + '\'' +
                ", repairedProcess='" + repairedProcess + '\'' +
                ", repairedImageID=" + repairedImageID +
                ", images=" + images +
                ", repairedFileID=" + repairedFileID +
                ", file=" + file +
                ", woEndDate=" + woEndDate +
                ", state='" + state + '\'' +
                ", repair=" + repair +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
