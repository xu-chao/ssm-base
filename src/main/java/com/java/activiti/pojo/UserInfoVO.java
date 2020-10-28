package com.java.activiti.pojo;

import com.java.activiti.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfoVO extends User {

    /**
     * �û�ID
     */
    private String userID;

    /**
     * �û���
     */
    private String userName;

    /**
     * �û����루�Ѽ��ܣ�
     */
    private String password;

    /**
     * �û���ɫ
     */
    private List<String> group = new ArrayList<>();

    /**
     * �û�����
     */
    private List<String> dept = new ArrayList<>();

    /**
     * �û���԰
     */
    private List<Integer> park = new ArrayList<>();

    /**
     * �û��ֿ�
     */
    private List<Integer> warehouse = new ArrayList<>();

    public List<Integer> getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(List<Integer> warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * �û��˻����Ե� getter �Լ� setter
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
