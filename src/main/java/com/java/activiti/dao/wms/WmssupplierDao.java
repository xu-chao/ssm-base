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
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmssupplierCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
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
     * @Description: 查询物料名称，自动补全
     * @param supplierName
     * @return
     */
    List<Wmssupplier> selectSupplierNameLike(@Param("supplierName") String supplierName);

    List<Wmssupplier> selectALL();
}
