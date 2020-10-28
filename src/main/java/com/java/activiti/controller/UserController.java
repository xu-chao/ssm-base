package com.java.activiti.controller;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.activiti.exception.SystemLogServiceException;
import com.java.activiti.exception.UserServiceException;
import com.java.activiti.model.*;
import com.java.activiti.pojo.UserAllVO;
import com.java.activiti.security.token.manage.TokenManager;
import com.java.activiti.security.util.EncryptingModel;
import com.java.activiti.service.*;
import com.java.activiti.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/user")
@Api(tags = "用户操作Controller，涉及用户表的CRUD")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserParkService userParkService;
    @Resource
    private UserDeptService userDeptService;

    @Resource
    private MemberShipService menberShipService;

    @Resource
    private GroupService groupService;

    @Resource
    private AccessRecordService accessRecordService;

    @Resource
    private ResponseUtil responseUtil;

    @Resource
    private UserUtil userUtil;

    @Resource
    private EncryptingModel encryptingModel;

    private static final String USER_ID = "id";
    private static final String USER_NAME = "userName";
    private static final String USER_PASSWORD = "password";

    /**
     * 登陆账户
     *
     * @param user 账户信息
     * @return 返回一个 Map 对象，其中包含登陆操作的结果
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "用户登录", notes = "工号，密码必须填")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    Map<String, Object> login(@RequestBody Map<String, Object> user
                           ) {
        // 初始化 Response
        Response response = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;
        String errorMsg = "";
        boolean validate = userService.CheckValidate((String)user.get("tokenIn"), Integer.parseInt((String)user.get("X")),Integer.parseInt((String)user.get("Y")));
        if (!validate) {
            errorMsg = "incorrectCredentials";
        } else {
            // 获取当前的用户的 Subject，shiro
            Subject currentUser = SecurityUtils.getSubject();
            // 判断用户是否已经登陆 isAuthenticated()
            if (currentUser != null && !currentUser.isAuthenticated()) {
                String id = (String) user.get(USER_ID);
                String password = (String) user.get(USER_PASSWORD);
                UsernamePasswordToken token = new UsernamePasswordToken(id, password);

                // 执行登陆操作
                try {
                    //会调用realms/UserAuthorizingRealm中的doGetAuthenticationInfo方法
                    currentUser.login(token);

                    // 设置登陆状态并记录
                    Session session = currentUser.getSession();
                    session.setAttribute("isAuthenticate", "true");
                    MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
                    String userID = sessionInfo.getUserId();
                    String userName = sessionInfo.getUser().getFirstName() + sessionInfo.getUser().getLastName();
                    String accessIP = session.getHost();
                    // String accessIP = IPUtil.getIpAddress();
                    accessRecordService.insertAccessRecord(userID, userName, accessIP, AccessRecordService.ACCESS_TYPE_LOGIN);
                    result = Response.RESPONSE_RESULT_SUCCESS;
                } catch (UnknownAccountException e) {
                    errorMsg = "unknownAccount";
                } catch (IncorrectCredentialsException e) {
                    errorMsg = "incorrectCredentials";
                } catch (DisabledAccountException e) {
                    errorMsg = e.getMessage();
                } catch (AuthenticationException e) {
                    errorMsg = "authenticationError";
                } catch (SystemLogServiceException e) {
                    errorMsg = "ServerError";
                }
            } else {
                errorMsg = "already login";
            }

        }
        // 设置 Response
        response.setResponseResult(result);
        response.setResponseMsg(errorMsg);
        return response.generateResponse();
    }
    /**
     *
     * 登入
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
//	@RequestMapping("/userLogin")
//	public String userLogin(HttpServletResponse response,HttpServletRequest request) throws Exception{
//		Map<String,Object> map=new HashMap<String, Object>();
//		map.put("userName", request.getParameter("userName"));
//		map.put("password", MD5Util.getMD5(request.getParameter("password")));
//		map.put("groupId", request.getParameter("groupId"));
//		MemberShip memberShip=menberShipService.sessionInfo(map);
//		JSONObject result=new JSONObject();
//		if(memberShip==null){
//			result.put("success", false);
//			result.put("errorInfo", "用户名或者密码错误！");
//		}else{
//			result.put("success", true);
//			request.getSession().setAttribute("currentMemberShip", memberShip);
//		}
//		ResponseUtil.write(response, result);
//		return null;
//	}

//		@RequestMapping("/logout")
//		public String logout() {
//			UserUtil.removeSubjectUser();
//			//return null;
//			return "userLogin";
//		}

    /**
     * 注销账户
     *
     * @return 返回一个 Map 对象，键值为 result 的内容代表注销操作的结果，值为 success 或 error
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "用户注销")
    @ApiResponses({
            @ApiResponse(code = 400, message = "用户Session失效"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    Map<String, Object> logout() {
        // 初始化 Response
        Response response = responseUtil.newResponseInstance();

        Subject currentSubject = SecurityUtils.getSubject();
        if (currentSubject != null && currentSubject.isAuthenticated()) {
            // 执行账户注销操作
            currentSubject.logout();
            response.setResponseResult(Response.RESPONSE_RESULT_SUCCESS);
        } else {
            response.setResponseResult(Response.RESPONSE_RESULT_ERROR);
            response.setResponseMsg("没有登录！");
        }

        return response.generateResponse();
    }

    /**
     * 登录提交
     * @param entity        登录的UUser
     * @param rememberMe    是否记住
     * @param request        request，用来取登录之前Url地址，用来登录后跳转到没有登录之前的页面。
     * @return
     */
