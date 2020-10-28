package com.java.activiti.model.erp;

import java.io.Serializable;

public class CheckoutList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String checkoutListId;//ºÏ—Èµ•∫≈Id

    private Checkout checkout;

    public String getCheckoutListId() {
        return checkoutListId;
    }

    public void setCheckoutListId(String checkoutListId) {
        this.checkoutListId = checkoutListId;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

    @Override
    public String toString() {
        return "CheckoutList{" +
                "checkoutListId='" + checkoutListId + '\'' +
                ", checkout=" + checkout +
                '}';
    }
}
