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
 * @Description:���Ź���
 * @author:     ��
 * @date:       2019��8��8��
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     * ��������
     * @author ��
     * @date 2019��8��9��
     * @return
     */
    @RequestMapping(value="/deptList")
    @ResponseBody
    public List<DeptTree> findAll() {
        return deptService.findDeptList();
    }

    /**
     * ���ݲ���id���Ҳ��ţ���ʾ��������
     * @author ��
     * @date 2019��8��9��
     * @param deptID ����
     * @return
     */
    @RequestMapping("/findDeptById")
    @ResponseBody
    public List<Dept> findById(String deptID) {
        return deptService.findDeptById(deptID);
    }
    /**
     * ���ݲ���id�����Ӳ��ţ���ʾ��������
     * @author ��
     * @date 2019��8��9��
     * @param deptID ����
     * @return
     */
    @RequestMapping(value = "/selectChild", method = RequestMethod.POST)
    @ResponseBody
    public List<Dept> selectChild(String deptID) {
        return deptService.selectChild(deptID);
    }

    /**
     * �������
     * @author ��
     * @date 2019��8��9��
     * @param dept ���Ŷ���
     * @return
     */
    @RequestMapping(value="/deptadd")
    @ResponseBody
    public GlobalResultVO insert(Dept dept) {
        return deptService.addDept(dept);
    }
    /**
     * ����idɾ������[�޸�״̬]
     * @author ��
     * @date 2019��2��15������9:47:41
     * @param deptID ����
     * @return
     */
    @RequestMapping(value="/deptdelete")
    @ResponseBody
    public GlobalResultVO deleteById(String deptID) {
        return deptService.deleteDeptById(deptID);
    }

    /**
     * ����id�޸�����
     * @author ��
     * @date 2019��8��9��
     * @param dept ���Ŷ���
     * @return
     */
    @RequestMapping(value="/deptupdate")
    @ResponseBody
    public GlobalResultVO updateDeptById(Dept dept) {
        return deptService.updateDeptById(dept);
    }
    /**
     * ����id�޸�����
     * @author ��
     * @date 2019��8��9��
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
//     * @Description: ����session�е�user_id���ز���
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

    /**
     * ���ݲ���id���Ҳ��ţ���ʾ�û�����
     * @author ��
     * @date 2019��8��9��
     * @param deptID ����
     * @return
     */
    @RequestMapping("/findUserByDeptId")
    @ResponseBody
    public List<User> findUserByDeptId(String deptID) {
        List<User> users = deptService.findUserByDeptId(deptID);
        return users;
    }
}
