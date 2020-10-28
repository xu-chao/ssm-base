package com.java.activiti.model.wms;

import com.java.activiti.model.MyType;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class WmsGood implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("goodEncoding")
    private String goodEncoding;//���ϱ���

    @Field("goodName")
    private String goodName;//��������

    @Field("goodModel")
    private String goodModel;//�����ͺ�

    @Field("goodUnit")
    private String goodUnit;//���ϵ�λ

    @Field("performStatus")
    private Integer performStatus;

    @Field("perform")
    private MyType perform;//�������-performStatus

    @Field("overhaulStatus")
    private Integer overhaulStatus;//�������-overhaulStatus

    @Field("overhaul")
    private MyType overhaul;//�������-overhaulStatus

    public String getGoodEncoding() {
        return goodEncoding;
    }

    public void setGoodEncoding(String goodEncoding) {
        this.goodEncoding = goodEncoding;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodModel() {
        return goodModel;
    }

    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    public String getGoodUnit() {
        return goodUnit;
    }

    public void setGoodUnit(String goodUnit) {
        this.goodUnit = goodUnit;
    }

    public Integer getPerformStatus() {
        return performStatus;
    }

    public void setPerformStatus(Integer performStatus) {
        this.performStatus = performStatus;
    }

    public MyType getPerform() {
        return perform;
    }

    public void setPerform(MyType perform) {
        this.perform = perform;
    }

    public Integer getOverhaulStatus() {
        return overhaulStatus;
    }

    public void setOverhaulStatus(Integer overhaulStatus) {
        this.overhaulStatus = overhaulStatus;
    }

    public MyType getOverhaul() {
        return overhaul;
    }

    public void setOverhaul(MyType overhaul) {
        this.overhaul = overhaul;
    }
}
