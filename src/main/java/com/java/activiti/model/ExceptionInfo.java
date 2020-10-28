package com.java.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class ExceptionInfo implements Serializable {
    private Integer ID;
    private String IP;//访问IP
    private String userID;//用户ID
    private String className;//类名
    private String methodName;//方法名
    private String exceptionType;//异常类型
    private String exceptionMsg;//异常信息
    private Date exceptionTime;//异常发生时间

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public Date getExceptionTime() {
        return exceptionTime;
    }

    public void setExceptionTime(Date exceptionTime) {
        this.exceptionTime = exceptionTime;
    }

    @Override
    public String toString() {
        return "ExceptionInfo{" +
                "ID=" + ID +
                ", IP='" + IP + '\'' +
                ", userID='" + userID + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", exceptionType='" + exceptionType + '\'' +
                ", exceptionMsg='" + exceptionMsg + '\'' +
                ", exceptionTime=" + exceptionTime +
                '}';
    }
}
