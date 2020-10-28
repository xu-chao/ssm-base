package com.java.activiti.listener;

import com.java.activiti.model.Repair;
import com.java.activiti.service.RepairService;
import com.java.activiti.util.DateUtil;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class Flow2TakeListener implements ExecutionListener {

    private static final long serialVersionUID = 1L;

    @Resource
    private TaskService taskService;

    @Resource
    private RepairService repairService;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        //获取超时的流程实例ID
        String processInstanceId = delegateExecution.getProcessInstanceId();
        //获取超时申故单
        Repair repair = repairService.getRepairByTaskId(processInstanceId);
        //获取流程历史数据
        //HistoricProcessInstance hisProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取业务主键ID
        String businessKey = delegateExecution.getProcessBusinessKey();

        Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();

        delegateExecution.setVariable("look","已查阅");
    }

}
