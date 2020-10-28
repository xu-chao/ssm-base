package com.java.activiti.model;

import java.io.Serializable;

public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    private String goodsId;

    private String goodsName;

    private String goodsSysName;

    private String goodsType;

    private String goodsCode;

    private String goodsUnit;

    private String goodsSize;

    private String goodsFunction;

    private String goodsMessage;

    private String goodsElse;

    private String goodsValue;

    private String goodsLink;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSysName() {
        return goodsSysName;
    }

    public void setGoodsSysName(String goodsSysName) {
        this.goodsSysName = goodsSysName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getGoodsFunction() {
        return goodsFunction;
    }

    public void setGoodsFunction(String goodsFunction) {
        this.goodsFunction = goodsFunction;
    }

    public String getGoodsMessage() {
        return goodsMessage;
    }

    public void setGoodsMessage(String goodsMessage) {
        this.goodsMessage = goodsMessage;
    }

    public String getGoodsElse() {
        return goodsElse;
    }

    public void setGoodsElse(String goodsElse) {
        this.goodsElse = goodsElse;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
    }

    public String getGoodsLink() {
        return goodsLink;
    }

    public void setGoodsLink(String goodsLink) {
        this.goodsLink = goodsLink;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsSysName='" + goodsSysName + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsUnit='" + goodsUnit + '\'' +
                ", goodsSize='" + goodsSize + '\'' +
                ", goodsFunction='" + goodsFunction + '\'' +
                ", goodsMessage='" + goodsMessage + '\'' +
                ", goodsElse='" + goodsElse + '\'' +
                ", goodsValue='" + goodsValue + '\'' +
                ", goodsLink='" + goodsLink + '\'' +
                '}';
    }
}
