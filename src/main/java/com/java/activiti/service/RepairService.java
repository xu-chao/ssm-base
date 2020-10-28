package com.java.activiti.service;

import com.java.activiti.model.Repair;
import com.java.activiti.model.WorkOrder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RepairService {

    List<Repair> repairPage(Map<String,Object> map);

    int repairCount(Map<String,Object> map);

    List<Repair> repairThroughPage(Map<String,Object> map);

    int repairThroughCount(Map<String,Object> map);

    int addRepair(Repair repair);

    Repair findById(String id);

    int updateRepair(Repair repair);

    Repair getRepairByTaskId(String processInstanceId);

    WorkOrder getWorkOrderByRepairID(String repairID);
    /**
     * @author      LIUHD
     * 参数 map
     * @description 根据 processInstanceId 流程ID 批量查询need单号
     * @date        2019/12/30 17:25
     * @Version     1.0
     */
    List<Repair> selectTaskByProcessID(List<String> maps);
}
