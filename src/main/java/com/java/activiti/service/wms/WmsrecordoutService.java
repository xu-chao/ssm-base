package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Wmsrecordout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmsrecordoutService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Wmsrecordout> wmsrecordoutPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsrecordoutCount(Map<String, Object> map);


    /**
     * �޸Ĺ�Ӧ��
     * @param wmsrecordout
     * @return
     */
    int updateWmsrecordout(Wmsrecordout wmsrecordout);

    /**
     * ������Ӧ��
     * @param wmsrecordout
     * @return
     */
    int addWmsrecordout(Wmsrecordout wmsrecordout);

    /**
     * ����ɾ����Ӧ��
     * @param id
     * @return
     */
    int deleteWmsrecordoutById(List<String> id);

    /**
     * �жϹ�Ӧ���Ƿ��Ѿ�����
     * @param wmsrecordout
     * @return
     */
    List<Wmsrecordout> foutdWmsrecordout(Wmsrecordout wmsrecordout);

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
     * @Title: wmsrecordoutImport
     * @Description: ����excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void wmsrecordoutImport(InputStream is) throws IOException;

    Wmsrecordout selectWmsrecordoutById(Integer supplierId);

    /**
     *
     * @Title: selectProjectNameLike
     * @Description: like��ѯ ������
     * @return
     */
    List<Wmsrecordout> selectProjectNameLike(String q);

}
