package com.java.activiti.service;

import java.util.Map;

import com.java.activiti.model.MemberShip;

public interface MemberShipService {
	/**
	 * �û�����ķ���
	 * @return
	 */
	public MemberShip sessionInfo(Map<String, Object> map);
	
	public int deleteAllGroupsByUserId(String userId);
	
	public int addMemberShip(MemberShip memberShip);
}
