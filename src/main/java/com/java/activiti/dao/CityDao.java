package com.java.activiti.dao;

import com.java.activiti.model.City;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CityDao {

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int cityCount(Map<String,Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<City> cityPage(Map<String,Object> map);

    /**
     *
     * @Title: selectRoleName
     * @Description: 查询城市，自动补全
     * @param cityName
     * @return
     */
    List<City> selectCityName(@Param("cityName")String cityName);

    List<City> selectALL();

    int addCity(City city);

    int updateCity(City city);

    int deleteCityById(List<String> id);

    List<City> findCity(City city);

    /**
     *
     * @Title: selectCityByCityID
     * @Description: 证据ID查询对应城市
     * @author: 许超
     * @param cityID 城市ID
     * @return
     */
    List<City> selectCityByCityID(int cityID);

    City findById(int cityID);

    City findCityByName(@Param("cityName") String cityName);
}
