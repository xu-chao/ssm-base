package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.CheckoutList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CheckoutListDao {

    List<CheckoutList> checkoutListPage(Map<String, Object> map);

    int checkoutListCount(Map<String, Object> map);

}
