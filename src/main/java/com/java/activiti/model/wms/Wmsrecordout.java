package com.java.activiti.model.wms;

import com.java.activiti.model.MyType;
import com.java.activiti.model.Project;
import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

public class Wmsrecordout implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer outId;//Id

    private String outOdd;//���ⵥ��

    private String userId;//�û�Id

    private User user;//�û�

    private String goodId;//����Id

    private WmsGood goods;//����

    private String outAmount;//����

    private String outRemark;//��ǩ

    private Integer inRespositoryId;//���ֿ�Id

    private Warehouse inWarehouse;//���ֿ�

    private Integer outRespositoryId;//����ֿ�Id

    private Warehouse outWarehouse;//����ֿ�

    private Date outDate;//����ʱ��

    private String outInfo;//���ⱸע

    private Integer outTypeId;//��������Id

    private MyType outType;//��������

    private Integer projectId;//��ĿId

    private Project project;//��Ŀ

    private Integer subProId;//��ϵͳId

    private MyType subPro;//��ϵͳ

    private Integer approveId;//��������

    public Integer getOutId() {
        return outId;
    }

    public void setOutId(Integer outId) {
        this.outId = outId;
    }

    public String getOutOdd() {
        return outOdd;
    }

    public void setOutOdd(String outOdd) {
        this.outOdd = outOdd;
    }

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

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public WmsGood getGoods() {
        return goods;
    }

    public void setGoods(WmsGood goods) {
        this.goods = goods;
    }

    public String getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(String outAmount) {
        this.outAmount = outAmount;
    }

    public String getOutRemark() {
        return outRemark;
    }

    public void setOutRemark(String outRemark) {
        this.outRemark = outRemark;
    }

    public Integer getInRespositoryId() {
        return inRespositoryId;
    }

    public void setInRespositoryId(Integer inRespositoryId) {
        this.inRespositoryId = inRespositoryId;
    }

    public Warehouse getInWarehouse() {
        return inWarehouse;
    }

    public void setInWarehouse(Warehouse inWarehouse) {
        this.inWarehouse = inWarehouse;
    }

    public Integer getOutRespositoryId() {
        return outRespositoryId;
    }

    public void setOutRespositoryId(Integer outRespositoryId) {
        this.outRespositoryId = outRespositoryId;
    }

    public Warehouse getOutWarehouse() {
        return outWarehouse;
    }

    public void setOutWarehouse(Warehouse outWarehouse) {
        this.outWarehouse = outWarehouse;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(String outInfo) {
        this.outInfo = outInfo;
    }

    public Integer getOutTypeId() {
        return outTypeId;
    }

    public void setOutTypeId(Integer outTypeId) {
        this.outTypeId = outTypeId;
    }

    public MyType getOutType() {
        return outType;
    }

    public void setOutType(MyType outType) {
        this.outType = outType;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getSubProId() {
        return subProId;
    }

    public void setSubProId(Integer subProId) {
        this.subProId = subProId;
    }

    public MyType getSubPro() {
        return subPro;
    }

    public void setSubPro(MyType subPro) {
        this.subPro = subPro;
    }

    public Integer getApproveId() {
        return approveId;
    }

    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    @Override
    public String toString() {
        return "Wmsrecordout{" +
                "outId=" + outId +
                ", outOdd='" + outOdd + '\'' +
                ", userId='" + userId + '\'' +
                ", user=" + user +
                ", goodId=" + goodId +
                ", goods=" + goods +
                ", outAmount='" + outAmount + '\'' +
                ", outRemark='" + outRemark + '\'' +
                ", inRespositoryId=" + inRespositoryId +
                ", inWarehouse=" + inWarehouse +
                ", outRespositoryId=" + outRespositoryId +
                ", outWarehouse=" + outWarehouse +
                ", outDate=" + outDate +
                ", outInfo='" + outInfo + '\'' +
                ", outTypeId=" + outTypeId +
                ", outType=" + outType +
                ", projectId=" + projectId +
                ", project=" + project +
                ", subProId=" + subProId +
                ", subPro=" + subPro +
                ", approveId=" + approveId +
                '}';
    }
}
