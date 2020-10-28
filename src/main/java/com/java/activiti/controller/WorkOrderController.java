package com.java.activiti.controller;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.Repair;
import com.java.activiti.model.User;
import com.java.activiti.model.WorkOrder;
import com.java.activiti.service.FileService;
import com.java.activiti.service.RepairService;
import com.java.activiti.service.UserService;
import com.java.activiti.service.WorkOrderService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UuidUtil;
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
import org.springframework.web.bind.annotation.InitBinder;
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
 *
 */
@Controller
@RequestMapping("/workOrder")
public class WorkOrderController {

	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private UserService userService;
	@Resource
	private FileService fileService;
	/**
	 * 分页查询业务
	 * @param response
	 * @param rows
	 * @param page
	 * @param userId
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/workOrderPage")
	public String workOrderPage(HttpServletResponse response, String rows,
			String page, String userId) throws Exception {
		PageInfo<WorkOrder> workOrderPage = new PageInfo<WorkOrder>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		Integer pageSize = Integer.parseInt(rows);
		workOrderPage.setPageSize(pageSize);
		if (page == null || page.equals("")) {
			page = "1";
		}
		workOrderPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
		map.put("pageIndex", workOrderPage.getPageIndex());
		map.put("pageSize", workOrderPage.getPageSize());
		int workOrderCount = workOrderService.workOrderCount(map);
		List<WorkOrder> workOrderList = workOrderService.workOrderPage(map);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(workOrderList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", workOrderCount);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 分页查询业务
	 * @param response
	 * @param rows
	 * @param page
	 * @param userId
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/workOrderThroughPage")
	public String workOrderThroughPage(HttpServletResponse response, String rows, String flag,
								String page, String userId, String searchType, String searchValue, String startDate, String endDate) throws Exception {
		PageInfo<WorkOrder> workOrderPage = new PageInfo<WorkOrder>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		Integer pageSize = Integer.parseInt(rows);
		workOrderPage.setPageSize(pageSize);
		if (page == null || page.equals("")) {
			page = "1";
		}
		workOrderPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
		map.put("pageIndex", workOrderPage.getPageIndex());
		map.put("pageSize", workOrderPage.getPageSize());
		int workOrderCount = workOrderService.workOrderThroughCount(map);
		List<WorkOrder> workOrderList = workOrderService.workOrderThroughPage(map);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(workOrderList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", workOrderCount);
		ResponseUtil.write(response, result);
		return null;
	}

//	/**
//	 * 添加故障单
//	 * @param repair
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/addRepair")
//	public String addRepair(Repair repair, HttpServletResponse response, String userId, HttpServletRequest request)throws Exception{
//		JSONObject result=new JSONObject();
//		User user=new User();
//		user.setId(userId);
//		int resultTotal=0;
//		if(repair.getRecordDate() == null){
//			repair.setRecordDate(new Date());
//		}
//		//添加用户对象
//		repair.setUser(user);
//		//添加uuid
//		repair.setId(UuidUtil.uuidUtil());
//		resultTotal=repairService.addRepair(repair);
//
//		if(resultTotal>0){
//			result.put("success", true);
//		}else{
//			result.put("success", false);
//		}
//		ResponseUtil.write(response, result);
//		return null;
//	}
//
//	// model 日期格式的处理
//	@InitBinder
//	public void init(WebDataBinder binder) {
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
//	}
//
//	/**
//	 * 提交故障申請
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/startApply")
//	@ResponseBody
//	public String startApply(HttpServletResponse response,@RequestParam("userID") String userID,
//							 @RequestParam("taskID") String taskID) throws Exception{
//		Map<String,Object> variables=new HashMap<String,Object>();
//		variables.put("repairID", taskID);
//		variables.put("projectSupervisor", userID);
//		// 启动流程
//		ProcessInstance pi= runtimeService.startProcessInstanceByKey("OMMSProcessing",variables);
//		// 根据流程实例Id查询任务
//		Task task=taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
//		 // 完成
//		taskService.complete(task.getId());
//		Repair repair=repairService.findById(taskID);
//		//修改状态
//		repair.setState("审核中");
//		repair.setProcessInstanceId(pi.getProcessInstanceId());
//		 // 修改运维单状态
//		repairService.updateRepair(repair);
//		JSONObject result=new JSONObject();
//		result.put("success", true);
//		ResponseUtil.write(response, result);
//		return null;
//	}
//
//	/**
//	 * 查询主管项目负责人信息
//	 * @param response
//	 * @param
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/findProjectSupervisorList")
//	public String findProjectSupervisorList(HttpServletResponse response) throws Exception{
//		List<User> list=userService.findProjectSupervisorList();
//		for (int i=0;i<list.size();i++){
//			list.get(i).setFirstName(list.get(i).getFirstName()+list.get(i).getLastName());
//		}
//		JSONArray jsonArray=new JSONArray();
//
//		JSONObject jsonObject=new JSONObject();
//		jsonObject.put("firstName", "---请选择---");
//		//转为JSON格式的数据
//		jsonArray.add(jsonObject);
//		//将list转为JSON
//		JSONArray rows=JSONArray.fromObject(list);
//		jsonArray.addAll(rows);
//		ResponseUtil.write(response, jsonArray);
//		return null;
//	}
//
//	/**
//	 * 查询维修人员信息
//	 * @param response
//	 * @param
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/findMaintainerList")
//	public String findMaintainerList(HttpServletResponse response) throws Exception{
//		List<User> list=userService.findMaintainerList();
//		for (int i=0;i<list.size();i++){
//			list.get(i).setFirstName(list.get(i).getFirstName()+list.get(i).getLastName());
//		}
//		JSONArray jsonArray=new JSONArray();
//
//		JSONObject jsonObject=new JSONObject();
//		jsonObject.put("firstName", "---请选择---");
//		//转为JSON格式的数据
//		jsonArray.add(jsonObject);
//		//将list转为JSON
//		JSONArray rows=JSONArray.fromObject(list);
//		jsonArray.addAll(rows);
//		ResponseUtil.write(response, jsonArray);
//		return null;
//	}
//
//	/**
//	 * 查询流程信息
//	 * @param response
//	 * @param taskId  流程实例ID
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/getRepairByTaskId")
//	public String getRepairByTaskId(HttpServletResponse response,String taskId) throws Exception{
//		//先根据流程ID查询
//		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
//		Repair repair=repairService.getRepairByTaskId(task.getProcessInstanceId());
//		JSONObject result=new JSONObject();
//		result.put("repair", JSONObject.fromObject(repair));
//		ResponseUtil.write(response, result);
//		return null;
//	}
}
