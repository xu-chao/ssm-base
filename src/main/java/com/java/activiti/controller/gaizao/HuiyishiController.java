package com.java.activiti.controller.gaizao;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.User;
import com.java.activiti.model.gaizao.Huiyishi;
import com.java.activiti.service.FileService;
import com.java.activiti.service.MailService;
import com.java.activiti.service.RedisService;
import com.java.activiti.service.UserService;
import com.java.activiti.service.gaizao.HuiyishiService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ҵ����
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/huiyishi")
public class HuiyishiController {
    private static final int HYS_INCREMENT_LENGTH = 10;//��ˮ�ų���
    private static final int HYS_DBINDEX = 9;//redisDB������
    private static final String HYS_ID = "HYS_ID";//redisDB������

    @Resource
    private HuiyishiService huiyishiService;
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
     * @author LIUHD
     * ���� response
     * ���� rows
     * ���� page
     * ���� userID
     * @description ��ҳ��ѯ
     * @date 2019/12/31 15:57
     * @Version 1.0
     */

    @RequestMapping("/huiyishiPage")
    public String huiyishiPage(HttpServletResponse response, String rows,
                               String page, String userID) throws Exception {
        PageInfo<Huiyishi> huiyishiPage = new PageInfo<Huiyishi>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userID == "" || "".equals(userID) || userID == null) {
            userID = UserUtil.getSubjectUserID();
        }
        map.put("userID", userID);
        Integer pageSize = Integer.parseInt(rows);
        huiyishiPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        huiyishiPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", huiyishiPage.getPageIndex());
        map.put("pageSize", huiyishiPage.getPageSize());
        int huiyishiCount = huiyishiService.huiyishiCount(map);
        List<Huiyishi> huiyishiList = huiyishiService.huiyishiPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(huiyishiList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", huiyishiCount);
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

    @RequestMapping("/huiyishiThroughPage")
    public String huiyishiPage(HttpServletResponse response, String rows,
                               String page, String searchType, String searchValue,
                               String stateID, String startDate, String endDate) throws Exception {
        PageInfo<Huiyishi> huiyishiPage = new PageInfo<Huiyishi>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        if (stateID == null) {
            stateID = "2";//ͨ��������
        }
        map.put("stateID", stateID);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        huiyishiPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        huiyishiPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", huiyishiPage.getPageIndex());
        map.put("pageSize", huiyishiPage.getPageSize());
        int huiyishiCount = huiyishiService.huiyishiThoughtCount(map);
        List<Huiyishi> huiyishiList = huiyishiService.huiyishiThrough(map);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(huiyishiList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", huiyishiCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ��ӹ��ϵ�
     *
     * @param huiyishi
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/addHuiyishi")
    public String addHuiyishi(Huiyishi huiyishi, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (huiyishi.getHysDate() == null) {
            huiyishi.setHysDate(new Date());
        }
        //���uuid
//        huiyishi.setHuiyishiID(UuidUtil.uuidUtil());
        String strID = redisService.getIncrementNum(HYS_INCREMENT_LENGTH, HYS_ID, HYS_DBINDEX, 2);
        huiyishi.setHysID(Integer.parseInt(strID));
        resultTotal = huiyishiService.addHuiyishi(huiyishi);

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
        variables.put("huiyishiID", taskID);
        variables.put("hys2", userID);
        String advicerUser = UserUtil.getSubjectUserID();
        variables.put("hys1", advicerUser);

        // ��������
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("huiyishi", variables);
        // ��������ʵ��Id��ѯ����
        Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        // ���
        taskService.complete(task.getId());
        Huiyishi huiyishi = huiyishiService.findById(taskID);
        //�޸�״̬
        huiyishi.setState("�����");
        huiyishi.setStateID(1);
        User adviserUserInfo = new User();
        adviserUserInfo.setId(advicerUser);
        huiyishi.setUserAdviser(adviserUserInfo);
        huiyishi.setProcessInstanceId(pi.getProcessInstanceId());
        // �޸���ά��״̬
        huiyishiService.updateHuiyishi(huiyishi);
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
    public String startRedictApply(Huiyishi huiyishiInfo,
                                   @RequestParam("HuiyishiAdviser") String huiyishiAdviser,
                                   HttpServletResponse response, HttpServletRequest request
    ) throws Exception {
        JSONObject result = new JSONObject();
        String basePath = request.getServletContext().getRealPath("\\"); // ��ȡ����·��
        int resultTotal = 0;
        if (huiyishiInfo.getHysDate() == null) {
            huiyishiInfo.setHysDate(new Date());
        }
        //���uuid
//        String huiyishiID = UuidUtil.uuidUtil();
        String huiyishiID = redisService.getIncrementNum(HYS_INCREMENT_LENGTH, HYS_ID, HYS_DBINDEX, 2);
        huiyishiInfo.setHysID(Integer.parseInt(huiyishiID));
        resultTotal = huiyishiService.addHuiyishi(huiyishiInfo);

        if (resultTotal > 0) {
            String userIDs = "";
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("huiyishiID", huiyishiID);
            variables.put("hys2", huiyishiAdviser);//������
            variables.put("hys1", UserUtil.getSubjectUserID());//�ύ��
            // ��������
            ProcessInstance pi = runtimeService.startProcessInstanceByKey("huiyishi", variables);
            // ��������ʵ��Id��ѯ����
            Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
            // ���
            taskService.complete(task.getId());
            Huiyishi huiyishiIn = huiyishiService.findById(huiyishiID);
            //�޸�״̬
            huiyishiIn.setState("�����");
            huiyishiIn.setStateID(1);//�����
            huiyishiIn.setProcessInstanceId(pi.getProcessInstanceId());
            // �޸���ά��״̬
            huiyishiService.updateHuiyishi(huiyishiIn);

            result.put("success", true);
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
    @RequestMapping("/updateHuiyishi")
    @ResponseBody
    public String updateHuiyishi(Huiyishi huiyishiInfo,
                                 HttpServletResponse response,
                                 HttpServletRequest request
    ) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (huiyishiInfo.getHysDate() == null) {
            huiyishiInfo.setHysDate(new Date());
        }
        resultTotal = huiyishiService.updateHuiyishi(huiyishiInfo);
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
    @RequestMapping("/getHuiyishiByTaskId")
    public String getHuiyishiByTaskId(HttpServletResponse response, String taskId) throws Exception {
        //�ȸ�������ID��ѯ
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        List<String> list = new ArrayList<>();
        list.add(task.getProcessInstanceId());
        List<Huiyishi> huiyishis = huiyishiService.selectTaskByProcessID(list);
        JSONObject result = new JSONObject();
        result.put("huiyishi", JSONObject.fromObject(huiyishis.get(0)));
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
    @RequestMapping(value = "huiyishiThroughExport", method = RequestMethod.POST)
    @ResponseBody
    public void parkExport(HttpServletResponse response, HttpServletRequest request, String searchType, String searchValue, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);
        String filename = "huiyishiExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
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

            huiyishiService.export(response.getOutputStream(), request, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
