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
	 * 查询所有角色填充下拉框
	 * @return
	 */
	public List<Group> findGroup(){
		return groupDao.findGroup();
	}
	

	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<Group> groupPage(Map<String,Object> map){
		return groupDao.groupPage(map);
	}
	/**
	 * 统计数量
	 * @param map
	 * @return
	 */
	public int groupCount(Map<String,Object> map){
		return groupDao.groupCount(map);
	}
	
	/**
	 * 批量刪除
	 * @param list
	 * @return
	 */
	public int deleteGroup(List<String> list){
		return groupDao.deleteGroup(list);
	}
	
	/**
	 * 修改
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
		// 1.根据角色id获取角色对应的菜单id
		List<String> menuidList = groupDao.selectGroupMenuidByGid(groupID);
		// 2.获取一级菜单
		List<Menu> Parentmenu = menuDao.selectMenuIdName("0");
		// 3.当前角色对象对应的菜单权限
		List<EasyUIOptionalTreeNode> treeList = new ArrayList<EasyUIOptionalTreeNode>();
		// 暂存一级菜单
		EasyUIOptionalTreeNode t1 = null;
		// 暂存二级菜单
		EasyUIOptionalTreeNode t2 = null;
		// 一级菜单遍历
		for (Menu m1 : Parentmenu) {
			t1 = new EasyUIOptionalTreeNode();
			t1.setId(m1.getMenuid());
			t1.setText(m1.getMenuname());
			List<Menu> leafmenu = menuDao.selectMenuIdName(m1.getMenuid());
			// 二级菜单遍历
			for (Menu m2 : leafmenu) {
				t2 = new EasyUIOptionalTreeNode();
				t2.setId(m2.getMenuid());
				t2.setText(m2.getMenuname());
				// 如果角色下包含有这个权限菜单，让它勾选上
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
			// 清空角色下的权限菜单
			groupDao.deleteMenuidByGid(groupID);
			// 权限角色id
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
			System.out.println("更新角色对应的对应的权限菜单 ，清除缓存");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedis!=null)jedis.close();
		}
		return GlobalResultVO.build(200, "权限设置成功");
	}
}
