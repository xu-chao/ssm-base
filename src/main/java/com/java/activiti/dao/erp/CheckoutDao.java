package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.Checkout;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CheckoutDao {

    List<Checkout> checkoutPage(Map<String, Object> map);

    int checkoutCount(Map<String, Object> map);

    int addCheckout(Checkout checkout);

    Checkout findById(String id);

    int updateCheckout(Checkout checkout);
}
