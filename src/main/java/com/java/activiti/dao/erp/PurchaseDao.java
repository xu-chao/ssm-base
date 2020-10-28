package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.Purchase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PurchaseDao {

    List<Purchase> purchasePage(Map<String, Object> map);

    int purchaseCount(Map<String, Object> map);

    int addPurchase(Purchase purchase);

    Purchase findById(String id);

    int updatePurchase(Purchase purchase);
}
