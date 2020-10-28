package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.CheckoutDao;
import com.java.activiti.dao.erp.RCheckoutDao;
import com.java.activiti.model.erp.Checkout;
import com.java.activiti.model.erp.RCheckout;
import com.java.activiti.service.erp.CheckoutService;
import com.java.activiti.service.erp.RCheckoutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("rCheckoutService")
public class RCheckoutServiceImpl implements RCheckoutService {
    @Resource
    private RCheckoutDao rCheckoutDao;

    @Override
    public List<RCheckout> rCheckoutPage(Map<String, Object> map) {
        return rCheckoutDao.rCheckoutPage(map);
    }

    @Override
    public int rCheckoutCount(Map<String, Object> map) {
        return rCheckoutDao.rCheckoutCount(map);
    }

    @Override
    public int addRCheckout(RCheckout rCheckout) {
        return rCheckoutDao.addRCheckout(rCheckout);
    }

    @Override
    public RCheckout findById(String id) {
        return rCheckoutDao.findById(id);
    }

    @Override
    public int updateRCheckout(RCheckout rCheckout) {
        return rCheckoutDao.updateRCheckout(rCheckout);
    }

    @Override
    public void export(OutputStream os, Map<String, Object> map) {
    }
}
