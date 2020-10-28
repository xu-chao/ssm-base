package com.java.activiti.service;

import java.util.List;
import java.util.Map;

import com.java.activiti.exception.UserServiceException;
import com.java.activiti.model.User;
import com.java.activiti.model.UserInfo;
import com.java.activiti.pojo.UserAllVO;

public interface UserService {
	
	public User findById(String userId);
	/**
	 * 登入
	 * @return
	 */
	public User userLogin(User user);

	/**
	 * 分页查询用户
	 * @param map
	 * @return
	 */
//	public List<User> userPage(Map<String, Object> map);

	public int userCount(Map<String, Object> map);

	/**
	 * 分页查询用户
	 * @param map
	 * @return
	 */
	public List<UserAllVO> userAllVOPage(Map<String, Object> map);

	/**
	 * 分页查询用户
	 * @param map
	 * @return
	 */
	public List<UserInfo> userPage(Map<String, Object> map);

	public int userAllVOCount(Map<String, Object> map);

	/**
	 * 批量删除用户
	 * @param id
	 * @return
	 */
	public int deleteUser(List<String> id);

	/**
	 * 冻结用户
	 * @param id
	 * @return
	 */
	public int freezeUserById(String id, int status);

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(User user);

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int addUser(User user);

	/**
	 * 查询所有用户填充下拉框
	 * @return
	 */
	public List<User> findUser();

	/**
	 * 查询所有operationalManagers填充下拉框
	 * @return
	 */
	public List<User> findoperationalManagersList();

	/**
	 * 查询所有projectSupervisor填充下拉框
	 * @return
	 */
	public List<User> findProjectSupervisorList();

	/**
	 * 查询所有maintainer填充下拉框
	 * @return
	 */
	public List<User> findMaintainerList();

	/**
	 * 密码更改
	 * @param userID 用户ID
	 * @param passwordInfo 更改的密码信息
	 */
	public void updateModifyPassword(String userID, Map<String, Object> passwordInfo) throws UserServiceException;
	/**
	 * 验证码判断
	 */
	public boolean CheckValidate(String token, int X, int Y);
	/**
	 * 更新用户资料
	 * @param user model对象
	 * @return int 1:success; 0: fail
	 */
	public int updateUserInfo(User user);
}
