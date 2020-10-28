package com.java.activiti.controller.question;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.model.*;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.model.question.QuestionTask;
import com.java.activiti.service.question.QuestionService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.StringUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 历史流程批注管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/questionTask")
public class QuestionTaskController {

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
    private QuestionService questionService;

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
        jsonConfig.registerJsonValueProcessor(Date.class,
                //时间格式转换
                new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
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
    public String redirectPage(String taskId, HttpServletResponse response) throws Exception {
        TaskFormData formData = formService.getTaskFormData(taskId);
        String url = formData.getFormKey();
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
            if (groupId.equals("QuestionAdviser") || groupId.equals("QuestionExaminer") || groupId.equals("QuestionScene")) {
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            } else {
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
            }
        } else {
            if (groupId.equals("QuestionAdviser") || groupId.equals("QuestionExaminer") || groupId.equals("QuestionScene")) {
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
        List<QuestionInfo> questions = null;
        if (processIDs.size() > 0) {//代办中显示 问题单对应的一些字段
            questions = questionService.selectTaskByProcessID(processIDs);
        }

        //这里需要使用一个工具类来转换一下主要是转成JSON格式
        List<MyTask> MyTaskList = new ArrayList<MyTask>();
        for (Task t : taskList) {
            MyTask myTask = new MyTask();
            for (QuestionInfo question : questions) {
                if (question.getProcessInstanceId().equals(t.getProcessInstanceId())) {
                    myTask.setName(question.getProblemTitle());//问题标题
                }
            }
            myTask.setId(t.getId());
            myTask.setTaskID(t.getProcessInstanceId());
//            myTask.setName(t.getName());
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
     * table table-border table-bordered table-striped
     * 查询申请详细信息
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getQuestionById")
    public String getQuestionById(HttpServletResponse response, String id) throws Exception {
        //先根据流程ID查询
        QuestionInfo question = questionService.findById(id);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "<table  width='100%' class=\"gridtable\"><tr>" +
                "<td>" + "单号: " + question.getQuestionID() + "</td>" + "<td>" + "工程名称: " + question.getPark().getParkName() + "</td>" + "<td>" + "项目名称: " + question.getProject().getProjectName() + "</td>" + "<td>" + "专业名称: " + question.getSubjectID() + "</td>" + "<td>" + "问题标题: " + question.getProblemTitle() + "</td>" + "<td>" + "提交人: " + question.getUser().getAllName() + "</td>" + "<td>" + "提交时间: " + ft.format(question.getQuestionDate()) + "</td>" + "</tr>" +
                "<tr>" + "<td  colspan=\"9\">" + "问题描述: " + question.getProblemText() + "</td>" + "</tr>" +
                "</table>";
        ResponseUtil.write(response, str);
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
                                 @RequestParam("state") Integer state, @RequestParam(value = "userId", required = false) String userId,
                                 @RequestParam(value = "fileAfterID", required = false) String fileAfterID,
                                 HttpServletResponse response) throws Exception {
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //取得角色用户登入对象
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        String questionID = (String) taskService.getVariable(taskId, "questionID");
        QuestionInfo question = questionService.findById(questionID);
//        if (fileAfterID != null || "".equals(fileAfterID)) {
        if (StringUtil.isNotEmpty(fileAfterID)) {
            question.setFileAfterID(fileAfterID);
        }
        if (currentGroup.get(0).getId().equals("QuestionAdviser")) {
            if (state == 1) {
                question.setState("问题建议人审批已通过");
                // 更新审核信息
            } else {
                question.setState("问题建议人审批未通过");
                // 更新审核信息
            }
            question.setStateID(1);//审核中
            // 设置-维修人员执行派工单
            variables.put("QuestionExaminer", userId);
        } else if (currentGroup.get(0).getId().equals("QuestionExaminer")) {
            if (state == 1) {
                    question.setState("审批人审批已通过");
                    variables.put("flag1", "1");
            } else {
                    question.setState("审批人审批驳回");
                variables.put("flag1", "2");
            }
            question.setStateID(1);//审核中
        }else if(currentGroup.get(0).getId().equals("QuestionScene")){
            if(state == 1){
                question.setState("提交人审批通过");
                variables.put("flag2", "1");
                question.setStateID(2);//结束
            }else{
                question.setState("提交人审批不通过");
                variables.put("flag2", "2");
                question.setStateID(1);//审核中
            }
        }

        questionService.updateQuestion(question);
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).

                getName() + "]");
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
     * 查詢流程正常走完的历史流程表 :  act_hi_actinst
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
                histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().count();
            } else {
                histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().count();
            }
        } else {
            if ("1".equals(type)) {//进行中流程
                histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().count();
            } else {//指定人去搜
                histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().count();
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
        for (HistoricTaskInstance t : histList) {
            processIDs.add(t.getProcessInstanceId());
        }
        List<QuestionInfo> questions = null;
        if (processIDs.size() > 0) {
            questions = questionService.selectTaskByProcessID(processIDs);
        }

        List<QuestionTask> taskList = new ArrayList<>();
        //这里递出没有用的字段，免得给前端页面做成加载压力
        for (HistoricTaskInstance hti : histList) {
            QuestionTask myTask = new QuestionTask();
            for (QuestionInfo question : questions) {
                if (question.getProcessInstanceId().equals(hti.getProcessInstanceId())) {
                    myTask.setQuestionID(question.getQuestionID());
                    myTask.setName(question.getProblemTitle());//问题标题
                }
            }
            myTask.setId(hti.getId());
            myTask.setTaskID(hti.getProcessInstanceId());
//            myTask.setName(hti.getName());
            myTask.setCreateTime(hti.getCreateTime());
            myTask.setEndTime(hti.getEndTime());
            taskList.add(myTask);
        }
// 根据getProcessInstanceId去重
        List<QuestionTask> unique = taskList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(
                        QuestionTask::getTaskID
                ))),ArrayList::new
        ));

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(unique, jsonConfig);
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
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(haiList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

}
