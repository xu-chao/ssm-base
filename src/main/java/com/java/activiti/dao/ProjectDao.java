package com.java.activiti.dao;

import com.java.activiti.model.Park;
import com.java.activiti.model.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectDao {

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int projectCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Project> projectPage(Map<String, Object> map);

    /**
     *
     * @Title: selectRoleName
     * @Description: 查询城市，自动补全
     * @param projectName
     * @return
     */
    List<Project> selectProjectName(@Param("projectName") String projectName);

    List<Project> searchAllProject();

    int addProject(Project project);

    int updateProject(Project project);

    int deleteProjectById(List<String> id);

    List<Project> findProject(Project project);

    List<Park> findParkByCityID(String cityID);
    List<Project> findProjectByParkID(String parkID);

    List<Project> selectProjectByProjectID(int projectID);

    Project findById (int projectID);
}
