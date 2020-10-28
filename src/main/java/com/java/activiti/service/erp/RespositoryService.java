package com.java.activiti.service.erp;

import com.java.activiti.model.erp.Respository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface RespositoryService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Respository> respositoryPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int respositoryCount(Map<String, Object> map);

    /**
     *
     * @Title: findRespositoryName
     * @Description: like��ѯ������
     * @return
     */
    List<Respository> findRespositoryName(String q);

    /**
     *
     * @Title: findRespositoryName
     * @Description: like��ѯ������
     * @return
     */
    List<Respository> findRespositoryName();

    /**
     * �޸�����
     * @param respository
     * @return
     */
    int updateRespository(Respository respository);

    /**
     * ��������
     * @param respository
     * @return
     */
    int addRespository(Respository respository);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteRespositoryById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param respository
     * @return
     */
    List<Respository> findRespository(Respository respository);

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
    void respositoryImport(InputStream is) throws IOException;

    Respository selectRespositoryById(int REPO_ID);
}
