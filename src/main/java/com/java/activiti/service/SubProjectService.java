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
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<SubProject> subProjectPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int subProjectCount(Map<String, Object> map);

    /**
     *
     * @Title: findProjectName
     * @Description: like��ѯ��ɫ��
     * @param subProjectName ��ɫ��
     * @return
     */
    List<SubProject> findSubProjectName(String subProjectName);

    List<SubProject> searchAllSubProject();

    List<Park> findParkByCityID(String cityID);

    List<SubProject>  findSubProjectByPark(int parkID);
//
    /**
     * �޸���Ŀ
     * @param subProject
     * @return
     */
    int updateSubProject(SubProject subProject);

    /**
     * ������Ŀ
     * @param subProject
     * @return
     */
    int addSubProject(SubProject subProject);

    /**
     * ����ɾ����Ŀ
     * @param id
     * @return
     */
    int deleteSubProjectById(List<String> id);

    /**
     * �ж���Ŀ�Ƿ��Ѿ�����
     * @param subProject
     * @return
     */
    List<SubProject> findSubProject(SubProject subProject);

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
     * @Title: subProjectImport
     * @Description: ����excel
     * @author: ��
     * @param is
     * @throws IOException
     */
    void subProjectImport(InputStream is) throws IOException;

    SubProject findSubProjectByParkID(int parkID,String subProjectName);
}
