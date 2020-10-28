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
 * @Description:枚举
 * @author:     LIUHD
 * @date:       2019年11月14日
 */
@Controller
@RequestMapping("/myType")
public class MyTypeController {

    @Resource
    private MyTypeService myTypeService;

//    /**
//     * 查找所有
//     * @author 许超
//     * @date 2019年8月9日
//     * @return
//     */
//    @RequestMapping(value="/menulist")
//    @ResponseBody
//    public List<Tree> findAll() {
//        return menuService.findMenuList();
//    }

    /**
     * 根据枚举类型，显示枚举详情
     * @author LIUHD
     * @date 2019年11月14日
     * @param typeCode 主键
     * @return
     */
    @RequestMapping("/findByTypeCode")
    @ResponseBody
    public List<MyType> findByTypeCode(String typeCode) {
        return myTypeService.selectMyTypeByTypeCode(typeCode);
    }
/**
 * @author      LIUHD
 * 参数 typeCode
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
//     * 添加数据
//     * @author 许超
//     * @date 2019年8月9日
//     * @param menu 菜单对象
//     * @return
//     */
//    @RequestMapping(value="/menuadd")
//    @ResponseBody
//    public GlobalResultVO insert(Menu menu) {
//        return menuService.addMenu(menu);
//    }
//
//    /**
//     * 根据id删除数据[修改状态]
//     * @author 许超
//     * @date 2019年2月15日下午9:47:41
//     * @param menuid 主键
//     * @return
//     */
//    @RequestMapping(value="/menudelete")
//    @ResponseBody
//    public GlobalResultVO deleteById(String menuid) {
//        return menuService.deleteMenuById(menuid);
//    }
//
//    /**
//     * 根据id修改数据
//     * @author 许超
//     * @date 2019年8月9日
//     * @param menu 菜单对象
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
//     * @Description: 根据session中的user_id加载菜单
//     * @return Menu
//     * @author 许超
//     * @date 2019年8月9日
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
