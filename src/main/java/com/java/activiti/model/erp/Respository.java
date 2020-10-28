package com.java.activiti.model.erp;

import java.io.Serializable;

public class Respository implements Serializable {

    private static final long serialVersionUID = 1L;

    private int REPO_ID;//≤÷ø‚ID

    private String REPO_ADDRESS;//≤÷ø‚µÿ÷∑

    private String REPO_STATUS;//≤÷ø‚◊¥Ã¨

    private String REPO_AREA;//≤÷ø‚µÿ÷∑

    private String REPO_DESC;//≤÷ø‚√Ë ˆ

    public int getREPO_ID() {
        return REPO_ID;
    }

    public void setREPO_ID(int REPO_ID) {
        this.REPO_ID = REPO_ID;
    }

    public String getREPO_ADDRESS() {
        return REPO_ADDRESS;
    }

    public void setREPO_ADDRESS(String REPO_ADDRESS) {
        this.REPO_ADDRESS = REPO_ADDRESS;
    }

    public String getREPO_STATUS() {
        return REPO_STATUS;
    }

    public void setREPO_STATUS(String REPO_STATUS) {
        this.REPO_STATUS = REPO_STATUS;
    }

    public String getREPO_AREA() {
        return REPO_AREA;
    }

    public void setREPO_AREA(String REPO_AREA) {
        this.REPO_AREA = REPO_AREA;
    }

    public String getREPO_DESC() {
        return REPO_DESC;
    }

    public void setREPO_DESC(String REPO_DESC) {
        this.REPO_DESC = REPO_DESC;
    }

    @Override
    public String toString() {
        return "Respository{" +
                "REPO_ID=" + REPO_ID +
                ", REPO_ADDRESS='" + REPO_ADDRESS + '\'' +
                ", REPO_STATUS='" + REPO_STATUS + '\'' +
                ", REPO_AREA='" + REPO_AREA + '\'' +
                ", REPO_DESC='" + REPO_DESC + '\'' +
                '}';
    }
}
