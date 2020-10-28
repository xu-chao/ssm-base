package com.java.activiti.model.wms;

import com.java.activiti.model.MyType;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("whId")
        private Integer whId;

    @Field("whName")
    private String whName;//≤÷ø‚√˚≥∆

    @Field("whAddress")
    private String whAddress;//≤÷ø‚µÿ÷∑

    @Field("whDesc")
    private String whDesc;//√Ë ˆ

    private Integer whType;//≤÷ø‚¿‡–Õ

    public Integer getWhId() {
        return whId;
    }

    public void setWhId(Integer whId) {
        this.whId = whId;
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getWhAddress() {
        return whAddress;
    }

    public void setWhAddress(String whAddress) {
        this.whAddress = whAddress;
    }

    public String getWhDesc() {
        return whDesc;
    }

    public void setWhDesc(String whDesc) {
        this.whDesc = whDesc;
    }

    public Integer getWhType() {
        return whType;
    }

    public void setWhType(Integer whType) {
        this.whType = whType;
    }
}
