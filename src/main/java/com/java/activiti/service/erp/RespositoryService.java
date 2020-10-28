package com.java.activiti.service.erp;

import com.java.activiti.model.erp.Respository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface RespositoryService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Respository> respositoryPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int respositoryCount(Map<String, Object> map);

    /**
     *
     * @Title: findRespositoryName
     * @Description: like查询物料名
     * @return
     */
    List<Respository> findRespositoryName(String q);

    /**
     *
     * @Title: findRespositoryName
     * @Description: like查询物料名
     * @return
     */
    List<Respository> findRespositoryName();

    /**
     * 修改物料
     * @param respository
     * @return
     */
    int updateRespository(Respository respository);

    /**
     * 新增物料
     * @param respository
     * @return
     */
    int addRespository(Respository respository);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteRespositoryById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param respository
     * @return
     */
    List<Respository> findRespository(Respository respository);

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
    void respositoryImport(InputStream is) throws IOException;

    Respository selectRespositoryById(int REPO_ID);
}
