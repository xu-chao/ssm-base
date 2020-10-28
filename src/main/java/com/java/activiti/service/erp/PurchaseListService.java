package com.java.activiti.service.erp;

import com.java.activiti.model.erp.PurchaseList;

import java.util.List;
import java.util.Map;

public interface PurchaseListService {

    List<PurchaseList> purchaseListPage(Map<String, Object> map);

    int purchaseListCount(Map<String, Object> map);

    int purchaseMountSum(Map<String, Object> map);

}
