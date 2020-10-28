package com.java.activiti.service.swylsb;

import com.java.activiti.model.swylsb.SwylsbJd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SwylsbJdService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<SwylsbJd> swylsbJdPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int swylsbJdCount(Map<String, Object> map);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like��ѯ������
     * @return
     */
    List<SwylsbJd> findSwylsbJdName(String q);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like��ѯ������
     * @return
     */
    List<SwylsbJd> findSwylsbJdName();

    /**
     * �޸�����
     * @param swylsbJd
     * @return
     */
    int updateSwylsbJd(SwylsbJd swylsbJd);

    /**
     * ��������
     * @param swylsbJd
     * @return
     */
    int addSwylsbJd(SwylsbJd swylsbJd);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteSwylsbJdById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param swylsbJd
     * @return
     */
    List<SwylsbJd> findSwylsbJd(SwylsbJd swylsbJd);

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
    void swylsbJdImport(InputStream is) throws IOException;

    SwylsbJd selectSwylsbJdById(int ID);

    /**
     * ����
     * @author xuchao
     * @description ��ѯ���й�Ӧ��
     * @date 2019/12/29 22:03
     * @Version     1.0
     */
    List<SwylsbJd> findAllSwylsbJd();
}
