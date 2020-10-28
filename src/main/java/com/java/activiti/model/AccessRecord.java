package com.java.activiti.model;

import java.util.Date;

public class AccessRecord {
    /**
     * 登入登出记录ID
     * 仅当该记录从数据库取出时有效
     */
    private Integer record_ID;

    /**
     * 登陆用户ID
     */
    private String user_ID;

    /**
     * 登陆用户名
     */
    private String user_Name;

    /**
     * 记录类型，登入或登出
     */
    private String access_Type;

    /**
     * 登入或登出时间
     */
    private Date access_Time;

    /**
     * 用户登入或登出对应的IP地址
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
