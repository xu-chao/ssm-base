package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.PurchaseListDao;
import com.java.activiti.dao.erp.RCheckoutListDao;
import com.java.activiti.model.erp.PurchaseList;
import com.java.activiti.model.erp.RCheckoutList;
import com.java.activiti.service.erp.PurchaseListService;
import com.java.activiti.service.erp.RCheckoutListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("purchaseListService")
public class PurchaseListServiceImpl implements PurchaseListService {
    @Resource
    private PurchaseListDao purchaseListDao;

    @Override
    public List<PurchaseList> purchaseListPage(Map<String, Object> map) {
        return purchaseListDao.purchaseListPage(map);
    }

    @Override
    public int purchaseListCount(Map<String, Object> map) {
        return purchaseListDao.purchaseListCount(map);
    }

    @Override
    public int purchaseMountSum(Map<String, Object> map) {
        return purchaseListDao.purchaseMountSum(map);
    }

}
