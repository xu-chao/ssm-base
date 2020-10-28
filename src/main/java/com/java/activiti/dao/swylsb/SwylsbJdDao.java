package com.java.activiti.dao.swylsb;

import com.java.activiti.model.swylsb.SwylsbJd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SwylsbJdDao {

    SwylsbJd findById(int ID);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int swylsbJdCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<SwylsbJd> swylsbJdPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: 查询物料，自动补全
     * @param sbName
     * @return
     */
    List<SwylsbJd> selectSwylsbJdName(@Param("sbName") String sbName);

    List<SwylsbJd> selectALL();

    int addSwylsbJd(SwylsbJd swylsbJd);

    int updateSwylsbJd(SwylsbJd swylsbJd);

    int deleteSwylsbJdById(List<String> id);

    List<SwylsbJd> findSwylsbJd(SwylsbJd swylsbJd);

    List<SwylsbJd> selectSwylsbJdBySwylsbJdID(int ID);

    List<SwylsbJd> findAllSwylsbJd();
}
