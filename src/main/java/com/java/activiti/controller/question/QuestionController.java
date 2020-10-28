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
 * ҵ����
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    private static final int QUESTION_INCREMENT_LENGTH = 15;//��ˮ�ų���
    private static final int QUESTION_DBINDEX = 9;//redisDB������
    private static final String QUESTION_ID = "QUE_ID";//redisDB������

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
     * ��ҳ��ѯҵ��
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
     * ��ҳ��ѯ����ͨ�������ⵥ
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
            stateID = "2";//ͨ��������
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
     * ��ӹ��ϵ�
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
        //���uuid
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
        variables.put("questionID", taskID);
        variables.put("QuestionAdviser", userID);
        variables.put("QuestionScene", UserUtil.getSubjectUserID());

        // ��������
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("QuestionProcessing", variables);
        // ��������ʵ��Id��ѯ����
        Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        // ���
        taskService.complete(task.getId());
        QuestionInfo question = questionService.findById(taskID);
        //�޸�״̬
        question.setState("�����");
        question.setStateID(1);
        question.setProcessInstanceId(pi.getProcessInstanceId());
        // �޸���ά��״̬
        questionService.updateQuestion(question);
        JSONObject result = new JSONObject();
        result.put("success", true);
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
    public String startRedictApply(QuestionInfo questionInfo,
                                   @RequestParam("QuestionAdviser") String questionAdviser,
                                   @RequestParam("isSend") boolean isSend,
                                   HttpServletResponse response, HttpServletRequest request
    ) throws Exception {
        JSONObject result = new JSONObject();
        String basePath = request.getServletContext().getRealPath("\\"); // ��ȡ����·��
        int resultTotal = 0;
        if (questionInfo.getQuestionDate() == null) {
            questionInfo.setQuestionDate(new Date());
        }
        //���uuid
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
            // ��������
            ProcessInstance pi = runtimeService.startProcessInstanceByKey("QuestionProcessing", variables);
            // ��������ʵ��Id��ѯ����
            Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
            // ���
            taskService.complete(task.getId());
            QuestionInfo questionIn = questionService.findById(questionID);
            //�޸�״̬
            questionIn.setState("�����");
            questionIn.setStateID(1);//�����
            questionIn.setProcessInstanceId(pi.getProcessInstanceId());
            // �޸���ά��״̬
            questionService.updateQuestion(questionIn);
            if (isSend == true) {
                User sendUser = userService.findById(questionAdviser);
                String emailName = sendUser.getFirstName() + sendUser.getLastName();
                String to = sendUser.getEmail();
                String subject = "ά�޵���(" + questionID + ")";
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
     * �޸� ���ⵥ
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
     * ��ѯ������Ϣ
     *
     * @param response
     * @param taskId   ����ʵ��ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/getQuestionByTaskId")
    public String getQuestionByTaskId(HttpServletResponse response, String taskId) throws Exception {
        //�ȸ�������ID��ѯ
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        QuestionInfo question = questionService.getQuestionByTaskId(task.getProcessInstanceId());
        JSONObject result = new JSONObject();
        result.put("question", JSONObject.fromObject(question));
        ResponseUtil.write(response, result);
        return null;
    }

//    /**
//     * �ϴ��ļ�
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
////�ϴ��ɹ�
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
//    /*
//     * �����ļ�
//     */
//
//    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
//    @ResponseBody
//    public String downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("path") String path) throws IOException {
////		String firstName =  path.substring(0,path.lastIndexOf("\\") + 1);
////        String basePath = request.getServletContext().getRealPath("\\"); // ��ȡ����·��
////        String basePath = "D:\\fangteFile\\"; // ��ȡ����·��
//        String basePath = FANGTE_FILE_REALPATH; // ��ȡ����·��
//        /* ��һ��:�����ļ�·����ȡ�ļ� */
////		, @RequestParam("path")String path
//
//        java.io.File file = new java.io.File(basePath + path);
//        if (file.exists()) { // �ļ�����
//            /* �ڶ����������Ѵ��ڵ��ļ��������ļ������� */
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//            /* ����������������������СΪ��������ַ��� */
//            byte[] buffer = new byte[inputStream.available()]; // int available() ����ֵΪ������δ��ȡ���ֽڵ�����
//            /* ���Ĳ������ļ����������ֽ����������� */
//            inputStream.read(buffer);
//            /* ���岽�� �ر������� */
//            inputStream.close();
//
//            String fileName = file.getName();// ��ȡ�ļ���
//            response.reset();
//            response.addHeader("Content-Disposition",
//                    "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
//            response.addHeader("Content-Length", "" + file.length());
//
//            /* �������������ļ������ */
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            /* ���߲����ѻ�����������д���ļ������ */
//            outputStream.write(buffer);
//            /* �ڰ˲���ˢ�����������������б�������ֽ� */
//            outputStream.flush();
//            /* �ھŲ����ر������ */
//            outputStream.close();
//
//        } //end  if (file.exists())
//
//        return null;
//    }

    /**
     * @return
     * @Title: pojectExport
     * @Description: ��������������������Ӧ������
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
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            questionService.export(response.getOutputStream(),request, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
