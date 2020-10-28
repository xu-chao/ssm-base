package com.java.activiti.service.wms;

import com.java.activiti.exception.WmsIncomingManageServiceException;
import com.java.activiti.model.wms.WmsIncoming;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmsIncomingService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<WmsIncoming> wmsIncomingPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsIncomingCount(Map<String, Object> map);


    /**
     * 修改供应商
     * @param wmsIncoming
     * @return
     */
    int updateWmsIncoming(WmsIncoming wmsIncoming);

    /**
     * 新增供应商
     * @param wmsIncoming
     * @return
     */
    int addWmsIncoming(WmsIncoming wmsIncoming);

    /**
     * 批量删除供应商
     * @param id
     * @return
     */
    int deleteWmsIncomingById(List<String> id);

    /**
     * 判断供应商是否已经存在
     * @param wmsIncoming
     * @return
     */
    List<WmsIncoming> findWmsIncoming(WmsIncoming wmsIncoming);

    /**
     *
     * @Title: export
     * @Description: 导出excel
     * @author: LIUHD
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    WmsIncoming selectWmsIncomingById(Integer supplierId);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: like查询 物料名
     * @return
     */
    List<WmsIncoming> selectSupplierNameLike(String q);

    public Map<String, Object> wmsIncomingImport(MultipartFile file) throws WmsIncomingManageServiceException, WmsIncomingManageServiceException;

    }
