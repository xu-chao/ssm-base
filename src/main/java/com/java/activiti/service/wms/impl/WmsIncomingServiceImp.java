package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.wms.WarehouseDao;
import com.java.activiti.dao.wms.WmsGoodDao;
import com.java.activiti.dao.wms.WmsIncomingDao;
import com.java.activiti.dao.wms.WmsIncomingDao;
import com.java.activiti.exception.WmsIncomingManageServiceException;
import com.java.activiti.model.Goods;
import com.java.activiti.model.wms.Warehouse;
import com.java.activiti.model.wms.WmsGood;
import com.java.activiti.model.wms.WmsIncoming;
import com.java.activiti.service.wms.WmsIncomingService;
import com.java.activiti.service.wms.WmsIncomingService;
import com.java.activiti.service.wms.WmsrecordstorageService;
import com.java.activiti.util.ExcelUtil;
import com.java.activiti.util.aop.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wmsIncomingService")
public class WmsIncomingServiceImp implements WmsIncomingService {

    @Resource
    private WmsIncomingDao wmsIncomingDao;

    @Resource
    private WmsGoodDao wmsGoodDao;

    @Resource
    private WarehouseDao warehouseDao;
    @Resource
    private WmsrecordstorageService wmsrecordstorageService;
    @Autowired
    private ExcelUtil excelUtil;

    @Override
    public List<WmsIncoming> wmsIncomingPage(Map<String, Object> map) {
        return wmsIncomingDao.WmsIncomingPage(map);
    }

    @Override
    public int wmsIncomingCount(Map<String, Object> map) {
        return wmsIncomingDao.WmsIncomingCount(map);
    }


    /**
     * 新增入库
     *
     * @param wmsIncoming
     * @return
     */
    @Override
    @Operation(value = "新增入库")
    public int addWmsIncoming(WmsIncoming wmsIncoming) {
        return wmsIncomingDao.insert(wmsIncoming);
    }

    /**
     * 修改入库
     *
     * @param wmsIncoming
     * @return
     */
    @Override
    @Operation(value = "更新入库信息")
    public int updateWmsIncoming(WmsIncoming wmsIncoming) {
        return wmsIncomingDao.updateWmsIncoming(wmsIncoming);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "批量删除入库")
    public int deleteWmsIncomingById(List<String> id) {
        return wmsIncomingDao.deleteWmsIncomingById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param wmsIncoming
     * @return
     */
    @Override
    public List<WmsIncoming> findWmsIncoming(WmsIncoming wmsIncoming) {
        return wmsIncomingDao.findWmsIncoming(wmsIncoming);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {

    }
    /**
     * 数据导入
     *
     * @param file 导入信息的文件
     * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
     */
    @Operation(value = "Excel导入入库信息")
    @Override
    public Map<String, Object> wmsIncomingImport(MultipartFile file) throws WmsIncomingManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        int total = 0;
        int available = 0;
        int index = 0;//add
        WmsGood   wmsGood = new WmsGood();
        Warehouse warehouse = new Warehouse();
        WmsIncoming wmsIncoming1 = new WmsIncoming();
                // 从 Excel 文件中读取
            List<Object> goodsList = excelUtil.excelReader(WmsIncoming.class, file);
        if (goodsList != null) {
            total = goodsList.size();

            // 验证每一条记录

            WmsIncoming wmsIncoming;
            List<WmsIncoming> availableList = new ArrayList<>();
            for (Object object : goodsList) {
                index++;//add
                wmsIncoming = (WmsIncoming) object;
                if (wmsIncomingCheck(wmsIncoming)) {
                    //查询物料ID 通过物料编码 查询 仓库ID
                    try {
                      wmsGood =  wmsGoodDao.findById(wmsIncoming.getDocumentsId());
                        warehouse =  warehouseDao.findWarehouseByWhName(wmsIncoming.getWarehouseName());
                    }catch (Exception e){
                        index =1;
                        break;
                    }

//                  判断物料和仓库是否存在
                    if (wmsGood==null){
                        index =1;
                       break;
                   }else{
                       wmsIncoming.setInventoryNo(wmsGood.getGoodEncoding());
                   }
                    if (warehouse==null){
                        index =1;
                       break;
                   }else{
                       wmsIncoming.setWarehouseId(warehouse.getWhId());
                   }

                   try {
                       int resultIn = wmsIncomingDao.insert(wmsIncoming);
                        wmsrecordstorageService.storageIncrease(wmsIncoming.getInventoryNo(),wmsIncoming.getWarehouseId(),
                                wmsIncoming.getInNum());

                   }catch (Exception e){
                       index =1;
                   }

                    availableList.add(wmsIncoming);
                }else{//add
                    break;
                }

            }
            // 保存到数据库
                available = availableList.size();
        }
        resultSet.put("false", index);//  -1失败
        resultSet.put("total", total);
        resultSet.put("available", available);

        return resultSet;
    }
    @Override
    public WmsIncoming selectWmsIncomingById(Integer supplierId) {
        return wmsIncomingDao.findById(supplierId);
    }

    @Override
    public List<WmsIncoming> selectSupplierNameLike(String q) {
        return wmsIncomingDao.selectSupplierNameLike(q);
    }
    /**
     * 检查入库信息是否满足要求
     *
     * @param wmsIncoming 入库信息
     * @return 若货物信息满足要求则返回true，否则返回false
     */
    private boolean wmsIncomingCheck(WmsIncoming wmsIncoming) {
        if (wmsIncoming != null) {
            if (wmsIncoming.getWarehouseId() != null && wmsIncoming.getDocumentsId() != null) {
                return true;
            }
        }
        return false;
    }
}
