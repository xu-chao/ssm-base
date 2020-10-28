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
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Supplier> supplierPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int supplierCount(Map<String, Object> map);

    /**
     *
     * @Title: findSupplierName
     * @Description: like查询物料名
     * @return
     */
    List<Supplier> findSupplierName(String q);

    /**
     *
     * @Title: findSupplierName
     * @Description: like查询物料名
     * @return
     */
    List<Supplier> findSupplierName();

    /**
     * 修改物料
     * @param supplier
     * @return
     */
    int updateSupplier(Supplier supplier);

    /**
     * 新增物料
     * @param supplier
     * @return
     */
    int addSupplier(Supplier supplier);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteSupplierById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param supplier
     * @return
     */
    List<Supplier> findSupplier(Supplier supplier);

    /**
     *
     * @Title: export
     * @Description: 导出excel
     * @author: 许超
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: goodsImport
     * @Description: 导入excel
     * @author: 许超
     * @param is
     * @throws IOException
     */
    void supplierImport(InputStream is) throws IOException;

    Supplier selectSupplierById(int SUPPLIER_ID);

    /**
     *
     * @Title: findGoodsCodeLike
     * @Description: like查询 型号/图号
     * @return
     */
    List<Supplier> findSUPPLIER_PERSONLike(String q);

    /**
     * 参数 
     * @author xuchao 
     * @description 查询所有供应商
     * @date 2019/12/29 22:03 
     * @Version     1.0
     */
    List<Supplier> findAllSupplier();
}
