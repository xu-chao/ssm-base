package com.java.activiti.controller;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.model.*;
import com.java.activiti.service.EquitService;
import com.java.activiti.service.WorkOrderService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * ��ʷ������ע����
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/equitTask")
public class EquitTaskController {

    // ����activiti�Դ���Service�ӿ�
    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private FormService formService;

    @Resource
    private EquitService equitService;

    @Resource
    private WorkOrderService workOrderService;

    @Resource
    private HistoryService historyService;

    @Resource
    private GroupDao groupDao;

    /**
     * ����
     *
     * @param taskId   ����id
     * @param comment  ��ע��Ϣ
     * @param state    ���״̬ 1 ͨ�� 2 ����
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/equitOperators")
    public String equitOperators(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                                 @RequestParam("state") Integer state, @RequestParam(value = "userId",required = false) String userId,
//                                 @RequestParam(value = "fileAfterID", required = false) String fileAfterID,
                                 HttpServletResponse response) throws Exception {
        //���ȸ���ID��ѯ����
        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //ȡ�ý�ɫ�û��������
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        String equitID = (String) taskService.getVariable(taskId, "id");
        Equit equit = equitService.findById(equitID);


        if (currentGroup.get(0).getId().equals("factoryPurchase")) {
            if (state == 1) {
                equit.setState("�ɹ���Ա����ͨ��");
                variables.put("msg", "ͨ��");
                // ���������Ϣ
                // ����-��һ��������Ա
                variables.put("factoryQuality", userId);
            } else {
                equit.setState("�ɹ���Ա����δͨ��");
                // ���������Ϣ
                variables.put("msg", "��ͨ��");
                equit.setEndDate(new Date());
            }


        }else if (currentGroup.get(0).getId().equals("factoryQuality")){
            if (state == 1) {
                variables.put("msg", "ͨ��");
                equit.setState("�ʼ���Ա����ͨ��");
                equit.setEndDate(new Date());
                // ���������Ϣ
            } else {
                equit.setState("�ʼ���Ա����δͨ��");
                // ���������Ϣ
                variables.put("msg", "��ͨ��");
                equit.setEndDate(new Date());
            }
        }
        equitService.updateEquit(equit);

        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // �����û�id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).

                getName() + "]");
        // �����ע��Ϣ
        taskService.addComment(taskId, processInstanceId, comment);
        // �������
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
}
