package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.Warehouse;
import com.java.activiti.pojo.wms.UserWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserWarehouseDao {

    int addWarehouseAllocation(UserWarehouse userWarehouse);

    /**
     * �û�����sessionDept�ķ���
     * @return
     */
    UserWarehouse sessionWarehouse(Map<String, Object> map);

    /**
     * ��ȡָ�� userID ��Ӧ�û�ӵ�еĹ�԰
     * @param userID �û�ID
     * @return ���� userID ָ���û��Ĺ�԰
     */
    List<Warehouse> selectUserWarehouse(String userID);

    List<UserWarehouse> findAllUser(Map<String, Object> map);
}
