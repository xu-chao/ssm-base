package com.java.activiti.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.activiti.pojo.EasyUIOptionalTreeNode;
import com.java.activiti.pojo.GlobalResultVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.java.activiti.model.Group;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.User;
import com.java.activiti.service.GroupService;
import com.java.activiti.util.ResponseUtil;

/**
 * ��ɫ����
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/group")
public class GroupController {
		@Resource
		private GroupService groupService;
		/**
		 * ����ɫ������
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/findGroup")
		public String findGroup(HttpServletResponse response) throws Exception{
			List<Group> list=groupService.findGroup();
			
			JSONArray jsonArray=new JSONArray();

			JSONObject jsonObject=new JSONObject();
			jsonObject.put("trueName", "��ѡ��...");
			//תΪJSON��ʽ������
			jsonArray.add(jsonObject);
			//��listתΪJSON
			JSONArray rows=JSONArray.fromObject(list);
			jsonArray.addAll(rows);
			ResponseUtil.write(response, jsonArray);
			return null;
		}
		
		/**
		 * ��ҳ��ѯ�û�
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("/groupPage")
		public String groupPage(HttpServletResponse response,
				@RequestParam(value = "page", required = false) String page,
				@RequestParam(value = "rows", required = false) String rows,
				User user, String name) throws Exception{
			Map<String,Object> groupMap=new HashMap<String, Object>();
			groupMap.put("id", user.getId());
			if(name != null){
				groupMap.put("name", name);
			}
			//groupMap.put("name", name);
			PageInfo<User> userPage = new PageInfo<User>();
			Integer pageSize=Integer.parseInt(rows);
			userPage.setPageSize(pageSize);
			
			// �ڼ�ҳ
			String pageIndex = page;
			if (pageIndex == null || pageIndex == "") {
				pageIndex = "1";
			}
			userPage.setPageIndex((Integer.parseInt(pageIndex) - 1)
					* pageSize);
			// ȡ����ҳ��
			int userCount=groupService.groupCount(groupMap);
			userPage.setCount(userCount);
			groupMap.put("pageIndex", userPage.getPageIndex());
			groupMap.put("pageSize", userPage.getPageSize());

			List<Group> cusDevPlanList = groupService.groupPage(groupMap);
			JSONObject json = new JSONObject();
			// ��List��ʽת����JSON
			JSONArray jsonArray = JSONArray.fromObject(cusDevPlanList);
			json.put("rows", jsonArray);
			json.put("total", userCount);
			ResponseUtil.write(response, json);
			return null;
		}
		/**
		 * �޸��û�
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("/updateGroup")
		public String updateGroup(HttpServletResponse response,@RequestBody Map<String, Object> map) throws Exception{
			Group group =new Group();
			group.setId((String)map.get("id"));
			group.setName((String)map.get("name"));
			int result=groupService.updateGroup(group);
			JSONObject json=new JSONObject();
			if(result>0){
				json.put("success", true);
			}else{
				json.put("success", false);
			}
			ResponseUtil.write(response, json);
			return null;
		}
		/**
		 * �����h���Ñ�
		 * @param response
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("/deleteGroup")
		public String deleteGroup(HttpServletResponse response,HttpServletRequest request) throws Exception{
			String id=request.getParameter("ids");
			JSONObject json=new JSONObject();
			List<String> list=new ArrayList<String>();
			  String[] strs = id.split(",");
		        for (String str : strs) {
		        	list.add(str);
		        }
		   try {	
				int userResult=groupService.deleteGroup(list);
				if(userResult>0){
					json.put("success", true);
				}else{
					json.put("success", false);
				}
			} catch (Exception e) {
				json.put("success", false);
				e.printStackTrace();
			}
			ResponseUtil.write(response, json);
			return null;
		}
	/**
	 * �ж��Ñ��Ƿ��Ѿ�����
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/existGroupName")
	public String existUser(HttpServletResponse response,String groupID) throws Exception{
		Group result=groupService.findById(groupID);
		JSONObject json=new JSONObject();
		if(result != null){
			json.put("success", true);
		}else{
			json.put("success", false);
		}
		ResponseUtil.write(response, json);
		return null;
	}
		/**
		 * �����Ñ�
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("/groupSave")
		public String groupSave(HttpServletResponse response,Group group) throws Exception{
			int userResult=groupService.addGroup(group);
			JSONObject json=new JSONObject();
			if(userResult>0){
				json.put("success", true);
			}else{
				json.put("success", false);
			}
			ResponseUtil.write(response, json);
			return null;
		}
		
		@RequestMapping("/listAllGroups")
		public String listAllGroups(HttpServletResponse response) throws Exception{
			List<Group> list=groupService.findGroup();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JSONArray.fromObject(list);
			result.put("groupList", jsonArray);
			ResponseUtil.write(response, result);
			return null;
		}

		@RequestMapping("/findGroupByUserId")
		public String findGroupByUserId(HttpServletResponse response,String userId) throws Exception{
			List<Group> groupList=groupService.findByUserId(userId);
			StringBuffer groups=new StringBuffer();
			for(Group g:groupList){
				groups.append(g.getId()+",");
			}
			JSONObject result=new JSONObject();
			if(groups.length()>0){
				result.put("groups", groups.deleteCharAt(groups.length()-1).toString());
			}else{
				result.put("groups",groups.toString());
			}
			ResponseUtil.write(response, result);
			return null;
		}

	/**
	 *
	 * @Title: searchGroupName
	 * @Description: ��ɫ���Զ���ȫ
	 * @param q
	 * @return
	 */
	@RequestMapping(value = "/searchGroupName", method = RequestMethod.POST)
	@ResponseBody
	public List<Group> searchGroupName(String q) {
		List<Group> groupName = groupService.findGroupName(q);
		return groupName;
	}

	/**
	 *
	 * @Title: findGroupMenuByGid
	 * @Description: ���ݽ�ɫid���ض�ӦȨ�޲˵�
	 * @param groupID
	 * @return List<EasyUIOptionalTreeNode>
	 * @author ��
	 * @date 2019��8��13��
	 */
	@RequestMapping(value = "/findGroupMenuByGid" , method = RequestMethod.POST)
	@ResponseBody
	public List<EasyUIOptionalTreeNode> findGroupMenuByGid(
			@RequestParam(value = "id", required = true)String groupID) {
		return groupService.findGroupMenuByGid(groupID);
	}

	/**
	 *
	 * @Title: updateGroupMenu
	 * @Description: ���½�ɫȨ�޲˵�
	 * @param groupID
	 * @param checkedIds
	 * @return GlobalResultVO
	 * @author ��
	 * @date 2019��8��14��
	 */
	@RequestMapping(value = "/updateGroupMenu" , method = RequestMethod.POST)
	@ResponseBody
	public GlobalResultVO updateGroupMenu(
			@RequestParam(value = "id", required = true) String groupID,
			@RequestParam(value = "checkedIds", required = true) String checkedIds) {
		GlobalResultVO result = groupService.updateGroupMenu(groupID, checkedIds);
		return result;
	}
}
