package com.java.activiti.pojo;

import com.java.activiti.model.UserDept;
import com.java.activiti.model.UserPark;

public class UserAllVO {

    /**
     * 用户-部门
     */
    private UserDept userDept;

    /**
     * 用户-公园
     */
    private UserPark userPark;

    public UserDept getUserDept() {
        return userDept;
    }

    public void setUserDept(UserDept userDept) {
        this.userDept = userDept;
    }

    public UserPark getUserPark() {
        return userPark;
    }

    public void setUserPark(UserPark userPark) {
        this.userPark = userPark;
    }

    @Override
    public String toString() {
        return "UserAllVO{" +
                "userDept=" + userDept +
                ", userPark=" + userPark +
                '}';
    }
}
