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
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsrecordinCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
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
     * @Description: 查询物料名称，自动补全
     * @param supplierName
     * @return
     */
    List<Wmsrecordin> selectSupplierNameLike(@Param("supplierName") String supplierName);
}
