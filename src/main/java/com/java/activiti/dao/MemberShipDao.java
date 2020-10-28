package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.Group;
import com.java.activiti.model.MemberShip;
import org.apache.ibatis.annotations.Param;

public interface MemberShipDao {
	/**
	 * �û�����ķ���
	 * @return
	 */
	MemberShip sessionInfo(Map<String, Object> map);
	
	int deleteAllGroupsByUserId(String userId);
	
	int addMemberShip(MemberShip memberShip);

	/**
	 *  �Զ��巽��
	 * */
	/**
	 * Ϊָ�� userID �û�ָ��ָ�� roleID �Ľ�ɫ
	 * @param userID �û�ID
	 * @param groupID ��ɫID
	 */
	void insert(@Param("userID") String userID, @Param("groupID") String groupID);

	/**
	 * ɾ��ָ���û��Ľ�ɫ
	 * @param userID �û�ID
	 */
	void deleteByUserID(String userID);

	/**
	 * ��ȡָ�� userID ��Ӧ�û�ӵ�еĽ�ɫ
	 * @param userID �û�ID
	 * @return ���� userID ָ���û��Ľ�ɫ
	 */
	List<Group> selectUserGroup(String userID);
}
