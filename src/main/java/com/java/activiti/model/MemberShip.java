package com.java.activiti.model;

import java.io.Serializable;

public class MemberShip implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user; // �û�
	private Group group; // ��ɫ
	private String userId;
	private String groupId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	
}
