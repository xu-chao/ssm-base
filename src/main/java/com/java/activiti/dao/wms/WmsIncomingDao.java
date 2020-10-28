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
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int WmsIncomingCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
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
     * @Description: 查询物料名称，自动补全
     * @param supplierName
     * @return
     */
    List<WmsIncoming> selectSupplierNameLike(@Param("supplierName") String supplierName);
}
