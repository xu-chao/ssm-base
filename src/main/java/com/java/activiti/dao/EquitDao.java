package com.java.activiti.dao;

import com.java.activiti.model.MyTask;
import com.java.activiti.model.Equit;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EquitDao {

    List<Equit> equitPage(Map<String, Object> map);

    int equitCount(Map<String, Object> map);

    int addEquit(Equit equit);

    Equit findById(String id);

    int updateEquit(Equit equit);

    Equit getEquitByTaskId(String processInstanceId);

    List<MyTask> selectTaskByProcessID(List<String> processInstanceId);
}
