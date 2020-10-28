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
     * ����
     *
     * @return
     */
    public User userLogin(User user) {
        return userDao.userLogin(user);
    }

    /**
     * ��ҳ��ѯ�û�
     * @param map
     * @return
     */
//	public List<User> userPage(Map<String, Object> map){
//		return userDao.userPage(map);
//	}

    /**
     * ��ҳ��ѯ�û�
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
     * ��ҳ��ѯ�û�
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
     * ����ɾ���û�
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "����ɾ���û�")
    public int deleteUser(List<String> id) {
        return userDao.deleteUser(id);
    }

    /**
     * �޸��û�
     *
     * @param user
     * @return
     */
    @Override
    @Operation(value = "�����û���Ϣ")
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    /**
     * �����û�
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "�����û�")
    public int freezeUserById(String id, int status) {
        return userDao.freezeUserById(id, status);
    }

    /**
     * �����û�
     *
     * @param user
     * @return
     */
    @Override
    @Operation(value = "�����û�")
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
     * �������
     */
    @Override
    public void updateModifyPassword(String userID, Map<String, Object> passwordInfo) throws UserServiceException {

        if (passwordInfo == null)
            throw new UserServiceException(UserServiceException.PASSWORD_ERROR);

        // ��ȡ����������Ϣ
        String rePassword = (String) passwordInfo.get(REPEAT_PASSWORD);
        String newPassword = (String) passwordInfo.get(NEW_PASSWORD);
        String oldPassword = (String) passwordInfo.get(OLD_PASSWORD);
        if (rePassword == null || newPassword == null || oldPassword == null) {
            throw new UserServiceException(UserServiceException.PASSWORD_ERROR);
        }

        try {
            // ��ȡ�û����˻���Ϣ
            UserInfoVO user = userInfoService.getUserInfo(userID);
            if (user == null) {
                throw new UserServiceException(UserServiceException.PASSWORD_ERROR);
            }

            // ������һ������֤
            if (!newPassword.equals(rePassword)) {
                throw new UserServiceException(UserServiceException.PASSWORD_UNMATCH);
            }

            // ԭ������ȷ����֤
            String password;
            password = encryptingModel.MD5(oldPassword + userID);
            if (!password.equals(user.getPassword())) {
                throw new UserServiceException(UserServiceException.PASSWORD_ERROR);
            }

            // ����µ����벢����
            password = encryptingModel.MD5(newPassword + userID);

            // ��֤�ɹ���������ݿ�
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
    @Operation(value = "�����û���Ϣ")
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }
}

