package com.java.activiti.service.wms;

import com.java.activiti.model.wms.WmsGood;
import com.java.activiti.pojo.Tree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmsGoodService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<WmsGood> wmsGoodPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsGoodCount(Map<String, Object> map);


    /**
     * 修改物料
     * @param wmsGood
     * @return
     */
    int updateWmsGood(WmsGood wmsGood);

    /**
     * 新增物料
     * @param wmsGood
     * @return
     */
    int addWmsGood(WmsGood wmsGood);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteWmsGoodById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param wmsGood
     * @return
     */
    List<WmsGood> findWmsGood(WmsGood wmsGood);

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
     * @Title: wmsGoodImport
     * @Description: 导入excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void wmsGoodImport(InputStream is) throws IOException;

    WmsGood selectWmsGoodById(String ECodeID);


    /**
     *
     * @Title: findGoodsNameLike
     * @Description: like查询 物料名
     * @return
     */
    List<WmsGood> findGoodEncodingLike(String q);
    /**
     *
     * @Title: findGoodsCodeLike
     * @Description: like查询 型号/图号
     * @return
     */
    List<WmsGood> findGoodNameLike(String q);
    /**
     *
     * @Title: findGoodsTypeLike
     * @Description: like查询 类型
     * @return
     */
    List<WmsGood> findGoodModelLike(String q);

    public List<WmsGood> findALL();
}
