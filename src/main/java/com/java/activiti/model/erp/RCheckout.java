package com.java.activiti.model.erp;

import java.io.Serializable;
import java.util.Date;

public class RCheckout implements Serializable {

    private static final long serialVersionUID = 1L;

    private String RCheckoutId;//返修Id

    private Date RCheckoutDate;//返修日期

    private int RCheckoutMount;//返修合格数量

    private int RCheckoutNotQuaMount;//返修不合格数量

    private String RCheckoutResult;//返修结果

    private String RCheckoutDesc;//返修描述

    private int RCheckoutNum;//返修次数

    private int RCheckoutFlag;//编辑/返修

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
