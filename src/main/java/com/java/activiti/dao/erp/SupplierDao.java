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
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int supplierCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Supplier> supplierPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: 查询物料，自动补全
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
     * @Description: 证据ID查询对应存货编码
     * @author: 许超
     * @param SUPPLIER_ID 供应商ID
     * @return
     */
    List<Supplier> selectSupplierBySupplierID(int SUPPLIER_ID);

    /**
     *
     * @Title: selectGoodsCodeLike
     * @Description: 查询型号/图号，自动补全
     * @param SUPPLIER_PERSON
     * @return
     */
    List<Supplier> selectSUPPLIER_PERSONLike(@Param("SUPPLIER_PERSON") String SUPPLIER_PERSON);

    /**
     * 参数 
     * @author xuchao 
     * @description 查询所有供应商
     * @date 2019/12/30 0:08
     * @Version     1.0
     */
    List<Supplier> findAllSupplier();

}
