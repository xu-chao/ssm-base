package com.java.activiti.model.erp;

import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    private String purchaseId;//�ջ���ˮ��Id

    private Date purchaseDate;//�ջ�����

    private int SUPPLIER_ID;//��Ӧ��Id

    private Supplier supplier;//��Ӧ����Ϣ

    private String purchasedId;//�ͻ�����

    private Integer purchaseMount;//�ջ�����

    private String purchasePerson;//�ɹ���Ա

    private String purchaseGoodsPerson;//�ջ���Ա

    private User user;

    private int REPO_ID;//�ֿ�Id

    private Respository respository;//�ֿ���Ϣ

    private Integer purchaseNum;//�ջ�����

    private Integer purchaseLeave;//�ջ�����

    private Integer purchaseFlag;//�༭/�ͻ�

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public String getPurchasedId() {
        return purchasedId;
    }

    public void setPurchasedId(String purchasedId) {
        this.purchasedId = purchasedId;
    }

    public Integer getPurchaseMount() {
        return purchaseMount;
    }

    public void setPurchaseMount(Integer purchaseMount) {
        this.purchaseMount = purchaseMount;
    }

    public String getPurchasePerson() {
        return purchasePerson;
    }

    public void setPurchasePerson(String purchasePerson) {
        this.purchasePerson = purchasePerson;
    }

    public String getPurchaseGoodsPerson() {
        return purchaseGoodsPerson;
    }

    public void setPurchaseGoodsPerson(String purchaseGoodsPerson) {
        this.purchaseGoodsPerson = purchaseGoodsPerson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getREPO_ID() {
        return REPO_ID;
    }

    public void setREPO_ID(int REPO_ID) {
        this.REPO_ID = REPO_ID;
    }

    public Respository getRespository() {
        return respository;
    }

    public void setRespository(Respository respository) {
        this.respository = respository;
    }

    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Integer getPurchaseLeave() {
        return purchaseLeave;
    }

    public void setPurchaseLeave(Integer purchaseLeave) {
        this.purchaseLeave = purchaseLeave;
    }

    public Integer getPurchaseFlag() {
        return purchaseFlag;
    }

    public void setPurchaseFlag(Integer purchaseFlag) {
        this.purchaseFlag = purchaseFlag;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseId='" + purchaseId + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", SUPPLIER_ID=" + SUPPLIER_ID +
                ", supplier=" + supplier +
                ", purchasedId='" + purchasedId + '\'' +
                ", purchaseMount=" + purchaseMount +
                ", purchasePerson='" + purchasePerson + '\'' +
                ", purchaseGoodsPerson='" + purchaseGoodsPerson + '\'' +
                ", user=" + user +
                ", REPO_ID=" + REPO_ID +
                ", respository=" + respository +
                ", purchaseNum=" + purchaseNum +
                ", purchaseLeave=" + purchaseLeave +
                ", purchaseFlag=" + purchaseFlag +
                '}';
    }
}
