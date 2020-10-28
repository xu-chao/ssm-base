package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.Group;
import org.apache.ibatis.annotations.Param;

public interface GroupDao {
	
	/**
	 * 查询所有角色填充下拉框
	 * @return
	 */
	List<Group> findGroup();
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<Group> groupPage(Map<String,Object> map);
	/**
	 * 统计数量
	 * @param map
	 * @return
	 */
	int groupCount(Map<String,Object> map);
	
	/**
	 * 批量刪除
	 * @param list
	 * @return
	 */
	int deleteGroup(List<String> list);
	
	/**
	 * 修改
	 * @param group
	 * @return
	 */
	int updateGroup(Group group);
	
	/**
	 * 新增
	 * @param group
	 * @return
	 */
	int addGroup(Group group);

	Group findById(String groupID);

	List<Group> findByUserId(String id);

	/**
	 *
	 * @Title: selectGroupName
	 * @Description: 查询角色，自动补全
	 * @param name
	 * @return
	 */
	List<Group> selectGroupName(@Param("name")String name);

	/**
	 * 获取角色对应的ID
	 * @param groupName 角色名
	 * @return 返回角色的ID
	 */
	String getGroupID(String groupName);

	/**
	 *
	 * @Title: selectRoleMenuidByRoleid
	 * @Description: 根据角色id获取对应的权限菜单id
	 * @param groupID
	 * @return List<Integer>
	 * @author 许超
	 * @date 2019年8月13日下午4:35:08
	 */
	public List<String> selectGroupMenuidByGid(@Param("groupID") String groupID);

	/**
	 *
	 * @Title: deleteMenuidByGid
	 * @Description: 根据groupID删除拥有的角色信息
	 * @param groupID void
	 * @author 许超
	 * @date 2019年8月13日
	 */
	public void deleteMenuidByGid(@Param("groupID") String groupID);

	/**
	 *
	 * @Title: insertGroupMenu
	 * @Description: 新增角色权限菜单关系
	 * @param menuuuid
	 * @param groupID void
	 * @author 许超
	 * @date 2019年8月13日
	 */
	public void insertGroupMenu(@Param("menuuuid") String menuuuid, @Param("groupID") String groupID);

	/**
	 *
	 * @Title: selectUseridByGid
	 * @Description: 根据角色id获取对应的用户id
	 * @param groupID
	 * @return List<Integer>
	 * @author 许超
	 * @date 2019年8月13日
	 */
	public List<String> selectUseridByGid(@Param("groupID") String groupID);

}
