package com.java.activiti.controller;

import com.java.activiti.model.Menu;
import com.java.activiti.model.User;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.MenuService;
import com.java.activiti.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName:  MenuController
 * @Description:�˵�����
 * @author:     ��
 * @date:       2019��8��8��
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * ��������
     * @author ��
     * @date 2019��8��9��
     * @return
     */
    @RequestMapping(value="/menulist")
    @ResponseBody
    public List<Tree> findAll() {
        return menuService.findMenuList();
    }

    /**
     * ���ݲ˵�id���Ҳ˵�����ʾ�˵�����
     * @author ��
     * @date 2019��8��9��
     * @param menuid ����
     * @return
     */
    @RequestMapping("/menufindById")
    @ResponseBody
    public List<Menu> findById(String menuid) {
        return menuService.findMenuById(menuid);
    }
    /**
     * �������
     * @author ��
     * @date 2019��8��9��
     * @param menu �˵�����
     * @return
     */
    @RequestMapping(value="/menuadd")
    @ResponseBody
    public GlobalResultVO insert(Menu menu) {
        return menuService.addMenu(menu);
    }

    /**
     * ����idɾ������[�޸�״̬]
     * @author ��
     * @date 2019��2��15������9:47:41
     * @param menuid ����
     * @return
     */
    @RequestMapping(value="/menudelete")
    @ResponseBody
    public GlobalResultVO deleteById(String menuid) {
        return menuService.deleteMenuById(menuid);
    }

    /**
     * ����id�޸�����
     * @author ��
     * @date 2019��8��9��
     * @param menu �˵�����
     * @return
     */
    @RequestMapping(value="/menuupdate")
    @ResponseBody
    public GlobalResultVO updateById(Menu menu) {
        return menuService.updateMenuById(menu);
    }


    /**
     *
     * @Title: loadMenu
     * @Description: ����session�е�user_id���ز˵�
     * @return Menu
     * @author ��
     * @date 2019��8��9��
     */
    @RequestMapping(value="/loadMenus")
    @ResponseBody
    public Menu loadMenus() {
        // User user = UserUtil.getSubjectUser();
        String userID = UserUtil.getSubjectUserID();
        Menu menus = null;
        if(userID!=null) {
            menus = menuService.findMenuByUserid(userID);
        }
        return menus;
    }

}