//	@RequestMapping(value="submitLogin",method=RequestMethod.POST)
//	@ResponseBody
//	public Map<String,Object> submitLogin(User entity,Boolean rememberMe,HttpServletRequest request){
//		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
//		try {
//			entity = TokenManager.login(entity,rememberMe);
//			resultMap.put("status", 200);
//			resultMap.put("message", "登录成功");
//
//
//			/**
//			 * shiro 获取登录之前的地址
//			 * 之前0.1版本这个没判断空。
//			 */
//			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
//			String url = null ;
//			if(null != savedRequest){
//				url = savedRequest.getRequestUrl();
//			}
//			/**
//			 * 我们平常用的获取上一个请求的方式，在Session不一致的情况下是获取不到的
//			 * String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
//			 */
//			LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]",url);
//			//如果登录之前没有地址，那么就跳转到首页。
//			if(StringUtils.isBlank(url)){
//				url = request.getContextPath() + "/main";
//			}
//			//跳转地址
//			resultMap.put("back_url", url);
//			/**
//			 * 这里其实可以直接catch Exception，然后抛出 message即可，但是最好还是各种明细catch 好点。。
//			 */
//		} catch (DisabledAccountException e) {
//			resultMap.put("status", 500);
//			resultMap.put("message", "帐号已经禁用。");
//		} catch (Exception e) {
//			resultMap.put("status", 500);
//			resultMap.put("message", "帐号或密码错误");
//		}
//		return resultMap;
//	}

    /**
     * 填充用户下拉框
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/findUser")
    @ApiOperation(value = "填充用户下拉框")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String findUser(HttpServletResponse response) throws Exception {
        List<User> list = userService.findUser();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setFirstName(list.get(i).getFirstName() + list.get(i).getLastName());
        }
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("trueName", "请选择...");
        //转为JSON格式的数据
        jsonArray.add(jsonObject);
        //将list转为JSON
        JSONArray rows = JSONArray.fromObject(list);
        jsonArray.addAll(rows);
        ResponseUtil.write(response, jsonArray);
        return null;
    }

    /**
     * 分页查询用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "userPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询用户", notes = "row表示页长，page表示起始页")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String userPage(HttpServletResponse response,
                           @RequestParam(value = "page", required = false) String page,
                           @RequestParam(value = "rows", required = false) String rows,
                           User user,
                           @RequestParam(value = "deptPid", required = false) String deptPid,
                           @RequestParam(value = "deptID", required = false) String deptID,
                           @RequestParam(value = "groupID", required = false) String groupID) throws Exception {
        Map<String, Object> userMap = new HashMap<String, Object>();

        //获取该部门的中心 如果部门为总部置空
        if (deptPid != null) {
            if (deptPid.equals("-1")) {
                deptPid = null;
            }
        }
        userMap.put("id", user.getId());
        userMap.put("deptPid", deptPid);
        userMap.put("deptID", deptID);
        userMap.put("groupID", groupID);
        PageInfo<User> userPage = new PageInfo<User>();
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

        List<UserInfo> cusDevPlanList = userService.userPage(userMap);
        JSONObject json = new JSONObject();
        // 把List格式转换成JSON
        JSONArray jsonArray = JSONArray.fromObject(cusDevPlanList);
        json.put("rows", jsonArray);
        json.put("total", userCount);
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 分页查询用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "userPageAll", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询用户", notes = "row表示页长，page表示起始页")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String userPageAll(HttpServletResponse response,
                           @RequestParam(value = "page", required = false) String page,
                           @RequestParam(value = "rows", required = false) String rows,
                           User user,
                           @RequestParam(value = "deptPid", required = false) String deptPid,
                           @RequestParam(value = "deptID", required = false) String deptID,
                           @RequestParam(value = "groupID", required = false) String groupID) throws Exception {
        Map<String, Object> userMap = new HashMap<String, Object>();

        //获取该部门的中心 如果部门为总部置空
        if (deptPid != null) {
            if (deptPid.equals("-1")) {
                deptPid = null;
            }
        }
        userMap.put("id", user.getId());
        userMap.put("deptPid", deptPid);
        userMap.put("deptID", deptID);
        userMap.put("groupID", groupID);
        PageInfo<UserInfo> userInfos = new PageInfo<UserInfo>();
        Integer pageSize = Integer.parseInt(rows);
        userInfos.setPageSize(pageSize);

        // 第几页
        String pageIndex = page;
        if (pageIndex == null || pageIndex == "") {
            pageIndex = "1";
        }
        userInfos.setPageIndex((Integer.parseInt(pageIndex) - 1)
                * pageSize);
        // 取得总页数
        int userAllVOCount = userService.userAllVOCount(userMap);
        userInfos.setCount(userAllVOCount);
        userMap.put("pageIndex", userInfos.getPageIndex());
        userMap.put("pageSize", userInfos.getPageSize());

        List<UserInfo> userInfo = userService.userPage(userMap);
        JSONObject json = new JSONObject();
        // 把List格式转换成JSON
        JSONArray jsonArray = JSONArray.fromObject(userInfo);
        json.put("rows", jsonArray);
        json.put("total", userAllVOCount);
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 修改用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateUser")
    @ApiOperation(value = "修改用户", notes = "请求参数为UserInfo Map")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String updateUser(HttpServletResponse response, @RequestBody Map<String, Object> userInfo) throws Exception {
        JSONObject json = new JSONObject();
        try {
            String password = encryptingModel.MD5((String) userInfo.get("password") + (String) userInfo.get("id"));
            User user = new User();
            UserPark userPark = new UserPark();
            UserDept userDept = new UserDept();


            userDept.setUserID((String) userInfo.get("id"));
            userDept.setDeptID((String) userInfo.get("deptID"));

            userPark.setUserID((String) userInfo.get("id"));
            userPark.setParkID(Integer.parseInt((String) userInfo.get("parkID")));

            user.setId((String) userInfo.get("id"));
            user.setPassword(password);
            user.setFirstName((String) userInfo.get("firstName"));
            user.setLastName((String) userInfo.get("lastName"));
            user.setEmail((String) userInfo.get("email"));
            user.setAllName((String) userInfo.get("firstName") + userInfo.get("lastName"));
            user.setPhone((String) userInfo.get("phone"));
            user.setPictureID((String) userInfo.get("pictureID"));
            user.setPosition((String) userInfo.get("position"));
            user.setCertificate((String) userInfo.get("certificate"));
            int userDeptResult = userDeptService.addDeptAllocation(userDept);
            int userResult = userService.updateUser(user);
            int userParkResult = userParkService.addParkAllocation(userPark);
            if (userDeptResult > 0 &&userResult > 0 && userParkResult > 0) {
                json.put("success", true);
            } else {
                json.put("success", false);
                json.put("msg", "输入值不正确！");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "输入值不正确！");
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 冻结用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/freezeUser")
    @ApiOperation(value = "冻结用户", notes = "status=0表示已冻结，=1表示解冻")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String freezeUser(HttpServletResponse response,
                             @RequestParam(value = "id") String id,
                             @RequestParam(value = "status") int status) throws Exception {
        int result = userService.freezeUserById(id, status);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 批量刪除用戶
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteUser")
    @ApiOperation(value = "批量刪除用戶", notes = "success=true表示成功，=false表示失败")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String deleteUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String id = request.getParameter("ids");
        JSONObject json = new JSONObject();
        List<String> list = new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int userResult = userService.deleteUser(list);
            if (userResult > 0) {
                json.put("success", true);
            } else {
                json.put("success", false);
            }
        } catch (Exception e) {
            json.put("success", false);
            e.printStackTrace();
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 新增用戶
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/userSave")
    @ApiOperation(value = "新增用戶", notes = "success=true表示成功，=false表示失败")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String userSave(HttpServletResponse response, @RequestBody Map<String, Object> userInfo) throws Exception {
        JSONObject json = new JSONObject();
        try {
            String password = encryptingModel.MD5((String) userInfo.get("password") + (String) userInfo.get("id"));
            User user = new User();
            UserPark userPark = new UserPark();
            UserDept userDept = new UserDept();
            user.setId((String) userInfo.get("id"));

            userDept.setUserID((String) userInfo.get("id"));
            userDept.setDeptID((String) userInfo.get("deptID"));

            userPark.setUserID((String) userInfo.get("id"));
            userPark.setParkID(Integer.parseInt((String) userInfo.get("parkID")));

            user.setPassword(password);
            user.setFirstName((String) userInfo.get("firstName"));
            user.setLastName((String) userInfo.get("lastName"));
            user.setEmail((String) userInfo.get("email"));
            user.setAllName((String) userInfo.get("firstName") + userInfo.get("lastName"));
            user.setPhone((String) userInfo.get("phone"));
            user.setPictureID((String) userInfo.get("pictureID"));
            user.setPosition((String) userInfo.get("position"));
            user.setCertificate((String) userInfo.get("certificate"));
            user.setStatus(1); //add by liuhd at 20191223
            int userResult = userService.addUser(user);
            int userDeptResult = userDeptService.addDeptAllocation(userDept);
            int userParkResult = userParkService.addParkAllocation(userPark);
            if (userDeptResult>0&&userResult > 0 && userParkResult > 0) {
                json.put("success", true);
            } else {
                json.put("success", false);
                json.put("msg", "输入值不正确！");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "输入值不正确！");
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 用户角色列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/listWithGroups")
    @ApiOperation(value = "用户角色列表", notes = "success=true表示成功，=false表示失败")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String listWithGroups(HttpServletResponse response, String rows, String page, User user
            , @RequestParam(value = "deptPid", required = false) String deptPid) throws Exception {
        PageInfo<User> userPage = new PageInfo<User>();
        Map<String, Object> userMap = new HashMap<String, Object>();
        //获取该部门的中心 如果部门为总部置空
        if (deptPid != null) {
            if (deptPid.equals("-1")) {
                deptPid = null;
            }
        }
        userMap.put("id", user.getId());
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

        List<UserInfo> userList = userService.userPage(userMap);
        for (User users : userList) {
            StringBuffer buffer = new StringBuffer();
            List<Group> groupList = groupService.findByUserId(users.getId());
            for (Group g : groupList) {
                buffer.append(g.getName() + ",");
            }
            if (buffer.length() > 0) {
                //deleteCharAt 删除最后一个元素
                users.setGroups(buffer.deleteCharAt(buffer.length() - 1).toString());
            } else {
                user.setGroups(buffer.toString());
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(userList);
        JSONObject result = new JSONObject();
        result.put("rows", jsonArray);
        result.put("total", userCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 判断用戶是否已经存在
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/existUserID")
    @ApiOperation(value = "判断用戶是否已经存在", notes = "success=true表示成功，=false表示失败")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String existUser(HttpServletResponse response, String userID) throws Exception {
        User result = userService.findById(userID);
        JSONObject json = new JSONObject();
        if (result != null) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 修改账户密码
     *
     * @param passwordInfo 密码信息
     * @param request      请求
     * @return 返回一个 Map 对象，其中键值为 result 代表修改密码操作的结果，
     * 值为 success 或 error；键值为 msg 代表需要返回给用户的信息
     */
    @RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "修改账户密码", notes = "success=true表示成功，=false表示失败")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    Map<String, Object> modifyPassword(@RequestBody Map<String, Object> passwordInfo,
                                       HttpServletRequest request) {
        //初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        String errorMsg = null;
        String result = Response.RESPONSE_RESULT_ERROR;

        // 获取用户 ID
        String userID = UserUtil.getSubjectUserID();

        try {
            // 更改密码
            userService.updateModifyPassword(userID, passwordInfo);

            result = Response.RESPONSE_RESULT_SUCCESS;
        } catch (UserServiceException e) {
            errorMsg = e.getExceptionDesc();
        }
        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseMsg(errorMsg);
        return responseContent.generateResponse();
    }
    /**
     * @param userID
     * @return
     * @Title: findUserDept
     * @Description: 查找所属部门 根据 userID
     */
    @RequestMapping(value = "/findUserDept", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查找所属部门",notes = "用户userID")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    public Dept findUserDept(String userID) {

        List<Dept> depts = userDeptService.selectUserDept(userID);

        return depts.get(0);
    }



    /**
     * @param userID
     * @return
     * @Title: findParkByUser
     * @Description: 查找所属公园 根据 userID
     */
    @RequestMapping(value = "/findParkByUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查找所属公园",notes = "用户userID")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    public Park findParkByUser(String userID) {

        List<Park> parks = userParkService.findParkByUser(userID);

        return parks.get(0);
    }

    /**
     * 冻结用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateUserInfo")
    @ApiOperation(value = "更新用户资料", notes = "result.success: true/false")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public String updateUserInfo(HttpServletResponse response,User user) throws Exception {
        /*
        实现逻辑！
         */
        int result = userService.updateUserInfo(user);
        JSONObject json = new JSONObject();
        if (result != 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;


    }



}
