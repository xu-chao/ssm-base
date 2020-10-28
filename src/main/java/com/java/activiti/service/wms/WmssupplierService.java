package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Wmssupplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmssupplierService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Wmssupplier> wmssupplierPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmssupplierCount(Map<String, Object> map);


    /**
     * 修改供应商
     * @param wmssupplier
     * @return
     */
    int updateWmssupplier(Wmssupplier wmssupplier);

    /**
     * 新增供应商
     * @param wmssupplier
     * @return
     */
    int addWmssupplier(Wmssupplier wmssupplier);

    /**
     * 批量删除供应商
     * @param id
     * @return
     */
    int deleteWmssupplierById(List<String> id);

    /**
     * 判断供应商是否已经存在
     * @param wmssupplier
     * @return
     */
    List<Wmssupplier> findWmssupplier(Wmssupplier wmssupplier);

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
     * @Title: wmssupplierImport
     * @Description: 导入excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void wmssupplierImport(InputStream is) throws IOException;

    Wmssupplier selectWmssupplierById(Integer supplierId);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: like查询 物料名
     * @return
     */
    List<Wmssupplier> selectSupplierNameLike(String q);

    List<Wmssupplier> findALL();
}
