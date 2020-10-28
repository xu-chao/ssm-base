package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.wms.WmsrecordoutDao;
import com.java.activiti.model.wms.Wmsrecordout;
import com.java.activiti.service.wms.WmsrecordoutService;
import com.java.activiti.util.aop.Operation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("wmsrecordoutService")
public class WmsrecordoutServiceImpl implements WmsrecordoutService {

    @Resource
    private WmsrecordoutDao wmsrecordoutDao;

    @Override
    public List<Wmsrecordout> wmsrecordoutPage(Map<String, Object> map) {
        return wmsrecordoutDao.wmsrecordoutPage(map);
    }

    @Override
    public int wmsrecordoutCount(Map<String, Object> map) {
        return wmsrecordoutDao.wmsrecordoutCount(map);
    }


    /**
     * �������
     *
     * @param wmsrecordout
     * @return
     */
    @Override
    @Operation(value = "�������")
    public int addWmsrecordout(Wmsrecordout wmsrecordout) {
        return wmsrecordoutDao.addWmsrecordout(wmsrecordout);
    }

    /**
     * �޸����
     *
     * @param wmsrecordout
     * @return
     */
    @Override
    @Operation(value = "���������Ϣ")
    public int updateWmsrecordout(Wmsrecordout wmsrecordout) {
        return wmsrecordoutDao.updateWmsrecordout(wmsrecordout);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "����ɾ�����")
    public int deleteWmsrecordoutById(List<String> id) {
        return wmsrecordoutDao.deleteWmsrecordoutById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param wmsrecordout
     * @return
     */
    @Override
    public List<Wmsrecordout> foutdWmsrecordout(Wmsrecordout wmsrecordout) {
        return wmsrecordoutDao.findWmsrecordout(wmsrecordout);
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
    public void wmsrecordoutImport(InputStream is) throws IOException {
    }

    @Override
    public Wmsrecordout selectWmsrecordoutById(Integer supplierId) {
        return wmsrecordoutDao.findById(supplierId);
    }

    @Override
    public List<Wmsrecordout> selectProjectNameLike(String q) {
        return wmsrecordoutDao.selectProjectNameLike(q);
    }
}
