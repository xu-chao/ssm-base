package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.Wmssupplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WmssupplierDao {

    Wmssupplier findById(Integer supplierId);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmssupplierCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Wmssupplier> wmssupplierPage(Map<String, Object> map);

    int addWmssupplier(Wmssupplier wmssupplier);

    int updateWmssupplier(Wmssupplier wmssupplier);

    int deleteWmssupplierById(List<String> supplierId);

    List<Wmssupplier> findWmssupplier(Wmssupplier wmssupplier);

    Wmssupplier findWmssupplierByExcel(Wmssupplier wmssupplier);

    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: ��ѯ�������ƣ��Զ���ȫ
     * @param supplierName
     * @return
     */
    List<Wmssupplier> selectSupplierNameLike(@Param("supplierName") String supplierName);

    List<Wmssupplier> selectALL();
}
