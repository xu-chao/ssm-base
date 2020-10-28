package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Wmsrecordout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmsrecordoutService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Wmsrecordout> wmsrecordoutPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsrecordoutCount(Map<String, Object> map);


    /**
     * 修改供应商
     * @param wmsrecordout
     * @return
     */
    int updateWmsrecordout(Wmsrecordout wmsrecordout);

    /**
     * 新增供应商
     * @param wmsrecordout
     * @return
     */
    int addWmsrecordout(Wmsrecordout wmsrecordout);

    /**
     * 批量删除供应商
     * @param id
     * @return
     */
    int deleteWmsrecordoutById(List<String> id);

    /**
     * 判断供应商是否已经存在
     * @param wmsrecordout
     * @return
     */
    List<Wmsrecordout> foutdWmsrecordout(Wmsrecordout wmsrecordout);

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
     * @Title: wmsrecordoutImport
     * @Description: 导入excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void wmsrecordoutImport(InputStream is) throws IOException;

    Wmsrecordout selectWmsrecordoutById(Integer supplierId);

    /**
     *
     * @Title: selectProjectNameLike
     * @Description: like查询 物料名
     * @return
     */
    List<Wmsrecordout> selectProjectNameLike(String q);

}
