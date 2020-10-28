package com.java.activiti.service;

import com.java.activiti.model.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface StorageService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Storage> storagePage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int storageCount(Map<String, Object> map);

    /**
     *
     * @Title: findStorageName
     * @Description: like��ѯ������
     * @return
     */
    List<Storage> findStorageName(String q);

    /**
     * �޸�����
     * @param storage
     * @return
     */
    int updateStorage(Storage storage);

    /**
     * ��������
     * @param storage
     * @return
     */
    int addStorage(Storage storage);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteStorageById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param storage
     * @return
     */
    List<Storage> findStorage(Storage storage);

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
    void storageImport(InputStream is) throws IOException;
}
