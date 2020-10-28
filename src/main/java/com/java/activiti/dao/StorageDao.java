package com.java.activiti.dao;

import com.java.activiti.model.Storage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StorageDao {

    Storage findById(String storageId);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int storageCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Storage> storagePage(Map<String, Object> map);

    /**
     *
     * @Title: selectStorageName
     * @Description: 查询物料，自动补全
     * @param storageId
     * @return
     */
    List<Storage> selectStorageName(@Param("storageId") String storageId);

    List<Storage> selectALL();

    int addStorage(Storage storage);

    int updateStorage(Storage storage);

    int deleteStorageById(List<String> id);

    List<Storage> findStorage(Storage storage);

    /**
     *
     * @Title: selectStorageByStorageID
     * @Description: 证据ID查询对应存货编码
     * @author: 许超
     * @param storageId 物料ID
     * @return
     */
    List<Storage> selectStorageByStorageID(String storageId);
}
