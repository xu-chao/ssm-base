package com.java.activiti.pojo;

import com.java.activiti.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfoVO extends User {

    /**
     * 用户ID
     */
    private String userID;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码（已加密）
     */
    private String password;

    /**
     * 用户角色
     */
    private List<String> group = new ArrayList<>();

    /**
     * 用户部门
     */
    private List<String> dept = new ArrayList<>();

    /**
     * 用户公园
     */
    private List<Integer> park = new ArrayList<>();

    /**
     * 用户仓库
     */
    private List<Integer> warehouse = new ArrayList<>();

    public List<Integer> getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(List<Integer> warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * 用户账户属性的 getter 以及 setter
     */

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getGroup() {
        return group;
    }

    public void setGroup(List<String> group) {
        this.group = group;
    }

    public List<String> getDept() {
        return dept;
    }

    public void setDept(List<String> dept) {
        this.dept = dept;
    }

    public List<Integer> getPark() {
        return park;
    }

    public void setPark(List<Integer> park) {
        this.park = park;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", group=" + group +
                ", dept=" + dept +
                ", park=" + park +
                '}';
    }
}
