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
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int subProjectCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<SubProject> subProjectPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSubProjectName
     * @Description: ��ѯ���У��Զ���ȫ
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
