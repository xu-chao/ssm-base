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
 * �û�����
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/user")
@Api(tags = "�û�����Controller���漰�û����CRUD")
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
     * ��½�˻�
     *
     * @param user �˻���Ϣ
     * @return ����һ�� Map �������а�����½�����Ľ��
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "�û���¼", notes = "���ţ����������")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    Map<String, Object> login(@RequestBody Map<String, Object> user
                           ) {
        // ��ʼ�� Response
        Response response = responseUtil.newResponseInstance();
        String result = Response.RESPONSE_RESULT_ERROR;
        String errorMsg = "";
        boolean validate = userService.CheckValidate((String)user.get("tokenIn"), Integer.parseInt((String)user.get("X")),Integer.parseInt((String)user.get("Y")));
        if (!validate) {
            errorMsg = "incorrectCredentials";
        } else {
            // ��ȡ��ǰ���û��� Subject��shiro
            Subject currentUser = SecurityUtils.getSubject();
            // �ж��û��Ƿ��Ѿ���½ isAuthenticated()
            if (currentUser != null && !currentUser.isAuthenticated()) {
                String id = (String) user.get(USER_ID);
                String password = (String) user.get(USER_PASSWORD);
                UsernamePasswordToken token = new UsernamePasswordToken(id, password);

                // ִ�е�½����
                try {
                    //�����realms/UserAuthorizingRealm�е�doGetAuthenticationInfo����
                    currentUser.login(token);

                    // ���õ�½״̬����¼
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
        // ���� Response
        response.setResponseResult(result);
        response.setResponseMsg(errorMsg);
        return response.generateResponse();
    }
    /**
     *
     * ����
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
//			result.put("errorInfo", "�û��������������");
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
     * ע���˻�
     *
     * @return ����һ�� Map ���󣬼�ֵΪ result �����ݴ���ע�������Ľ����ֵΪ success �� error
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "�û�ע��")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�û�SessionʧЧ"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    Map<String, Object> logout() {
        // ��ʼ�� Response
        Response response = responseUtil.newResponseInstance();

        Subject currentSubject = SecurityUtils.getSubject();
        if (currentSubject != null && currentSubject.isAuthenticated()) {
            // ִ���˻�ע������
            currentSubject.logout();
            response.setResponseResult(Response.RESPONSE_RESULT_SUCCESS);
        } else {
            response.setResponseResult(Response.RESPONSE_RESULT_ERROR);
            response.setResponseMsg("û�е�¼��");
        }

        return response.generateResponse();
    }

    /**
     * ��¼�ύ
     * @param entity        ��¼��UUser
     * @param rememberMe    �Ƿ��ס
     * @param request        request������ȡ��¼֮ǰUrl��ַ��������¼����ת��û�е�¼֮ǰ��ҳ�档
     * @return
     */
//	@RequestMapping(value="submitLogin",method=RequestMethod.POST)
//	@ResponseBody
//	public Map<String,Object> submitLogin(User entity,Boolean rememberMe,HttpServletRequest request){
//		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
//		try {
//			entity = TokenManager.login(entity,rememberMe);
//			resultMap.put("status", 200);
//			resultMap.put("message", "��¼�ɹ�");
//
//
//			/**
//			 * shiro ��ȡ��¼֮ǰ�ĵ�ַ
//			 * ֮ǰ0.1�汾���û�жϿա�
//			 */
//			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
//			String url = null ;
//			if(null != savedRequest){
//				url = savedRequest.getRequestUrl();
//			}
//			/**
//			 * ����ƽ���õĻ�ȡ��һ������ķ�ʽ����Session��һ�µ�������ǻ�ȡ������
//			 * String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
//			 */
//			LoggerUtils.fmtDebug(getClass(), "��ȡ��¼֮ǰ��URL:[%s]",url);
//			//�����¼֮ǰû�е�ַ����ô����ת����ҳ��
//			if(StringUtils.isBlank(url)){
//				url = request.getContextPath() + "/main";
//			}
//			//��ת��ַ
//			resultMap.put("back_url", url);
//			/**
//			 * ������ʵ����ֱ��catch Exception��Ȼ���׳� message���ɣ�������û��Ǹ�����ϸcatch �õ㡣��
//			 */
//		} catch (DisabledAccountException e) {
//			resultMap.put("status", 500);
//			resultMap.put("message", "�ʺ��Ѿ����á�");
//		} catch (Exception e) {
//			resultMap.put("status", 500);
//			resultMap.put("message", "�ʺŻ��������");
//		}
//		return resultMap;
//	}

    /**
     * ����û�������
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/findUser")
    @ApiOperation(value = "����û�������")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    public String findUser(HttpServletResponse response) throws Exception {
        List<User> list = userService.findUser();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setFirstName(list.get(i).getFirstName() + list.get(i).getLastName());
        }
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("trueName", "��ѡ��...");
        //תΪJSON��ʽ������
        jsonArray.add(jsonObject);
        //��listתΪJSON
        JSONArray rows = JSONArray.fromObject(list);
        jsonArray.addAll(rows);
        ResponseUtil.write(response, jsonArray);
        return null;
    }

    /**
     * ��ҳ��ѯ�û�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "userPage", method = RequestMethod.POST)
    @ApiOperation(value = "��ҳ��ѯ�û�", notes = "row��ʾҳ����page��ʾ��ʼҳ")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    public String userPage(HttpServletResponse response,
                           @RequestParam(value = "page", required = false) String page,
                           @RequestParam(value = "rows", required = false) String rows,
                           User user,
                           @RequestParam(value = "deptPid", required = false) String deptPid,
                           @RequestParam(value = "deptID", required = false) String deptID,
                           @RequestParam(value = "groupID", required = false) String groupID) throws Exception {
        Map<String, Object> userMap = new HashMap<String, Object>();

        //��ȡ�ò��ŵ����� �������Ϊ�ܲ��ÿ�
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

        List<UserInfo> cusDevPlanList = userService.userPage(userMap);
        JSONObject json = new JSONObject();
        // ��List��ʽת����JSON
        JSONArray jsonArray = JSONArray.fromObject(cusDevPlanList);
        json.put("rows", jsonArray);
        json.put("total", userCount);
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * ��ҳ��ѯ�û�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "userPageAll", method = RequestMethod.POST)
    @ApiOperation(value = "��ҳ��ѯ�û�", notes = "row��ʾҳ����page��ʾ��ʼҳ")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    public String userPageAll(HttpServletResponse response,
                           @RequestParam(value = "page", required = false) String page,
                           @RequestParam(value = "rows", required = false) String rows,
                           User user,
                           @RequestParam(value = "deptPid", required = false) String deptPid,
                           @RequestParam(value = "deptID", required = false) String deptID,
                           @RequestParam(value = "groupID", required = false) String groupID) throws Exception {
        Map<String, Object> userMap = new HashMap<String, Object>();

        //��ȡ�ò��ŵ����� �������Ϊ�ܲ��ÿ�
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

        // �ڼ�ҳ
        String pageIndex = page;
        if (pageIndex == null || pageIndex == "") {
            pageIndex = "1";
        }
        userInfos.setPageIndex((Integer.parseInt(pageIndex) - 1)
                * pageSize);
        // ȡ����ҳ��
        int userAllVOCount = userService.userAllVOCount(userMap);
        userInfos.setCount(userAllVOCount);
        userMap.put("pageIndex", userInfos.getPageIndex());
        userMap.put("pageSize", userInfos.getPageSize());

        List<UserInfo> userInfo = userService.userPage(userMap);
        JSONObject json = new JSONObject();
        // ��List��ʽת����JSON
        JSONArray jsonArray = JSONArray.fromObject(userInfo);
        json.put("rows", jsonArray);
        json.put("total", userAllVOCount);
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * �޸��û�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateUser")
    @ApiOperation(value = "�޸��û�", notes = "�������ΪUserInfo Map")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
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
                json.put("msg", "����ֵ����ȷ��");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "����ֵ����ȷ��");
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * �����û�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/freezeUser")
    @ApiOperation(value = "�����û�", notes = "status=0��ʾ�Ѷ��ᣬ=1��ʾ�ⶳ")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
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
     * �����h���Ñ�
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteUser")
    @ApiOperation(value = "�����h���Ñ�", notes = "success=true��ʾ�ɹ���=false��ʾʧ��")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
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
     * �����Ñ�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/userSave")
    @ApiOperation(value = "�����Ñ�", notes = "success=true��ʾ�ɹ���=false��ʾʧ��")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
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
                json.put("msg", "����ֵ����ȷ��");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "����ֵ����ȷ��");
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * �û���ɫ�б�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/listWithGroups")
    @ApiOperation(value = "�û���ɫ�б�", notes = "success=true��ʾ�ɹ���=false��ʾʧ��")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    public String listWithGroups(HttpServletResponse response, String rows, String page, User user
            , @RequestParam(value = "deptPid", required = false) String deptPid) throws Exception {
        PageInfo<User> userPage = new PageInfo<User>();
        Map<String, Object> userMap = new HashMap<String, Object>();
        //��ȡ�ò��ŵ����� �������Ϊ�ܲ��ÿ�
        if (deptPid != null) {
            if (deptPid.equals("-1")) {
                deptPid = null;
            }
        }
        userMap.put("id", user.getId());
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

        List<UserInfo> userList = userService.userPage(userMap);
        for (User users : userList) {
            StringBuffer buffer = new StringBuffer();
            List<Group> groupList = groupService.findByUserId(users.getId());
            for (Group g : groupList) {
                buffer.append(g.getName() + ",");
            }
            if (buffer.length() > 0) {
                //deleteCharAt ɾ�����һ��Ԫ��
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
     * �ж��Ñ��Ƿ��Ѿ�����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/existUserID")
    @ApiOperation(value = "�ж��Ñ��Ƿ��Ѿ�����", notes = "success=true��ʾ�ɹ���=false��ʾʧ��")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
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
     * �޸��˻�����
     *
     * @param passwordInfo ������Ϣ
     * @param request      ����
     * @return ����һ�� Map �������м�ֵΪ result �����޸���������Ľ����
     * ֵΪ success �� error����ֵΪ msg ������Ҫ���ظ��û�����Ϣ
     */
    @RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "�޸��˻�����", notes = "success=true��ʾ�ɹ���=false��ʾʧ��")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    Map<String, Object> modifyPassword(@RequestBody Map<String, Object> passwordInfo,
                                       HttpServletRequest request) {
        //��ʼ�� Response
        Response responseContent = responseUtil.newResponseInstance();

        String errorMsg = null;
        String result = Response.RESPONSE_RESULT_ERROR;

        // ��ȡ�û� ID
        String userID = UserUtil.getSubjectUserID();

        try {
            // ��������
            userService.updateModifyPassword(userID, passwordInfo);

            result = Response.RESPONSE_RESULT_SUCCESS;
        } catch (UserServiceException e) {
            errorMsg = e.getExceptionDesc();
        }
        // ���� Response
        responseContent.setResponseResult(result);
        responseContent.setResponseMsg(errorMsg);
        return responseContent.generateResponse();
    }
    /**
     * @param userID
     * @return
     * @Title: findUserDept
     * @Description: ������������ ���� userID
     */
    @RequestMapping(value = "/findUserDept", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "������������",notes = "�û�userID")
    @ApiResponses({
            @ApiResponse(code=400,message="�������û���"),
            @ApiResponse(code=404,message="����·��û�л�ҳ����ת·������")
    })
    public Dept findUserDept(String userID) {

        List<Dept> depts = userDeptService.selectUserDept(userID);

        return depts.get(0);
    }



    /**
     * @param userID
     * @return
     * @Title: findParkByUser
     * @Description: ����������԰ ���� userID
     */
    @RequestMapping(value = "/findParkByUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "����������԰",notes = "�û�userID")
    @ApiResponses({
            @ApiResponse(code=400,message="�������û���"),
            @ApiResponse(code=404,message="����·��û�л�ҳ����ת·������")
    })
    public Park findParkByUser(String userID) {

        List<Park> parks = userParkService.findParkByUser(userID);

        return parks.get(0);
    }

    /**
     * �����û�
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateUserInfo")
    @ApiOperation(value = "�����û�����", notes = "result.success: true/false")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    public String updateUserInfo(HttpServletResponse response,User user) throws Exception {
        /*
        ʵ���߼���
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
