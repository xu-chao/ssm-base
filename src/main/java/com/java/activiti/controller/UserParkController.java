package com.java.activiti.controller;

import com.java.activiti.model.*;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.UserDeptService;
import com.java.activiti.service.UserParkService;
import com.java.activiti.service.UserService;
import com.java.activiti.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserDeptController
 * @Description:���Ź���
 * @author: ��
 * @date: 2019��8��8��
 */
@Controller
@RequestMapping("/userPark")
public class UserParkController {

    @Resource
    private UserParkService userParkService;
    @Resource
    private UserService userService;

    /**
     * �û���ɫ�б�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAllUser")
    @ApiOperation(value = "�û���԰�б�", notes = "success=true��ʾ�ɹ���=false��ʾʧ��")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    public String findAllUser(HttpServletResponse response, String rows, String page,
                              @RequestParam(value = "userID", required = false) String userID,
                              @RequestParam(value = "deptPid", required = false) String deptPid) throws Exception {
        PageInfo<User> userPage = new PageInfo<User>();
        Map<String, Object> userMap = new HashMap<String, Object>();
        //��ȡ�ò��ŵ����� �������Ϊ�ܲ��ÿ�
        if (deptPid != null) {
            if (deptPid.equals("-1")) {
                deptPid = null;
            }
        }
        userMap.put("id", userID);
        userMap.put("deptPid", deptPid);
        Integer pageSize = Integer.parseInt(rows);
        userPage.setPageSize(pageSize);

        // �ڼ�ҳ
        String pageIndex = page;
        if (pageIndex == null || pageIndex == "") {
            pageIndex = "1";
        }
        userPage.setPageIndex((Integer.parseInt(pageIndex) - 1)
                * pageSize);
        // ȡ����ҳ��
        int userCount = userService.userCount(userMap);
        userPage.setCount(userCount);
        userMap.put("pageIndex", userPage.getPageIndex());
        userMap.put("pageSize", userPage.getPageSize());

        List<UserPark> userList = userParkService.findAllUser(userMap);

        JSONArray jsonArray = JSONArray.fromObject(userList);
        JSONObject result = new JSONObject();
        result.put("rows", jsonArray);
        result.put("total", userCount);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/updateUserPark")
    @ApiOperation(value = "�û���԰�б�", notes = "success=true��ʾ�ɹ���=false��ʾʧ��")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    public String updateUserPark(HttpServletResponse response, @RequestParam("userID") String userID,
                                 @RequestParam("parkID") String parkID) throws Exception {
        UserPark userPark = new UserPark();
        JSONObject json = new JSONObject();
        int userParkResult = -1;
        try {
            userPark.setUserID(userID);
            userPark.setParkID(Integer.parseInt(parkID));
            userParkResult = userParkService.addParkAllocation(userPark);
            if (userParkResult > 0) {
                json.put("success", true);
            } else {
                json.put("success", false);
                json.put("msg", "������ֵ����ȷ��");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "������ֵ����ȷ��");
        }

        ResponseUtil.write(response, json);
        return null;
    }
//
//    /**
//     * ����idɾ������[�޸�״̬]
//     * @author ��
//     * @date 2019��2��15������9:47:41
//     * @param deptID ����
//     * @return
//     */
//    @RequestMapping(value="/deleteUserDept")
//    @ResponseBody
//    public GlobalResultVO deleteUserDept(@RequestParam("deptID") String deptID,
//                                         @RequestParam("userID")String userID) {
//        return userDeptService.deleteUserDept(deptID,userID);
//    }
}
