package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Warehouse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WarehouseService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Warehouse> warehousePage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int warehouseCount(Map<String, Object> map);


    /**
     * �޸�����
     * @param warehouse
     * @return
     */
    int updateWarehouse(Warehouse warehouse);

    /**
     * ��������
     * @param warehouse
     * @return
     */
    int addWarehouse(Warehouse warehouse);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteWarehouseById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param warehouse
     * @return
     */
    List<Warehouse> findWarehouse(Warehouse warehouse);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: LIUHD
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: warehouseImport
     * @Description: ����excel
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
