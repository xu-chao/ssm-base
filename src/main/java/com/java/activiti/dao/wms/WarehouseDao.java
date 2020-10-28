package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.Warehouse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WarehouseDao {

    Warehouse findById(Integer whId);

    Warehouse findWarehouseByWhName(String whName);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int warehouseCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Warehouse> warehousePage(Map<String, Object> map);

    int addWarehouse(Warehouse warehouse);

    int updateWarehouse(Warehouse warehouse);

    int deleteWarehouseById(List<String> whId);

    List<Warehouse> findWarehouse(Warehouse warehouse);

    Warehouse findWarehouseByExcel(Warehouse warehouse);

    List<Warehouse> selectALL();

    List<Warehouse> findWarehouseName(@Param("whName") String whName, @Param("whType") String whType);

    List<Warehouse> findWarehouseNameByWhType(@Param("whType") String whType);
}
