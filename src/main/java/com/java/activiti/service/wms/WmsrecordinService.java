package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Wmsrecordin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmsrecordinService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Wmsrecordin> wmsrecordinPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsrecordinCount(Map<String, Object> map);


    /**
     * �޸Ĺ�Ӧ��
     * @param wmsrecordin
     * @return
     */
    int updateWmsrecordin(Wmsrecordin wmsrecordin);

    /**
     * ������Ӧ��
     * @param wmsrecordin
     * @return
     */
    int addWmsrecordin(Wmsrecordin wmsrecordin);

    /**
     * ����ɾ����Ӧ��
     * @param id
     * @return
     */
    int deleteWmsrecordinById(List<String> id);

    /**
     * �жϹ�Ӧ���Ƿ��Ѿ�����
     * @param wmsrecordin
     * @return
     */
    List<Wmsrecordin> findWmsrecordin(Wmsrecordin wmsrecordin);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: LIUHD
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: wmsrecordinImport
     * @Description: ����excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void wmsrecordinImport(InputStream is) throws IOException;

    Wmsrecordin selectWmsrecordinById(Integer supplierId);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: like��ѯ ������
     * @return
     */
    List<Wmsrecordin> selectSupplierNameLike(String q);
}
