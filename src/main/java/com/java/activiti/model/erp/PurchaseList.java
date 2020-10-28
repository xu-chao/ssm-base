package com.java.activiti.model.erp;

import java.io.Serializable;

public class PurchaseList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String purchaseListId;//收货流水号记录Id

    private Purchase purchase;

    public String getPurchaseListId() {
        return purchaseListId;
    }

    public void setPurchaseListId(String purchaseListId) {
        this.purchaseListId = purchaseListId;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "PurchaseList{" +
                "purchaseListId='" + purchaseListId + '\'' +
                ", purchase=" + purchase +
                '}';
    }
}
