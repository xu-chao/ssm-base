package com.java.activiti.service.erp;

import com.java.activiti.model.erp.CheckoutList;

import java.util.List;
import java.util.Map;

public interface CheckoutListService {

    List<CheckoutList> checkoutListPage(Map<String, Object> map);

    int checkoutListCount(Map<String, Object> map);

}
