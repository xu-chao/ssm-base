package com.java.activiti.service.swylsb;

import com.java.activiti.model.swylsb.SwylsbXq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SwylsbXqService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<SwylsbXq> swylsbXqPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int swylsbXqCount(Map<String, Object> map);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like��ѯ������
     * @return
     */
    List<SwylsbXq> findSwylsbXqName(String q);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like��ѯ������
     * @return
     */
    List<SwylsbXq> findSwylsbXqName();

    /**
     * �޸�����
     * @param swylsbXq
     * @return
     */
    int updateSwylsbXq(SwylsbXq swylsbXq);

    /**
     * ��������
     * @param swylsbXq
     * @return
     */
    int addSwylsbXq(SwylsbXq swylsbXq);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteSwylsbXqById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param swylsbXq
     * @return
     */
    List<SwylsbXq> findSwylsbXq(SwylsbXq swylsbXq);

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
    void swylsbXqImport(InputStream is) throws IOException;

    SwylsbXq selectSwylsbXqById(int ID);

    /**
     * ���� 
     * @author xuchao 
     * @description ��ѯ���й�Ӧ��
     * @date 2019/12/29 22:03 
     * @Version     1.0
     */
    List<SwylsbXq> findAllSwylsbXq();
}
