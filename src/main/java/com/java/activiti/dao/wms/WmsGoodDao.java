package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.WmsGood;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WmsGoodDao {

    WmsGood findById(String wmsGoodId);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsGoodCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<WmsGood> wmsGoodPage(Map<String, Object> map);

    List<WmsGood> selectALL();

    int addWmsGood(WmsGood wmsGood);

    int updateWmsGood(WmsGood wmsGood);

    int deleteWmsGoodById(List<String> id);

    List<WmsGood> findWmsGood(WmsGood wmsGood);

    /**
     * 物料编码查找
     * @param goodEncoding
     * @return
     */
    List<WmsGood> selectWmsGoodByGoodEncoding(String goodEncoding);

    WmsGood findWmsGoodByExcel(WmsGood wmsGood);


    /**
     *
     * @Title: selectGoodEncodingLike
     * @Description: 查询物料编码，自动补全
     * @param goodEncoding
     * @return
     */
    List<WmsGood> selectGoodEncodingLike(@Param("goodEncoding") String goodEncoding);

    /**
     *
     * @Title: selectGoodNameLike
     * @Description: 查询物料名称，自动补全
     * @param goodName
     * @return
     */
    List<WmsGood> selectGoodNameLike(@Param("goodName") String goodName);

    /**
     *
     * @Title: selectGoodModelLike
     * @Description: 查询类型，自动补全
     * @param goodModel
     * @return
     */
    List<WmsGood> selectGoodModelLike(@Param("goodModel") String goodModel);


}
