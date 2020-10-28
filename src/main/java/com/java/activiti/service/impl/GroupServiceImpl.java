package com.java.activiti.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.java.activiti.dao.MenuDao;
import com.java.activiti.model.Menu;
import com.java.activiti.pojo.EasyUIOptionalTreeNode;
import com.java.activiti.pojo.GlobalResultVO;
import org.springframework.stereotype.Service;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.model.Group;
import com.java.activiti.service.GroupService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("groupService")
public class GroupServiceImpl implements GroupService{

	@Resource
	private GroupDao groupDao;

	@Resource
	private MenuDao menuDao;

	@Resource
	private JedisPool jedisPool;
	
	public List<Group> findByUserId(String id){
		return groupDao.findByUserId(id);
	}

	@Override
	public List<Group> findGroupName(String name) { return groupDao.selectGroupName(name); }

	/**
	 * ��ѯ���н�ɫ���������
	 * @return
	 */
	public List<Group> findGroup(){
		return groupDao.findGroup();
	}
	

	/**
	 * ��ҳ��ѯ
	 * @param map
	 * @return
	 */
	public List<Group> groupPage(Map<String,Object> map){
		return groupDao.groupPage(map);
	}
	/**
	 * ͳ������
	 * @param map
	 * @return
	 */
	public int groupCount(Map<String,Object> map){
		return groupDao.groupCount(map);
	}
	
	/**
	 * �����h��
	 * @param list
	 * @return
	 */
	public int deleteGroup(List<String> list){
		return groupDao.deleteGroup(list);
	}
	
	/**
	 * �޸�
	 * @param group
	 * @return
	 */
	public int updateGroup(Group group){
		return groupDao.updateGroup(group);
	}
	
	public int addGroup(Group group){
		return groupDao.addGroup(group);
	}

	@Override
	public Group findById(String groupID) {
		return groupDao.findById(groupID);
	}

	@Override
	public List<EasyUIOptionalTreeNode> findGroupMenuByGid(String groupID) {
		// 1.���ݽ�ɫid��ȡ��ɫ��Ӧ�Ĳ˵�id
		List<String> menuidList = groupDao.selectGroupMenuidByGid(groupID);
		// 2.��ȡһ���˵�
		List<Menu> Parentmenu = menuDao.selectMenuIdName("0");
		// 3.��ǰ��ɫ�����Ӧ�Ĳ˵�Ȩ��
		List<EasyUIOptionalTreeNode> treeList = new ArrayList<EasyUIOptionalTreeNode>();
		// �ݴ�һ���˵�
		EasyUIOptionalTreeNode t1 = null;
		// �ݴ�����˵�
		EasyUIOptionalTreeNode t2 = null;
		// һ���˵�����
		for (Menu m1 : Parentmenu) {
			t1 = new EasyUIOptionalTreeNode();
			t1.setId(m1.getMenuid());
			t1.setText(m1.getMenuname());
			List<Menu> leafmenu = menuDao.selectMenuIdName(m1.getMenuid());
			// �����˵�����
			for (Menu m2 : leafmenu) {
				t2 = new EasyUIOptionalTreeNode();
				t2.setId(m2.getMenuid());
				t2.setText(m2.getMenuname());
				// �����ɫ�°��������Ȩ�޲˵���������ѡ��
				for (String menuid : menuidList) {
					if (m2.getMenuid().equals(menuid)) {
						t2.setChecked(true);
					}
				}
				t1.getChildren().add(t2);
			}
			treeList.add(t1);
		}
		return treeList;
	}

	@Override
	public GlobalResultVO updateGroupMenu(String groupID, String checkedIds) {
		Jedis jedis = jedisPool.getResource();
		try {
			// ��ս�ɫ�µ�Ȩ�޲˵�
			groupDao.deleteMenuidByGid(groupID);
			// Ȩ�޽�ɫid
			if (checkedIds != null) {
				String[] ids = checkedIds.split(",");
				for (String menuuuid : ids) {
					groupDao.insertGroupMenu(menuuuid, groupID);
				}
			}
			List<String> useridList = groupDao.selectUseridByGid(groupID);
			for (String userid : useridList) {

				jedis.del("menusEasyui_" + userid);
				jedis.del("menusList_" + userid);
			}
			System.out.println("���½�ɫ��Ӧ�Ķ�Ӧ��Ȩ�޲˵� ���������");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedis!=null)jedis.close();
		}
		return GlobalResultVO.build(200, "Ȩ�����óɹ�");
	}
}
