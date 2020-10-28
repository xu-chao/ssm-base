package com.java.activiti.model;

import java.util.Date;

public class OperationRecord {
    /**
     * 记录ID
     */
    private Integer record_ID;

    /**
     * 执行操作的用户ID
     */
    private String user_ID;

    /**
     * 执行操作的用户名
     */
    private String user_Name;

    /**
     * 操作的名称
     */
    private String operation_Name;

    /**
     * 执行操作的时间
     */
    private Date operation_Time;

    /**
     * 执行操作结果
     */
    private String operation_Result;

    public void setrecord_ID(Integer record_ID) {
        this.record_ID = record_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public void setOperation_Name(String operation_Name) {
        this.operation_Name = operation_Name;
    }

    public void setOperation_Time(Date operation_Time) {
        this.operation_Time = operation_Time;
    }

    public void setOperation_Result(String operation_Result) {
        this.operation_Result = operation_Result;
    }

    public Integer getrecord_ID() {
        return record_ID;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public String getOperation_Name() {
        return operation_Name;
    }

    public Date getOperation_Time() {
        return operation_Time;
    }

    public String getOperation_Result() {
        return operation_Result;
    }

    @Override
    public String toString() {
        return "OperationRecord{" +
                "record_ID=" + record_ID +
                ", user_ID=" + user_ID +
                ", user_Name='" + user_Name + '\'' +
                ", operation_Name='" + operation_Name + '\'' +
                ", operation_Time=" + operation_Time +
                ", operation_Result='" + operation_Result + '\'' +
                '}';
    }
}
