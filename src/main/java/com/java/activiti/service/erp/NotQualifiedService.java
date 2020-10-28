package com.java.activiti.service.erp;

import com.java.activiti.model.erp.NotQualified;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface NotQualifiedService {

    List<NotQualified> notQualifiedPage(Map<String, Object> map);

    int notQualifiedCount(Map<String, Object> map);

    int addNotQualified(NotQualified notQualified);

    NotQualified findById(String id);

    int updateNotQualified(NotQualified notQualified);

    void export(OutputStream os, Map<String, Object> map);
}
