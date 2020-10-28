package com.java.activiti.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.annotation.Resource;

import com.java.activiti.dao.*;
import com.java.activiti.exception.UserInfoServiceException;
import com.java.activiti.exception.UserServiceException;
import com.java.activiti.model.UserInfo;
import com.java.activiti.pojo.UserAllVO;
import com.java.activiti.pojo.UserInfoVO;
import com.java.activiti.security.service.UserInfoService;
import com.java.activiti.security.util.EncryptingModel;
import com.java.activiti.util.aop.Operation;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.java.activiti.model.User;
import com.java.activiti.service.UserService;

@Service("userService")
public class UserServiceImpl<ppublic> implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserDeptDao userDeptDao;
    @Resource
    private UserParkDao userParkDao;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private EncryptingModel encryptingModel;

    private static final String OLD_PASSWORD = "oldPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String REPEAT_PASSWORD = "rePassword";

    public User findById(String userId) {
        return userDao.findById(userId);
    }

    /**
     * 登入
     *
     * @return
     */
    public User userLogin(User user) {
        return userDao.userLogin(user);
    }

    /**
     * 分页查询用户
     * @param map
     * @return
     */
//	public List<User> userPage(Map<String, Object> map){
//		return userDao.userPage(map);
//	}

    /**
     * 分页查询用户
     *
     * @param map
     * @return
     */
    @Override
    public List<UserInfo> userPage(Map<String, Object> map) {
        return userDao.userPage(map);
    }

    @Override
    public int userCount(Map<String, Object> map) {
        return userDao.userCount(map);
    }

    /**
     * 分页查询用户
     *
     * @param map
     * @return
     */
    @Override
    public List<UserAllVO> userAllVOPage(Map<String, Object> map) {

        List<UserAllVO> userAllVOList = new ArrayList<UserAllVO>();
//		UserAllVO userAllVO = new UserAllVO();
//		userAllVO.setUserPark(userParkDao.findAllUser(map));
//		userAllVOList.;
        return userAllVOList;
    }

    @Override
    public int userAllVOCount(Map<String, Object> map) {

        return userDao.userCount(map);
    }

    /**
     * 批量删除用户
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "批量删除用户")
    public int deleteUser(List<String> id) {
        return userDao.deleteUser(id);
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @Override
    @Operation(value = "更新用户信息")
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    /**
     * 冻结用户
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "冻结用户")
    public int freezeUserById(String id, int status) {
        return userDao.freezeUserById(id, status);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Override
    @Operation(value = "新增用户")
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public List<User> findUser() {
        return userDao.findUser();
    }

    @Override
    public List<User> findoperationalManagersList() {
        return userDao.findoperationalManagersList();
    }

    @Override
    public List<User> findProjectSupervisorList() {
        return userDao.findProjectSupervisorList();
    }

    @Override
    public List<User> findMaintainerList() {
        return userDao.findMaintainerList();
    }

    /**
     * 密码更改
     */
    @Override
    public void updateModifyPassword(String userID, Map<String, Object> passwordInfo) throws UserServiceException {

        if (passwordInfo == null)
            throw new UserServiceException(UserServiceException.PASSWORD_ERROR);

        // 获取更改密码信息
        String rePassword = (String) passwordInfo.get(REPEAT_PASSWORD);
        String newPassword = (String) passwordInfo.get(NEW_PASSWORD);
        String oldPassword = (String) passwordInfo.get(OLD_PASSWORD);
        if (rePassword == null || newPassword == null || oldPassword == null) {
            throw new UserServiceException(UserServiceException.PASSWORD_ERROR);
        }

        try {
            // 获取用户的账户信息
            UserInfoVO user = userInfoService.getUserInfo(userID);
            if (user == null) {
                throw new UserServiceException(UserServiceException.PASSWORD_ERROR);
            }

            // 新密码一致性验证
            if (!newPassword.equals(rePassword)) {
                throw new UserServiceException(UserServiceException.PASSWORD_UNMATCH);
            }

            // 原密码正确性验证
            String password;
            password = encryptingModel.MD5(oldPassword + userID);
            if (!password.equals(user.getPassword())) {
                throw new UserServiceException(UserServiceException.PASSWORD_ERROR);
            }

            // 获得新的密码并加密
            password = encryptingModel.MD5(newPassword + userID);

            // 验证成功后更新数据库
            user.setPassword(password);
            userInfoService.updateUserInfo(user);
        } catch (NoSuchAlgorithmException | NullPointerException | UserInfoServiceException e) {
            throw new UserServiceException(UserServiceException.PASSWORD_ERROR);
        }

    }


    public boolean CheckValidate(String token, int X, int Y) {
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        if (null == token || token.trim().length() < 1) {
            return false;
        }
//        Map<String, Object> tokenObj = JedisUtils.getObjectMap(JedisConfig.KEY_VALIDATE_TOKEN + ":" + token);
        Map<String, Object> ValidateCode = (Map<String, Object>) session.getAttribute(token);
        if (null == ValidateCode) {
            return false;
        } else {
            int sX = (Integer) ValidateCode.get("X");
            int sY = (Integer) ValidateCode.get("Y");
            if (sY != Y) {
                return false;
            } else {
                if (Math.abs(sX - X) <= 3) {
                    String checkCode = String.valueOf(X) + String.valueOf(Y);
                    session.setAttribute("checkCode", checkCode);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    @Operation(value = "更新用户信息")
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }
}

