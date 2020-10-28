package com.java.activiti.service;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.Group;
import com.java.activiti.pojo.EasyUIOptionalTreeNode;
import com.java.activiti.pojo.GlobalResultVO;

public interface GroupService {

	public List<Group> findByUserId(String id);

	/**
	 *
	 * @Title: findUserName
	 * @Description: like查询角色名
	 * @param name 角色名
	 * @return
	 */
	public List<Group> findGroupName(String name);

	/**
	 * 查询所有角色填充下拉框
	 * @return
	 */
	public List<Group> findGroup();

	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<Group> groupPage(Map<String,Object> map);
	/**
	 * 统计数量
	 * @param map
	 * @return
	 */
	public int groupCount(Map<String,Object> map);
	
	/**
	 * 批量刪除
	 * @param list
	 * @return
	 */
	public int deleteGroup(List<String> list);
	
	/**
	 * 修改
	 * @param group
	 * @return
	 */
	public int updateGroup(Group group);
	
	public int addGroup(Group group);

	public Group findById(String groupID);

	/**
	 *
	 * @Title: findGroupMenuByGid
	 * @Description: 获取角色菜单权限
	-- 1.根据角色id获取对应的权限菜单id,比如角色id roleuuid=1
	select role_menu.menuuuid from role,role_menu WHERE role_menu.roleuuid=1;
	-- 2.获取所有权限菜单(menuid,menuname)
	SELECT menuid,menuname FROM menu;
	 * @param groupID
	 * @return List<EasyUIOptionalTreeNode>
	 * @author 许超
	 * @date 2019年8月13日下午4:40:34
	 */
	public List<EasyUIOptionalTreeNode> findGroupMenuByGid(String groupID);

	/**
	 *
	 * @Title: updateGroupMenu
	 * @Description: 更新角色权限菜单
	 * @param groupID
	 * @param checkedIds
	 * @return GlobalResultVO
	 * @author 许超
	 * @date 2019年8月13日
	 */
	public GlobalResultVO updateGroupMenu(String groupID, String checkedIds);
}
