package com.java.activiti.model.wms;

import com.java.activiti.model.MyType;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class WmsGood implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("goodEncoding")
    private String goodEncoding;//物料编码

    @Field("goodName")
    private String goodName;//物料名称

    @Field("goodModel")
    private String goodModel;//物料型号

    @Field("goodUnit")
    private String goodUnit;//物料单位

    @Field("performStatus")
    private Integer performStatus;

    @Field("perform")
    private MyType perform;//性能类别-performStatus

    @Field("overhaulStatus")
    private Integer overhaulStatus;//检修类别-overhaulStatus

    @Field("overhaul")
    private MyType overhaul;//检修类别-overhaulStatus

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
