package com.java.activiti.model;

import com.java.activiti.model.erp.Company;

import java.io.Serializable;
import java.util.Date;

public class Need implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id; // 单号
    private String EID; // 序号
    private Date EApplicantData; // 申请日期
    private String EPType;// 加工类型
    private String EBillCompany; //提单公司
    private String EType; // 类型/专业
    private String EZone; // 区域
    private String EConsumer; // 工厂耗材:是/否
    private String NID; // 采购单号
    private int ESubID;// 子项目ID
    private SubProject subProject;//子项目名称
    private String ESubProjectNameElse;//自定义子项目名称
    private String ESysName;// 系统名称
    private String EApplicant;// 申请人
    private User user; // 提交人
    private int ECompanyId;// 公司主体
    private Company company;
    private Date endDate;
    private String state; // 审核状态  未提交  审核中 审核通过 审核未通过
    private String processInstanceId; // 流程实例Id processInstanceId

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEID() {
        return EID;
    }

    public void setEID(String EID) {
        this.EID = EID;
    }

    public Date getEApplicantData() {
        return EApplicantData;
    }

    public void setEApplicantData(Date EApplicantData) {
        this.EApplicantData = EApplicantData;
    }

    public String getEPType() {
        return EPType;
    }

    public void setEPType(String EPType) {
        this.EPType = EPType;
    }

    public String getEBillCompany() {
        return EBillCompany;
    }

    public void setEBillCompany(String EBillCompany) {
        this.EBillCompany = EBillCompany;
    }

    public String getEType() {
        return EType;
    }

    public void setEType(String EType) {
        this.EType = EType;
    }

    public String getEZone() {
        return EZone;
    }

    public void setEZone(String EZone) {
        this.EZone = EZone;
    }

    public String getEConsumer() {
        return EConsumer;
    }

    public void setEConsumer(String EConsumer) {
        this.EConsumer = EConsumer;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public int getESubID() {
        return ESubID;
    }

    public void setESubID(int ESubID) {
        this.ESubID = ESubID;
    }

    public SubProject getSubProject() {
        return subProject;
    }

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
    }

    public String getESubProjectNameElse() {
        return ESubProjectNameElse;
    }

    public void setESubProjectNameElse(String ESubProjectNameElse) {
        this.ESubProjectNameElse = ESubProjectNameElse;
    }

    public String getESysName() {
        return ESysName;
    }

    public void setESysName(String ESysName) {
        this.ESysName = ESysName;
    }

    public String getEApplicant() {
        return EApplicant;
    }

    public void setEApplicant(String EApplicant) {
        this.EApplicant = EApplicant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getECompanyId() {
        return ECompanyId;
    }

    public void setECompanyId(int ECompanyId) {
        this.ECompanyId = ECompanyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    @Override
    public String toString() {
        return "Need{" +
                "id='" + id + '\'' +
                ", EID='" + EID + '\'' +
                ", EApplicantData=" + EApplicantData +
                ", EPType='" + EPType + '\'' +
                ", EBillCompany='" + EBillCompany + '\'' +
                ", EType='" + EType + '\'' +
                ", EZone='" + EZone + '\'' +
                ", EConsumer='" + EConsumer + '\'' +
                ", NID='" + NID + '\'' +
                ", ESubID=" + ESubID +
                ", subProject=" + subProject +
                ", ESubProjectNameElse='" + ESubProjectNameElse + '\'' +
                ", ESysName='" + ESysName + '\'' +
                ", EApplicant='" + EApplicant + '\'' +
                ", user=" + user +
                ", ECompanyId=" + ECompanyId +
                ", company=" + company +
                ", endDate=" + endDate +
                ", state='" + state + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
