package com.java.activiti.service.impl;

import com.java.activiti.dao.EquitDao;
import com.java.activiti.model.Equit;
import com.java.activiti.model.MyTask;
import com.java.activiti.service.EquitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
@Service("equitService")
public class EquitServiceImpl implements EquitService {
    @Resource
    private EquitDao equitDao;
    @Override
    public List<Equit> equitPage(Map<String, Object> map) {
        return equitDao.equitPage(map);
    }

    @Override
    public int equitCount(Map<String, Object> map) {
        return equitDao.equitCount(map);
    }

    @Override
    public int addEquit(Equit equit) {
        return equitDao.addEquit(equit);
    }

    @Override
    public Equit findById(String id) {
        return equitDao.findById(id);
    }

    @Override
    public int updateEquit(Equit equit) {
        return equitDao.updateEquit(equit);
    }

    @Override
    public Equit getEquitByTaskId(String processInstanceId) {
        return equitDao.getEquitByTaskId(processInstanceId);
    }

    @Override
    public List<MyTask> selectTaskByProcessID(List<String> map) {
        return null;
    }

    @Override
    public void export(OutputStream os, Map<String, Object> map) {

    }
}
