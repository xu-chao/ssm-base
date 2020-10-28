package com.java.activiti.controller;

import com.java.activiti.model.*;
import com.java.activiti.service.*;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ҵ����
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/repair")
public class RepairController {

	@Resource
	private RepairService repairService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private UserService userService;
	@Resource
	private DeptService deptService;
	@Resource
	private FileService fileService;
	@Resource
	private MailService mailService;
	/**
	 * ��ҳ��ѯҵ��
	 * @param response
	 * @param rows
	 * @param page
	 * @param userId
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/repairPage")
	public String repairPage(HttpServletResponse response, String rows,
			String page, String userId) throws Exception {
		PageInfo<Repair> repairPage = new PageInfo<Repair>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (userId==""||"".equals(userId)||userId==null){
			userId = UserUtil.getSubjectUserID();
		}
		map.put("userId", userId);
		Integer pageSize = Integer.parseInt(rows);
		repairPage.setPageSize(pageSize);
		if (page == null || page.equals("")) {
			page = "1";
		}
		repairPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
		map.put("pageIndex", repairPage.getPageIndex());
		map.put("pageSize", repairPage.getPageSize());
		int repairCount = repairService.repairCount(map);
		List<Repair> repairList = repairService.repairPage(map);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(repairList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", repairCount);
		ResponseUtil.write(response, result);
		return null;
	}

	@RequestMapping("/repairThroughPage")
	public String repairThroughPage(HttpServletResponse response, String rows, String flag,
							 String page, String userId,String searchType, String searchValue, String startDate, String endDate) throws Exception {
		PageInfo<Repair> repairPage = new PageInfo<Repair>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (userId==""||"".equals(userId)||userId==null){
			userId = UserUtil.getSubjectUserID();
		}
		map.put("userId", userId);
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		Integer pageSize = Integer.parseInt(rows);
		repairPage.setPageSize(pageSize);
		if (page == null || page.equals("")) {
			page = "1";
		}
		repairPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
		map.put("pageIndex", repairPage.getPageIndex());
		map.put("pageSize", repairPage.getPageSize());
		int repairCount = repairService.repairThroughCount(map);
		List<Repair> repairList = repairService.repairThroughPage(map);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(repairList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", repairCount);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * ��ӹ��ϵ�
	 * @param repair
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addRepair")
	public String addRepair(Repair repair, HttpServletResponse response,
							@RequestParam("userId") String userId,
							@RequestParam("repairFileID")String repairFileID,
							HttpServletRequest request)throws Exception{
		JSONObject result=new JSONObject();
		User user=new User();
		user.setId(userId);
		int resultTotal=0;
		if(repair.getRecordDate() == null){
			repair.setRecordDate(new Date());
		}
		//����û�����
		repair.setUser(user);
		repair.setRepairFileID(repairFileID);
		//���uuid
		repair.setId(UuidUtil.uuidUtil());
		resultTotal=repairService.addRepair(repair);

		if(resultTotal>0){
			result.put("success", true);
		}else{
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startApply")
	@ResponseBody
	public String startApply(HttpServletResponse response,@RequestParam("userID") String userID,
							 @RequestParam("taskID") String taskID) throws Exception{
//		String userIDs = "";
		Map<String,Object> variables=new HashMap<String,Object>();
		variables.put("repairID", taskID);
		variables.put("projectSupervisor", userID);
//		List<User> users = userService.findoperationalManagersList();
//		for (User user:users) {
//			userID = user.getId();
//			userIDs = userIDs + userID+",";
//		}
//		userIDs = userIDs.substring(0,userIDs.length()-1);
		variables.put("Operators", UserUtil.getSubjectUserID());
		// ��������
		ProcessInstance pi= runtimeService.startProcessInstanceByKey("OMMSing",variables);
		// ��������ʵ��Id��ѯ����
		Task task=taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		 // ���
		taskService.complete(task.getId());
		Repair repair=repairService.findById(taskID);
		//�޸�״̬
		repair.setState("�����");
		repair.setProcessInstanceId(pi.getProcessInstanceId());
		 // �޸���ά��״̬
		repairService.updateRepair(repair);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * ���ӹ�����Ո���ύ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startRedictApply")
	@ResponseBody
	public String startRedictApply(HttpServletResponse response, HttpServletRequest request, Model model,
								   @RequestParam("userId") String userId, @RequestParam("projectSupervisor") String projectSupervisor,
								   @RequestParam("repairFileID")String repairFileID, @RequestParam("isSend") boolean isSend, Repair repair) throws Exception{

		String basePath =request.getServletContext().getRealPath("\\"); // ��ȡ����·��
		JSONObject result=new JSONObject();
		User user = new User();
		user.setId(userId);
		int resultTotal=0;
		if(repair.getRecordDate() == null){
			repair.setRecordDate(new Date());
		}
		//����û�����
		repair.setUser(user);
		repair.setRepairFileID(repairFileID);
		//���uuid
		String repairID = UuidUtil.uuidUtil();
		repair.setId(repairID);
		resultTotal=repairService.addRepair(repair);

		if(resultTotal>0){
//			String userIDs = "";
			Map<String,Object> variables=new HashMap<String,Object>();
			variables.put("repairID", repairID);
			variables.put("projectSupervisor", projectSupervisor);
//			List<User> users = userService.findoperationalManagersList();
//			for (User userfor:users) {
//				userId = userfor.getId();
//				userIDs = userIDs + userId+",";
//			}
//			userIDs = userIDs.substring(0,userIDs.length()-1);
			variables.put("Operators", UserUtil.getSubjectUserID());
			// ��������
			ProcessInstance pi= runtimeService.startProcessInstanceByKey("OMMSing",variables);
			// ��������ʵ��Id��ѯ����
			Task task=taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
			// ���
			taskService.complete(task.getId());
			Repair repairin=repairService.findById(repairID);
			//�޸�״̬
			repairin.setState("�����");
			//model.addAttribute("state",repairin.getState());
			repairin.setProcessInstanceId(pi.getProcessInstanceId());
			// �޸���ά��״̬
			repairService.updateRepair(repairin);
			if(isSend == true){
				User sendUser = userService.findById(projectSupervisor);
//				String emailName = sendUser.getFirstName()+sendUser.getLastName();
				String to = sendUser.getEmail();
				String subject = "ά�޵���(" + repairID + ")";
				Repair context = repairin;
				Boolean isSending = mailService.sendMail(subject,context,UserUtil.getSubjectUser().getAllName(),to,basePath);
				result.put("success", true);
			}else {
				result.put("success", true);
			}
			ResponseUtil.write(response, result);
			return null;
		}else{
			result.put("success", false);
			return null;
		}
	}

	/**
	 * ��ѯ������Ŀ��������Ϣ
	 * @param response
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findProjectSupervisorList")
	public String findProjectSupervisorList(HttpServletResponse response) throws Exception{
		List<User> list=userService.findProjectSupervisorList();
		for (int i=0;i<list.size();i++){
			list.get(i).setFirstName(list.get(i).getFirstName()+list.get(i).getLastName());
		}
		JSONArray jsonArray=new JSONArray();

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("firstName", "---��ѡ��---");
		//תΪJSON��ʽ������
		jsonArray.add(jsonObject);
		//��listתΪJSON
		JSONArray rows=JSONArray.fromObject(list);
		jsonArray.addAll(rows);
		ResponseUtil.write(response, jsonArray);
		return null;
	}

	/**
	 * ��ѯά����Ա��Ϣ
	 * @param response
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findMaintainerList")
	public String findMaintainerList(HttpServletResponse response) throws Exception{
		List<User> list=userService.findMaintainerList();
		for (int i=0;i<list.size();i++){
			list.get(i).setFirstName(list.get(i).getFirstName()+list.get(i).getLastName());
		}
		JSONArray jsonArray=new JSONArray();

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("firstName", "---��ѡ��---");
		//תΪJSON��ʽ������
		jsonArray.add(jsonObject);
		//��listתΪJSON
		JSONArray rows=JSONArray.fromObject(list);
		jsonArray.addAll(rows);
		ResponseUtil.write(response, jsonArray);
		return null;
	}

	/**
	 * ��ѯ������Ϣ
	 * @param response
	 * @param taskId  ����ʵ��ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRepairByTaskId")
	public String getRepairByTaskId(HttpServletResponse response,String taskId) throws Exception{
		//�ȸ�������ID��ѯ
		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		Repair repair=repairService.getRepairByTaskId(task.getProcessInstanceId());
		WorkOrder workOrder=repairService.getWorkOrderByRepairID(repair.getId());
		Dept dept=deptService.findDeptByUserId(repair.getUser().getId());
		JSONObject result=new JSONObject();
		result.put("repair", JSONObject.fromObject(repair));
		result.put("workOrder", JSONObject.fromObject(workOrder));
		result.put("dept", JSONObject.fromObject(dept));
		ResponseUtil.write(response, result);
		return null;
	}
//	/**
//	 * �ϴ��ļ�
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/uploadFile")
//	public String uploadFile(
//			@RequestParam("fileID")String fileID,
//			@RequestParam("file") CommonsMultipartFile file,
//			HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		int out =fileService.addFileImage(file,request,fileID);
//
//        //�ϴ��ɹ�
//		JSONObject result=new JSONObject();
//		if(out>0){
//			result.put("success", true);
//		}else{
//			result.put("success", false);
//		}
//		ResponseUtil.write(response, result);
//		return null;
//	}
//	/**
//	 * ���Ҹ��ɹ����������ļ�
//	 */
//	@RequestMapping(value = "findFiles", method=RequestMethod.POST)
//	public String findFiles(@RequestParam("repairFileID")String repairFileID,HttpServletResponse response) throws Exception {
//
//		List<com.java.activiti.model.File> files = fileService.findFilesByRepairID(repairFileID);
//		JSONArray jsonArray=new JSONArray();
//		JSONArray rows=JSONArray.fromObject(files);
//		jsonArray.addAll(rows);
//		ResponseUtil.write(response, jsonArray);
//		return null;
//	}
	/*
	 * �����ļ�
	 */

//	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
//	@ResponseBody
//	public String downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("path")String path) throws IOException {
////      String firstName =  path.substring(0,path.lastIndexOf("\\") + 1);
//		String basePath =request.getServletContext().getRealPath("\\"); // ��ȡ����·��
//		/* ��һ��:�����ļ�·����ȡ�ļ� */
////      @RequestParam("path")String path
//
//		java.io.File file = new java.io.File(basePath + path);
//		if (file.exists()) { // �ļ�����
//			/* �ڶ����������Ѵ��ڵ��ļ��������ļ������� */
//			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//			/* ����������������������СΪ��������ַ��� */
//			byte[] buffer = new byte[inputStream.available()]; // int available() ����ֵΪ������δ��ȡ���ֽڵ�����
//			/* ���Ĳ������ļ����������ֽ����������� */
//			inputStream.read(buffer);
//			/* ���岽�� �ر������� */
//			inputStream.close();
//
//			String fileName = file.getName();// ��ȡ�ļ���
//			response.reset();
//			response.addHeader("Content-Disposition",
//					"attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
//			response.addHeader("Content-Length", "" + file.length());
//
//			/* �������������ļ������ */
//			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//			response.setContentType("application/octet-stream");
//			/* ���߲����ѻ�����������д���ļ������ */
//			outputStream.write(buffer);
//			/* �ڰ˲���ˢ�����������������б�������ֽ� */
//			outputStream.flush();
//			/* �ھŲ����ر������ */
//			outputStream.close();
//
//		} //end  if (file.exists())
//
//		return null;
//	}
}
