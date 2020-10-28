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
 * 历史流程批注管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/repairTask")
public class RepairTaskController {

    // 引入activiti自带的Service接口
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
     * 查询历史流程批注
     *
     * @param response
     * @param processInstanceId 流程ID
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
        // 改变顺序，按原顺序的反向顺序返回list
        Collections.reverse(commentList); //集合元素反转
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class,
                //时间格式转换
                new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 重定向审核处理页面
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
            if(repair.getState().equals("维修人员已完成维修")){
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
     * 待办流程分页查询
     *
     * @param response
     * @param page     当前页数
     * @param rows     每页显示页数
     * @param s_name   流程名称
     * @param userId   流程ID
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
        // 获取总记录数
        long total = 0;
        //有想法的话，可以去数据库观察  ACT_RU_TASK 的变化
        List<Task> taskList = null;
        if (s_name == null || s_name == "") {
            if (groupId.equals("ProjectSupervisor")) {
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else if (groupId.equals("OperationalManagers")) {
                // 获取总记录数
                total = taskService.createTaskQuery().taskCandidateGroup(groupId).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskCandidateGroup(groupId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else if (groupId.equals("Maintainer")) {
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            }else {
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            }
        } else {
            if (groupId.equals("ProjectSupervisor")) {
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else if (groupId.equals("OperationalManagers")) {
                // 获取总记录数
                total = taskService.createTaskQuery().taskCandidateGroup(groupId).processInstanceId(s_name).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskCandidateGroup(groupId).processInstanceId(s_name).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else if (groupId.equals("Maintainer")) {
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            }
        }
        //获取对于流程的问题单
        List<String> processIDs = new ArrayList<>();
        for (Task t : taskList) {//获取代办流程 id
            processIDs.add(t.getProcessInstanceId());
        }
        List<Repair> repairs = null;
        if (processIDs.size() > 0) {//代办中显示 问题单对应的一些字段
            repairs = repairService.selectTaskByProcessID(processIDs);
        }
        //这里需要使用一个工具类来转换一下主要是转成JSON格式
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
     * 查询当前流程图
     *
     * @return
     */
    @RequestMapping("/showCurrentView")
    public String showCurrentView(HttpServletResponse response, String taskId) {
        //视图
        ModelAndView mav = new ModelAndView();

        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        // 获取流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // 创建流程定义查询
                // 根据流程定义id查询
                .processDefinitionId(processDefinitionId)
                .singleResult();
        // 部署id
        mav.addObject("deploymentId", processDefinition.getDeploymentId());
        mav.addObject("diagramResourceName", processDefinition.getDiagramResourceName()); // 图片资源文件名称

        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)
                repositoryService.getProcessDefinition(processDefinitionId);
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 根据流程实例id获取流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        // 根据活动id获取活动实例
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(pi.getActivityId());
        //整理好View视图返回到显示页面
        mav.addObject("x", activityImpl.getX()); // x坐标
        mav.addObject("y", activityImpl.getY()); // y坐标
        mav.addObject("width", activityImpl.getWidth()); // 宽度
        mav.addObject("height", activityImpl.getHeight()); // 高度
        mav.setViewName("page/currentView");
        return null;
    }

    /**
     * 查询历史批注
     *
     * @param response
     * @param taskId   流程ID
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
            // 集合元素反转
            Collections.reverse(commentList);

            //日期格式转换
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        }
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 审批
     *
     * @param taskId   任务id
     * @param comment  批注信息
     * @param state    审核状态 1 通过 2 驳回
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/auditOperators")
    public String auditOperators(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                                 @RequestParam("state") Integer state, @RequestParam(value = "userId",required = false) String userId,
                                 HttpServletResponse response, Model model) throws Exception {
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //取得角色用户登入对象
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        //计算两个日期之间相隔的天数
        Date startDate = task.getCreateTime();
        Date endDate = new Date();
        int countDays = DateUtil.countDays(startDate, endDate);
        if (currentGroup.get(0).getId().equals("ProjectSupervisor")) {
            if (state == 1) {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                if(repair.getState().equals("审核中")){
                    // model.addAttribute("state",repair.getState());
                    repair.setState("主管项目负责人已通过");
                    // 保留字段
                    // repair.setRepairImageID(userId);
                    // 更新审核信息
                    repairService.updateRepair(repair);
                    variables.put("msg", "通过");
                    variables.put("days", countDays);
                    variables.put("wo", "派单中");
                    // 设置维修人员执行派工单
                    variables.put("Maintainer", userId);
                }
                else if(repair.getState().equals("维修人员未完成维修")){
                    // model.addAttribute("state",repair.getState());
                    repair.setState("主管项目负责人根据返单重新派工");
                    // 保留字段
                    // repair.setRepairImageID(userId);
                    // 更新审核信息
                    repairService.updateRepair(repair);
                    variables.put("msg", "通过");
                    variables.put("days", countDays);
                    variables.put("wo", "派单中");
                    // 设置维修人员执行派工单
                    variables.put("Maintainer", userId);
                }
                else if(repair.getState().equals("维修人员已完成维修")){
                    // model.addAttribute("state",repair.getState());
                    repair.setState("主管项目负责人已再次通过");
                    // 更新审核信息
                    repairService.updateRepair(repair);
                    variables.put("msg", "通过");
                    variables.put("days", countDays);
                    variables.put("wo", "派单完成");
                }
            } else {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                repair.setState("主管项目负责人未通过");
                // 更新审核信息
                repairService.updateRepair(repair);
                //设置驳回标志
                variables.put("msg", "未通过");
                variables.put("days", countDays);
            }
        }
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 审批
     *
     * @param taskId   任务id
     * @param comment  批注信息
     * @param state    审核状态 1 通过 2 驳回
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/auditProjectSupervisor")
    public String auditProjectSupervisor(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                                         @RequestParam("state") Integer state, WorkOrder workOrder, HttpServletResponse response) throws Exception {
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //取得角色用户登入对象
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
                repair.setState("维修人员已完成维修");
                // 更新审核信息
                repairService.updateRepair(repair);
                variables.put("finished", "完成");
                variables.put("flag", "一般修理");
                // 保留字段
                // variables.put("ProjectSupervisor",repair.getRepairImageID());
            } else {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                repair.setState("维修人员未完成维修");
                // 更新审核信息
                repairService.updateRepair(repair);
                variables.put("finished", "未完成");
                // variables.put("ProjectSupervisor",repair.getRepairImageID());
            }
        }
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 审批
     *
     * @param taskId   任务id
     * @param state    审核状态 1 已查阅
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/auditOperationalManagers")
    public String auditOperationalManagers(@RequestParam("taskId") String taskId, @RequestParam("state") Integer state, HttpServletResponse response) throws Exception {
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //取得角色用户登入对象
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        if (currentGroup.get(0).getId().equals("OperationalManagers")) {
            if (state == 1) {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                repair.setState("运营管理人员已查阅");
                // 更新审核信息
                repairService.updateRepair(repair);
                variables.put("look", "已查阅");
            } else {
                String repairID = (String) taskService.getVariable(taskId, "repairID");
                Repair repair = repairService.findById(repairID);
                repair.setState("运营管理人员未查阅");
                // 更新审核信息
                repairService.updateRepair(repair);
                //设置驳回标志
                variables.put("look", "未查阅");
            }
        }
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // 添加批注信息
        // taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询流程正常走完的历史流程表 :  act_hi_actinst
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
        //为什么要这样呢？因为程序首次运行进入后台时，
        //s_name必定是等于null的，如果直接这样填写进查询语句中就会出现  % null %这样就会导致查询结果有误
        PageInfo pageInfo = new PageInfo();
        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        //创建流程历史实例查询
        List<HistoricTaskInstance> histList = null;
        long histCount = 0;
        if (s_name == null || s_name == "") {
                if ("1".equals(type)) { //进行中流程
                    if (groupId.equals("ProjectSupervisor") || groupId.equals("Maintainer")) {
                    histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                    histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().count();
                    } else if (groupId.equals("OperationalManagers")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processUnfinished().count();
                    }else{//指定人去搜
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
                    }else{//指定人去搜
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().count();
                    }
                }
            } else {
                if ("1".equals(type)) {//进行中流程
                    if (groupId.equals("ProjectSupervisor") || groupId.equals("Maintainer")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().count();
                    } else if (groupId.equals("OperationalManagers")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processInstanceId(s_name).processUnfinished().count();
                    }else{//指定人去搜
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().count();
                    }
                } else {//指定人去搜
                    if (groupId.equals("ProjectSupervisor") || groupId.equals("Maintainer")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().count();
                    } else if (groupId.equals("OperationalManagers")) {
                        histList = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processInstanceId(s_name).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(groupId).processInstanceId(s_name).processFinished().count();
                    }else{//指定人去搜
                        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                        histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().count();
                    }
                }
            }
        // 进行降序排列 感觉时间
        Collections.sort(histList, new Comparator<HistoricTaskInstance>() {
            public int compare(HistoricTaskInstance o1, HistoricTaskInstance o2) {
                return o2.getStartTime().compareTo(o1.getStartTime());
            }
        });
        //获取对于流程的问题单
        List<String> processIDs = new ArrayList<>();
        for (HistoricTaskInstance t : histList) {//获取代办流程 id
            processIDs.add(t.getProcessInstanceId());
        }
        List<Repair> repairs = null;
        if (processIDs.size() > 0) {//代办中显示 问题单对应的一些字段
            repairs = repairService.selectTaskByProcessID(processIDs);
        }
        List<MyTaskInfo> taskList = new ArrayList<MyTaskInfo>();
        //这里递出没有用的字段，免得给前端页面做成加载压力
        for (HistoricTaskInstance hti : histList) {
            MyTaskInfo myTask = new MyTaskInfo();
            for (Repair repair : repairs) {
                if (repair.getProcessInstanceId().equals(hti.getProcessInstanceId())) {
                    myTask.setStr1(repair.getId());
                    myTask.setStr2(repair.getRepairType());//问题标题
                }
            }
            myTask.setId(hti.getId());
            myTask.setTaskID(hti.getProcessInstanceId());
            myTask.setName(hti.getName());
            myTask.setCreateTime(hti.getCreateTime());
            myTask.setEndTime(hti.getEndTime());
            taskList.add(myTask);
        }

        // 根据getProcessInstanceId去重
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
     * 根据任务id查询流程实例的具体执行过程
     *
     * @param taskId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/listAction")
    public String listAction(String taskId, HttpServletResponse response) throws Exception {
        List<HistoricTaskInstance> hti = historyService.createHistoricTaskInstanceQuery().taskId(taskId).list();
        String processInstanceId = hti.get(0).getProcessInstanceId(); // 获取流程实例id
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
