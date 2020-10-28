package com.java.activiti.service;

import com.java.activiti.model.MyTask;
import com.java.activiti.model.Equit;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface EquitService {

    List<Equit> equitPage(Map<String, Object> map);

    int equitCount(Map<String, Object> map);

    int addEquit(Equit equit);


    Equit findById(String id);

    int updateEquit(Equit equit);

    Equit getEquitByTaskId(String processInstanceId);
    List<MyTask> selectTaskByProcessID(List<String> map);
    void export(OutputStream os, Map<String, Object> map);
}
