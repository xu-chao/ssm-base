package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Warehouse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WarehouseService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Warehouse> warehousePage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int warehouseCount(Map<String, Object> map);


    /**
     * 修改物料
     * @param warehouse
     * @return
     */
    int updateWarehouse(Warehouse warehouse);

    /**
     * 新增物料
     * @param warehouse
     * @return
     */
    int addWarehouse(Warehouse warehouse);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteWarehouseById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param warehouse
     * @return
     */
    List<Warehouse> findWarehouse(Warehouse warehouse);

    /**
     *
     * @Title: export
     * @Description: 导出excel
     * @author: LIUHD
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: warehouseImport
     * @Description: 导入excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void warehouseImport(InputStream is) throws IOException;

    Warehouse selectWarehouseById(Integer ECodeID);

    List<Warehouse> findALL();

    List<Warehouse> findWarehouseName(String q,String whType);

    List<Warehouse> findWarehouseNameByWhType(String whType);
}
