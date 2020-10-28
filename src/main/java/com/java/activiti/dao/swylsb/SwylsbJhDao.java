package com.java.activiti.dao.swylsb;

import com.java.activiti.model.swylsb.SwylsbJh;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SwylsbJhDao {

    List<SwylsbJh> findById(int ID);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int swylsbJhCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<SwylsbJh> swylsbJhPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: 查询物料，自动补全
     * @param sbName
     * @return
     */
    List<SwylsbJh> selectSwylsbJhName(@Param("sbName") String sbName);

    List<SwylsbJh> selectALL();

    int addSwylsbJh(SwylsbJh swylsbJh);

    int updateSwylsbJh(List<SwylsbJh> swylsbJhs);

    int deleteSwylsbJhById(List<String> id);

    List<SwylsbJh> findSwylsbJh(SwylsbJh swylsbJh);

    List<SwylsbJh> selectSwylsbJhBySwylsbJhID(int ID);

    List<SwylsbJh> findAllSwylsbJh();
}
