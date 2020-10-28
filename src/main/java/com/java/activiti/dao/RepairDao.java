package com.java.activiti.dao;

import com.java.activiti.model.Repair;
import com.java.activiti.model.WorkOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RepairDao {

    List<Repair> repairPage(Map<String,Object> map);

    int repairCount(Map<String,Object> map);

    List<Repair> repairThroughPage(Map<String,Object> map);

    int repairThroughCount(Map<String,Object> map);

    int addRepair(Repair repair);

    Repair findById(String id);

    int updateRepair(Repair repair);

    Repair getRepairByTaskId(String processInstanceId);
    WorkOrder getWorkOrderByRepairID(String processInstanceId);
/**
 * @author      LIUHD
 * ���� processInstanceId
 * @description ������ѯ  BY ����ID
 * @date        2019/12/31 15:21
 * @Version     1.0
 */
    List<Repair> selectTaskByProcessID(List<String> processInstanceId);

}
