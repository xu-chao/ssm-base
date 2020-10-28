package com.java.activiti.model.gaizao;/**
 * @ProjectName: fangte
 * @Package: com.java.activiti.model.gaizao
 * @ClassName: Huiyishi
 * @Author: LIUHD
 * @Description: ª·“È “
 * @Date: 2019/12/31 11:08
 * @Version: 1.0
 */

import com.java.activiti.model.Park;
import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LIUHD
 * @version 1.0
 * @description
 * @updateRemark
 * @updateUser
 * @createDate 2019/12/31 11:08
 * @updateDate 2019/12/31 11:08
 **/
public class Huiyishi implements Serializable {
    private static final long serialVersionUID = 1L;
    private int hysID;
    private Park park;
    private User userID;
    private User userAdviser;
    private String hysText;
    private String remark1;
    private String remark2;
    private String fileID;
    private Date hysDate;
    private String state;
    private int stateID;
    private String processInstanceId;

    public int getHysID() {
        return hysID;
    }

    public void setHysID(int hysID) {
        this.hysID = hysID;
    }

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public User getUserAdviser() {
        return userAdviser;
    }

    public void setUserAdviser(User userAdviser) {
        this.userAdviser = userAdviser;
    }

    public String getHysText() {
        return hysText;
    }

    public void setHysText(String hysText) {
        this.hysText = hysText;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public Date getHysDate() {
        return hysDate;
    }

    public void setHysDate(Date hysDate) {
        this.hysDate = hysDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
