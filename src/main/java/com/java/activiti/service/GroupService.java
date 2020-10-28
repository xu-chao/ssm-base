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
	 * @Description: like��ѯ��ɫ��
	 * @param name ��ɫ��
	 * @return
	 */
	public List<Group> findGroupName(String name);

	/**
	 * ��ѯ���н�ɫ���������
	 * @return
	 */
	public List<Group> findGroup();

	/**
	 * ��ҳ��ѯ
	 * @param map
	 * @return
	 */
	public List<Group> groupPage(Map<String,Object> map);
	/**
	 * ͳ������
	 * @param map
	 * @return
	 */
	public int groupCount(Map<String,Object> map);
	
	/**
	 * �����h��
	 * @param list
	 * @return
	 */
	public int deleteGroup(List<String> list);
	
	/**
	 * �޸�
	 * @param group
	 * @return
	 */
	public int updateGroup(Group group);
	
	public int addGroup(Group group);

	public Group findById(String groupID);

	/**
	 *
	 * @Title: findGroupMenuByGid
	 * @Description: ��ȡ��ɫ�˵�Ȩ��
	-- 1.���ݽ�ɫid��ȡ��Ӧ��Ȩ�޲˵�id,�����ɫid roleuuid=1
	select role_menu.menuuuid from role,role_menu WHERE role_menu.roleuuid=1;
	-- 2.��ȡ����Ȩ�޲˵�(menuid,menuname)
	SELECT menuid,menuname FROM menu;
	 * @param groupID
	 * @return List<EasyUIOptionalTreeNode>
	 * @author ��
	 * @date 2019��8��13������4:40:34
	 */
	public List<EasyUIOptionalTreeNode> findGroupMenuByGid(String groupID);

	/**
	 *
	 * @Title: updateGroupMenu
	 * @Description: ���½�ɫȨ�޲˵�
	 * @param groupID
	 * @param checkedIds
	 * @return GlobalResultVO
	 * @author ��
	 * @date 2019��8��13��
	 */
	public GlobalResultVO updateGroupMenu(String groupID, String checkedIds);
}
