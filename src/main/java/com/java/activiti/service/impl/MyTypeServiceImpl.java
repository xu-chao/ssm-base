package com.java.activiti.service.impl;

import com.alibaba.fastjson.JSON;
import com.java.activiti.dao.MyTypeDao;
import com.java.activiti.model.Menu;
import com.java.activiti.model.MyType;
import com.java.activiti.service.MyTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: MyTypeServiceImpl
 * @Description: ö����صĲ���
 * @author: LIUHD
 * @date: 2019��8��9��
 */
@Service("myTypeService")
public class MyTypeServiceImpl implements MyTypeService {
    private static Logger logger = LoggerFactory.getLogger(MyTypeServiceImpl.class);

    @Resource
    private MyTypeDao myTypeDao;

    @Override
    public List<MyType> findMyTypeByTypeCode(String typeCode) {
        return myTypeDao.findMyTypeByTypeCode(typeCode);
    }

    @Override
    public List<MyType> selectMyTypeByTypeCode(String typeCode) {
        return myTypeDao.selectMyTypeByTypeCode(typeCode);
    }

//    @Override
//    public GlobalResultVO addMenu(Menu menu) {
//        // ����Ĭ����ӵĲ˵���״̬Ϊʹ����
//        Integer insertCount = menuDao.insertMenu(menu);
//        if (insertCount != null && insertCount > 0) {
//            // ���±�ǩΪ����ǩ
//            Menu m = new Menu();
//            m.setMenuid(menu.getPid());
//            m.setIs_parent(1);
//            if (200 == updateMenuById(m).getStatus()) {
//                batchDel("menus");
//                return new GlobalResultVO(200, "������ӳɹ�", null);
//            } else {
//                return new GlobalResultVO(400, "�������ʧ��", null);
//            }
//        } else {
//            return new GlobalResultVO(400, "�������ʧ��", null);
//        }
//    }

//    /**
//     * ����idɾ������
//     *
//     * @author
//     * @date 2019��2��15������9:51:50
//     * @param menuid ����
//     * @return
//     */
//    @Override
//    public GlobalResultVO deleteMenuById(String menuid) {
//        Integer deleteCount = menuDao.deleteMenuById(menuid);
//        if (deleteCount != null && deleteCount > 0) {
//            batchDel("menus");
//            return new GlobalResultVO(200, "����ɾ���ɹ�", null);
//        } else {
//            return new GlobalResultVO(400, "����ɾ��ʧ��", null);
//        }
//    }
//
//    /**
//     * @author
//     * @date 2019��8��15������9:51:50
//     * @param menu
//     * @return
//     */
//    @Override
//    public GlobalResultVO updateMenuById(Menu menu) {
//        Integer updateCount = menuDao.updateMenuById(menu);
//        if (updateCount != null && updateCount > 0) {
//            batchDel("menus");
//            return new GlobalResultVO(200, "�����޸ĳɹ�", null);
//        } else {
//            return new GlobalResultVO(400, "�����޸�ʧ��", null);
//        }
//    }
//
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
}

