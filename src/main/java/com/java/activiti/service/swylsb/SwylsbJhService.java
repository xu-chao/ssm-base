package com.java.activiti.service.swylsb;

import com.java.activiti.model.swylsb.SwylsbJh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SwylsbJhService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<SwylsbJh> swylsbJhPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int swylsbJhCount(Map<String, Object> map);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like��ѯ������
     * @return
     */
    List<SwylsbJh> findSwylsbJhName(String q);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like��ѯ������
     * @return
     */
    List<SwylsbJh> findSwylsbJhName();

    /**
     * �޸�����
     * @param swylsbJh
     * @return
     */
    int updateSwylsbJh(List<SwylsbJh> swylsbJh);

    /**
     * ��������
     * @param swylsbJh
     * @return
     */
    int addSwylsbJh(SwylsbJh swylsbJh);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteSwylsbJhById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param swylsbJh
     * @return
     */
    List<SwylsbJh> findSwylsbJh(SwylsbJh swylsbJh);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: ��
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: goodsImport
     * @Description: ����excel
     * @author: ��
     * @param is
     * @throws IOException
     */
    void swylsbJhImport(InputStream is) throws IOException;

    List<SwylsbJh> selectSwylsbJhById(int ID);

    /**
     * ���� 
     * @author xuchao 
     * @description ��ѯ���й�Ӧ��
     * @date 2019/12/29 22:03 
     * @Version     1.0
     */
    List<SwylsbJh> findAllSwylsbJh();
}
