package com.java.activiti.pojo;

import com.java.activiti.model.UserDept;
import com.java.activiti.model.UserPark;

public class UserAllVO {

    /**
     * �û�-����
     */
    private UserDept userDept;

    /**
     * �û�-��԰
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
