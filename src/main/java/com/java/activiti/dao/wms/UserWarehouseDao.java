package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.Warehouse;
import com.java.activiti.pojo.wms.UserWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserWarehouseDao {

    int addWarehouseAllocation(UserWarehouse userWarehouse);

    /**
     * 用户登入sessionDept的方法
     * @return
     */
    UserWarehouse sessionWarehouse(Map<String, Object> map);

    /**
     * 获取指定 userID 对应用户拥有的公园
     * @param userID 用户ID
     * @return 返回 userID 指定用户的公园
     */
    List<Warehouse> selectUserWarehouse(String userID);

    List<UserWarehouse> findAllUser(Map<String, Object> map);
}
