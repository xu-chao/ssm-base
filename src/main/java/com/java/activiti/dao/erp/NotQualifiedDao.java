package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.NotQualified;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NotQualifiedDao {

    List<NotQualified> notQualifiedPage(Map<String, Object> map);

    int notQualifiedCount(Map<String, Object> map);

    int addNotQualified(NotQualified notQualified);

    NotQualified findById(String id);

    int updateNotQualified(NotQualified notQualified);
}
