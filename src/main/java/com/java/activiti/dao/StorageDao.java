package com.java.activiti.dao;

import com.java.activiti.model.Storage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StorageDao {

    Storage findById(String storageId);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int storageCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Storage> storagePage(Map<String, Object> map);

    /**
     *
     * @Title: selectStorageName
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param storageId
     * @return
     */
    List<Storage> selectStorageName(@Param("storageId") String storageId);

    List<Storage> selectALL();

    int addStorage(Storage storage);

    int updateStorage(Storage storage);

    int deleteStorageById(List<String> id);

    List<Storage> findStorage(Storage storage);

    /**
     *
     * @Title: selectStorageByStorageID
     * @Description: ֤��ID��ѯ��Ӧ�������
     * @author: ��
     * @param storageId ����ID
     * @return
     */
    List<Storage> selectStorageByStorageID(String storageId);
}
