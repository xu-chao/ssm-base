package com.java.activiti.service.erp;

import com.java.activiti.model.erp.Purchase;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface PurchaseService {

    List<Purchase> purchasePage(Map<String, Object> map);

    int purchaseCount(Map<String, Object> map);

    int addPurchase(Purchase purchase);

    Purchase findById(String id);

    int updatePurchase(Purchase purchase);

    void export(OutputStream os, Map<String, Object> map);
}
