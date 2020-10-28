package com.java.activiti.dao;

import com.java.activiti.model.ExceptionInfo;


import java.util.List;
import java.util.Map;

public interface ExceptionDao {
    public int addException(ExceptionInfo exceptionInfo);
    public int exceptionRecordCount(Map<String,Object> map);
    public List<ExceptionInfo> exceptionRecordPage(Map<String,Object> map);
}
