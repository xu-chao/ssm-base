package com.java.activiti.controller;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.model.*;
import com.java.activiti.service.RepairService;
import com.java.activiti.service.WorkOrderService;
import com.java.activiti.util.*;
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
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ��ʷ������ע����
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/repairTask")
public class RepairTaskController {

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
    private RepairService repairService;

    @Resource
    private WorkOrderService workOrderService;

    @Resource
    private HistoryService historyService;

    @Resource
    private GroupDao groupDao;

    /**
     * ��ѯ��ʷ������ע
     *
     * @param response
     * @param processInstanceId ����ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryCommentWithProcessInstanceId")
    public String listHistoryCommentWithProcessInstanceId(
            HttpServletResponse response, String processInstanceId) throws Exception {
        if (processInstanceId == null) {
            return null;
        }
        List<Comment> commentList = taskService
                .getProcessInstanceComments(processInstanceId);
        // �ı�˳�򣬰�ԭ˳��ķ���˳�򷵻�list
        Collections.reverse(commentList); //����Ԫ�ط�ת
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class,
                //ʱ���ʽת��
                new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * �ض�����˴���ҳ��
     *
     * @param taskId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/redirectPage")
    public String redirectPage(String taskId, HttpServletResponse response,@RequestParam("groupId") String groupId) throws Exception {
        //String repairID = (String) taskService.getVariable(taskId, "repairID");
        //Repair repair = repairService.findById(repairID);
        //model.addAttribute("state",repair.getState());
        TaskFormData formData = formService.getTaskFormData(taskId);
        String url = formData.getFormKey();
        if(groupId.equals("ProjectSupervisor")){
            String repairID = (String) taskService.getVariable(taskId, "repairID");
            Repair repair = repairService.findById(repairID);
            if(repair.getState().equals("ά����Ա�����ά��")){
                url = "todoFinished.jsp";
            }
        }
        //String pageUrl = "page/" + url.substring(0,url.lastIndexOf("."));
        System.out.println("*********************" + url + "*********************");
        JSONObject result = new JSONObject();
        result.put("url", url);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * �������̷�ҳ��ѯ
     *
     * @param response
     * @param page     ��ǰҳ��
     * @param rows     ÿҳ��ʾҳ��
     * @param s_name   ��������
     * @param userId   ����ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/taskPage")
    public String taskPage(HttpServletResponse response, String page, String rows, String s_name, String userId, String groupId) throws Exception {
        PageInfo pageInfo = new PageInfo();
        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageInfo.getPageSize());
        // ��ȡ�ܼ�¼��
        long total = 0;
        //���뷨�Ļ�������ȥ���ݿ�۲�  ACT_RU_TASK �ı仯
        List<Task> taskList = null;
        if (s_name == null || s_name == "") {
            if (groupId.equals("ProjectSupervisor")) {
                // ��ȡ�ܼ�¼��
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // ���ش���ҳ�Ľ������
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else if (groupId.equals("OperationalManagers")) {
                // ��ȡ�ܼ�¼��
                total = taskService.createTaskQuery().taskCandidateGroup(groupId).count();
                // ���ش���ҳ�Ľ������
                taskList = taskService.createTaskQuery().taskCandidateGroup(groupId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else if (groupId.equals("Maintainer")) {
                // ��ȡ�ܼ�¼��
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // ���ش���ҳ�Ľ������
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            }else {
                // ��ȡ�ܼ�¼��
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // ���ش���ҳ�Ľ������
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            }
        } else {
            if (groupId.equals("ProjectSupervisor")) {
                // ��ȡ�ܼ�¼��
                total = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).count();
                // ���ش���ҳ�Ľ������
                taskList = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else if (groupId.equals("OperationalManagers")) {
                // ��ȡ�ܼ�¼��
                total = taskService.createTaskQuery().taskCandidateGroup(groupId).processInstanceId(s_name).count();
                // ���ش���ҳ�Ľ������
                taskList = taskService.createTaskQuery().taskCandidateGroup(groupId).processInstanceId(s_name).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else if (groupId.equals("Maintainer")) {
                // ��ȡ�ܼ�¼��
                total = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).count();
                // ���ش���ҳ�Ľ������
                taskList = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            }
        }
        //��ȡ�������̵����ⵥ
        List<String> processIDs = new ArrayList<>();
        for (Task t : taskList) {//��ȡ�������� id
            processIDs.add(t.getProcessInstanceId());
        }
        List<Repair> repairs = null;
        if (processIDs.size() > 0) {//��������ʾ ���ⵥ��Ӧ��һЩ�ֶ�
            repairs = repairService.selectTaskByProcessID(processIDs);
        }
        //������Ҫʹ��һ����������ת��һ����Ҫ��ת��JSON��ʽ
        List<MyTaskInfo> MyTaskList = new ArrayList<MyTaskInfo>();
        for (Task t : taskList) {
            MyTaskInfo myTask = new MyTaskInfo();
            for (Repair repair : repairs) {
                if (repair.getProcessInstanceId().equals(t.getProcessInstanceId())) {
                    myTask.setStr1(repair.getId());
                    myTask.setStr2(repair.getRepairType());
                }
            }
            myTask.setId(t.getId());
            myTask.setTaskID(t.getProcessInstanceId());
            myTask.setName(t.getName());
            myTask.setCreateTime(t.getCreateTime());
            MyTaskList.add(myTask);
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(MyTaskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ��ѯ��ǰ����ͼ
     *
     * @return
     */
    @RequestMapping("/showCurrentView")
    public String showCurrentView(HttpServletResponse response, String taskId) {
        //��ͼ
        ModelAndView mav = new ModelAndView();

        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        // ��ȡ���̶���id
        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // �������̶����ѯ
                // �������̶���id��ѯ
                .processDefinitionId(processDefinitionId)
                .singleResult();
        // ����id
        mav.addObject("deploymentId", processDefinition.getDeploymentId());
        mav.addObject("diagramResourceName", processDefinition.getDiagramResourceName()); // ͼƬ��Դ�ļ�����

        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)
                repositoryService.getProcessDefinition(processDefinitionId);
        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // ��������ʵ��id��ȡ����ʵ��
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        // ���ݻid��ȡ�ʵ��
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(pi.getActivityId());
        //�����View��ͼ���ص���ʾҳ��
        mav.addObject("x", activityImpl.getX()); // x����
        mav.addObject("y", activityImpl.getY()); // y����
        mav.addObject("width", activityImpl.getWidth()); // ���
        mav.addObject("height", activityImpl.getHeight()); // �߶�
        mav.setViewName("page/currentView");
        return null;
    }

    /**
     * ��ѯ��ʷ��ע
     *
     * @param response
     * @param taskId   ����ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryComment")
    public String listHistoryComment(HttpServletResponse response, String taskId) throws Exception {
        if (taskId == null) {
            taskId = "";
            return null;
        }
        HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .singleResult();
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<Comment> commentList = null;
        if (hti != null) {
            commentList = taskService.getProcessInstanceComments(hti.getProcessInstanceId());
            // ����Ԫ�ط�ת
            Collections.reverse(commentList);

            //���ڸ�ʽת��
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        }
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

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
    @RequestMapping("/auditOperators")
    public String auditOperators(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                                 @RequestParam("state") Integer state, @RequestParam(value = "userId",required = false) String userId,
                                 HttpServletResponse response, Model model) throws Exception {
        //���ȸ���ID��ѯ����
        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //ȡ�ý�ɫ�û��������
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        //������������֮�����������
        Date startDate = task.getCreateTime();
        Date endDate = new Date();
        int countDays = DateUtil.countDays(startDate, endDate);
        if (currentGroup.get(0).getId().equals("ProjectSupervisor")) {
            if (state == 1) {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                if(repair.getState().equals("�����")){
                    // model.addAttribute("state",repair.getState());
                    repair.setState("������Ŀ��������ͨ��");
                    // �����ֶ�
                    // repair.setRepairImageID(userId);
                    // ���������Ϣ
                    repairService.updateRepair(repair);
                    variables.put("msg", "ͨ��");
                    variables.put("days", countDays);
                    variables.put("wo", "�ɵ���");
                    // ����ά����Աִ���ɹ���
                    variables.put("Maintainer", userId);
                }
                else if(repair.getState().equals("ά����Աδ���ά��")){
                    // model.addAttribute("state",repair.getState());
                    repair.setState("������Ŀ�����˸��ݷ��������ɹ�");
                    // �����ֶ�
                    // repair.setRepairImageID(userId);
                    // ���������Ϣ
                    repairService.updateRepair(repair);
                    variables.put("msg", "ͨ��");
                    variables.put("days", countDays);
                    variables.put("wo", "�ɵ���");
                    // ����ά����Աִ���ɹ���
                    variables.put("Maintainer", userId);
                }
                else if(repair.getState().equals("ά����Ա�����ά��")){
                    // model.addAttribute("state",repair.getState());
                    repair.setState("������Ŀ���������ٴ�ͨ��");
                    // ���������Ϣ
                    repairService.updateRepair(repair);
                    variables.put("msg", "ͨ��");
                    variables.put("days", countDays);
                    variables.put("wo", "�ɵ����");
                }
            } else {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                repair.setState("������Ŀ������δͨ��");
                // ���������Ϣ
                repairService.updateRepair(repair);
                //���ò��ر�־
                variables.put("msg", "δͨ��");
                variables.put("days", countDays);
            }
        }
        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // �����û�id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // �����ע��Ϣ
        taskService.addComment(taskId, processInstanceId, comment);
        // �������
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

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
    @RequestMapping("/auditProjectSupervisor")
    public String auditProjectSupervisor(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                                         @RequestParam("state") Integer state, WorkOrder workOrder, HttpServletResponse response) throws Exception {
        //���ȸ���ID��ѯ����
        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //ȡ�ý�ɫ�û��������
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());

        if (currentGroup.get(0).getId().equals("Maintainer")) {
            if (state == 1) {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                workOrder.setRepair(repair);
                workOrder.setId(UuidUtil.uuidUtil());
                workOrder.setUser(currentUser);
                workOrder.setRepairedPerson(currentUser.getFirstName() + currentUser.getLastName());
                workOrder.setProcessInstanceId(taskId);
                workOrder.setWoEndDate(new Date());
                int result = workOrderService.addWorkOrder(workOrder);
                repair.setState("ά����Ա�����ά��");
                // ���������Ϣ
                repairService.updateRepair(repair);
                variables.put("finished", "���");
                variables.put("flag", "һ������");
                // �����ֶ�
                // variables.put("ProjectSupervisor",repair.getRepairImageID());
            } else {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                repair.setState("ά����Աδ���ά��");
                // ���������Ϣ
                repairService.updateRepair(repair);
                variables.put("finished", "δ���");
                // variables.put("ProjectSupervisor",repair.getRepairImageID());
            }
        }
        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // �����û�id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // �����ע��Ϣ
        taskService.addComment(taskId, processInstanceId, comment);
        // �������
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ����
     *
     * @param taskId   ����id
     * @param state    ���״̬ 1 �Ѳ���
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/auditOperationalManagers")
    public String auditOperationalManagers(@RequestParam("taskId") String taskId, @RequestParam("state") Integer state, HttpServletResponse response) throws Exception {
        //���ȸ���ID��ѯ����
        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //ȡ�ý�ɫ�û��������
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        if (currentGroup.get(0).getId().equals("OperationalManagers")) {
            if (state == 1) {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                repair.setState("��Ӫ������Ա�Ѳ���");
                // ���������Ϣ
                repairService.updateRepair(repair);
                variables.put("look", "�Ѳ���");
            } else {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                repair.setState("��Ӫ������Աδ����");
                // ���������Ϣ
                repairService.updateRepair(repair);
                //���ò��ر�־
                variables.put("look", "δ����");
            }
        }
        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // �����û�id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // �����ע��Ϣ
        // taskService.addComment(taskId, processInstanceId, comment);
        // �������
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ��ѯ���������������ʷ���̱� :  act_hi_actinst
     *
     * @param response
     * @param rows
     * @param page
     * @param s_name
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/finishedList")
    public String finishedList(HttpServletResponse response, String rows, String page,
                               String s_name, String groupId, String userId,String type) throws Exception {
        //ΪʲôҪ�����أ���Ϊ�����״����н����̨ʱ��
        //s_name�ض��ǵ���null�ģ����ֱ��������д����ѯ����оͻ����  % null %�����ͻᵼ�²�ѯ�������
        PageInfo pageInfo = new PageInfo();
        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        //����������ʷʵ����ѯ
        List<HistoricTaskInstance> histList = null;
        long histCount = 0;
        if (s_name == null || s_name == "") {
                if ("1".equals(type)) { //����������
                    if (groupId.equals("ProjectSupervisor") || groupId.equals("Maintainer")) {
                    histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                    histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().count();
                    } else if (groupId.equals("OperationalManagers")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processUnfinished().count();
                    }else{//ָ����ȥ��
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().count();
                    }
                } else {
                    if (groupId.equals("ProjectSupervisor") || groupId.equals("Maintainer")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().count();
                    } else if (groupId.equals("OperationalManagers")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processFinished().count();
                    }else{//ָ����ȥ��
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().count();
                    }
                }
            } else {
                if ("1".equals(type)) {//����������
                    if (groupId.equals("ProjectSupervisor") || groupId.equals("Maintainer")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().count();
                    } else if (groupId.equals("OperationalManagers")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processInstanceId(s_name).processUnfinished().count();
                    }else{//ָ����ȥ��
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().count();
                    }
                } else {//ָ����ȥ��
                    if (groupId.equals("ProjectSupervisor") || groupId.equals("Maintainer")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().count();
                    } else if (groupId.equals("OperationalManagers")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processInstanceId(s_name).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processInstanceId(s_name).processFinished().count();
                    }else{//ָ����ȥ��
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().count();
                    }
                }
            }
        // ���н������� �о�ʱ��
        Collections.sort(histList, new Comparator<HistoricTaskInstance>() {
            public int compare(HistoricTaskInstance o1, HistoricTaskInstance o2) {
                return o2.getStartTime().compareTo(o1.getStartTime());
            }
        });
        //��ȡ�������̵����ⵥ
        List<String> processIDs = new ArrayList<>();
        for (HistoricTaskInstance t : histList) {//��ȡ�������� id
            processIDs.add(t.getProcessInstanceId());
        }
        List<Repair> repairs = null;
        if (processIDs.size() > 0) {//��������ʾ ���ⵥ��Ӧ��һЩ�ֶ�
            repairs = repairService.selectTaskByProcessID(processIDs);
        }
        List<MyTaskInfo> taskList = new ArrayList<MyTaskInfo>();
        //����ݳ�û���õ��ֶΣ���ø�ǰ��ҳ�����ɼ���ѹ��
        for (HistoricTaskInstance hti : histList) {
            MyTaskInfo myTask = new MyTaskInfo();
            for (Repair repair : repairs) {
                if (repair.getProcessInstanceId().equals(hti.getProcessInstanceId())) {
                    myTask.setStr1(repair.getId());
                    myTask.setStr2(repair.getRepairType());//�������
                }
            }
            myTask.setId(hti.getId());
            myTask.setTaskID(hti.getProcessInstanceId());
            myTask.setName(hti.getName());
            myTask.setCreateTime(hti.getCreateTime());
            myTask.setEndTime(hti.getEndTime());
            taskList.add(myTask);
        }

        // ����getProcessInstanceIdȥ��
        List<MyTaskInfo> unique = taskList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(
                        MyTaskInfo::getTaskID
                ))),ArrayList::new
        ));
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(taskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", histCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ��������id��ѯ����ʵ���ľ���ִ�й���
     *
     * @param taskId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/listAction")
    public String listAction(String taskId, HttpServletResponse response) throws Exception {
        List<HistoricTaskInstance> hti = historyService.createHistoricTaskInstanceQuery().taskId(taskId).list();
        String processInstanceId = hti.get(0).getProcessInstanceId(); // ��ȡ����ʵ��id
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(haiList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }
}
