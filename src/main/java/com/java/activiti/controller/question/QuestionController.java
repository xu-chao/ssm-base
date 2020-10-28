package com.java.activiti.controller.question;

import com.java.activiti.dao.ParkDao;
import com.java.activiti.dao.ProjectDao;
import com.java.activiti.model.*;
import com.java.activiti.model.File;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.service.*;

import com.java.activiti.service.question.QuestionService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import com.java.activiti.util.UuidUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.PropertySource;
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
 * 业务处理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    private static final int QUESTION_INCREMENT_LENGTH = 15;//流水号长度
    private static final int QUESTION_DBINDEX = 9;//redisDB缓存区
    private static final String QUESTION_ID = "QUE_ID";//redisDB缓存区

    @Resource
    private QuestionService questionService;
    @Resource
    private RedisService redisService;
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
     * 分页查询业务
     *
     * @param response
     * @param rows
     * @param page
     * @param userId
     * @return
     * @throws Exception
     */

    @RequestMapping("/questionPage")
    public String questionPage(HttpServletResponse response, String rows,
                               String page, String userId) throws Exception {
        PageInfo<QuestionInfo> questionPage = new PageInfo<QuestionInfo>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        questionPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        questionPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", questionPage.getPageIndex());
        map.put("pageSize", questionPage.getPageSize());
        int questionCount = questionService.questionCount(map);
        List<QuestionInfo> questionList = questionService.questionPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(questionList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", questionCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 分页查询审批通过的问题单
     *
     * @param response
     * @param rows
     * @param page
     * @return
     * @throws Exception
     */

    @RequestMapping("/questionThroughPage")
    public String questionPage(HttpServletResponse response, String rows,
                               String page, String searchType, String searchValue,
                               String stateID, String startDate, String endDate) throws Exception {
        PageInfo<QuestionInfo> questionPage = new PageInfo<QuestionInfo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        if (stateID==null){
            stateID = "2";//通过的流程
        }
        map.put("stateID", stateID);
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
        int questionCount = questionService.questionThoughtCount(map);
        List<QuestionInfo> questionList = questionService.questionThrough(map);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(questionList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", questionCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 添加故障单
     *
     * @param question
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/addQuestion")
    public String addQuestion(QuestionInfo question, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (question.getQuestionDate() == null) {
            question.setQuestionDate(new Date());
        }
        //添加uuid
//        question.setQuestionID(UuidUtil.uuidUtil());
        question.setQuestionID(redisService.getIncrementNum(QUESTION_INCREMENT_LENGTH,QUESTION_ID,QUESTION_DBINDEX,2));
        resultTotal = questionService.addQuestion(question);

        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    // model 日期格式的处理
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    /**
     * 提交故障申請
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/startApply")
    @ResponseBody
    public String startApply(HttpServletResponse response, @RequestParam("userID") String userID,
                             @RequestParam("taskID") String taskID) throws Exception {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("questionID", taskID);
        variables.put("QuestionAdviser", userID);
        variables.put("QuestionScene", UserUtil.getSubjectUserID());

        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("QuestionProcessing", variables);
        // 根据流程实例Id查询任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        // 完成
        taskService.complete(task.getId());
        QuestionInfo question = questionService.findById(taskID);
        //修改状态
        question.setState("审核中");
        question.setStateID(1);
        question.setProcessInstanceId(pi.getProcessInstanceId());
        // 修改运维单状态
        questionService.updateQuestion(question);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 增加故障申請并提交
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/startRedictApply")
    @ResponseBody
    public String startRedictApply(QuestionInfo questionInfo,
                                   @RequestParam("QuestionAdviser") String questionAdviser,
                                   @RequestParam("isSend") boolean isSend,
                                   HttpServletResponse response, HttpServletRequest request
    ) throws Exception {
        JSONObject result = new JSONObject();
        String basePath = request.getServletContext().getRealPath("\\"); // 获取基本路径
        int resultTotal = 0;
        if (questionInfo.getQuestionDate() == null) {
            questionInfo.setQuestionDate(new Date());
        }
        //添加uuid
//        String questionID = UuidUtil.uuidUtil();
        String questionID = redisService.getIncrementNum(QUESTION_INCREMENT_LENGTH,QUESTION_ID,QUESTION_DBINDEX,2);
        questionInfo.setQuestionID(questionID);
        resultTotal = questionService.addQuestion(questionInfo);

        if (resultTotal > 0) {
            String userIDs = "";
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("questionID", questionID);
            variables.put("QuestionAdviser", questionAdviser);
            variables.put("QuestionScene", UserUtil.getSubjectUserID());
            // 启动流程
            ProcessInstance pi = runtimeService.startProcessInstanceByKey("QuestionProcessing", variables);
            // 根据流程实例Id查询任务
            Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
            // 完成
            taskService.complete(task.getId());
            QuestionInfo questionIn = questionService.findById(questionID);
            //修改状态
            questionIn.setState("审核中");
            questionIn.setStateID(1);//审核中
            questionIn.setProcessInstanceId(pi.getProcessInstanceId());
            // 修改运维单状态
            questionService.updateQuestion(questionIn);
            if (isSend == true) {
                User sendUser = userService.findById(questionAdviser);
                String emailName = sendUser.getFirstName() + sendUser.getLastName();
                String to = sendUser.getEmail();
                String subject = "维修单号(" + questionID + ")";
                Boolean isSending = mailService.sendMailQestion(subject, questionIn, UserUtil.getSubjectUser().getAllName(), to, basePath);
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

    /**
     * 修改 问题单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateQuestion")
    @ResponseBody
    public String updateQuestion(QuestionInfo questionInfo,
                                 HttpServletResponse response,
                                 HttpServletRequest request
    ) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (questionInfo.getQuestionDate() == null) {
            questionInfo.setQuestionDate(new Date());
        }
        resultTotal = questionService.updateQuestion(questionInfo);
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);

        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询流程信息
     *
     * @param response
     * @param taskId   流程实例ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/getQuestionByTaskId")
    public String getQuestionByTaskId(HttpServletResponse response, String taskId) throws Exception {
        //先根据流程ID查询
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        QuestionInfo question = questionService.getQuestionByTaskId(task.getProcessInstanceId());
        JSONObject result = new JSONObject();
        result.put("question", JSONObject.fromObject(question));
        ResponseUtil.write(response, result);
        return null;
    }

//    /**
//     * 上传文件
//     *
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/uploadFile")
//    public String uploadFile(
//            @RequestParam("fileID") String fileID,
//            @RequestParam("file") CommonsMultipartFile file,
//            HttpServletRequest request,
//            HttpServletResponse response) throws Exception {
//
//
//        int out = fileService.addFileImage(file, request, fileID);
////上传成功
//
//        JSONObject result = new JSONObject();
//        if (out > 0) {
//            result.put("success", true);
//        } else {
//            result.put("success", false);
//        }
//        ResponseUtil.write(response, result);
//        return null;
//    }

//    /**
//     * 查找该派工单下所有文件
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
//    /*
//     * 下载文件
//     */
//
//    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
//    @ResponseBody
//    public String downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("path") String path) throws IOException {
////		String firstName =  path.substring(0,path.lastIndexOf("\\") + 1);
////        String basePath = request.getServletContext().getRealPath("\\"); // 获取基本路径
////        String basePath = "D:\\fangteFile\\"; // 获取基本路径
//        String basePath = FANGTE_FILE_REALPATH; // 获取基本路径
//        /* 第一步:根据文件路径获取文件 */
////		, @RequestParam("path")String path
//
//        java.io.File file = new java.io.File(basePath + path);
//        if (file.exists()) { // 文件存在
//            /* 第二步：根据已存在的文件，创建文件输入流 */
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//            /* 第三步：创建缓冲区，大小为流的最大字符数 */
//            byte[] buffer = new byte[inputStream.available()]; // int available() 返回值为流中尚未读取的字节的数量
//            /* 第四步：从文件输入流读字节流到缓冲区 */
//            inputStream.read(buffer);
//            /* 第五步： 关闭输入流 */
//            inputStream.close();
//
//            String fileName = file.getName();// 获取文件名
//            response.reset();
//            response.addHeader("Content-Disposition",
//                    "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
//            response.addHeader("Content-Length", "" + file.length());
//
//            /* 第六步：创建文件输出流 */
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            /* 第七步：把缓冲区的内容写入文件输出流 */
//            outputStream.write(buffer);
//            /* 第八步：刷空输出流，并输出所有被缓存的字节 */
//            outputStream.flush();
//            /* 第九步：关闭输出流 */
//            outputStream.close();
//
//        } //end  if (file.exists())
//
//        return null;
//    }

    /**
     * @return
     * @Title: pojectExport
     * @Description: 根据搜索条件，导出对应的数据
     */
    @RequestMapping(value = "questionThroughExport", method = RequestMethod.POST)
    @ResponseBody
    public void parkExport(HttpServletResponse response, HttpServletRequest request, String searchType, String searchValue, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);
        String filename = "questionExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            questionService.export(response.getOutputStream(),request, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
