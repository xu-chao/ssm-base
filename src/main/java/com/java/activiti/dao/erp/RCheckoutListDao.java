package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.RCheckoutList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RCheckoutListDao {

    List<RCheckoutList> rCheckoutListPage(Map<String, Object> map);

    int rCheckoutListCount(Map<String, Object> map);

}
