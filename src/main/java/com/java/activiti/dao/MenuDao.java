package com.java.activiti.dao;

import com.java.activiti.model.Menu;
import com.java.activiti.pojo.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    /**
     *
     * @Title: selectMenuList
     * @Description: ������������
     * @author: ��
     * @return
     */
    List<Tree> selectMenuList();

    /**
     *
     * @Title: selectMenuById
     * @Description: ���ݲ˵�id���Ҳ˵�����ʾ�˵�����
     * @author: ��
     * @param menuid
     * @return
     */
    List<Menu> selectMenuById(@Param("menuid") String menuid);

    /**
     *
     * @Title: insertMenu
     * @Description: �������
     * @author: ��
     * @param Menu
     * @return
     */
    Integer insertMenu(Menu Menu);

    /**
     *
     * @Title: deleteMenuById
     * @Description: ����idɾ������[�޸�״̬]
     * @author: ��
     * @param menuid
     * @return
     */
    Integer deleteMenuById(String menuid);

    /**
     *
     * @Title: updateMenuById
     * @Description: ����id�޸�����
     * @author: ��
     * @param Menu
     * @return
     */
    Integer updateMenuById(Menu Menu);

    /**
     *
     * @Title: selectMenuIdName
     * @Description: ����pid��ȡ����Ȩ�޲˵�(menuid,menuname)
     * @return List<Menu>
     * @author ��
     * @date 2019��2��16������7:05:10
     */
    public List<Menu> selectMenuIdName(@Param("pid") String pid);

    /**
     *
     * @Title: selectMenuByUserid
     * @Description: ����userid���ض�Ӧ�˵�
     * @param userid
     * @return List<Menu>
     * @author ��
     * @date 2019��2��16������8:40:39
     */
    public List<Menu> selectMenuByUserid(@Param("userid") String userid);

    /**
     *
     * @Title: selectMenu
     * @Description: ��ѯ���в˵�����������
     * @param pid
     * @return List<Menu>
     * @author ��
     * @date 2019��2��16������9:04:26
     */
    public List<Menu> selectMenu(@Param("pid") String pid);
}
