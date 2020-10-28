package com.java.activiti.service;

import com.java.activiti.model.ExceptionInfo;

import java.util.List;
import java.util.Map;

public interface ExceptionLogService {

    public int addExceptionLog(ExceptionInfo exceptionInfo);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<ExceptionInfo> exceptionRecordPage(Map<String,Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int exceptionRecordCount(Map<String,Object> map);
}
