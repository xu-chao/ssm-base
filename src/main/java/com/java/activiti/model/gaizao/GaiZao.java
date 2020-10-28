package com.java.activiti.model.gaizao;

import com.java.activiti.model.MyType;
import com.java.activiti.model.Project;
import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;
//改造
public class GaiZao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String gzID;

    private Project project;//项目名

    private User userID;//负责人

    private String gzUser;//改造人员

    private int gzcs;//改造次数

    private Date gzDate;//改造日期

    private MyType gzlx;//改造类型

    private String gzxq;//改造详情

    private String beizu;//备注

    private String file_id;//附件

    public String getGzID() {
        return gzID;
    }

    public void setGzID(String gzID) {
        this.gzID = gzID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public String getGzUser() {
        return gzUser;
    }

    public void setGzUser(String gzUser) {
        this.gzUser = gzUser;
    }

    public int getGzcs() {
        return gzcs;
    }

    public void setGzcs(int gzcs) {
        this.gzcs = gzcs;
    }

    public Date getGzDate() {
        return gzDate;
    }

    public void setGzDate(Date gzDate) {
        this.gzDate = gzDate;
    }

    public MyType getGzlx() {
        return gzlx;
    }

    public void setGzlx(MyType gzlx) {
        this.gzlx = gzlx;
    }

    public String getGzxq() {
        return gzxq;
    }

    public void setGzxq(String gzxq) {
        this.gzxq = gzxq;
    }

    public String getBeizu() {
        return beizu;
    }

    public void setBeizu(String beizu) {
        this.beizu = beizu;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
}
