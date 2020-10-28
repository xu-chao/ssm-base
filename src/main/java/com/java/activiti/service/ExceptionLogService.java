package com.java.activiti.service;

import com.java.activiti.model.ExceptionInfo;

import java.util.List;
import java.util.Map;

public interface ExceptionLogService {

    public int addExceptionLog(ExceptionInfo exceptionInfo);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<ExceptionInfo> exceptionRecordPage(Map<String,Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int exceptionRecordCount(Map<String,Object> map);
}
