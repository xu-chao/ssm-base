package com.java.activiti.service;

import com.java.activiti.model.Goods;
import com.java.activiti.pojo.Tree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface GoodsService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Goods> goodsPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int goodsCount(Map<String, Object> map);

    /**
     *
     * @Title: findGoodsNameLike
     * @Description: like查询 物料名
     * @return
     */
    List<Goods> findGoodsNameLike(String q);
    /**
     *
     * @Title: findGoodsCodeLike
     * @Description: like查询 型号/图号
     * @return
     */
    List<Goods> findGoodsCodeLike(String q);
    /**
     *
     * @Title: findGoodsTypeLike
     * @Description: like查询 类型
     * @return
     */
    List<Goods> findGoodsTypeLike(String q);
    /**
     *
     * @Title: selectGoodsType
     * @Description: like查询物料名
     * @return
     */
    List<Tree> selectGoodsType();

    /**
     * 修改物料
     * @param goods
     * @return
     */
    int updateGoods(Goods goods);

    /**
     * 新增物料
     * @param goods
     * @return
     */
    int addGoods(Goods goods);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteGoodsById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param goods
     * @return
     */
    List<Goods> findGoods(Goods goods);

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
    void goodsImport(InputStream is) throws IOException;

    Goods selectGoodsById(String ECodeID);
}
