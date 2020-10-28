package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.Wmsrecordstorage;
import com.java.activiti.model.wms.Wmssupplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 库存管理
 */
@Repository
public interface WmsrecordstorageDao {

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsrecordstorageCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Wmsrecordstorage> wmsrecordstoragePage(Map<String, Object> map);

    /**
     * 选择所有的库存信息
     * @return 返回所有的库存信息
     */
    List<Wmsrecordstorage> selectAllAndRepositoryID(@Param("repositoryID") Integer repositoryID);

    /**
     * 选择指定货物ID和仓库ID的库存信息
     * @param recordGoodId 货物ID
     * @param recordWarehouseId 库存ID
     * @return 返回所有指定货物ID和仓库ID的库存信息
     * liuhd
     */
    List<Wmsrecordstorage> selectByGoodsIDAndRepositoryID(@Param("recordGoodId") String recordGoodId,
                                                          @Param("recordWarehouseId") Integer recordWarehouseId);

    /**
     * 选择指定货物名的库存信息
     * @param goodsName 货物名称
     * @return 返回所有指定货物名称的库存信息
     */
    List<Wmsrecordstorage> selectByGoodsNameAndRepositoryID(@Param("goodsName") String goodsName,
                                                            @Param("repositoryID") Integer repositoryID);

    /**
     * 选择指定货物类型的库存信息
     * @param goodsType 货物类型
     * @return 返回所有指定货物类型的库存信息
     */
    List<Wmsrecordstorage> selectByGoodsTypeAndRepositoryID(@Param("goodsType") String goodsType,
                                                            @Param("repositoryID") Integer repositoryID);

    /**
     * 更新库存信息
     * 该库存信息必需已经存在于数据库当中，否则更新无效
     * @param wsrecordstorage 库存信息
     */
    void update(Wmsrecordstorage wsrecordstorage);

    /**
     * 插入新的库存信息
     * @param wsrecordstorage 库存信息
     */
    void insert(Wmsrecordstorage wsrecordstorage);

    /**
     * 批量导入库存信息
     * @param wsrecordstorage 若干条库存信息
     */
    void insertBatch(List<Wmsrecordstorage> wsrecordstorage);

    /**
     * 删除指定货物ID的库存信息
     * @param goodsID 货物ID
     */
    void deleteByGoodsID(String goodsID);

    /**
     * 删除指定仓库的库存信息
     * @param repositoryID 仓库ID
     */
    void deleteByRepositoryID(Integer repositoryID);

    /**
     * 删除指定仓库中的指定货物的库存信息
     * @param goodsID 货物ID
     * @param repositoryID 仓库ID
     */
    void deleteByRepositoryIDAndGoodsID(@Param("goodsID") String goodsID, @Param("repositoryID") Integer repositoryID);
}
