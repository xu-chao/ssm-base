package com.java.activiti.model;

import com.java.activiti.model.erp.Company;

import java.io.Serializable;
import java.util.Date;

public class Need implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id; // ����
    private String EID; // ���
    private Date EApplicantData; // ��������
    private String EPType;// �ӹ�����
    private String EBillCompany; //�ᵥ��˾
    private String EType; // ����/רҵ
    private String EZone; // ����
    private String EConsumer; // �����Ĳ�:��/��
    private String NID; // �ɹ�����
    private int ESubID;// ����ĿID
    private SubProject subProject;//����Ŀ����
    private String ESubProjectNameElse;//�Զ�������Ŀ����
    private String ESysName;// ϵͳ����
    private String EApplicant;// ������
    private User user; // �ύ��
    private int ECompanyId;// ��˾����
    private Company company;
    private Date endDate;
    private String state; // ���״̬  δ�ύ  ����� ���ͨ�� ���δͨ��
    private String processInstanceId; // ����ʵ��Id processInstanceId

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
