package com.java.activiti.dao;

import com.java.activiti.model.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsDao {

    Goods findById(String goodsId);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int goodsCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Goods> goodsPage(Map<String, Object> map);

    /**
     *
     * @Title: selectGoodsNameLike
     * @Description: 查询物料，自动补全
     * @param goodsName
     * @return
     */
    List<Goods> selectGoodsNameLike(@Param("goodsName") String goodsName);

    /**
     *
     * @Title: selectGoodsCodeLike
     * @Description: 查询型号/图号，自动补全
     * @param goodsCode
     * @return
     */
    List<Goods> selectGoodsCodeLike(@Param("goodsCode") String goodsCode);

    /**
     *
     * @Title: selectGoodsTypeLike
     * @Description: 查询类型，自动补全
     * @param goodsType
     * @return
     */
    List<Goods> selectGoodsTypeLike(@Param("goodsType") String goodsType);
    /**
     *
     * @Title: selectGoodsType
     * @Description: 查询物料，自动补全
     * @return
     */
    List<String> selectGoodsTypeAll();

    List<Goods> selectALL();

    int addGoods(Goods goods);

    int updateGoods(Goods goods);

    int deleteGoodsById(List<String> id);

    List<Goods> findGoods(Goods goods);

    /**
     *
     * @Title: selectGoodsByGoodsID
     * @Description: 证据ID查询对应存货编码
     * @author: 许超
     * @param goodsId 物料ID
     * @return
     */
    List<Goods> selectGoodsByGoodsID(String goodsId);

    Goods findGoodsByExcel(Goods goods);

}
