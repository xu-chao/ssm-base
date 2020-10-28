package com.java.activiti.pojo;

import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

public class UserOnlineVO extends User implements Serializable {

    private static final int serialVersionUID = 1;

    //Session Id
    private String sessionId;
    //Session Host
    private String host;
    //Session����ʱ��
    private Date startTime;
    //Session��󽻻�ʱ��
    private Date lastAccess;
    //Session timeout
    private long timeout;
    //session �Ƿ��߳�
    private boolean sessionStatus = Boolean.TRUE;

    public UserOnlineVO() {
    }

    public UserOnlineVO(User user) {
        super(user);
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
