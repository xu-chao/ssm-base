package com.java.activiti.service.impl;

import com.java.activiti.dao.WorkOrderDao;
import com.java.activiti.model.WorkOrder;
import com.java.activiti.service.WorkOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("workOrderService")
public class WorkOrderServiceImpl implements WorkOrderService {

    @Resource
    private WorkOrderDao workOrderDao;

    public List<WorkOrder> workOrderPage(Map<String,Object> map){
        return workOrderDao.workOrderPage(map);
    }

    public int workOrderCount(Map<String,Object> map){
        return workOrderDao.workOrderCount(map);
    }

    public List<WorkOrder> workOrderThroughPage(Map<String,Object> map){
        return workOrderDao.workOrderThroughPage(map);
    }

    public int workOrderThroughCount(Map<String,Object> map){
        return workOrderDao.workOrderThroughCount(map);
    }

    public int addWorkOrder(WorkOrder workOrder){
        return workOrderDao.addWorkOrder(workOrder);
    }
//
//    public Repair findById(String id){
//        return repairDao.findById(id);
//    }
//
//    public int updateRepair(Repair repair){
//        return repairDao.updateRepair(repair);
//    }
//
//    public Repair getRepairByTaskId(String processInstanceId){
//        return repairDao.getRepairByTaskId(processInstanceId);
//    }
}
