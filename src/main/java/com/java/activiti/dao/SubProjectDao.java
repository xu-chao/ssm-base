package com.java.activiti.dao;

import com.java.activiti.model.Park;
import com.java.activiti.model.SubProject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SubProjectDao {

    SubProject findById(int subProjectID);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int subProjectCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<SubProject> subProjectPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSubProjectName
     * @Description: 查询城市，自动补全
     * @param subProjectName
     * @return
     */
    List<SubProject> selectSubProjectName(@Param("subProjectName") String subProjectName);

    List<SubProject> searchAllSubProject();

    int addSubProject(SubProject subProject);

    int updateSubProject(SubProject subProject);

    int deleteSubProjectById(List<String> id);

    List<SubProject> findSubProject(SubProject subProject);

    List<Park> findParkByCityID(String cityID);

    List<SubProject> selectSubProjectBySubProjectID(int subProjectID);

    SubProject findSubProjectByParkID(@Param("parkID") int parkID,@Param("subProjectName") String subProjectName);

    List<SubProject> findSubProjectByPark(int parkID);
}
