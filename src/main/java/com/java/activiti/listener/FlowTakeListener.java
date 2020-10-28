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
        //��ȡ��ʱ������ʵ��ID
        String processInstanceId = delegateExecution.getProcessInstanceId();
        //��ȡ��ʱ��ʵ�
        Repair repair = repairService.getRepairByTaskId(processInstanceId);
        //��ȡ������ʷ����
        //HistoricProcessInstance hisProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //��ȡҵ������ID
        String businessKey = delegateExecution.getProcessBusinessKey();

        Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();

        //������������֮�����������
        Date startDate = task.getCreateTime();
        Date endDate = new Date();
        int countDays = DateUtil.countDays(startDate, endDate);
        if(repair.getState() == "ά����Ա��δ���ά��" || repair.getState() == "�����"){
            //����������
            delegateExecution.setVariable("msg","ͨ��");
            delegateExecution.setVariable("days",countDays);
            delegateExecution.setVariable("userId", repair.getRepairImageID());
            delegateExecution.setVariable("wo","�ɵ���");
        }else if(repair.getState() == "ά����Ա�����ά��"){
            delegateExecution.setVariable("msg","ͨ��");
            delegateExecution.setVariable("days",countDays);
            delegateExecution.setVariable("wo","�ɵ����");
        }
    }

}
