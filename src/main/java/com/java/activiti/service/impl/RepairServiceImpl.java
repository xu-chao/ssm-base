package com.java.activiti.service.impl;

import com.java.activiti.dao.RepairDao;
import com.java.activiti.model.Repair;
import com.java.activiti.model.WorkOrder;
import com.java.activiti.service.RepairService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

@Service("repairService")
public class RepairServiceImpl implements RepairService {

    @Resource
    private RepairDao repairDao;

    public List<Repair> repairPage(Map<String,Object> map){
        return repairDao.repairPage(map);
    }

    public int repairCount(Map<String,Object> map){
        return repairDao.repairCount(map);
    }

    public List<Repair> repairThroughPage(Map<String,Object> map){
        return repairDao.repairThroughPage(map);
    }

    public int repairThroughCount(Map<String,Object> map){
        return repairDao.repairThroughCount(map);
    }

    public int addRepair(Repair repair){
        return repairDao.addRepair(repair);
    }

    public Repair findById(String id){
        return repairDao.findById(id);
    }

    public int updateRepair(Repair repair){
        return repairDao.updateRepair(repair);
    }

    public Repair getRepairByTaskId(String processInstanceId){
        return repairDao.getRepairByTaskId(processInstanceId);
    }
    public WorkOrder getWorkOrderByRepairID(String processInstanceId){
        return repairDao.getWorkOrderByRepairID(processInstanceId);
    }
    @Override
    public  List<Repair> selectTaskByProcessID(List<String> maps) {
        return repairDao.selectTaskByProcessID(maps);
    }
}
