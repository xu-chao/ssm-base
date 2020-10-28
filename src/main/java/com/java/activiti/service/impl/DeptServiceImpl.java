package com.java.activiti.service.impl;

import com.alibaba.fastjson.JSON;
import com.java.activiti.dao.DeptDao;
import com.java.activiti.dao.MenuDao;
import com.java.activiti.model.Dept;
import com.java.activiti.model.Menu;
import com.java.activiti.model.User;
import com.java.activiti.pojo.DeptTree;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.DeptService;
import com.java.activiti.service.MenuService;
import com.java.activiti.util.aop.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: MenuServiceImpl
 * @Description: �˵���صĲ���
 * @author: ��
 * @date: 2019��8��9��
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service("deptService")
public class DeptServiceImpl implements DeptService {
    private static Logger logger = LoggerFactory.getLogger(DeptServiceImpl.class);

    @Resource
    private DeptDao deptDao;

//    @Resource
//    private JedisPool jedisPool;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<DeptTree> findDeptList() {
        List<DeptTree> deptTree = deptDao.selectDeptList();
        return deptTree;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Dept> findDeptById(String deptID) {
        return deptDao.selectDeptById(deptID);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Dept findDeptByUserId(String userID) {
        return deptDao.findDeptByUserId(userID);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Dept> selectChild(String deptID) {
        return deptDao.selectChild(deptID);
    }

    @Override
    public GlobalResultVO addDept(Dept dept) {
        // ����Ĭ����ӵĲ˵���״̬Ϊʹ����
        Integer insertCount = deptDao.insertDept(dept);
        if (insertCount != null && insertCount > 0) {
            return new GlobalResultVO(200, "������ӳɹ�", null);
        } else {
            return new GlobalResultVO(400, "�������ʧ��", null);
        }
    }

    /**
     * ����idɾ������
     *
     * @author
     * @date 2019��2��15������9:51:50
     * @param deptID ����
     * @return
     */
    @Override
    public GlobalResultVO deleteDeptById(String deptID) {
        Integer deleteCount = deptDao.deleteDeptById(deptID);
        if (deleteCount != null && deleteCount > 0) {
            return new GlobalResultVO(200, "����ɾ���ɹ�", null);
        } else {
            return new GlobalResultVO(400, "����ɾ��ʧ��", null);
        }
    }
    /**
     * @author
     * @date 2019��2��15������9:51:50
     * @param dept
     * @return
     */
    @Override
    public GlobalResultVO updateDeptById(Dept dept) {
        Integer updateCount = deptDao.updateDeptById(dept);
        if (updateCount != null && updateCount > 0) {
            return new GlobalResultVO(200, "�����޸ĳɹ�", null);
        } else {
            return new GlobalResultVO(400, "�����޸�ʧ��", null);
        }
    }
    /**
     * @author
     * @date 2019��2��15������9:51:50
     * @return
     */
    @Override
    public GlobalResultVO updateNameById(String deptID,String deptName) {
        Integer updateCount = deptDao.updateNameById(deptID,deptName);
        if (updateCount != null && updateCount > 0) {
            return new GlobalResultVO(200, "�����޸ĳɹ�", null);
        } else {
            return new GlobalResultVO(400, "�����޸�ʧ��", null);
        }
    }
//    /**
//     * ����keyǰ׺����ɾ������
//     *
//     * @param key
//     * @return
//     */
//    private void batchDel(String key) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            Set<String> set = jedis.keys(key + "*");
//            Iterator<String> it = set.iterator();
//            while (it.hasNext()) {
//                String keyStr = it.next();
//                jedis.del(keyStr);
//            }
//        } catch (Exception e) {
//        }finally {
//            if(jedis!=null)jedis.close();
//        }
//    }
//
//    /**
//     *
//     * @Title: cloneMenu
//     * @Description: ����menu
//     * @author: ��
//     * @param src
//     * @return
//     */
//    private Menu cloneMenu(Menu src) {
//        Menu menu = new Menu();
//        menu.setIcon(src.getIcon());
//        menu.setMenuid(src.getMenuid());
//        menu.setMenuname(src.getMenuname());
//        menu.setUrl(src.getUrl());
//        menu.setMenus(new ArrayList<Menu>());
//        return menu;
//    }
//
//    @Override
//    public Menu findMenuByUserid(String userid) {
//        // �ӻ����ж�ȡ����
//        Jedis jedis = jedisPool.getResource();
//        Menu menu;
//        try {
//            String easyuiMenusJson = jedis.get("menusEasyui_" + userid);
//            menu = null;
//            if (easyuiMenusJson == null) {
//                // ��ȡ���˵�
//                List<Menu> root = menuDao.selectMenu("-1");
//                // �û��µĲ˵����� �һ���
//                List<Menu> userMenus = findMenuListByUserid(userid);
//                // ���˵�
//                menu = cloneMenu(root.get(0));
//                // �ݴ�һ���˵�
//                Menu _m1 = null;
//                // �ݴ�����˵�
//                Menu _m2 = null;
//                // ��ȡȫ����һ���˵�
//                List<Menu> parentMenus = menuDao.selectMenu("0");
//                // ѭ��һ���˵�
//                for (Menu m1 : parentMenus) {
//                    _m1 = cloneMenu(m1);
//                    // ��ȡ��ǰһ���˵������ж����˵�
//                    List<Menu> leafMenus = menuDao.selectMenu(_m1.getMenuid());
//                    // ѭ��ƥ������˵�
//                    for (Menu m2 : leafMenus) {
//                        for (Menu userMenu : userMenus) {
//                            if (userMenu.getMenuid().equals(m2.getMenuid())) {
//                                // �������˵�����һ���˵�
//                                _m2 = cloneMenu(m2);
//                                _m1.getMenus().add(_m2);
//                            }
//                        }
//                    }
//                    // �ж����˵����ǲżӽ���
//                    if (_m1.getMenus().size() > 0) {
//                        // ��һ���˵����뵽���˵���
//                        menu.getMenus().add(_m1);
//                    }
//                }
//                logger.debug("�����ݿ��ȡ�����û���");
//                //			System.out.println("�����ݿ��ȡ�����û���");
//                jedis.set("menusEasyui_" + userid, JSON.toJSONString(menu));
//            } else {
//                menu = JSON.parseObject(easyuiMenusJson, Menu.class);
//                //			menu = JSON.parseArray(easyuiMenusJson, Menu.class).get(0);
//                //			System.out.println("�ӻ����ȡ");
//                logger.debug("�ӻ����ȡ");
//            }
//        } finally {
//            if(jedis!=null)jedis.close();
//        }
//        return menu;
//    }
//
//    @Override
//    public List<Menu> findMenuListByUserid(String userid) {
//        Jedis jedis = jedisPool.getResource();
//        List<Menu> menuList;
//        try {
//            String menuListJson = jedis.get("menusList_" + userid);
//            menuList = null;
//            if (menuListJson == null) {
//                // 1.�����ݿ��в���������뻺����
//                menuList = menuDao.selectMenuByUserid(userid);
//                jedis.set("menusList_" + userid, JSON.toJSONString(menuList));
//                logger.debug("�����ݿ��в�ѯmenuList");
//            } else {
//                // 2.ֱ�Ӵӻ�������
//                logger.debug("�ӻ����в�ѯmenuList" + menuListJson);
//                menuList = JSON.parseArray(menuListJson, Menu.class);
//            }
//        } finally {
//            if(jedis!=null)jedis.close();
//        }
//        return menuList;
//    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<User> findUserByDeptId(String deptID) {
        return deptDao.findUserByDeptId(deptID);
    }

}

