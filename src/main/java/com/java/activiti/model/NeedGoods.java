package com.java.activiti.model;

import com.java.activiti.model.erp.Checkout;
import com.java.activiti.model.erp.Purchase;

import java.io.Serializable;

public class NeedGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nId;

    private String goodsId;

    private String storageId;

    private String purchaseId;

    private String checkoutId;

    private Need need;

    private Goods goods;

    private Storage storage;

    private Purchase purchase;

    private Checkout checkout;

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public Need getNeed() {
        return need;
    }

    public void setNeed(Need need) {
        this.need = need;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

    @Override
    public String toString() {
        return "NeedGoods{" +
                "nId='" + nId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", storageId='" + storageId + '\'' +
                ", purchaseId='" + purchaseId + '\'' +
                ", checkoutId='" + checkoutId + '\'' +
                ", need=" + need +
                ", goods=" + goods +
                ", storage=" + storage +
                ", purchase=" + purchase +
                ", checkout=" + checkout +
                '}';
    }
}
