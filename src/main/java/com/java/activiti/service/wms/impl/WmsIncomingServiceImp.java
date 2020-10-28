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
     * �������
     *
     * @param wmsIncoming
     * @return
     */
    @Override
    @Operation(value = "�������")
    public int addWmsIncoming(WmsIncoming wmsIncoming) {
        return wmsIncomingDao.insert(wmsIncoming);
    }

    /**
     * �޸����
     *
     * @param wmsIncoming
     * @return
     */
    @Override
    @Operation(value = "���������Ϣ")
    public int updateWmsIncoming(WmsIncoming wmsIncoming) {
        return wmsIncomingDao.updateWmsIncoming(wmsIncoming);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "����ɾ�����")
    public int deleteWmsIncomingById(List<String> id) {
        return wmsIncomingDao.deleteWmsIncomingById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param wmsIncoming
     * @return
     */
    @Override
    public List<WmsIncoming> findWmsIncoming(WmsIncoming wmsIncoming) {
        return wmsIncomingDao.findWmsIncoming(wmsIncoming);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {

    }
    /**
     * ���ݵ���
     *
     * @param file ������Ϣ���ļ�
     * @return ����һ��Map�����У�keyΪtotal��������ܼ�¼����keyΪavailable������Ч����ļ�¼��
     */
    @Operation(value = "Excel���������Ϣ")
    @Override
    public Map<String, Object> wmsIncomingImport(MultipartFile file) throws WmsIncomingManageServiceException {
        // ��ʼ�������
        Map<String, Object> resultSet = new HashMap<>();
        int total = 0;
        int available = 0;
        int index = 0;//add
        WmsGood   wmsGood = new WmsGood();
        Warehouse warehouse = new Warehouse();
        WmsIncoming wmsIncoming1 = new WmsIncoming();
                // �� Excel �ļ��ж�ȡ
            List<Object> goodsList = excelUtil.excelReader(WmsIncoming.class, file);
        if (goodsList != null) {
            total = goodsList.size();

            // ��֤ÿһ����¼

            WmsIncoming wmsIncoming;
            List<WmsIncoming> availableList = new ArrayList<>();
            for (Object object : goodsList) {
                index++;//add
                wmsIncoming = (WmsIncoming) object;
                if (wmsIncomingCheck(wmsIncoming)) {
                    //��ѯ����ID ͨ�����ϱ��� ��ѯ �ֿ�ID
                    try {
                      wmsGood =  wmsGoodDao.findById(wmsIncoming.getDocumentsId());
                        warehouse =  warehouseDao.findWarehouseByWhName(wmsIncoming.getWarehouseName());
                    }catch (Exception e){
                        index =1;
                        break;
                    }

//                  �ж����ϺͲֿ��Ƿ����
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
            // ���浽���ݿ�
                available = availableList.size();
        }
        resultSet.put("false", index);//  -1ʧ��
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
     * ��������Ϣ�Ƿ�����Ҫ��
     *
     * @param wmsIncoming �����Ϣ
     * @return ��������Ϣ����Ҫ���򷵻�true�����򷵻�false
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
