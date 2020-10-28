package com.java.activiti.service.erp;

import com.java.activiti.model.Goods;
import com.java.activiti.model.erp.Respository;
import com.java.activiti.model.erp.Supplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SupplierService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Supplier> supplierPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int supplierCount(Map<String, Object> map);

    /**
     *
     * @Title: findSupplierName
     * @Description: like��ѯ������
     * @return
     */
    List<Supplier> findSupplierName(String q);

    /**
     *
     * @Title: findSupplierName
     * @Description: like��ѯ������
     * @return
     */
    List<Supplier> findSupplierName();

    /**
     * �޸�����
     * @param supplier
     * @return
     */
    int updateSupplier(Supplier supplier);

    /**
     * ��������
     * @param supplier
     * @return
     */
    int addSupplier(Supplier supplier);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteSupplierById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param supplier
     * @return
     */
    List<Supplier> findSupplier(Supplier supplier);

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
    void supplierImport(InputStream is) throws IOException;

    Supplier selectSupplierById(int SUPPLIER_ID);

    /**
     *
     * @Title: findGoodsCodeLike
     * @Description: like��ѯ �ͺ�/ͼ��
     * @return
     */
    List<Supplier> findSUPPLIER_PERSONLike(String q);

    /**
     * ���� 
     * @author xuchao 
     * @description ��ѯ���й�Ӧ��
     * @date 2019/12/29 22:03 
     * @Version     1.0
     */
    List<Supplier> findAllSupplier();
}
