package com.java.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class Repair implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // 故障申报单号

    private User user; // 运营人员

    private Date repairDate;// 故障日期

    private String repairPlace; // 故障类别

    private String repairLevel; // 故障等级

    private String repairType; //故障类别

    private String repairContext; // 故障描述

    private String repairImageID; // 图片ID

    private String repairFileID; // 文件ID

    private Date recordDate; // 记录日期

    private String state; // 审核状态  未提交  审核中 审核通过 审核未通过

    private String processInstanceId; // 流程实例Id

    private Images repairImage; // 上传图片

    private File file; // 上传文件

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

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public String getRepairPlace() {
        return repairPlace;
    }

    public void setRepairPlace(String repairPlace) {
        this.repairPlace = repairPlace;
    }

    public String getRepairLevel() {
        return repairLevel;
    }

    public void setRepairLevel(String repairLevel) {
        this.repairLevel = repairLevel;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getRepairContext() {
        return repairContext;
    }

    public void setRepairContext(String repairContext) {
        this.repairContext = repairContext;
    }

    public String getRepairImageID() {
        return repairImageID;
    }

    public void setRepairImageID(String repairImageID) {
        this.repairImageID = repairImageID;
    }

    public String getRepairFileID() {
        return repairFileID;
    }

    public void setRepairFileID(String repairFileID) {
        this.repairFileID = repairFileID;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Images getRepairImage() {
        return repairImage;
    }

    public void setRepairImage(Images repairImage) {
        this.repairImage = repairImage;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", repairDate=" + repairDate +
                ", repairPlace='" + repairPlace + '\'' +
                ", repairLevel='" + repairLevel + '\'' +
                ", repairType='" + repairType + '\'' +
                ", repairContext='" + repairContext + '\'' +
                ", repairImageID=" + repairImageID +
                ", repairFileID=" + repairFileID +
                ", recordDate=" + recordDate +
                ", state='" + state + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", repairImage=" + repairImage +
                ", file=" + file +
                '}';
    }
}
