package com.java.activiti.model.wms;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class Wmssupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("supplierId")
        private Integer supplierId;

    @Field("supplierName")
    private String supplierName;//��Ӧ������

    @Field("supplierTel")
    private String supplierTel;//��Ӧ�̵绰

    @Field("supplierEmail")
    private String supplierEmail;//��Ӧ������

    @Field("supplierAddress")
    private String supplierAddress;//��Ӧ�̵�ַ

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
