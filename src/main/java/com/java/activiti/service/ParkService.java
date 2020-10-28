package com.java.activiti.service;

import com.java.activiti.model.Park;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ParkService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Park> parkPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int parkCount(Map<String, Object> map);

    /**
     *
     * @Title: findCityName
     * @Description: like查询角色名
     * @param parkName 角色名
     * @return
     */
    List<Park> findParkName(String parkName);

    Park findParkByID(int parkID);

    List<Park> findALL();

    /**
     * 修改公园
     * @param park
     * @return
     */
    int updatePark(Park park);

    /**
     * 新增城市
     * @param park
     * @return
     */
    int addPark(Park park);

    /**
     * 批量删除公园
     * @param id
     * @return
     */
    int deleteParkById(List<String> id);
//
    /**
     * 判断公园是否已经存在
     * @param park
     * @return
     */
    List<Park> findPark(Park park);

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
     * @Title: parkImport
     * @Description: 导入excel
     * @author: 许超
     * @param is
     * @throws IOException
     */
    void parkImport(InputStream is) throws IOException;

    Park findParkByCityID(int cityID, String parkName);

}
