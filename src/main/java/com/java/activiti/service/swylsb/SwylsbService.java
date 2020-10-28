package com.java.activiti.service.swylsb;

import com.java.activiti.model.swylsb.Swylsb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SwylsbService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Swylsb> swylsbPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int swylsbCount(Map<String, Object> map);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like��ѯ������
     * @return
     */
    List<Swylsb> findSwylsbName(String q);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like��ѯ������
     * @return
     */
    List<Swylsb> findSwylsbName();

    /**
     * �޸�����
     * @param swylsb
     * @return
     */
    int updateSwylsb(Swylsb swylsb);

    /**
     * ��������
     * @param swylsb
     * @return
     */
    int addSwylsb(Swylsb swylsb);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteSwylsbById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param swylsb
     * @return
     */
    List<Swylsb> findSwylsb(Swylsb swylsb);

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
    void swylsbImport(InputStream is) throws IOException;

    Swylsb selectSwylsbById(int ID);

    /**
     * ���� 
     * @author xuchao 
     * @description ��ѯ���й�Ӧ��
     * @date 2019/12/29 22:03 
     * @Version     1.0
     */
    List<Swylsb> findAllSwylsb();

/**
 * @author      LIUHD
 * ���� ID
 * @description ��ѯ
 * @date        2020/1/16 9:22
 * @Version     1.0
 */
    public Swylsb findById(int ID);

}
