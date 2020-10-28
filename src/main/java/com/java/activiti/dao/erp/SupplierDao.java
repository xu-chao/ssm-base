package com.java.activiti.dao.erp;

import com.java.activiti.model.Goods;
import com.java.activiti.model.erp.Supplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SupplierDao {

    Supplier findById(int SUPPLIER_ID);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int supplierCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Supplier> supplierPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param SUPPLIER_NAME
     * @return
     */
    List<Supplier> selectSupplierName(@Param("SUPPLIER_NAME") String SUPPLIER_NAME);

    List<Supplier> selectALL();

    int addSupplier(Supplier supplier);

    int updateSupplier(Supplier supplier);

    int deleteSupplierById(List<String> id);

    List<Supplier> findSupplier(Supplier supplier);

    /**
     *
     * @Title: selectSupplierBySupplierID
     * @Description: ֤��ID��ѯ��Ӧ�������
     * @author: ��
     * @param SUPPLIER_ID ��Ӧ��ID
     * @return
     */
    List<Supplier> selectSupplierBySupplierID(int SUPPLIER_ID);

    /**
     *
     * @Title: selectGoodsCodeLike
     * @Description: ��ѯ�ͺ�/ͼ�ţ��Զ���ȫ
     * @param SUPPLIER_PERSON
     * @return
     */
    List<Supplier> selectSUPPLIER_PERSONLike(@Param("SUPPLIER_PERSON") String SUPPLIER_PERSON);

    /**
     * ���� 
     * @author xuchao 
     * @description ��ѯ���й�Ӧ��
     * @date 2019/12/30 0:08
     * @Version     1.0
     */
    List<Supplier> findAllSupplier();

}
