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
public class FlowTakeListener implements ExecutionListener {

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

        //计算两个日期之间相隔的天数
        Date startDate = task.getCreateTime();
        Date endDate = new Date();
        int countDays = DateUtil.countDays(startDate, endDate);
        if(repair.getState() == "维修人员已未完成维修" || repair.getState() == "审核中"){
            //设置审批人
            delegateExecution.setVariable("msg","通过");
            delegateExecution.setVariable("days",countDays);
            delegateExecution.setVariable("userId", repair.getRepairImageID());
            delegateExecution.setVariable("wo","派单中");
        }else if(repair.getState() == "维修人员已完成维修"){
            delegateExecution.setVariable("msg","通过");
            delegateExecution.setVariable("days",countDays);
            delegateExecution.setVariable("wo","派单完成");
        }
    }

}
