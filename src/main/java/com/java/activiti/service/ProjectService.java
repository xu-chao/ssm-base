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
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Project> projectPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int projectCount(Map<String, Object> map);

    /**
     *
     * @Title: findProjectName
     * @Description: like��ѯ��ɫ��
     * @param projectName ��ɫ��
     * @return
     */
    List<Project> findProjectName(String projectName);
    List<Project> searchAllProject();

    List<Park>  findParkByCityID(String cityID);

    List<Project>  findProjectByParkID(String parkID);
//
    /**
     * �޸���Ŀ
     * @param project
     * @return
     */
    int updateProject(Project project);

    /**
     * ��������
     * @param project
     * @return
     */
    int addProject(Project project);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteProjectById(List<String> id);

    /**
     * �жϳ����Ƿ��Ѿ�����
     * @param project
     * @return
     */
    List<Project> findProject(Project project);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: ��
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: projectImport
     * @Description: ����excel
     * @author: ��
     * @param is
     * @throws IOException
     */
    void projectImport(InputStream is) throws IOException;
}
