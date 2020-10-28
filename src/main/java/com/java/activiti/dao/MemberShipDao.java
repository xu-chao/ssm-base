package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.Group;
import com.java.activiti.model.MemberShip;
import org.apache.ibatis.annotations.Param;

public interface MemberShipDao {
	/**
	 * 用户登入的方法
	 * @return
	 */
	MemberShip sessionInfo(Map<String, Object> map);
	
	int deleteAllGroupsByUserId(String userId);
	
	int addMemberShip(MemberShip memberShip);

	/**
	 *  自定义方法
	 * */
	/**
	 * 为指定 userID 用户指派指定 roleID 的角色
	 * @param userID 用户ID
	 * @param groupID 角色ID
	 */
	void insert(@Param("userID") String userID, @Param("groupID") String groupID);

	/**
	 * 删除指定用户的角色
	 * @param userID 用户ID
	 */
	void deleteByUserID(String userID);

	/**
	 * 获取指定 userID 对应用户拥有的角色
	 * @param userID 用户ID
	 * @return 返回 userID 指定用户的角色
	 */
	List<Group> selectUserGroup(String userID);
}
