package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.CheckoutListDao;
import com.java.activiti.dao.erp.PurchaseListDao;
import com.java.activiti.model.erp.CheckoutList;
import com.java.activiti.model.erp.PurchaseList;
import com.java.activiti.service.erp.CheckoutListService;
import com.java.activiti.service.erp.PurchaseListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("checkoutListService")
public class CheckoutListServiceImpl implements CheckoutListService {
    @Resource
    private CheckoutListDao checkoutListDao;

    @Override
    public List<CheckoutList> checkoutListPage(Map<String, Object> map) {
        return checkoutListDao.checkoutListPage(map);
    }

    @Override
    public int checkoutListCount(Map<String, Object> map) {
        return checkoutListDao.checkoutListCount(map);
    }

}
