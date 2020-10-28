package com.java.activiti.service;

import com.java.activiti.model.Menu;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;

import java.util.List;

public interface MenuService {

    /**
     *
     * @Title: findMenuList
     * @Description: ������������
     * @author: ��
     * @return
     */
    List<Tree> findMenuList();

    /**
     *
     * @Title: findMenuById
     * @Description: ���ݲ˵�id���Ҳ˵�����ʾ�˵�����
     * @author: ��
     * @param menuid
     * @return
     */
    List<Menu> findMenuById(String menuid);

    /**
     *
     * @Title: addMenu
     * @Description: �������
     * @author: ��������_dd43
     * @param Menu
     * @return
     */
    GlobalResultVO addMenu(Menu Menu);

    /**
     *
     * @Title: deleteMenuById
     * @Description: ����idɾ������
     * @author: ��
     * @param menuid
     * @return
     */
    GlobalResultVO deleteMenuById(String menuid);

    /**
     *
     * @Title: updateMenuById
     * @Description: ����id�޸�����
     * @author: ��
     * @param Menu
     * @return
     */
    GlobalResultVO updateMenuById(Menu Menu);
    /**
     *
     * @Title: findMenuByUserid
     * @Description: ����userid���ض�Ӧ�˵�
     * @param userid
     * @return Menu
     * @author ��
     * @date 2019��2��16������8:43:39
     */
    Menu findMenuByUserid(String userid);
    /**
     *
     * @Title: findMenuListByUserid
     * @Description: ����userid���ض�Ӧ�˵������б�
     * @param userid
     * @return List<Menu>
     * @author ��
     * @date 2019��2��17������8:55:10
     */
    List<Menu> findMenuListByUserid(String userid);
}
