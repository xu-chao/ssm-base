package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.User;
import com.java.activiti.model.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

	public User findById(String userId);

	public User userLogin(User user);

//	public List<User> userPage(Map<String, Object> map);

	/**
	 * 查找user相关的绑定信息
	 * add by liuhd at 20191223
	 * */
	public List<UserInfo> userPage(Map<String, Object> map);

	public int userCount(Map<String, Object> map);

	public int deleteUser(List<String> id);

	public int updateUser(User user);

	public int freezeUserById(@Param("id") String id,@Param("status") int status);

	public int addUser(User user);

	public List<User> findUser();

	public List<User> findoperationalManagersList();

	public List<User> findProjectSupervisorList();

	public List<User> findMaintainerList();

	public int updateUserInfo(User user);
}
