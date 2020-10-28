package com.java.activiti.security.session;

import java.io.Serializable;

public class SessionStatus implements Serializable {

    private static final int serialVersionUID = 1;

    //�Ƿ��߳� true:��Ч��false���߳���
    private Boolean onlineStatus = Boolean.TRUE;


    public Boolean isOnlineStatus(){
        return onlineStatus;
    }

    public Boolean getOnlineStatus() {
        return onlineStatus;
    }
    public void setOnlineStatus(Boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
