package com.java.activiti.controller.planTask;

import com.java.activiti.dao.ParkDao;
import com.java.activiti.dao.ProjectDao;
import com.java.activiti.model.File;
import com.java.activiti.model.Need;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.User;
import com.java.activiti.model.planTask.PlanTask;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.service.FileService;
import com.java.activiti.service.MailService;
import com.java.activiti.service.PlanTask.PlanTaskService;
import com.java.activiti.service.PlanTask.impl.PlanTaskServiceImpl;
import com.java.activiti.service.RedisService;
import com.java.activiti.service.UserService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ҵ����
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/planTask")
public class PlanTaskController {
    private static final int QUESTION_INCREMENT_LENGTH = 15;//��ˮ�ų���
    private static final int QUESTION_DBINDEX = 9;//redisDB������

    @Resource
    private PlanTaskService planTaskService;
    @Resource
    private RedisService redisService;
    @Resource
    private ProjectDao projectDao;
    @Resource
    private ParkDao parkDao;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private UserService userService;
    @Resource
    private FileService fileService;
    @Resource
    private MailService mailService;

    /**
     * ��ҳ��ѯ����ͨ�������ⵥ
     *
     * @param response
     * @param rows
     * @param page
     * @return
     * @throws Exception
     */

    @RequestMapping("/planTaskThoughtPage")
    public String questionPage(HttpServletResponse response, String rows,
                               String page, String searchType, String searchValue,
                               String startDate, String endDate) throws Exception {
        PageInfo<PlanTask> questionPage = new PageInfo<PlanTask>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        questionPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        questionPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", questionPage.getPageIndex());
        map.put("pageSize", questionPage.getPageSize());
        int planTaskCount = planTaskService.planTaskThoughtCount(map);
        List<PlanTask> planTaskList = planTaskService.planTaskThoughtPage(map);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(planTaskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", planTaskCount);
        ResponseUtil.write(response, result);
        return null;
    }


    @RequestMapping("/planTaskPage")
    public String planTaskPage(HttpServletResponse response, String rows,
                           String page, String userId) throws Exception {
        PageInfo<PlanTask> planTaskPage = new PageInfo<PlanTask>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        planTaskPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        planTaskPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", planTaskPage.getPageIndex());
        map.put("pageSize", planTaskPage.getPageSize());
        int planTaskCount = planTaskService.planTaskCount(map);
        List<PlanTask> planTaskList = planTaskService.planTaskPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(planTaskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", planTaskCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ��ӹ��ϵ�
     *
     * @param planTask
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/addPlanTask")
    public String addPlanTask(PlanTask planTask, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (planTask.getPtdate() == null) {
            planTask.setPtdate(new Date());
        }
        if (planTask.getState()==null){
            planTask.setState("δ�ύ");
        }
        //���uuid
//        question.setPlanTaskID(UuidUtil.uuidUtil());
        planTask.setPtID( "MYDN"+ redisService.getIncrementNum(11,"PLAN_ID",9,2));
        resultTotal = planTaskService.addPlanTask(planTask);

        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    // model ���ڸ�ʽ�Ĵ���
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    /**
     * �ύ������Ո
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/startApply")
    @ResponseBody
    public String startApply(HttpServletResponse response, @RequestParam("userID") String userID,
                             @RequestParam("taskID") String taskID) throws Exception {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("ID", taskID);
        variables.put("PTScene", userID);
        variables.put("PTAVManager", UserUtil.getSubjectUserID());

        // ��������
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("planTaskProcess", variables);
        // ��������ʵ��Id��ѯ����
        Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        // ���
        taskService.complete(task.getId());
        PlanTask planTask = planTaskService.findPlanTaskById(taskID);
        //�޸�״̬
        planTask.setState("�����");
        planTask.setProcessInstanceId(pi.getProcessInstanceId());
        // �޸���ά��״̬
        planTaskService.updatePlanTask(planTask);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * �޸�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePlanTask")
    @ResponseBody
    public String updatePlanTask(PlanTask planTask,
                                 HttpServletResponse response,
                                 HttpServletRequest request
    ) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (planTask.getPtdate() == null) {
            planTask.setPtdate(new Date());
        }
        resultTotal = planTaskService.updatePlanTask(planTask);
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);

        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ��ѯ������Ϣ
     *
     * @param response
     * @param taskId   ����ʵ��ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/getPlanTaskByTaskId")
    public String getPlanTaskByTaskId(HttpServletResponse response, String taskId) throws Exception {
        //�ȸ�������ID��ѯ
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        PlanTask planTask = planTaskService.getPlanTaskByTaskId(task.getProcessInstanceId());
        JSONObject result = new JSONObject();
        result.put("planTask", JSONObject.fromObject(planTask));
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ���ӹ�����Ո���ύ
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/startRedictApply")
    @ResponseBody
    public String startRedictApply(PlanTask planTask,
                                   @RequestParam("PTScene") String PTScene,
                                   @RequestParam("isSend") boolean isSend,
                                   HttpServletResponse response, HttpServletRequest request
    ) throws Exception {
        JSONObject result = new JSONObject();
        String basePath = request.getServletContext().getRealPath("\\"); // ��ȡ����·��
        int resultTotal = 0;
        if (planTask.getPtdate() == null) {
            planTask.setPtdate(new Date());
        }
        //���uuid
        String ptID =  "MYDN"+ redisService.getIncrementNum(11,"PLAN_ID",9,2);
        planTask.setPtID(ptID);
        resultTotal = planTaskService.addPlanTask(planTask);

        if (resultTotal > 0) {
            String userIDs = "";
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("ptID", ptID);
            variables.put("PTScene", PTScene);
            variables.put("PTAVManager", UserUtil.getSubjectUserID());
            // ��������
            ProcessInstance pi = runtimeService.startProcessInstanceByKey("planTaskProcess", variables);
            // ��������ʵ��Id��ѯ����
            Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
            // ���
            taskService.complete(task.getId());
           PlanTask planTaskInfo = planTaskService.findById(ptID);
            //�޸�״̬
            planTaskInfo.setState("�����");
            planTaskInfo.setProcessInstanceId(pi.getProcessInstanceId());
            // �޸���ά��״̬
            planTaskService.updatePlanTask(planTask);
            if (isSend == true) {
                User sendUser = userService.findById(PTScene);
                String emailName = sendUser.getFirstName() + sendUser.getLastName();
                String to = sendUser.getEmail();
                String subject = "ά�޵���(" + ptID + ")";
//                Boolean isSending = mailService.sendMailQestion(subject, questionIn, emailName, to, basePath);
                result.put("success", true);
            } else {
                result.put("success", true);
            }
            ResponseUtil.write(response, result);
            return null;
        } else {
            result.put("success", false);
            return null;
        }
    }

//    /**
//     * ���Ҹ��ɹ����������ļ�
//     */
//    @RequestMapping(value = "findFiles", method = RequestMethod.POST)
//    public String findFiles(@RequestParam("fileID") String fileID, HttpServletResponse response) throws Exception {
//
//        List<File> files = fileService.findFilesByRepairID(fileID);
//        JSONArray jsonArray = new JSONArray();
//        JSONArray rows = JSONArray.fromObject(files);
//        jsonArray.addAll(rows);
//        ResponseUtil.write(response, jsonArray);
//        return null;
//    }

//
//    /**
//     * @return
//     * @Title: pojectExport
//     * @Description: ��������������������Ӧ������
//     */
//    @RequestMapping(value = "questionThroughExport", method = RequestMethod.POST)
//    @ResponseBody
//    public void parkExport(HttpServletResponse response, HttpServletRequest request, String searchType, String searchValue, String startDate, String endDate) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        String dateStr = sdf.format(date);
//        String filename = "questionExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("searchType", searchType);
//        map.put("searchValue", searchValue);
//        map.put("startDate", startDate);
//        map.put("endDate", endDate);
//        // ��Ӧ����
//        try {
//            // ���������,ʵ�������ļ�
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));
//
//            questionService.export(response.getOutputStream(),request, map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
