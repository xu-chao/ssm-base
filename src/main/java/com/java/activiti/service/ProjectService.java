package com.java.activiti.service;

import com.java.activiti.model.Park;
import com.java.activiti.model.Project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ProjectService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Project> projectPage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int projectCount(Map<String, Object> map);

    /**
     *
     * @Title: findProjectName
     * @Description: like查询角色名
     * @param projectName 角色名
     * @return
     */
    List<Project> findProjectName(String projectName);
    List<Project> searchAllProject();

    List<Park>  findParkByCityID(String cityID);

    List<Project>  findProjectByParkID(String parkID);
//
    /**
     * 修改项目
     * @param project
     * @return
     */
    int updateProject(Project project);

    /**
     * 新增城市
     * @param project
     * @return
     */
    int addProject(Project project);

    /**
     * 批量删除城市
     * @param id
     * @return
     */
    int deleteProjectById(List<String> id);

    /**
     * 判断城市是否已经存在
     * @param project
     * @return
     */
    List<Project> findProject(Project project);

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
     * @Title: projectImport
     * @Description: 导入excel
     * @author: 许超
     * @param is
     * @throws IOException
     */
    void projectImport(InputStream is) throws IOException;
}
