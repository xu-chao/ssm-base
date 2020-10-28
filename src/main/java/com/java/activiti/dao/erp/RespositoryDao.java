package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.Respository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RespositoryDao {

    Respository findById(int REPO_ID);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int respositoryCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Respository> respositoryPage(Map<String, Object> map);

    /**
     *
     * @Title: selectRespositoryName
     * @Description: 查询物料，自动补全
     * @param REPO_ADDRESS
     * @return
     */
    List<Respository> selectRespositoryName(@Param("REPO_ADDRESS") String REPO_ADDRESS);

    List<Respository> selectALL();

    int addRespository(Respository respository);

    int updateRespository(Respository respository);

    int deleteRespositoryById(List<String> id);

    List<Respository> findRespository(Respository respository);

    /**
     *
     * @Title: selectGoodsByGoodsID
     * @Description: 证据ID查询对应存货编码
     * @author: 许超
     * @param REPO_ID 物料ID
     * @return
     */
    List<Respository> selectRespositoryByRespositoryID(int REPO_ID);

}
