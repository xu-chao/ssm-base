package com.java.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class Equit implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 单号
    private String EID; // 序号
    private Date ApplicantData; // 申请日期
    private String EType; // 类型
    private String NID; // 采购单号
    private String EPName;// 工程名称
    private String EProjectName;// 项目名称
    private String ESubName;// 子项目名称
    private String ESysName;// 系统名称
    private String ECode;// 存货编号
    private String EProduct;// 物品名称
    private String ESpecs;// 规格型号\图号
    private String EUnit;// 单位
    private String EMount;// 工艺数量
    private String ENote;// 备用
    private String ERMount;// 实购数量
//    private String EApplicant;// 申请人
    private User user; // 提交人
    private String ETemp;// 临时备注
    private String EPlan;// 计划备注
    private String EIsRun;// 发货标识
    private String EPType;// 加工类型
    private String ECompany;// 公司主体
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

    public Date getApplicantData() {
        return ApplicantData;
    }

    public void setApplicantData(Date applicantData) {
        ApplicantData = applicantData;
    }

    public String getEType() {
        return EType;
    }

    public void setEType(String EType) {
        this.EType = EType;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public String getEPName() {
        return EPName;
    }

    public void setEPName(String EPName) {
        this.EPName = EPName;
    }

    public String getEProjectName() {
        return EProjectName;
    }

    public void setEProjectName(String EProjectName) {
        this.EProjectName = EProjectName;
    }

    public String getESubName() {
        return ESubName;
    }

    public void setESubName(String ESubName) {
        this.ESubName = ESubName;
    }

    public String getESysName() {
        return ESysName;
    }

    public void setESysName(String ESysName) {
        this.ESysName = ESysName;
    }

    public String getECode() {
        return ECode;
    }

    public void setECode(String ECode) {
        this.ECode = ECode;
    }

    public String getEProduct() {
        return EProduct;
    }

    public void setEProduct(String EProduct) {
        this.EProduct = EProduct;
    }

    public String getESpecs() {
        return ESpecs;
    }

    public void setESpecs(String ESpecs) {
        this.ESpecs = ESpecs;
    }

    public String getEUnit() {
        return EUnit;
    }

    public void setEUnit(String EUnit) {
        this.EUnit = EUnit;
    }

    public String getEMount() {
        return EMount;
    }

    public void setEMount(String EMount) {
        this.EMount = EMount;
    }

    public String getENote() {
        return ENote;
    }

    public void setENote(String ENote) {
        this.ENote = ENote;
    }

    public String getERMount() {
        return ERMount;
    }

    public void setERMount(String ERMount) {
        this.ERMount = ERMount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getETemp() {
        return ETemp;
    }

    public void setETemp(String ETemp) {
        this.ETemp = ETemp;
    }

    public String getEPlan() {
        return EPlan;
    }

    public void setEPlan(String EPlan) {
        this.EPlan = EPlan;
    }

    public String getEIsRun() {
        return EIsRun;
    }

    public void setEIsRun(String EIsRun) {
        this.EIsRun = EIsRun;
    }

    public String getEPType() {
        return EPType;
    }

    public void setEPType(String EPType) {
        this.EPType = EPType;
    }

    public String getECompany() {
        return ECompany;
    }

    public void setECompany(String ECompany) {
        this.ECompany = ECompany;
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
        return "Equit{" +
                "id='" + id + '\'' +
                ", EID='" + EID + '\'' +
                ", ApplicantData=" + ApplicantData +
                ", EType='" + EType + '\'' +
                ", NID='" + NID + '\'' +
                ", EPName='" + EPName + '\'' +
                ", EProjectName='" + EProjectName + '\'' +
                ", ESubName='" + ESubName + '\'' +
                ", ESysName='" + ESysName + '\'' +
                ", ECode='" + ECode + '\'' +
                ", EProduct='" + EProduct + '\'' +
                ", ESpecs='" + ESpecs + '\'' +
                ", EUnit='" + EUnit + '\'' +
                ", EMount='" + EMount + '\'' +
                ", ENote='" + ENote + '\'' +
                ", ERMount='" + ERMount + '\'' +
                ", user=" + user +
                ", ETemp='" + ETemp + '\'' +
                ", EPlan='" + EPlan + '\'' +
                ", EIsRun='" + EIsRun + '\'' +
                ", EPType='" + EPType + '\'' +
                ", ECompany='" + ECompany + '\'' +
                ", endDate=" + endDate +
                ", state='" + state + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
