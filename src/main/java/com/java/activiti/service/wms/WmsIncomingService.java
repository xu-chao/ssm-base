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
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<WmsIncoming> wmsIncomingPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsIncomingCount(Map<String, Object> map);


    /**
     * �޸Ĺ�Ӧ��
     * @param wmsIncoming
     * @return
     */
    int updateWmsIncoming(WmsIncoming wmsIncoming);

    /**
     * ������Ӧ��
     * @param wmsIncoming
     * @return
     */
    int addWmsIncoming(WmsIncoming wmsIncoming);

    /**
     * ����ɾ����Ӧ��
     * @param id
     * @return
     */
    int deleteWmsIncomingById(List<String> id);

    /**
     * �жϹ�Ӧ���Ƿ��Ѿ�����
     * @param wmsIncoming
     * @return
     */
    List<WmsIncoming> findWmsIncoming(WmsIncoming wmsIncoming);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: LIUHD
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    WmsIncoming selectWmsIncomingById(Integer supplierId);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: like��ѯ ������
     * @return
     */
    List<WmsIncoming> selectSupplierNameLike(String q);

    public Map<String, Object> wmsIncomingImport(MultipartFile file) throws WmsIncomingManageServiceException, WmsIncomingManageServiceException;

    }
