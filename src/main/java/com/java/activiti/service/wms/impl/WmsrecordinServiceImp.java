package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.wms.WmsrecordinDao;
import com.java.activiti.model.wms.Wmsrecordin;
import com.java.activiti.service.wms.WmsrecordinService;
import com.java.activiti.util.aop.Operation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("wmsrecordinService")
public class WmsrecordinServiceImp implements WmsrecordinService {

    @Resource
    private WmsrecordinDao wmsrecordinDao;

    @Override
    public List<Wmsrecordin> wmsrecordinPage(Map<String, Object> map) {
        return wmsrecordinDao.wmsrecordinPage(map);
    }

    @Override
    public int wmsrecordinCount(Map<String, Object> map) {
        return wmsrecordinDao.wmsrecordinCount(map);
    }


    /**
     * �������
     *
     * @param wmsrecordin
     * @return
     */
    @Override
    @Operation(value = "�������")
    public int addWmsrecordin(Wmsrecordin wmsrecordin) {
        return wmsrecordinDao.addWmsrecordin(wmsrecordin);
    }

    /**
     * �޸����
     *
     * @param wmsrecordin
     * @return
     */
    @Override
    @Operation(value = "���������Ϣ")
    public int updateWmsrecordin(Wmsrecordin wmsrecordin) {
        return wmsrecordinDao.updateWmsrecordin(wmsrecordin);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "����ɾ�����")
    public int deleteWmsrecordinById(List<String> id) {
        return wmsrecordinDao.deleteWmsrecordinById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param wmsrecordin
     * @return
     */
    @Override
    public List<Wmsrecordin> findWmsrecordin(Wmsrecordin wmsrecordin) {
        return wmsrecordinDao.findWmsrecordin(wmsrecordin);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {

    }

    /**
     * ���ݵ���
     */
    @Operation(value = "Excel���������Ϣ")
    @Override
    public void wmsrecordinImport(InputStream is) throws IOException {
    }

    @Override
    public Wmsrecordin selectWmsrecordinById(Integer supplierId) {
        return wmsrecordinDao.findById(supplierId);
    }

    @Override
    public List<Wmsrecordin> selectSupplierNameLike(String q) {
        return wmsrecordinDao.selectSupplierNameLike(q);
    }

}
