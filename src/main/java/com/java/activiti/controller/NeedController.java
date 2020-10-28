package com.java.activiti.controller;

import com.java.activiti.model.*;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.GoodsService;
import com.java.activiti.service.NeedGoodsService;
import com.java.activiti.service.NeedService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import com.java.activiti.util.UuidUtil;
import io.netty.channel.epoll.Epoll;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务处理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/need")
public class NeedController {
    @Resource
    private NeedService needService;
    @Resource
    private NeedGoodsService needGoodsService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private FormService formService;
    @Resource
    private HistoryService historyService;
    /**
     * 分页查询业务
     *
     * @param response
     * @param rows
     * @param page
     * @param searchType
     * @return
     * @throws Exception
     */

    @RequestMapping("/needThroughPage")
    public String needThroughPage(HttpServletResponse response, String rows,
                                  String page, String searchType, String searchValue, String startDate, String endDate) throws Exception {
        PageInfo<Need> needPage = new PageInfo<Need>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        needPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        needPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", needPage.getPageIndex());
        map.put("pageSize", needPage.getPageSize());
        int needCount = needService.needCount(map);
        List<Need> needList = needService.needPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(needList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", needCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * @return
     * @Title: pojectExport
     * @Description: 根据搜索条件，导出对应的数据
     */
    @RequestMapping(value = "needThroughExport", method = RequestMethod.POST)
    @ResponseBody
    public void needThroughExport(HttpServletResponse response, String searchType, String searchValue, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);
        String filename = "needExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
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

            needService.export(response.getOutputStream(),map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/needPage")
    public String needPage(HttpServletResponse response, String rows,
                           String page, String userId) throws Exception {
        PageInfo<Need> needPage = new PageInfo<Need>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        needPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        needPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", needPage.getPageIndex());
        map.put("pageSize", needPage.getPageSize());
        int needCount = needService.needCount(map);
        List<Need> needList = needService.needPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(needList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", needCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询申请详细信息
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getNeedById")
    public String getNeedById(HttpServletResponse response, String id) throws Exception {
        Need need = needService.findNeedById(id);
        List<NeedGoods> needGoodsList = needGoodsService.selectNeedGoods(need.getNID());
        //SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //JSONObject result = new JSONObject();
        String strHead = "<table  width='100%' class=\"gridtable\">";
//        String strHead = "<table  width='100%' class=\"easyui-datagrid\" " +
//                "data-options=\"singleSelect:true,collapsible:true,url:'../static/mock/mock_permission.json',method:'get'\"><thead>";
        String strEnd = "</thead></table>";
        String str = "";
        int i = 0;
        for (NeedGoods needGoods : needGoodsList) {
            if (needGoods.getStorage() == null) {
                str = str +
                        "<tr>" +
                        "<td>" + "序号: " + i + "</td>" +
                        "<td>" + "存货编号: <span style='color:red'>" + needGoods.getGoodsId() + "</span></td>" +
                        "<td>" + "物品名称: " + needGoods.getGoods().getGoodsName() + "</td>" +
                        "<td>" + "规格型号: " + needGoods.getGoods().getGoodsCode() + "</td>" +
                        "<td>" + "单位: " + needGoods.getGoods().getGoodsUnit() + "</td>" +
                        "<td>" + "<a class=\"easyui-linkbutton\" iconCls=\"icon-add\" href=\"javascript:saveStorage(" + "'" + needGoods.getnId() + "'" + ',' + "'" + needGoods.getGoodsId() + "'" + ")\">编辑库存数量</a>" + "</td>" +
                        "</tr>";
                i++;
            } else {
                str = str +
                        "<tr>" +
                        "<td>" + "序号: " + i + "</td>" +
                        "<td>" + "存货编号: <span style='color:red'>" + needGoods.getGoodsId() + "</span></td>" +
                        "<td>" + "物品名称: " + needGoods.getGoods().getGoodsName() + "</td>" +
                        "<td>" + "规格型号: " + needGoods.getGoods().getGoodsCode() + "</td>" +
                        "<td>" + "单位: " + needGoods.getGoods().getGoodsUnit() + "</td>" +
                        "<td>" + "<a class=\"easyui-linkbutton\" iconCls=\"icon-save\" href=\"javascript:updateStorage(" + "'" + needGoods.getnId() + "'" + ',' + "'" + needGoods.getGoodsId() + "'" + "," + "'" + needGoods.getStorageId() + "'" + ")\">修改库存数量</a>" + "</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td>" + "库存编码: " + needGoods.getStorage().getStorageId()+ "</td>" +
                        "<td>" + "工艺数量: " + needGoods.getStorage().getMount() + "</td>" +
                        "<td>" + "备用: " + needGoods.getStorage().getMountBack() + "</td>" +
                        "<td>" + "实购数量: " + needGoods.getStorage().getMountIn() + "</td>" +
                        "<td>" + "合格数量: " + needGoods.getStorage().getMountQualified() + "</td>" +
                        "<td>" + "不合格数量: " + needGoods.getStorage().getMountNotQualified() + "</td>" +
                        "</tr>";
                i++;
//                str = "<th data-options=\"field:'perssionName',width:80\">权限名</th>" +
//                      "<th data-options=\"field:'perssionUrl',width:80\">权限URL</th>";
            }
        }
        ResponseUtil.write(response, strHead + str + strEnd);
        return null;
    }

    /**
     * 查询申请详细信息
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getNeedGoodsById")
    public String getNeedGoodsById(HttpServletResponse response,String rows,
                                   String page, String id, String nId, String goodsId) throws Exception {
        PageInfo<Need> needPage = new PageInfo<Need>();
        Map<String, Object> map = new HashMap<String, Object>();
        Integer pageSize = Integer.parseInt(rows);
        needPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        needPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", needPage.getPageIndex());
        map.put("pageSize", needPage.getPageSize());
        if(nId != null){
            map.put("nId",nId);
            map.put("goodsId",goodsId);
        }else if(id != null){
            Need need = needService.findNeedById(id);
            map.put("nId",need.getNID());
        }
        int needGoodsCount = needGoodsService.needGoodsCount(map);
        List<NeedGoods> needGoodsList = needGoodsService.needGoodsPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(needGoodsList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", needGoodsCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 添加Erp单
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/addNeed")
    public String addNeed(Need need,HttpServletResponse response, HttpServletRequest request,
                          @RequestParam(value = "ECodeID") String ECodeID,
                          @RequestParam(value = "ESubID") int ESubID,
                          @RequestParam(value = "EP1") String EP1,
                          @RequestParam(value = "EP3") String EP3) throws Exception {
        JSONObject result = new JSONObject();
        String[] strs = ECodeID.split(",");
        String EP4_s;
        int resultNeedTotal = 0;
        int resultNeedGoodsTotal = 0;
        if (need.getEApplicantData() == null) {
            need.setEApplicantData(new Date());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String newDate = sdf.format(need.getEApplicantData());
        String eTemp = need.getEPType().substring(0, 2);
        String EP0 = "";
        if (eTemp.equals("外协")) {
            EP0 = "WX";
        } else {
            EP0 = "SG";
        }
        if (EP1.equals("empty")) {
            EP1 = "";
        }
        String EP2 = need.getEType();
        if (EP3.equals("empty")) {
            EP3 = "";
        }
        int EP4 = needService.needCountByMonth() + 1;
        EP4_s = String.format("%03d", EP4);
        String nId_t = EP0 + newDate.substring(2, 4) + newDate.substring(4, 6) + '-' + EP1 + EP2 + EP4_s + EP3;
        need.setNID(nId_t);
        need.setESubID(ESubID);
        User user = new User();
        user.setId(UserUtil.getSubjectUserID());
        need.setUser(user);
        need.setEApplicant(user.getId());
        //添加uuid
        need.setId(UuidUtil.uuidUtil());
        need.setEID(need.getId());
        if(need.getEndDate()==null){
            need.setEndDate(new Date());
        }
//        if(nId != null){
//            resultNeedTotal = needService.updateNeedByNId(need);
//            need.setNID(nId_t);
//            List<Need> needResult = needService.findNeed(need);
//            if(needResult.size() > 0){
//                result.put("success", "error");
//                ResponseUtil.write(response, result);
//                return null;
//            }else{
//                resultNeedTotal = needService.updateNeed(need);
//                resultNeedGoodsTotal = 1;
//            }
//        }else {
            List<Need> needResult = needService.findNeed(need);
            if(needResult.size() > 0){
                result.put("success", "error");
                ResponseUtil.write(response, result);
                return null;
            }else {
                resultNeedTotal = needService.addNeed(need);
            }
            for (String str : strs) {
                Goods goods = goodsService.selectGoodsById(str);
                NeedGoods needGoods = new NeedGoods();
                needGoods.setNeed(need);
                needGoods.setGoods(goods);
                resultNeedGoodsTotal = needGoodsService.addNeedGoods(needGoods);
            }
        //}
        if (resultNeedTotal > 0 && resultNeedGoodsTotal > 0) {
            result.put("success", "success");
        } else {
            result.put("success", "fail");
        }
        ResponseUtil.write(response, result);
        return null;
    }

//    /**
//     * 添加Erp单
//     *
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/addNeed")
//    public String addNeed(Need need,HttpServletResponse response, HttpServletRequest request,
//                          @RequestParam(value = "ECodeID") String ECodeID,
//                          @RequestParam(value = "nId") String nId,
//                          @RequestParam(value = "ESubID") int ESubID,
//                          @RequestParam(value = "EP1") String EP1,
//                          @RequestParam(value = "EP3") String EP3) throws Exception {
//        JSONObject result = new JSONObject();
//        String[] strs = ECodeID.split(",");
//        String EP4_s;
//        int resultNeedTotal = 0;
//        int resultNeedGoodsTotal = 0;
//        if (need.getEApplicantData() == null) {
//            need.setEApplicantData(new Date());
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String newDate = sdf.format(need.getEApplicantData());
//        String eTemp = need.getEPType().substring(0, 2);
//        String EP0 = "";
//        if (eTemp.equals("外协")) {
//            EP0 = "WX";
//        } else {
//            EP0 = "SG";
//        }
//        if (EP1.equals("empty")) {
//            EP1 = "";
//        }
//        String EP2 = need.getEType();
//        if (EP3.equals("empty")) {
//            EP3 = "";
//        }
//        int EP4 = needService.needCountByMonth() + 1;
//        EP4_s = String.format("%03d", EP4);
//        String nId_t = EP0 + newDate.substring(2, 4) + newDate.substring(4, 6) + '-' + EP1 + EP2 + EP4_s + EP3;
//        need.setNID(nId_t);
//        need.setESubID(ESubID);
//        User user = new User();
//        user.setId(UserUtil.getSubjectUserID());
//        need.setUser(user);
//        need.setEApplicant(user.getId());
//        //添加uuid
//        need.setId(UuidUtil.uuidUtil());
//        need.setEID(need.getId());
//        if(need.getEndDate()==null){
//            need.setEndDate(new Date());
//        }
////        if(nId != null){
////            resultNeedTotal = needService.updateNeedByNId(need);
////            need.setNID(nId_t);
////            List<Need> needResult = needService.findNeed(need);
////            if(needResult.size() > 0){
////                result.put("success", "error");
////                ResponseUtil.write(response, result);
////                return null;
////            }else{
////                resultNeedTotal = needService.updateNeed(need);
////                resultNeedGoodsTotal = 1;
////            }
////        }else {
//        List<Need> needResult = needService.findNeed(need);
//        if(needResult.size() > 0){
//            result.put("success", "error");
//            ResponseUtil.write(response, result);
//            return null;
//        }else {
//            resultNeedTotal = needService.addNeed(need);
//        }
//        for (String str : strs) {
//            Goods goods = goodsService.selectGoodsById(str);
//            NeedGoods needGoods = new NeedGoods();
//            needGoods.setNeed(need);
//            needGoods.setGoods(goods);
//            resultNeedGoodsTotal = needGoodsService.addNeedGoods(needGoods);
//        }
//        //}
//        if (resultNeedTotal > 0 && resultNeedGoodsTotal > 0) {
//            result.put("success", "success");
//        } else {
//            result.put("success", "fail");
//        }
//        ResponseUtil.write(response, result);
//        return null;
//    }

    // model 日期格式的处理
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    /**
     * 提交Need申請
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
        variables.put("factoryPlan", UserUtil.getSubjectUserID());

        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("factoryProcessing", variables);
        // 根据流程实例Id查询任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
        // 完成
        taskService.complete(task.getId());
        Need need = needService.findNeedById(taskID);
        //修改状态
        need.setState("审核中");
        need.setProcessInstanceId(pi.getProcessInstanceId());
        // 修改运维单状态
        needService.updateNeed(need);
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
    @RequestMapping("/getNeedByTaskId")
    public String getNeedByTaskId(HttpServletResponse response, String taskId, String id) throws Exception {
        //先根据流程ID查询
        Need need = new Need();
        if(id == null){
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            need = needService.getNeedByTaskId(task.getProcessInstanceId());
        }else {
            need = needService.findById(id);
        }
        JSONObject result = new JSONObject();
        result.put("need", JSONObject.fromObject(need));
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
//        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//        Need need = needService.getNeedByTaskId(task.getProcessInstanceId());
        String url = formData.getFormKey();
//        if(need.getState().equals("质检人员对部分产品质检合格")){
//           url = "factoryPurchaseNext.jsp";
//        }
//        if(need.getState().equals("仓管人员再次审批通过")){
//            url = "factoryQualityNext.jsp";
//        }
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
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        } else {
                // 获取总记录数
                total = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).count();
                // 返回带分页的结果集合
                taskList = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        }

        //获取对于流程的问题单
        List<String> processIDs = new ArrayList<>();
        for (Task t : taskList) {//获取代办流程 id
            processIDs.add(t.getProcessInstanceId());
        }
        List<Need> needs = null;
        if (processIDs.size() > 0) {//代办中显示 问题单对应的一些字段
            needs = needService.selectTaskByProcessID(processIDs);
        }
        //这里需要使用一个工具类来转换一下主要是转成JSON格式
        List<MyTaskInfo> MyTaskList = new ArrayList<MyTaskInfo>();
        for (Task t : taskList) {
            MyTaskInfo myTask = new MyTaskInfo();
            for (Need need : needs) {
                if (need.getProcessInstanceId().equals(t.getProcessInstanceId())) {
                    myTask.setStr1(need.getNID());
                    myTask.setStr2(need.getSubProject().getPark().getParkName());
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
        // 进行降序排列 按时间
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
        List<Need> needs = null;
        if (processIDs.size() > 0) {//代办中显示 问题单对应的一些字段
            needs = needService.selectTaskByProcessID(processIDs);
        }
        List<MyTaskInfo> taskList = new ArrayList<MyTaskInfo>();
        //这里递出没有用的字段，免得给前端页面做成加载压力
        for (HistoricTaskInstance hti : histList) {
            MyTaskInfo myTask = new MyTaskInfo();
            for (Need need : needs) {
                if (need.getProcessInstanceId().equals(hti.getProcessInstanceId())) {
                    myTask.setStr1(need.getNID());
                    myTask.setStr2(need.getSubProject().getPark().getParkName());//问题标题
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
        JSONArray jsonArray = JSONArray.fromObject(unique, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", histCount);
        ResponseUtil.write(response, result);
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

    /**
     *
     * @Title: goodsImport
     * @Description: 导入物料信息excel
     * @author: 许超
     * @return
     */
    @RequestMapping(value = "/goodsImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO goodsImport(HttpServletResponse response,MultipartFile file) throws Exception {
        try {
            List<NeedGoods> needGoodsList = needService.goodsImport(file.getInputStream());
            JSONObject result = new JSONObject();
            JSONArray jsonArray = JSONArray.fromObject(needGoodsList);
            result.put("needGoodsList", jsonArray);
            ResponseUtil.write(response, result);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }

    /**
     *
     * @Title: needImport
     * @Description: 导入信息excel
     * @author: 许超
     * @return
     */
    @RequestMapping(value = "/needImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO needImport(HttpServletResponse response,MultipartFile file) throws IOException {
        try {
            needService.needImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            //e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败:" + e.getMessage(), null);
        }
    }
}
