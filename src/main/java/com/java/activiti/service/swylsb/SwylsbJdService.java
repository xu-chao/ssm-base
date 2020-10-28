package com.java.activiti.service.swylsb;

import com.java.activiti.model.swylsb.SwylsbJd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SwylsbJdService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<SwylsbJd> swylsbJdPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int swylsbJdCount(Map<String, Object> map);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like查询物料名
     * @return
     */
    List<SwylsbJd> findSwylsbJdName(String q);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like查询物料名
     * @return
     */
    List<SwylsbJd> findSwylsbJdName();

    /**
     * 修改物料
     * @param swylsbJd
     * @return
     */
    int updateSwylsbJd(SwylsbJd swylsbJd);

    /**
     * 新增物料
     * @param swylsbJd
     * @return
     */
    int addSwylsbJd(SwylsbJd swylsbJd);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteSwylsbJdById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param swylsbJd
     * @return
     */
    List<SwylsbJd> findSwylsbJd(SwylsbJd swylsbJd);

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
    void swylsbJdImport(InputStream is) throws IOException;

    SwylsbJd selectSwylsbJdById(int ID);

    /**
     * 参数
     * @author xuchao
     * @description 查询所有供应商
     * @date 2019/12/29 22:03
     * @Version     1.0
     */
    List<SwylsbJd> findAllSwylsbJd();
}
