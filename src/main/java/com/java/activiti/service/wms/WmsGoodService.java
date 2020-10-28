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
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<WmsGood> wmsGoodPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsGoodCount(Map<String, Object> map);


    /**
     * �޸�����
     * @param wmsGood
     * @return
     */
    int updateWmsGood(WmsGood wmsGood);

    /**
     * ��������
     * @param wmsGood
     * @return
     */
    int addWmsGood(WmsGood wmsGood);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteWmsGoodById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param wmsGood
     * @return
     */
    List<WmsGood> findWmsGood(WmsGood wmsGood);

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
     * @Title: wmsGoodImport
     * @Description: ����excel
     * @author: LIUHD
     * @param is
     * @throws IOException
     */
    void wmsGoodImport(InputStream is) throws IOException;

    WmsGood selectWmsGoodById(String ECodeID);


    /**
     *
     * @Title: findGoodsNameLike
     * @Description: like��ѯ ������
     * @return
     */
    List<WmsGood> findGoodEncodingLike(String q);
    /**
     *
     * @Title: findGoodsCodeLike
     * @Description: like��ѯ �ͺ�/ͼ��
     * @return
     */
    List<WmsGood> findGoodNameLike(String q);
    /**
     *
     * @Title: findGoodsTypeLike
     * @Description: like��ѯ ����
     * @return
     */
    List<WmsGood> findGoodModelLike(String q);

    public List<WmsGood> findALL();
}
