package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Wmsrecordin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmsrecordinService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Wmsrecordin> wmsrecordinPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsrecordinCount(Map<String, Object> map);


    /**
     * 修改供应商
     * @param wmsrecordin
     * @return
     */
    int updateWmsrecordin(Wmsrecordin wmsrecordin);

    /**
     * 新增供应商
     * @param wmsrecordin
     * @return
     */
    int addWmsrecordin(Wmsrecordin wmsrecordin);

    /**
     * 批量删除供应商
     * @param id
     * @return
     */
    int deleteWmsrecordinById(List<String> id);

    /**
     * 判断供应商是否已经存在
     * @param wmsrecordin
     * @return
     */
    List<Wmsrecordin> findWmsrecordin(Wmsrecordin wmsrecordin);

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
     * @Title: wmsrecordinImport
     * @Description: 导入excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void wmsrecordinImport(InputStream is) throws IOException;

    Wmsrecordin selectWmsrecordinById(Integer supplierId);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: like查询 物料名
     * @return
     */
    List<Wmsrecordin> selectSupplierNameLike(String q);
}
