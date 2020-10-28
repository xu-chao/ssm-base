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
 * 业务处理
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
     * 分页查询业务
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
     * 查询申请详细信息
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getEquitById")
    public String getEquitById(HttpServletResponse response, String id) throws Exception{
        //先根据流程ID查询
        Equit equit = equitService.findById(id);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        JSONObject result=new JSONObject();
        String str =  "<table  width='100%' class=\"gridtable\"><tr>" +
                "<td>" + "序号: " + equit.getEID() + "</td>" + "<td>" + "日期: " + ft.format(equit.getApplicantData())  + "</td>" + "<td>" + "类型: " + equit.getEType()  + "</td>" + "<td>" + "采购单号: " + equit.getNID()  + "</td>" + "<td>" + "工程名称: " + equit.getEPName()  + "</td>" + "<td>" + "项目名称: " + equit.getEProjectName()  + "</td>" + "<td>" + "子项目名称: " + equit.getESubName() + "</td>" +
                "</tr>" + "<tr>" + "<td>" + "系统名称: " + equit.getESysName()  + "</td>" + "<td>" + "存货编号: " + equit.getECode()  + "</td>" + "<td>" + "物品名称: " + equit.getEProduct()  + "</td>" + "<td>" + "规格型号: " + equit.getESpecs()  + "</td>" + "<td>" + "单位: " + equit.getEUnit()  + "</td>" + "<td>" + "工艺数量: " + equit.getEMount() + "</td>" + "<td>" + "备用: " + equit.getENote()  + "</td>" +
                "</tr>" + "<tr>" + "<td>" + "实购数量: " + equit.getERMount()  + "</td>" + "<td>" + "申请人: " + equit.getUser().getAllName()  + "</td>" + "<td>" + "临时备注: " + equit.getETemp()  + "</td>" + "<td>" + "计划备注: " + equit.getEPlan()  + "</td>" + "<td>" + "发货标识: " + equit.getEIsRun()  + "</td>" + "<td>" + "加工类型: " + equit.getEPType()  + "</td>" + "<td>" + "公司主体: " + equit.getECompany()  + "</td>" + "</tr>" + "</table>";

//		result.put("equit", JSONObject.fromObject(equit));
        ResponseUtil.write(response, str);
        return null;
    }
    /**
     * 添加故障单
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
        //添加uuid
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
     * 提交故障申請
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

        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("factoryProcess", variables);
        // 根据流程实例Id查询任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        // 完成
        taskService.complete(task.getId());
        Equit equit = equitService.findById(taskID);
        //修改状态
        equit.setState("审核中");
        equit.setProcessInstanceId(pi.getProcessInstanceId());
        // 修改运维单状态
        equitService.updateEquit(equit);
        JSONObject result = new JSONObject();
        result.put("success", true);
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
    @RequestMapping("/getEquitByTaskId")
    public String getEquitByTaskId(HttpServletResponse response, String taskId) throws Exception {
        //先根据流程ID查询
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Equit equit = equitService.getEquitByTaskId(task.getProcessInstanceId());
        JSONObject result = new JSONObject();
        result.put("equit", JSONObject.fromObject(equit));
        ResponseUtil.write(response, result);
        return null;
    }
}
