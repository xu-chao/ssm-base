package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.Wmsrecordin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WmsrecordinDao {

    Wmsrecordin findById(Integer inId);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsrecordinCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Wmsrecordin> wmsrecordinPage(Map<String, Object> map);

    int addWmsrecordin(Wmsrecordin wmsrecordin);

    int updateWmsrecordin(Wmsrecordin wmsrecordin);

    int deleteWmsrecordinById(List<String> inId);

    List<Wmsrecordin> findWmsrecordin(Wmsrecordin wmsrecordin);

    Wmsrecordin findWmsrecordinByExcel(Wmsrecordin wmsrecordin);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: ��ѯ�������ƣ��Զ���ȫ
     * @param supplierName
     * @return
     */
    List<Wmsrecordin> selectSupplierNameLike(@Param("supplierName") String supplierName);
}
