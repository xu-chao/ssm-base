package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Wmsrecordstorage;
import com.java.activiti.model.wms.Wmssupplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmsrecordstorageService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Wmsrecordstorage> wmsrecordstoragePage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsrecordstorageCount(Map<String, Object> map);

    /**
     * ����������
     *
     * @param goodsID      ����ID
     * @param repositoryID ���ֿ�ID
     * @param number       �������
     * @return ����һ��boolean ֵ����ֵΪtrue��ʾ���ɹ��������ʾ���ʧ��
     */
    Map<String,Object>  stockInOperation(String goodsID, Integer repositoryID, long number) throws Exception;

    /**
     * Ϊָ���Ļ������¼����ָ����Ŀ
     *
     * @param goodsID      ����ID
     * @param repositoryID �ֿ�ID
     * @param number       ���ӵ�����
     * @return ����һ�� boolean ֵ����ֵΪtrue��ʾ��Ŀ���ӳɹ��������ʾ����ʧ��
     */
    boolean storageIncrease(String goodsID, Integer repositoryID, long number) throws Exception;



    /**
     * Ϊָ���Ļ������¼����ָ������Ŀ
     *
     * @param goodsID      ����ID
     * @param repositoryID �ֿ�ID
     * @param number       ���ٵ�����
     * @return ����һ�� boolean ֵ����ֵΪ true ��ʾ��Ŀ���ٳɹ��������ʾ����ʧ��
     */
    boolean storageDecrease(String goodsID, Integer repositoryID, long number) throws Exception;

    /**
     * ����һ������¼
     *
     * @param goodsID      ָ���Ļ���ID
     * @param repositoryID ָ���Ĳֿ�ID
     * @param number       ���µĿ������
     * @return ����һ��booleanֵ��ֵΪtrue������³ɹ����������ʧ��
     */
    boolean updateStorage(String goodsID, Integer repositoryID, long number) throws Exception;

    /**
     * ���һ������¼
     *
     * @param goodsID      ָ���Ļ���ID
     * @param repositoryID ָ���Ĳֿ�ID
     * @param number       �������
     * @return ����һ��booleanֵ��ֵΪtrue������³ɹ����������ʧ��
     */
    boolean addNewStorage(String goodsID, Integer repositoryID, long number) throws Exception;

}
