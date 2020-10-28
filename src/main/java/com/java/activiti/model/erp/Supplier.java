package com.java.activiti.model.erp;

import java.io.Serializable;

public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    private int SUPPLIER_ID;//供应商Id

    private String SUPPLIER_NAME;//供应商名称

    private String SUPPLIER_PERSON;//供应商负责人

    private String SUPPLIER_TEL;//供应商联系方式

    private String SUPPLIER_EMAIL;//供应商Email

    private String SUPPLIER_ADDRESS;//供应商地址

    public int getSUPPLIER_ID() {
        return SUPPLIER_ID;
    }

    public void setSUPPLIER_ID(int SUPPLIER_ID) {
        this.SUPPLIER_ID = SUPPLIER_ID;
    }

    public String getSUPPLIER_NAME() {
        return SUPPLIER_NAME;
    }

    public void setSUPPLIER_NAME(String SUPPLIER_NAME) {
        this.SUPPLIER_NAME = SUPPLIER_NAME;
    }

    public String getSUPPLIER_PERSON() {
        return SUPPLIER_PERSON;
    }

    public void setSUPPLIER_PERSON(String SUPPLIER_PERSON) {
        this.SUPPLIER_PERSON = SUPPLIER_PERSON;
    }

    public String getSUPPLIER_TEL() {
        return SUPPLIER_TEL;
    }

    public void setSUPPLIER_TEL(String SUPPLIER_TEL) {
        this.SUPPLIER_TEL = SUPPLIER_TEL;
    }

    public String getSUPPLIER_EMAIL() {
        return SUPPLIER_EMAIL;
    }

    public void setSUPPLIER_EMAIL(String SUPPLIER_EMAIL) {
        this.SUPPLIER_EMAIL = SUPPLIER_EMAIL;
    }

    public String getSUPPLIER_ADDRESS() {
        return SUPPLIER_ADDRESS;
    }

    public void setSUPPLIER_ADDRESS(String SUPPLIER_ADDRESS) {
        this.SUPPLIER_ADDRESS = SUPPLIER_ADDRESS;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "SUPPLIER_ID=" + SUPPLIER_ID +
                ", SUPPLIER_NAME='" + SUPPLIER_NAME + '\'' +
                ", SUPPLIER_PERSON='" + SUPPLIER_PERSON + '\'' +
                ", SUPPLIER_TEL='" + SUPPLIER_TEL + '\'' +
                ", SUPPLIER_EMAIL='" + SUPPLIER_EMAIL + '\'' +
                ", SUPPLIER_ADDRESS='" + SUPPLIER_ADDRESS + '\'' +
                '}';
    }
}
