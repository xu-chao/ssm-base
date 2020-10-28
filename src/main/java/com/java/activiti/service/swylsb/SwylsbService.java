package com.java.activiti.service.swylsb;

import com.java.activiti.model.swylsb.Swylsb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SwylsbService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Swylsb> swylsbPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int swylsbCount(Map<String, Object> map);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like查询物料名
     * @return
     */
    List<Swylsb> findSwylsbName(String q);

    /**
     *
     * @Title: findSwylsbName
     * @Description: like查询物料名
     * @return
     */
    List<Swylsb> findSwylsbName();

    /**
     * 修改物料
     * @param swylsb
     * @return
     */
    int updateSwylsb(Swylsb swylsb);

    /**
     * 新增物料
     * @param swylsb
     * @return
     */
    int addSwylsb(Swylsb swylsb);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteSwylsbById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param swylsb
     * @return
     */
    List<Swylsb> findSwylsb(Swylsb swylsb);

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
    void swylsbImport(InputStream is) throws IOException;

    Swylsb selectSwylsbById(int ID);

    /**
     * 参数 
     * @author xuchao 
     * @description 查询所有供应商
     * @date 2019/12/29 22:03 
     * @Version     1.0
     */
    List<Swylsb> findAllSwylsb();

/**
 * @author      LIUHD
 * 参数 ID
 * @description 查询
 * @date        2020/1/16 9:22
 * @Version     1.0
 */
    public Swylsb findById(int ID);

}
