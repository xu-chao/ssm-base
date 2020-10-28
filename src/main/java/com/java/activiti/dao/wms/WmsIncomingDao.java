package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.WmsIncoming;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WmsIncomingDao {

    WmsIncoming findById(Integer inId);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int WmsIncomingCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<WmsIncoming> WmsIncomingPage(Map<String, Object> map);

    int insert(WmsIncoming wmsIncoming);

    int insertByBatch(List<WmsIncoming> wmsIncomings);

    int updateWmsIncoming(WmsIncoming wmsIncoming);

    int deleteWmsIncomingById(List<String> inId);

    List<WmsIncoming> findWmsIncoming(WmsIncoming wmsIncoming);

    WmsIncoming findWmsIncomingByExcel(WmsIncoming wmsIncoming);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: ��ѯ�������ƣ��Զ���ȫ
     * @param supplierName
     * @return
     */
    List<WmsIncoming> selectSupplierNameLike(@Param("supplierName") String supplierName);
}
