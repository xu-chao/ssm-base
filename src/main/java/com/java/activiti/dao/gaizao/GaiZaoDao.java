package com.java.activiti.dao.gaizao;

import com.java.activiti.model.Project;
import com.java.activiti.model.gaizao.GaiZao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GaiZaoDao {

    /**
     * 分页总数
     *
     * @return 返回所有符合条件的记录总数
     */
    int gaiZaoCount(List<Project> projects);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<GaiZao> gaiZaoPage(@Param("map")Map<String, Object> map, @Param("list")List<Project> projects);/**

     * 获取该公园下所有的记录
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<GaiZao> gaiZaoDetail(Map<String, Object> map);

    /**
     * 新增 改造记录
     *
     * @param gaiZao
     * @return 返回 0 1
     */
    int addGaiZao(GaiZao gaiZao);

    /**
     * 更新 改造记录
     *
     * @param gaiZao
     * @return 返回 0 1
     */
    int updateGaiZao(GaiZao gaiZao);
    /**
     * 删除 改造记录
     *
     * @return 返回 0 1
     */
    int deleteGaiZao(List<String> id);

//    /**
//     *
//     * @Title: selectRoleName
//     * @Description: 查询城市，自动补全
//     * @param cityName
//     * @return
//     */
//    List<City> selectCityName(@Param("cityName") String cityName);
//
//    List<City> selectALL();
//

//
//    int updateCity(City city);
//
//    int deleteCityById(List<String> id);
//
//    List<City> findCity(City city);
//
//    /**
//     *
//     * @Title: selectCityByCityID
//     * @Description: 证据ID查询对应城市
//     * @author: 许超
//     * @param cityID 城市ID
//     * @return
//     */
//    List<City> selectCityByCityID(int cityID);
//
//    City findById(int cityID);
}
