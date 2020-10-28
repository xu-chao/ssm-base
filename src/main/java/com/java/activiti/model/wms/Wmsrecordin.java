package com.java.activiti.model.wms;

import com.java.activiti.model.MyType;
import com.java.activiti.model.User;
import io.swagger.models.auth.In;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * ���
 */

public class Wmsrecordin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("inId")
        private Integer inId;

    @Field("inOdd")
    private String inOdd;//��ⵥ��

    @Field("inOrder")
    private String inOrder;//������

    @Field("supplierId")
    private Integer supplierId;//��Ӧ��

    @Field("supplier")
    private Wmssupplier supplier;//��Ӧ��

    @Field("goodId")
    private String goodId;//����

    @Field("good")
    private WmsGood good;//����

    @Field("whId")
    private Integer whId;//�ֿ�ID

    @Field("warehouse")
    private Warehouse warehouse;//�ֿ�

    @Field("userId")
    private String userId;//�û�

    @Field("user")
    private User user;//�û�

    @Field("inAmount")
    private Integer inAmount;//����

    @Field("inPrice")
    private double inPrice;//�۸�

    @Field("inAllPri")
    private double inAllPri;//�ܼ۸�

    @Field("inDate")
    private Date inDate;//����

    @Field("inStatus")
    private Integer inStatus;//������

    @Field("inSta")
    private MyType inSta;//������

    @Field("inRemark")
    private String inRemark;//��ע

    public Integer getInId() {
        return inId;
    }

    public void setInId(Integer inId) {
        this.inId = inId;
    }

    public String getInOdd() {
        return inOdd;
    }

    public void setInOdd(String inOdd) {
        this.inOdd = inOdd;
    }

    public String getInOrder() {
        return inOrder;
    }

    public void setInOrder(String inOrder) {
        this.inOrder = inOrder;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Wmssupplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Wmssupplier supplier) {
        this.supplier = supplier;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public WmsGood getGood() {
        return good;
    }

    public void setGood(WmsGood good) {
        this.good = good;
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

    public Integer getInAmount() {
        return inAmount;
    }

    public void setInAmount(Integer inAmount) {
        this.inAmount = inAmount;
    }

    public double getInPrice() {
        return inPrice;
    }

    public void setInPrice(double inPrice) {
        this.inPrice = inPrice;
    }

    public double getInAllPri() {
        return inAllPri;
    }

    public void setInAllPri(double inAllPri) {
        this.inAllPri = inAllPri;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Integer getInStatus() {
        return inStatus;
    }

    public void setInStatus(Integer inStatus) {
        this.inStatus = inStatus;
    }

    public MyType getInSta() {
        return inSta;
    }

    public void setInSta(MyType inSta) {
        this.inSta = inSta;
    }

    public String getInRemark() {
        return inRemark;
    }

    public void setInRemark(String inRemark) {
        this.inRemark = inRemark;
    }
}
