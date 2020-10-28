package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.RCheckout;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RCheckoutDao {

    List<RCheckout> rCheckoutPage(Map<String, Object> map);

    int rCheckoutCount(Map<String, Object> map);

    int addRCheckout(RCheckout rCheckout);

    RCheckout findById(String id);

    int updateRCheckout(RCheckout rCheckout);
}
