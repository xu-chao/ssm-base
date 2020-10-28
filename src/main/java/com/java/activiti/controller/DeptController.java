package com.java.activiti.controller;

import com.java.activiti.model.Dept;
import com.java.activiti.model.Menu;
import com.java.activiti.model.User;
import com.java.activiti.pojo.DeptTree;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.DeptService;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:  MenuController
 * @Description:部门管理
 * @author:     许超
 * @date:       2019年8月8日
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     * 查找所有
     * @author 许超
     * @date 2019年8月9日
     * @return
     */
    @RequestMapping(value="/deptList")
    @ResponseBody
    public List<DeptTree> findAll() {
        return deptService.findDeptList();
    }

    /**
     * 根据部门id查找部门，显示部门详情
     * @author 许超
     * @date 2019年8月9日
     * @param deptID 主键
     * @return
     */
    @RequestMapping("/findDeptById")
    @ResponseBody
    public List<Dept> findById(String deptID) {
        return deptService.findDeptById(deptID);
    }
    /**
     * 根据部门id查找子部门，显示部门详情
     * @author 许超
     * @date 2019年8月9日
     * @param deptID 主键
     * @return
     */
    @RequestMapping(value = "/selectChild", method = RequestMethod.POST)
    @ResponseBody
    public List<Dept> selectChild(String deptID) {
        return deptService.selectChild(deptID);
    }

    /**
     * 添加数据
     * @author 许超
     * @date 2019年8月9日
     * @param dept 部门对象
     * @return
     */
    @RequestMapping(value="/deptadd")
    @ResponseBody
    public GlobalResultVO insert(Dept dept) {
        return deptService.addDept(dept);
    }
    /**
     * 根据id删除数据[修改状态]
     * @author 许超
     * @date 2019年2月15日下午9:47:41
     * @param deptID 主键
     * @return
     */
    @RequestMapping(value="/deptdelete")
    @ResponseBody
    public GlobalResultVO deleteById(String deptID) {
        return deptService.deleteDeptById(deptID);
    }

    /**
     * 根据id修改数据
     * @author 许超
     * @date 2019年8月9日
     * @param dept 部门对象
     * @return
     */
    @RequestMapping(value="/deptupdate")
    @ResponseBody
    public GlobalResultVO updateDeptById(Dept dept) {
        return deptService.updateDeptById(dept);
    }
    /**
     * 根据id修改名字
     * @author 许超
     * @date 2019年8月9日
     * @return
     */
    @RequestMapping(value="/updateNameById")
    @ResponseBody
    public GlobalResultVO updateNameById(@RequestParam("deptID")String deptID,
                                         @RequestParam("deptName")String deptName ) {
        return deptService.updateNameById(deptID,deptName);
    }

//
//    /**
//     *
//     * @Title: loadMenu
//     * @Description: 根据session中的user_id加载部门
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

    /**
     * 根据部门id查找部门，显示用户详情
     * @author 许超
     * @date 2019年8月9日
     * @param deptID 主键
     * @return
     */
    @RequestMapping("/findUserByDeptId")
    @ResponseBody
    public List<User> findUserByDeptId(String deptID) {
        List<User> users = deptService.findUserByDeptId(deptID);
        return users;
    }
}
