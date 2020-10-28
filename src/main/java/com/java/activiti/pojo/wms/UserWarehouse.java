package com.java.activiti.pojo.wms;

import com.java.activiti.model.User;
import com.java.activiti.model.wms.Warehouse;

import java.io.Serializable;

public class UserWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private User user;

    private Integer whId;

    private Warehouse warehouse;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getWhId() {
        return whId;
    }

    public void setWhId(Integer whId) {
        this.whId = whId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {
        return "UserWarehouse{" +
                "userId='" + userId + '\'' +
                ", user=" + user +
                ", whId='" + whId + '\'' +
                ", warehouse=" + warehouse +
                '}';
    }
}
