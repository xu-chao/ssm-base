package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.PurchaseList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PurchaseListDao {

    List<PurchaseList> purchaseListPage(Map<String, Object> map);

    int purchaseListCount(Map<String, Object> map);

    int purchaseMountSum(Map<String, Object> map);

}
