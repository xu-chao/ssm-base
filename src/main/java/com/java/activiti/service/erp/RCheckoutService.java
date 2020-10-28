package com.java.activiti.service.erp;

import com.java.activiti.model.erp.RCheckout;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface RCheckoutService {

    List<RCheckout> rCheckoutPage(Map<String, Object> map);

    int rCheckoutCount(Map<String, Object> map);

    int addRCheckout(RCheckout rCheckout);

    RCheckout findById(String id);

    int updateRCheckout(RCheckout rCheckout);

    void export(OutputStream os, Map<String, Object> map);
}
