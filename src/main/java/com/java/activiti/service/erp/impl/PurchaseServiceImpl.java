package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.PurchaseDao;
import com.java.activiti.model.erp.Purchase;
import com.java.activiti.service.erp.PurchaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {
    @Resource
    private PurchaseDao purchaseDao;

    @Override
    public List<Purchase> purchasePage(Map<String, Object> map) {
        return purchaseDao.purchasePage(map);
    }

    @Override
    public int purchaseCount(Map<String, Object> map) {
        return purchaseDao.purchaseCount(map);
    }

    @Override
    public int addPurchase(Purchase purchase) {
        return purchaseDao.addPurchase(purchase);
    }

    @Override
    public Purchase findById(String id) {
        return purchaseDao.findById(id);
    }

    @Override
    public int updatePurchase(Purchase purchase) {
        return purchaseDao.updatePurchase(purchase);
    }

    @Override
    public void export(OutputStream os, Map<String, Object> map) {
    }
}
