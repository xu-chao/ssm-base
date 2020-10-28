package com.java.activiti.model.erp;

import java.io.Serializable;

public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private int COMPANY_ID;//公司主体Id

    private String COMPANY_NAME;//公司主体名称

    private String COMPANY_PERSON;//公司主体负责人

    private String COMPANY_TEL;//公司主体联系方式

    private String COMPANY_EMAIL;//公司主体Email

    private String COMPANY_ADDRESS;//公司主体地址

    public int getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(int COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public void setCOMPANY_NAME(String COMPANY_NAME) {
        this.COMPANY_NAME = COMPANY_NAME;
    }

    public String getCOMPANY_PERSON() {
        return COMPANY_PERSON;
    }

    public void setCOMPANY_PERSON(String COMPANY_PERSON) {
        this.COMPANY_PERSON = COMPANY_PERSON;
    }

    public String getCOMPANY_TEL() {
        return COMPANY_TEL;
    }

    public void setCOMPANY_TEL(String COMPANY_TEL) {
        this.COMPANY_TEL = COMPANY_TEL;
    }

    public String getCOMPANY_EMAIL() {
        return COMPANY_EMAIL;
    }

    public void setCOMPANY_EMAIL(String COMPANY_EMAIL) {
        this.COMPANY_EMAIL = COMPANY_EMAIL;
    }

    public String getCOMPANY_ADDRESS() {
        return COMPANY_ADDRESS;
    }

    public void setCOMPANY_ADDRESS(String COMPANY_ADDRESS) {
        this.COMPANY_ADDRESS = COMPANY_ADDRESS;
    }

    @Override
    public String toString() {
        return "Company{" +
                "COMPANY_ID=" + COMPANY_ID +
                ", COMPANY_NAME='" + COMPANY_NAME + '\'' +
                ", COMPANY_PERSON='" + COMPANY_PERSON + '\'' +
                ", COMPANY_TEL='" + COMPANY_TEL + '\'' +
                ", COMPANY_EMAIL='" + COMPANY_EMAIL + '\'' +
                ", COMPANY_ADDRESS='" + COMPANY_ADDRESS + '\'' +
                '}';
    }
}
