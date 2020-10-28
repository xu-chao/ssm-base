package com.java.activiti.model.erp;

import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

public class Checkout implements Serializable {

    private static final long serialVersionUID = 1L;

    private String checkoutId;//���鵥��Id

    private Date checkoutDate;//�ͼ�����

    private int SUPPLIER_ID;//��Ӧ��Id

    private Supplier supplier;//��Ӧ����Ϣ

    private int checkoutMount;//�ϸ�����

    private int checkoutNotQuaMount;//���ϸ�����

    private String checkoutType;//�ͼ�����

    private String checkoutMaterial;//�ͼ�����

    private String checkoutPerson;//�ͼ���Ա

    private User user;

    private String checkoutStatus;//�״μ���״̬

    private String checkoutDataStatus;//����״̬

    private String checkoutLabelStatus;//��ǩ״̬

    private Integer checkoutNum;//�ͼ����

    private Integer checkoutFlag;//�ͼ�/�༭

    private String checkoutNotQuaId;//���ϸ�Id

    private NotQualified notQualified;

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getSUPPLIER_ID() {
        return SUPPLIER_ID;
    }

    public void setSUPPLIER_ID(int SUPPLIER_ID) {
        this.SUPPLIER_ID = SUPPLIER_ID;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public int getCheckoutMount() {
        return checkoutMount;
    }

    public void setCheckoutMount(int checkoutMount) {
        this.checkoutMount = checkoutMount;
    }

    public int getCheckoutNotQuaMount() {
        return checkoutNotQuaMount;
    }

    public void setCheckoutNotQuaMount(int checkoutNotQuaMount) {
        this.checkoutNotQuaMount = checkoutNotQuaMount;
    }

    public String getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(String checkoutType) {
        this.checkoutType = checkoutType;
    }

    public String getCheckoutMaterial() {
        return checkoutMaterial;
    }

    public void setCheckoutMaterial(String checkoutMaterial) {
        this.checkoutMaterial = checkoutMaterial;
    }

    public String getCheckoutPerson() {
        return checkoutPerson;
    }

    public void setCheckoutPerson(String checkoutPerson) {
        this.checkoutPerson = checkoutPerson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCheckoutStatus() {
        return checkoutStatus;
    }

    public void setCheckoutStatus(String checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }

    public String getCheckoutDataStatus() {
        return checkoutDataStatus;
    }

    public void setCheckoutDataStatus(String checkoutDataStatus) {
        this.checkoutDataStatus = checkoutDataStatus;
    }

    public String getCheckoutLabelStatus() {
        return checkoutLabelStatus;
    }

    public void setCheckoutLabelStatus(String checkoutLabelStatus) {
        this.checkoutLabelStatus = checkoutLabelStatus;
    }

    public Integer getCheckoutNum() {
        return checkoutNum;
    }

    public void setCheckoutNum(Integer checkoutNum) {
        this.checkoutNum = checkoutNum;
    }

    public Integer getCheckoutFlag() {
        return checkoutFlag;
    }

    public void setCheckoutFlag(Integer checkoutFlag) {
        this.checkoutFlag = checkoutFlag;
    }

    public String getCheckoutNotQuaId() {
        return checkoutNotQuaId;
    }

    public void setCheckoutNotQuaId(String checkoutNotQuaId) {
        this.checkoutNotQuaId = checkoutNotQuaId;
    }

    public NotQualified getNotQualified() {
        return notQualified;
    }

    public void setNotQualified(NotQualified notQualified) {
        this.notQualified = notQualified;
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "checkoutId='" + checkoutId + '\'' +
                ", checkoutDate=" + checkoutDate +
                ", SUPPLIER_ID=" + SUPPLIER_ID +
                ", supplier=" + supplier +
                ", checkoutMount=" + checkoutMount +
                ", checkoutNotQuaMount=" + checkoutNotQuaMount +
                ", checkoutType='" + checkoutType + '\'' +
                ", checkoutMaterial='" + checkoutMaterial + '\'' +
                ", checkoutPerson='" + checkoutPerson + '\'' +
                ", user=" + user +
                ", checkoutStatus='" + checkoutStatus + '\'' +
                ", checkoutDataStatus='" + checkoutDataStatus + '\'' +
                ", checkoutLabelStatus='" + checkoutLabelStatus + '\'' +
                ", checkoutNum=" + checkoutNum +
                ", checkoutFlag=" + checkoutFlag +
                ", checkoutNotQuaId='" + checkoutNotQuaId + '\'' +
                ", notQualified=" + notQualified +
                '}';
    }
}
