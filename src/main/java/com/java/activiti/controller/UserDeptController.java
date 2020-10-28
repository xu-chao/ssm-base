package com.java.activiti.controller;

import com.java.activiti.model.Dept;
import com.java.activiti.model.User;
import com.java.activiti.model.UserDept;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.UserDeptService;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName:  UserDeptController
 * @Description:���Ź���
 * @author:     ��
 * @date:       2019��8��8��
 */
@Controller
@RequestMapping("/userDept")
public class UserDeptController {

    @Resource
    private UserDeptService userDeptService;

    /**
     *
     * @Title: addDeptAllocation
     * @Description: ���ݲ���ID����û�
     * @return
     */
    @RequestMapping(value = "/addDeptAllocation", method = RequestMethod.POST)
    @ResponseBody
    public String addDeptAllocation(HttpServletResponse response, HttpServletRequest request,
                                    @RequestParam("userID") String userID,@RequestParam("deptID") String deptID) throws Exception{
        User user = new User();
        user.setId(userID);
        Dept dept = new Dept();
        dept.setDeptID(deptID);
        UserDept userDept = new UserDept();
        userDept.setUser(user);
        userDept.setDept(dept);
        userDeptService.addDeptAllocation(userDept);
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ����idɾ������[�޸�״̬]
     * @author ��
     * @date 2019��2��15������9:47:41
     * @param deptID ����
     * @return
     */
    @RequestMapping(value="/deleteUserDept")
    @ResponseBody
    public GlobalResultVO deleteUserDept(@RequestParam("deptID") String deptID,
                                         @RequestParam("userID")String userID) {
        return userDeptService.deleteUserDept(deptID,userID);
    }
}
