package com.java.activiti.model.wms;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class Wmssupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("supplierId")
        private Integer supplierId;

    @Field("supplierName")
    private String supplierName;//供应商名称

    @Field("supplierTel")
    private String supplierTel;//供应商电话

    @Field("supplierEmail")
    private String supplierEmail;//供应商邮箱

    @Field("supplierAddress")
    private String supplierAddress;//供应商地址

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierTel() {
        return supplierTel;
    }

    public void setSupplierTel(String supplierTel) {
        this.supplierTel = supplierTel;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }
}
