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
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int warehouseCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
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
