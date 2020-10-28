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
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int projectCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Project> projectPage(Map<String, Object> map);

    /**
     *
     * @Title: selectRoleName
     * @Description: ��ѯ���У��Զ���ȫ
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
