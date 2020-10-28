package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Warehouse;
import com.java.activiti.pojo.wms.UserWarehouse;

import java.util.List;
import java.util.Map;


public interface UserWarehouseService {

    /**
     * 增加部门用户
     * @param userWarehouse
     * @return
     */
    int addWarehouseAllocation(UserWarehouse userWarehouse);

    UserWarehouse sessionWarehouse(Map<String, Object> map);

    List<UserWarehouse> findAllUser(Map<String, Object> map);

    List<Warehouse> findWarehouseByUser(String userId);
}
