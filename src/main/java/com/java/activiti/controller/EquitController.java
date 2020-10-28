package com.java.activiti.controller;

import com.java.activiti.model.Equit;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.User;
import com.java.activiti.service.EquitService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/equit")
public class EquitController {
    @Resource
    private EquitService equitService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
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

    @RequestMapping("/equitPage")
    public String equitPage(HttpServletResponse response, String rows,
                            String page, String userId) throws Exception {
        PageInfo<Equit> equitPage = new PageInfo<Equit>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId==""||"".equals(userId)||userId==null){
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        equitPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        equitPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", equitPage.getPageIndex());
        map.put("pageSize", equitPage.getPageSize());
        int equitCount = equitService.equitCount(map);
        List<Equit> equitList = equitService.equitPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(equitList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", equitCount);
        ResponseUtil.write(response, result);
        return null;
    }
    /**table table-border table-bordered table-striped
     * ��ѯ������ϸ��Ϣ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getEquitById")
    public String getEquitById(HttpServletResponse response, String id) throws Exception{
        //�ȸ�������ID��ѯ
        Equit equit = equitService.findById(id);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        JSONObject result=new JSONObject();
        String str =  "<table  width='100%' class=\"gridtable\"><tr>" +
                "<td>" + "���: " + equit.getEID() + "</td>" + "<td>" + "����: " + ft.format(equit.getApplicantData())  + "</td>" + "<td>" + "����: " + equit.getEType()  + "</td>" + "<td>" + "�ɹ�����: " + equit.getNID()  + "</td>" + "<td>" + "��������: " + equit.getEPName()  + "</td>" + "<td>" + "��Ŀ����: " + equit.getEProjectName()  + "</td>" + "<td>" + "����Ŀ����: " + equit.getESubName() + "</td>" +
                "</tr>" + "<tr>" + "<td>" + "ϵͳ����: " + equit.getESysName()  + "</td>" + "<td>" + "������: " + equit.getECode()  + "</td>" + "<td>" + "��Ʒ����: " + equit.getEProduct()  + "</td>" + "<td>" + "����ͺ�: " + equit.getESpecs()  + "</td>" + "<td>" + "��λ: " + equit.getEUnit()  + "</td>" + "<td>" + "��������: " + equit.getEMount() + "</td>" + "<td>" + "����: " + equit.getENote()  + "</td>" +
                "</tr>" + "<tr>" + "<td>" + "ʵ������: " + equit.getERMount()  + "</td>" + "<td>" + "������: " + equit.getUser().getAllName()  + "</td>" + "<td>" + "��ʱ��ע: " + equit.getETemp()  + "</td>" + "<td>" + "�ƻ���ע: " + equit.getEPlan()  + "</td>" + "<td>" + "������ʶ: " + equit.getEIsRun()  + "</td>" + "<td>" + "�ӹ�����: " + equit.getEPType()  + "</td>" + "<td>" + "��˾����: " + equit.getECompany()  + "</td>" + "</tr>" + "</table>";

//		result.put("equit", JSONObject.fromObject(equit));
        ResponseUtil.write(response, str);
        return null;
    }
    /**
     * ��ӹ��ϵ�
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/addEquit")
    public String addEquit(Equit equit, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (equit.getApplicantData() == null) {
            equit.setApplicantData(new Date());
        }
        User user = new User();
        user.setId(UserUtil.getSubjectUserID());
        equit.setUser(user);
        //���uuid
        equit.setId(UuidUtil.uuidUtil());
        resultTotal = equitService.addEquit(equit);

        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
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
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", taskID);
        variables.put("factoryPurchase", userID);
//        variables.put("factoryPlan", UserUtil.getSubjectUserID());

        // ��������
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("factoryProcess", variables);
        // ��������ʵ��Id��ѯ����
        Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        // ���
        taskService.complete(task.getId());
        Equit equit = equitService.findById(taskID);
        //�޸�״̬
        equit.setState("�����");
        equit.setProcessInstanceId(pi.getProcessInstanceId());
        // �޸���ά��״̬
        equitService.updateEquit(equit);
        JSONObject result = new JSONObject();
        result.put("success", true);
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
    @RequestMapping("/getEquitByTaskId")
    public String getEquitByTaskId(HttpServletResponse response, String taskId) throws Exception {
        //�ȸ�������ID��ѯ
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Equit equit = equitService.getEquitByTaskId(task.getProcessInstanceId());
        JSONObject result = new JSONObject();
        result.put("equit", JSONObject.fromObject(equit));
        ResponseUtil.write(response, result);
        return null;
    }
}
