package com.java.activiti.controller;

import com.java.activiti.model.Menu;
import com.java.activiti.model.MyType;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.MenuService;
import com.java.activiti.service.MyTypeService;
import com.java.activiti.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName:  MenuController
 * @Description:ö��
 * @author:     LIUHD
 * @date:       2019��11��14��
 */
@Controller
@RequestMapping("/myType")
public class MyTypeController {

    @Resource
    private MyTypeService myTypeService;

//    /**
//     * ��������
//     * @author ��
//     * @date 2019��8��9��
//     * @return
//     */
//    @RequestMapping(value="/menulist")
//    @ResponseBody
//    public List<Tree> findAll() {
//        return menuService.findMenuList();
//    }

    /**
     * ����ö�����ͣ���ʾö������
     * @author LIUHD
     * @date 2019��11��14��
     * @param typeCode ����
     * @return
     */
    @RequestMapping("/findByTypeCode")
    @ResponseBody
    public List<MyType> findByTypeCode(String typeCode) {
        return myTypeService.selectMyTypeByTypeCode(typeCode);
    }
/**
 * @author      LIUHD
 * ���� typeCode
 * @description findMyTypeByTypeCode
 * @date        2020/1/13 16:11
 * @Version     1.0
 */
    @RequestMapping("/findMyTypeByTypeCode")
    @ResponseBody
    public List<MyType> findMyTypeByTypeCode(String typeCode) {
        return myTypeService.findMyTypeByTypeCode(typeCode);
    }
//    /**
//     * �������
//     * @author ��
//     * @date 2019��8��9��
//     * @param menu �˵�����
//     * @return
//     */
//    @RequestMapping(value="/menuadd")
//    @ResponseBody
//    public GlobalResultVO insert(Menu menu) {
//        return menuService.addMenu(menu);
//    }
//
//    /**
//     * ����idɾ������[�޸�״̬]
//     * @author ��
//     * @date 2019��2��15������9:47:41
//     * @param menuid ����
//     * @return
//     */
//    @RequestMapping(value="/menudelete")
//    @ResponseBody
//    public GlobalResultVO deleteById(String menuid) {
//        return menuService.deleteMenuById(menuid);
//    }
//
//    /**
//     * ����id�޸�����
//     * @author ��
//     * @date 2019��8��9��
//     * @param menu �˵�����
//     * @return
//     */
//    @RequestMapping(value="/menuupdate")
//    @ResponseBody
//    public GlobalResultVO updateById(Menu menu) {
//        return menuService.updateMenuById(menu);
//    }
//
//
//    /**
//     *
//     * @Title: loadMenu
//     * @Description: ����session�е�user_id���ز˵�
//     * @return Menu
//     * @author ��
//     * @date 2019��8��9��
//     */
//    @RequestMapping(value="/loadMenus")
//    @ResponseBody
//    public Menu loadMenus() {
//        // User user = UserUtil.getSubjectUser();
//        String userID = UserUtil.getSubjectUserID();
//        Menu menus = null;
//        if(userID!=null) {
//            menus = menuService.findMenuByUserid(userID);
//        }
//        return menus;
//    }

}
