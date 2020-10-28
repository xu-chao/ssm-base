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
 * @Description:部门管理
 * @author: 许超
 * @date: 2019年8月8日
 */
@Controller
@RequestMapping("/userPark")
public class UserParkController {

    @Resource
    private UserParkService userParkService;
    @Resource
    private UserService userService;

    /**
     * 用户角色列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAllUser")
    @ApiOperation(value = "用户公园列表", notes = "success=true表示成功，=false表示失败")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String findAllUser(HttpServletResponse response, String rows, String page,
                              @RequestParam(value = "userID", required = false) String userID,
                              @RequestParam(value = "deptPid", required = false) String deptPid) throws Exception {
        PageInfo<User> userPage = new PageInfo<User>();
        Map<String, Object> userMap = new HashMap<String, Object>();
        //获取该部门的中心 如果部门为总部置空
        if (deptPid != null) {
            if (deptPid.equals("-1")) {
                deptPid = null;
            }
        }
        userMap.put("id", userID);
        userMap.put("deptPid", deptPid);
        Integer pageSize = Integer.parseInt(rows);
        userPage.setPageSize(pageSize);

        // 第几页
        String pageIndex = page;
        if (pageIndex == null || pageIndex == "") {
            pageIndex = "1";
        }
        userPage.setPageIndex((Integer.parseInt(pageIndex) - 1)
                * pageSize);
        // 取得总页数
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
    @ApiOperation(value = "用户公园列表", notes = "success=true表示成功，=false表示失败")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
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
                json.put("msg", "下拉框值不正确！");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "下拉框值不正确！");
        }

        ResponseUtil.write(response, json);
        return null;
    }
//
//    /**
//     * 根据id删除数据[修改状态]
//     * @author 许超
//     * @date 2019年2月15日下午9:47:41
//     * @param deptID 主键
//     * @return
//     */
//    @RequestMapping(value="/deleteUserDept")
//    @ResponseBody
//    public GlobalResultVO deleteUserDept(@RequestParam("deptID") String deptID,
//                                         @RequestParam("userID")String userID) {
//        return userDeptService.deleteUserDept(deptID,userID);
//    }
}
