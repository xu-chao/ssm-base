package com.java.activiti.service;

import com.java.activiti.model.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface StorageService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Storage> storagePage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int storageCount(Map<String, Object> map);

    /**
     *
     * @Title: findStorageName
     * @Description: like查询物料名
     * @return
     */
    List<Storage> findStorageName(String q);

    /**
     * 修改物料
     * @param storage
     * @return
     */
    int updateStorage(Storage storage);

    /**
     * 新增物料
     * @param storage
     * @return
     */
    int addStorage(Storage storage);

    /**
     * 批量删除物料
     * @param id
     * @return
     */
    int deleteStorageById(List<String> id);

    /**
     * 判断物料是否已经存在
     * @param storage
     * @return
     */
    List<Storage> findStorage(Storage storage);

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
    void storageImport(InputStream is) throws IOException;
}
