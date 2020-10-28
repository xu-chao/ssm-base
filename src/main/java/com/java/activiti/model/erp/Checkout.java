package com.java.activiti.model.erp;

import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

public class Checkout implements Serializable {

    private static final long serialVersionUID = 1L;

    private String checkoutId;//检验单号Id

    private Date checkoutDate;//送检日期

    private int SUPPLIER_ID;//供应商Id

    private Supplier supplier;//供应商信息

    private int checkoutMount;//合格数量

    private int checkoutNotQuaMount;//不合格数量

    private String checkoutType;//送检类型

    private String checkoutMaterial;//送检资料

    private String checkoutPerson;//送检人员

    private User user;

    private String checkoutStatus;//首次检验状态

    private String checkoutDataStatus;//单据状态

    private String checkoutLabelStatus;//标签状态

    private Integer checkoutNum;//送检次数

    private Integer checkoutFlag;//送检/编辑

    private String checkoutNotQuaId;//不合格Id

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
