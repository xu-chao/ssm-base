package com.java.activiti.service;

import com.java.activiti.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface CityService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<City> cityPage(Map<String,Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int cityCount(Map<String,Object> map);

    /**
     *
     * @Title: findCityName
     * @Description: like查询角色名
     * @return
     */
    List<City> findCityName();

    /**
     * 修改城市
     * @param city
     * @return
     */
    int updateCity(City city);

    /**
     * 新增城市
     * @param city
     * @return
     */
    int addCity(City city);

    /**
     * 批量删除城市
     * @param id
     * @return
     */
    int deleteCityById(List<String> id);

    /**
     * 判断城市是否已经存在
     * @param city
     * @return
     */
    List<City> findCity(City city);

    /**
     *
     * @Title: export
     * @Description: 导出excel
     * @author: 许超
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String,Object> map);

    /**
     *
     * @Title: cityImport
     * @Description: 导入excel
     * @author: 许超
     * @param is
     * @throws IOException
     */
    void cityImport(InputStream is) throws IOException;

    City findCityByName(String cityName);
}
