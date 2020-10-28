package com.java.activiti.model.planTask;

import com.java.activiti.model.MyType;
import com.java.activiti.model.Park;
import com.java.activiti.model.Project;
import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

public class PlanTask implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID 唯一
     */
    private int ID;
    /**
     * 计划单号
     */
    private String ptID;

    /**
     * 申请人
     */
    private User user;
    /**
     * 项目
     */
    private int projectID;

    private Project project;
    /**
     * 系统
     */
    private MyType ptxitong;
    /**
     * 类目
     */
    private String ptleimu;

    /**
     * 任务名称
     */
    private String ptrwmc;
    /**
     * 图号
     */
    private String pttuhao;
    /**
     * 设备名称
     */
    private String ptsbmc;
    /**
     * 型号
     */
    private String ptxinghao;
    /**
     * 单位
     */
    private MyType ptdanwei;
    /**
     * 数量
     */
    private int ptsuliang;
    /**
     * 工种
     */
    private String ptgz;
    /**
     * 类型
     */
    private MyType ptlx;
    /**
     * 备注
     */
    private String ptbz;
    /**
     * 日期
     */
    private Date ptdate;
    /**
     * 审核状态
     */
    private String state;
    /**
     * 流程实例Id
     */
    private String processInstanceId;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPtID() {
        return ptID;
    }

    public void setPtID(String ptID) {
        this.ptID = ptID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public MyType getPtxitong() {
        return ptxitong;
    }

    public void setPtxitong(MyType ptxitong) {
        this.ptxitong = ptxitong;
    }

    public String getPtleimu() {
        return ptleimu;
    }

    public void setPtleimu(String ptleimu) {
        this.ptleimu = ptleimu;
    }

    public String getPtrwmc() {
        return ptrwmc;
    }

    public void setPtrwmc(String ptrwmc) {
        this.ptrwmc = ptrwmc;
    }

    public String getPttuhao() {
        return pttuhao;
    }

    public void setPttuhao(String pttuhao) {
        this.pttuhao = pttuhao;
    }

    public String getPtsbmc() {
        return ptsbmc;
    }

    public void setPtsbmc(String ptsbmc) {
        this.ptsbmc = ptsbmc;
    }

    public String getPtxinghao() {
        return ptxinghao;
    }

    public void setPtxinghao(String ptxinghao) {
        this.ptxinghao = ptxinghao;
    }

    public MyType getPtdanwei() {
        return ptdanwei;
    }

    public void setPtdanwei(MyType ptdanwei) {
        this.ptdanwei = ptdanwei;
    }

    public int getPtsuliang() {
        return ptsuliang;
    }

    public void setPtsuliang(int ptsuliang) {
        this.ptsuliang = ptsuliang;
    }

    public String getPtgz() {
        return ptgz;
    }

    public void setPtgz(String ptgz) {
        this.ptgz = ptgz;
    }

    public MyType getPtlx() {
        return ptlx;
    }

    public void setPtlx(MyType ptlx) {
        this.ptlx = ptlx;
    }

    public String getPtbz() {
        return ptbz;
    }

    public void setPtbz(String ptbz) {
        this.ptbz = ptbz;
    }

    public Date getPtdate() {
        return ptdate;
    }

    public void setPtdate(Date ptdate) {
        this.ptdate = ptdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "PlanTask{" +
                "ID=" + ID +
                ", ptID='" + ptID + '\'' +
                ", user=" + user +
                ", projectID=" + projectID +
                ", project=" + project +
                ", ptxitong=" + ptxitong +
                ", ptleimu='" + ptleimu + '\'' +
                ", ptrwmc='" + ptrwmc + '\'' +
                ", pttuhao='" + pttuhao + '\'' +
                ", ptsbmc='" + ptsbmc + '\'' +
                ", ptxinghao='" + ptxinghao + '\'' +
                ", ptdanwei=" + ptdanwei +
                ", ptsuliang=" + ptsuliang +
                ", ptgz='" + ptgz + '\'' +
                ", ptlx=" + ptlx +
                ", ptbz='" + ptbz + '\'' +
                ", ptdate=" + ptdate +
                ", state='" + state + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
