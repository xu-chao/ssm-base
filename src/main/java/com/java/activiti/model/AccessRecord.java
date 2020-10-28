package com.java.activiti.model;

import java.util.Date;

public class AccessRecord {
    /**
     * ����ǳ���¼ID
     * �����ü�¼�����ݿ�ȡ��ʱ��Ч
     */
    private Integer record_ID;

    /**
     * ��½�û�ID
     */
    private String user_ID;

    /**
     * ��½�û���
     */
    private String user_Name;

    /**
     * ��¼���ͣ������ǳ�
     */
    private String access_Type;

    /**
     * �����ǳ�ʱ��
     */
    private Date access_Time;

    /**
     * �û������ǳ���Ӧ��IP��ַ
     */
    private String access_IP;

    public Integer getRecord_ID() {
        return record_ID;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public String getAccess_IP() {
        return access_IP;
    }

    public String getAccess_Type() {
        return access_Type;
    }

    public Date getAccess_Time() {
        return access_Time;
    }

    public void setRecord_ID(Integer record_ID) {
        this.record_ID = record_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public void setAccess_Type(String access_Type) {
        this.access_Type = access_Type;
    }

    public void setAccess_Time(Date access_Time) {
        this.access_Time = access_Time;
    }

    public void setAccess_IP(String access_IP) {
        this.access_IP = access_IP;
    }

    @Override
    public String toString() {
        return "AccessRecord{" +
                "record_ID=" + record_ID +
                ", user_ID=" + user_ID +
                ", user_Name='" + user_Name + '\'' +
                ", access_Type='" + access_Type + '\'' +
                ", access_Time=" + access_Time +
                ", access_IP='" + access_IP + '\'' +
                '}';
    }
}
