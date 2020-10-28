package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Wmssupplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmssupplierService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Wmssupplier> wmssupplierPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmssupplierCount(Map<String, Object> map);


    /**
     * �޸Ĺ�Ӧ��
     * @param wmssupplier
     * @return
     */
    int updateWmssupplier(Wmssupplier wmssupplier);

    /**
     * ������Ӧ��
     * @param wmssupplier
     * @return
     */
    int addWmssupplier(Wmssupplier wmssupplier);

    /**
     * ����ɾ����Ӧ��
     * @param id
     * @return
     */
    int deleteWmssupplierById(List<String> id);

    /**
     * �жϹ�Ӧ���Ƿ��Ѿ�����
     * @param wmssupplier
     * @return
     */
    List<Wmssupplier> findWmssupplier(Wmssupplier wmssupplier);

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
     * @Title: wmssupplierImport
     * @Description: ����excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void wmssupplierImport(InputStream is) throws IOException;

    Wmssupplier selectWmssupplierById(Integer supplierId);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: like��ѯ ������
     * @return
     */
    List<Wmssupplier> selectSupplierNameLike(String q);

    List<Wmssupplier> findALL();
}
