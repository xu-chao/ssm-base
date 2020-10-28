package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.Wmsrecordstorage;
import com.java.activiti.model.wms.Wmssupplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ������
 */
@Repository
public interface WmsrecordstorageDao {

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsrecordstorageCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Wmsrecordstorage> wmsrecordstoragePage(Map<String, Object> map);

    /**
     * ѡ�����еĿ����Ϣ
     * @return �������еĿ����Ϣ
     */
    List<Wmsrecordstorage> selectAllAndRepositoryID(@Param("repositoryID") Integer repositoryID);

    /**
     * ѡ��ָ������ID�Ͳֿ�ID�Ŀ����Ϣ
     * @param recordGoodId ����ID
     * @param recordWarehouseId ���ID
     * @return ��������ָ������ID�Ͳֿ�ID�Ŀ����Ϣ
     * liuhd
     */
    List<Wmsrecordstorage> selectByGoodsIDAndRepositoryID(@Param("recordGoodId") String recordGoodId,
                                                          @Param("recordWarehouseId") Integer recordWarehouseId);

    /**
     * ѡ��ָ���������Ŀ����Ϣ
     * @param goodsName ��������
     * @return ��������ָ���������ƵĿ����Ϣ
     */
    List<Wmsrecordstorage> selectByGoodsNameAndRepositoryID(@Param("goodsName") String goodsName,
                                                            @Param("repositoryID") Integer repositoryID);

    /**
     * ѡ��ָ���������͵Ŀ����Ϣ
     * @param goodsType ��������
     * @return ��������ָ���������͵Ŀ����Ϣ
     */
    List<Wmsrecordstorage> selectByGoodsTypeAndRepositoryID(@Param("goodsType") String goodsType,
                                                            @Param("repositoryID") Integer repositoryID);

    /**
     * ���¿����Ϣ
     * �ÿ����Ϣ�����Ѿ����������ݿ⵱�У����������Ч
     * @param wsrecordstorage �����Ϣ
     */
    void update(Wmsrecordstorage wsrecordstorage);

    /**
     * �����µĿ����Ϣ
     * @param wsrecordstorage �����Ϣ
     */
    void insert(Wmsrecordstorage wsrecordstorage);

    /**
     * ������������Ϣ
     * @param wsrecordstorage �����������Ϣ
     */
    void insertBatch(List<Wmsrecordstorage> wsrecordstorage);

    /**
     * ɾ��ָ������ID�Ŀ����Ϣ
     * @param goodsID ����ID
     */
    void deleteByGoodsID(String goodsID);

    /**
     * ɾ��ָ���ֿ�Ŀ����Ϣ
     * @param repositoryID �ֿ�ID
     */
    void deleteByRepositoryID(Integer repositoryID);

    /**
     * ɾ��ָ���ֿ��е�ָ������Ŀ����Ϣ
     * @param goodsID ����ID
     * @param repositoryID �ֿ�ID
     */
    void deleteByRepositoryIDAndGoodsID(@Param("goodsID") String goodsID, @Param("repositoryID") Integer repositoryID);
}
