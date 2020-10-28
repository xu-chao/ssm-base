package com.java.activiti.model;

import java.io.Serializable;

public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String storageId;

    private Integer mount;//��������������

    private Integer mountBack;//��������

    private Integer mountIn;//ʵ������

    private Integer mountStorage;//���

    private Integer mountQualified;//�ϸ�����

    private Integer mountNotQualified;//���ϸ�����

    private String ETemp;//���

    private String EPlan;//�ƻ���ע

    private String EIsRun;//������ʶ

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public Integer getMount() {
        return mount;
    }

    public void setMount(Integer mount) {
        this.mount = mount;
    }

    public Integer getMountBack() {
        return mountBack;
    }

    public void setMountBack(Integer mountBack) {
        this.mountBack = mountBack;
    }

    public Integer getMountIn() {
        return mountIn;
    }

    public void setMountIn(Integer mountIn) {
        this.mountIn = mountIn;
    }

    public Integer getMountStorage() {
        return mountStorage;
    }

    public void setMountStorage(Integer mountStorage) {
        this.mountStorage = mountStorage;
    }

    public Integer getMountQualified() {
        return mountQualified;
    }

    public void setMountQualified(Integer mountQualified) {
        this.mountQualified = mountQualified;
    }

    public Integer getMountNotQualified() {
        return mountNotQualified;
    }

    public void setMountNotQualified(Integer mountNotQualified) {
        this.mountNotQualified = mountNotQualified;
    }

    public String getETemp() {
        return ETemp;
    }

    public void setETemp(String ETemp) {
        this.ETemp = ETemp;
    }

    public String getEPlan() {
        return EPlan;
    }

    public void setEPlan(String EPlan) {
        this.EPlan = EPlan;
    }

    public String getEIsRun() {
        return EIsRun;
    }

    public void setEIsRun(String EIsRun) {
        this.EIsRun = EIsRun;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "storageId='" + storageId + '\'' +
                ", mount=" + mount +
                ", mountBack=" + mountBack +
                ", mountIn=" + mountIn +
                ", mountStorage=" + mountStorage +
                ", mountQualified=" + mountQualified +
                ", mountNotQualified=" + mountNotQualified +
                ", ETemp='" + ETemp + '\'' +
                ", EPlan='" + EPlan + '\'' +
                ", EIsRun='" + EIsRun + '\'' +
                '}';
    }
}
