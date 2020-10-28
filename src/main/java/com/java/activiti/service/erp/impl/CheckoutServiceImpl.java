package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.CheckoutDao;
import com.java.activiti.dao.erp.PurchaseDao;
import com.java.activiti.model.erp.Checkout;
import com.java.activiti.model.erp.Purchase;
import com.java.activiti.service.erp.CheckoutService;
import com.java.activiti.service.erp.PurchaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("checkoutService")
public class CheckoutServiceImpl implements CheckoutService {
    @Resource
    private CheckoutDao checkoutDao;

    @Override
    public List<Checkout> checkoutPage(Map<String, Object> map) {
        return checkoutDao.checkoutPage(map);
    }

    @Override
    public int checkoutCount(Map<String, Object> map) {
        return checkoutDao.checkoutCount(map);
    }

    @Override
    public int addCheckout(Checkout checkout) {
        return checkoutDao.addCheckout(checkout);
    }

    @Override
    public Checkout findById(String id) {
        return checkoutDao.findById(id);
    }

    @Override
    public int updateCheckout(Checkout checkout) {
        return checkoutDao.updateCheckout(checkout);
    }

    @Override
    public void export(OutputStream os, Map<String, Object> map) {
    }
}
