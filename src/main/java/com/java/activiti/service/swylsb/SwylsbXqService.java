package com.java.activiti.service.swylsb;

import com.java.activiti.model.swylsb.SwylsbXq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SwylsbXqService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<SwylsbXq> swylsbXqPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int swylsbXqCount(Map<String, Object> map);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like查询物料名
     * @return
     */
    List<SwylsbXq> findSwylsbXqName(String q);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like查询物料名
     * @return
     */
    List<SwylsbXq> findSwylsbXqName();

    /**
     * 修改物料
     * @param swylsbXq
     * @return
     */
    int updateSwylsbXq(SwylsbXq swylsbXq);

    /**
     * 新增物料
     * @param swylsbXq
     * @return
     */
    int addSwylsbXq(SwylsbXq swylsbXq);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteSwylsbXqById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param swylsbXq
     * @return
     */
    List<SwylsbXq> findSwylsbXq(SwylsbXq swylsbXq);

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
    void swylsbXqImport(InputStream is) throws IOException;

    SwylsbXq selectSwylsbXqById(int ID);

    /**
     * 参数 
     * @author xuchao 
     * @description 查询所有供应商
     * @date 2019/12/29 22:03 
     * @Version     1.0
     */
    List<SwylsbXq> findAllSwylsbXq();
}
