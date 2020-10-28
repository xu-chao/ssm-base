package com.java.activiti.model.erp;

import java.io.Serializable;
import java.util.Date;

public class RCheckoutList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String RCheckoutListId;//·µÐÞId

    private RCheckout rCheckout;

    public String getRCheckoutListId() {
        return RCheckoutListId;
    }

    public void setRCheckoutListId(String RCheckoutListId) {
        this.RCheckoutListId = RCheckoutListId;
    }

    public RCheckout getrCheckout() {
        return rCheckout;
    }

    public void setrCheckout(RCheckout rCheckout) {
        this.rCheckout = rCheckout;
    }

    @Override
    public String toString() {
        return "RCheckoutList{" +
                "RCheckoutListId='" + RCheckoutListId + '\'' +
                ", rCheckout=" + rCheckout +
                '}';
    }
}
