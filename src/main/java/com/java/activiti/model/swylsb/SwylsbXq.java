package com.java.activiti.model.swylsb;

import java.io.Serializable;

public class SwylsbXq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;//ID

    private String sbzt;//�豸״̬

    private String ztbz;//״̬��ע

    private String wtfk;//���ⷴ��

    private String yjsm;//Ԥ��˵��

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getSbzt() {
        return sbzt;
    }

    public void setSbzt(String sbzt) {
        this.sbzt = sbzt;
    }

    public String getZtbz() {
        return ztbz;
    }

    public void setZtbz(String ztbz) {
        this.ztbz = ztbz;
    }

    public String getWtfk() {
        return wtfk;
    }

    public void setWtfk(String wtfk) {
        this.wtfk = wtfk;
    }

    public String getYjsm() {
        return yjsm;
    }

    public void setYjsm(String yjsm) {
        this.yjsm = yjsm;
    }

    @Override
    public String toString() {
        return "SwylsbXq{" +
                "ID=" + ID +
                ", sbzt='" + sbzt + '\'' +
                ", ztbz='" + ztbz + '\'' +
                ", wtfk='" + wtfk + '\'' +
                ", yjsm='" + yjsm + '\'' +
                '}';
    }
}
