package com.java.activiti.service;

import com.java.activiti.exception.SystemLogServiceException;
import com.java.activiti.model.AccessRecord;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface AccessRecordService {
    String ACCESS_TYPE_LOGIN = "����";
    String ACCESS_TYPE_LOGOUT = "�ǳ�";

    /**
     * �����û�����ǳ���¼
     *
     * @param userID     �û�ID
     * @param userName   �û���
     * @param accessIP   ��½IP
     * @param accessType ��¼����
     */
    void insertAccessRecord(String userID, String userName, String accessIP, String accessType) throws SystemLogServiceException;

    /**
     * ��ѯָ���û�ID����¼���ͻ����ڷ�Χ�ĵ���ǳ���¼
     *
     * @param userID       �û�ID
     * @param accessType   ��¼����
     * @param startDateStr ��¼��ʼ����
     * @param endDateStr   ��¼��������
     * @return ����һ��Map�� ���м�ֵΪ data ��ֵΪ���з��������ļ�¼�� ����ֵΪ total ��ֵΪ���������ļ�¼������
     */
    Map<String, Object> selectAccessRecord(String userID, String accessType, String startDateStr, String endDateStr) throws SystemLogServiceException;

    /**
     * ��ҳ��ѯָ���û�ID����¼���ͻ����ڷ�Χ�ĵ���ǳ���¼
     *
     * @param userID       �û�ID
     * @param accessType   ��¼����
     * @param startDateStr ��¼��ʼ����
     * @param endDateStr   ��¼��������
     * @param offset       ��ҳƫ��ֵ
     * @param limit        ��ҳ��С
     * @return ����һ��Map�� ���м�ֵΪ data ��ֵΪ���з��������ļ�¼�� ����ֵΪ total ��ֵΪ���������ļ�¼������
     */
    Map<String, Object> selectAccessRecord(String userID, String accessType, String startDateStr, String endDateStr, int offset, int limit) throws SystemLogServiceException;

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<AccessRecord> accessRecordPage(Map<String,Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int accessRecordCount(Map<String,Object> map);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: ��
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String,Object> map);
}
