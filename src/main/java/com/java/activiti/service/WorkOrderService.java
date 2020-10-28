package com.java.activiti.service;

import com.java.activiti.model.WorkOrder;

import java.util.List;
import java.util.Map;

public interface WorkOrderService {

    List<WorkOrder> workOrderPage(Map<String, Object> map);

    int workOrderCount(Map<String, Object> map);

    List<WorkOrder> workOrderThroughPage(Map<String, Object> map);

    int workOrderThroughCount(Map<String, Object> map);

    int addWorkOrder(WorkOrder workOrder);
//
//    Repair findById(String id);
//
//    int updateRepair(Repair repair);
//
//    Repair getRepairByTaskId(String processInstanceId);
}
