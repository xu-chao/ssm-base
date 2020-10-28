package com.java.activiti.service.erp;

import com.java.activiti.model.erp.RCheckoutList;

import java.util.List;
import java.util.Map;

public interface RCheckoutListService {

    List<RCheckoutList> rCheckoutListPage(Map<String, Object> map);

    int rCheckoutListCount(Map<String, Object> map);

}
