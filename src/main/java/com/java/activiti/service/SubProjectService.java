package com.java.activiti.service;

import com.java.activiti.model.Park;
import com.java.activiti.model.SubProject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface SubProjectService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<SubProject> subProjectPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int subProjectCount(Map<String, Object> map);

    /**
     *
     * @Title: findProjectName
     * @Description: like查询角色名
     * @param subProjectName 角色名
     * @return
     */
    List<SubProject> findSubProjectName(String subProjectName);

    List<SubProject> searchAllSubProject();

    List<Park> findParkByCityID(String cityID);

    List<SubProject>  findSubProjectByPark(int parkID);
//
    /**
     * 修改项目
     * @param subProject
     * @return
     */
    int updateSubProject(SubProject subProject);

    /**
     * 新增项目
     * @param subProject
     * @return
     */
    int addSubProject(SubProject subProject);

    /**
     * 批量删除项目
     * @param id
     * @return
     */
    int deleteSubProjectById(List<String> id);

    /**
     * 判断项目是否已经存在
     * @param subProject
     * @return
     */
    List<SubProject> findSubProject(SubProject subProject);

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
     * @Title: subProjectImport
     * @Description: 导入excel
     * @author: 许超
     * @param is
     * @throws IOException
     */
    void subProjectImport(InputStream is) throws IOException;

    SubProject findSubProjectByParkID(int parkID,String subProjectName);
}
