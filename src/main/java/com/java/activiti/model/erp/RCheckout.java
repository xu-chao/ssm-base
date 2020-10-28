package com.java.activiti.model.erp;

import java.io.Serializable;
import java.util.Date;

public class RCheckout implements Serializable {

    private static final long serialVersionUID = 1L;

    private String RCheckoutId;//����Id

    private Date RCheckoutDate;//��������

    private int RCheckoutMount;//���޺ϸ�����

    private int RCheckoutNotQuaMount;//���޲��ϸ�����

    private String RCheckoutResult;//���޽��

    private String RCheckoutDesc;//��������

    private int RCheckoutNum;//���޴���

    private int RCheckoutFlag;//�༭/����

    public String getRCheckoutId() {
        return RCheckoutId;
    }

    public void setRCheckoutId(String RCheckoutId) {
        this.RCheckoutId = RCheckoutId;
    }

    public Date getRCheckoutDate() {
        return RCheckoutDate;
    }

    public void setRCheckoutDate(Date RCheckoutDate) {
        this.RCheckoutDate = RCheckoutDate;
    }

    public int getRCheckoutMount() {
        return RCheckoutMount;
    }

    public void setRCheckoutMount(int RCheckoutMount) {
        this.RCheckoutMount = RCheckoutMount;
    }

    public int getRCheckoutNotQuaMount() {
        return RCheckoutNotQuaMount;
    }

    public void setRCheckoutNotQuaMount(int RCheckoutNotQuaMount) {
        this.RCheckoutNotQuaMount = RCheckoutNotQuaMount;
    }

    public String getRCheckoutResult() {
        return RCheckoutResult;
    }

    public void setRCheckoutResult(String RCheckoutResult) {
        this.RCheckoutResult = RCheckoutResult;
    }

    public String getRCheckoutDesc() {
        return RCheckoutDesc;
    }

    public void setRCheckoutDesc(String RCheckoutDesc) {
        this.RCheckoutDesc = RCheckoutDesc;
    }

    public int getRCheckoutNum() {
        return RCheckoutNum;
    }

    public void setRCheckoutNum(int RCheckoutNum) {
        this.RCheckoutNum = RCheckoutNum;
    }

    public int getRCheckoutFlag() {
        return RCheckoutFlag;
    }

    public void setRCheckoutFlag(int RCheckoutFlag) {
        this.RCheckoutFlag = RCheckoutFlag;
    }

    @Override
    public String toString() {
        return "RCheckout{" +
                "RCheckoutId='" + RCheckoutId + '\'' +
                ", RCheckoutDate=" + RCheckoutDate +
                ", RCheckoutMount=" + RCheckoutMount +
                ", RCheckoutNotQuaMount=" + RCheckoutNotQuaMount +
                ", RCheckoutResult='" + RCheckoutResult + '\'' +
                ", RCheckoutDesc='" + RCheckoutDesc + '\'' +
                ", RCheckoutNum=" + RCheckoutNum +
                ", RCheckoutFlag=" + RCheckoutFlag +
                '}';
    }
}
