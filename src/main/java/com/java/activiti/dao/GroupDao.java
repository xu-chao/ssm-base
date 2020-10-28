package com.java.activiti.dao;

import java.util.List;
import java.util.Map;

import com.java.activiti.model.Group;
import org.apache.ibatis.annotations.Param;

public interface GroupDao {
	
	/**
	 * ��ѯ���н�ɫ���������
	 * @return
	 */
	List<Group> findGroup();
	
	/**
	 * ��ҳ��ѯ
	 * @param map
	 * @return
	 */
	List<Group> groupPage(Map<String,Object> map);
	/**
	 * ͳ������
	 * @param map
	 * @return
	 */
	int groupCount(Map<String,Object> map);
	
	/**
	 * �����h��
	 * @param list
	 * @return
	 */
	int deleteGroup(List<String> list);
	
	/**
	 * �޸�
	 * @param group
	 * @return
	 */
	int updateGroup(Group group);
	
	/**
	 * ����
	 * @param group
	 * @return
	 */
	int addGroup(Group group);

	Group findById(String groupID);

	List<Group> findByUserId(String id);

	/**
	 *
	 * @Title: selectGroupName
	 * @Description: ��ѯ��ɫ���Զ���ȫ
	 * @param name
	 * @return
	 */
	List<Group> selectGroupName(@Param("name")String name);

	/**
	 * ��ȡ��ɫ��Ӧ��ID
	 * @param groupName ��ɫ��
	 * @return ���ؽ�ɫ��ID
	 */
	String getGroupID(String groupName);

	/**
	 *
	 * @Title: selectRoleMenuidByRoleid
	 * @Description: ���ݽ�ɫid��ȡ��Ӧ��Ȩ�޲˵�id
	 * @param groupID
	 * @return List<Integer>
	 * @author ��
	 * @date 2019��8��13������4:35:08
	 */
	public List<String> selectGroupMenuidByGid(@Param("groupID") String groupID);

	/**
	 *
	 * @Title: deleteMenuidByGid
	 * @Description: ����groupIDɾ��ӵ�еĽ�ɫ��Ϣ
	 * @param groupID void
	 * @author ��
	 * @date 2019��8��13��
	 */
	public void deleteMenuidByGid(@Param("groupID") String groupID);

	/**
	 *
	 * @Title: insertGroupMenu
	 * @Description: ������ɫȨ�޲˵���ϵ
	 * @param menuuuid
	 * @param groupID void
	 * @author ��
	 * @date 2019��8��13��
	 */
	public void insertGroupMenu(@Param("menuuuid") String menuuuid, @Param("groupID") String groupID);

	/**
	 *
	 * @Title: selectUseridByGid
	 * @Description: ���ݽ�ɫid��ȡ��Ӧ���û�id
	 * @param groupID
	 * @return List<Integer>
	 * @author ��
	 * @date 2019��8��13��
	 */
	public List<String> selectUseridByGid(@Param("groupID") String groupID);

}
