package com.java.activiti.dao;

import com.java.activiti.model.Park;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ParkDao {

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int parkCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Park> parkPage(Map<String, Object> map);


    List<Park> selectParkName(@Param("parkName") String parkName);
    List<Park> selectALL();

    int addPark(Park park);

    int updatePark(Park park);

    int deleteParkById(List<String> id);

    List<Park> findPark(Park park);

    /**
     *
     * @Title: selectCityByCityID
     * @Description: 证据ID查询对应城市
     * @author: 许超
     * @param parkID 城市ID
     * @return
     */
    List<Park> selectParkByParkID(int parkID);

     Park findById(int parkID);

     Park findParkByCityID(@Param("cityID") int cityID,@Param("parkName") String parkName);
}
