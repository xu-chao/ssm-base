package com.java.activiti.dao.swylsb;

import com.java.activiti.model.swylsb.SwylsbXq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SwylsbXqDao {

    SwylsbXq findById(int ID);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int swylsbXqCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<SwylsbXq> swylsbXqPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: 查询物料，自动补全
     * @param sbName
     * @return
     */
    List<SwylsbXq> selectSwylsbXqName(@Param("sbName") String sbName);

    List<SwylsbXq> selectALL();

    int addSwylsbXq(SwylsbXq swylsbXq);

    int updateSwylsbXq(SwylsbXq swylsbXq);

    int deleteSwylsbXqById(List<String> id);

    List<SwylsbXq> findSwylsbXq(SwylsbXq swylsbXq);

    List<SwylsbXq> selectSwylsbXqBySwylsbXqID(int ID);

    List<SwylsbXq> findAllSwylsbXq();
}
