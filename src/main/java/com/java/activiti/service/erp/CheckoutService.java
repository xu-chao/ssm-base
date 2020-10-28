package com.java.activiti.service.erp;

import com.java.activiti.model.erp.Checkout;
import com.java.activiti.model.erp.Purchase;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface CheckoutService {

    List<Checkout> checkoutPage(Map<String, Object> map);

    int checkoutCount(Map<String, Object> map);

    int addCheckout(Checkout checkout);

    Checkout findById(String id);

    int updateCheckout(Checkout checkout);

    void export(OutputStream os, Map<String, Object> map);
}
