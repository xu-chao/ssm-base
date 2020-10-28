package com.java.activiti.security.service.impl;

import com.java.activiti.dao.*;
import com.java.activiti.dao.wms.UserWarehouseDao;
import com.java.activiti.exception.UserInfoServiceException;
import com.java.activiti.model.*;
import com.java.activiti.model.wms.Warehouse;
import com.java.activiti.pojo.UserInfoVO;
import com.java.activiti.pojo.wms.UserWarehouse;
import com.java.activiti.security.service.UserInfoService;
import com.java.activiti.security.util.EncryptingModel;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private MemberShipDao memberShipDao;
    @Resource
    private UserDeptDao userDeptDao;
    @Resource
    private UserParkDao userParkDao;
    @Resource
    private UserWarehouseDao userWarehouseDao;
    @Resource
    private EncryptingModel encryptingModel;
    @Resource
    private GroupDao groupDao;

    /**
     * ��ȡָ���û�ID��Ӧ���û��˻���Ϣ
     *
     * @param userID �û�ID
     * @return �����û��˻���Ϣ
     */
    @Override
    public UserInfoVO getUserInfo(String userID) throws UserInfoServiceException {
        if (userID == null)
            return null;

        try {
            // ��ȡ�û���Ϣ
            User userInfo = userInfoDao.selectByUserID(userID);
            // ��ȡ�û���ɫ��Ϣ
            List<Group> groups = memberShipDao.selectUserGroup(userID);
            //��ȡ�û�������Ϣ
            List<Dept> depts = userDeptDao.selectUserDept(userID);
            //��ȡ�û���԰��Ϣ
            List<Park> parks = userParkDao.selectUserPark(userID);
            //��ȡ�û���԰��Ϣ
            List<Warehouse> warehouses = userWarehouseDao.selectUserWarehouse(userID);

            return assembleUserInfo(userInfo, groups, depts,parks,warehouses);
        } catch (PersistenceException e) {
            throw new UserInfoServiceException(e);
        }
    }

    /**
     * ��ȡָ�� userName ��Ӧ���û��˻���Ϣ
     *
     * @param userName �û���
     * @return �����û��˻���Ϣ
     */
    @Override
    public UserInfoVO getUserNameInfo(String userName) throws UserInfoServiceException {
        if (userName == null)
            return null;

        try {
            // ��ȡ�û���Ϣ
            User user = userInfoDao.selectByName(userName);
            // ��ȡ�û���ɫ��Ϣ
            if (user != null) {
                List<Group> groups = memberShipDao.selectUserGroup(user.getId());
                List<Dept> depts = userDeptDao.selectUserDept(user.getId());
                List<Park> parks = userParkDao.selectUserPark(user.getId());
                List<Warehouse> warehouses = userWarehouseDao.selectUserWarehouse(user.getId());
                return assembleUserInfo(user, groups, depts, parks, warehouses);
            } else
                return null;
        } catch (PersistenceException e) {
            throw new UserInfoServiceException(e);
        }
    }

    /**
     * ��ȡ�����û��˻���Ϣ
     *
     * @return �������е��û��˻���Ϣ
     */
    @Override
    public List<UserInfoVO> getAllUserInfo() throws UserInfoServiceException {
        // ���������û��� UserInfoDTO ����
        List<UserInfoVO> userInfoVOS = null;

        // ��ȡ�����û����˻���Ϣ����������ɫ��Ϣ��
        try {
            List<User> users = userInfoDao.selectAll();
            if (users != null) {
                List<Group> groups;
                List<Dept> depts;
                List<Park> parks;
                List<Warehouse> warehouses;
                UserInfoVO userInfoVO;
                userInfoVOS = new ArrayList<>(users.size());
                for (User user : users) {
                    // ��ȡ�û���Ӧ�Ľ�ɫ��Ϣ
                    groups = memberShipDao.selectUserGroup(user.getId());
                    depts = userDeptDao.selectUserDept(user.getId());
                    parks = userParkDao.selectUserPark(user.getId());
                    warehouses = userWarehouseDao.selectUserWarehouse(user.getId());
                    userInfoVO = assembleUserInfo(user, groups, depts, parks, warehouses);
                    userInfoVOS.add(userInfoVO);
                }
            }

            return userInfoVOS;
        } catch (PersistenceException e) {
            throw new UserInfoServiceException(e);
        }
    }

    /**
     * �����û����˻���Ϣ
     *
     * @param userInfoVO �û��˻���Ϣ
     */
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) throws UserInfoServiceException {
        if (userInfoVO != null) {
            try {
                // ���� User ������Ϣ
                String userID = userInfoVO.getUserID();
                String password = userInfoVO.getPassword();
                Date lastLoginTime = userInfoVO.getLastLoginTime();
                if (userID != null) {
                    User user = new User();
                    user.setId(userID);
                    user.setPassword(password);
                    user.setLastLoginTime(lastLoginTime);
//                    if(lastLoginTime != null){
//                        user.setLastLoginTime(lastLoginTime);
//                    }
                    userInfoDao.updateUser(user);

                }

                // ���½�ɫ��Ϣ
            } catch (PersistenceException e) {
                throw new UserInfoServiceException(e);
            }
        }

    }

    /**
     * ɾ��ָ�� userID ���û��˻���Ϣ
     *
     * @param userID ָ�����û�ID
     */
    @Override
    public void deleteUserInfo(String userID) throws UserInfoServiceException {
        if (userID == null)
            return;

        try {
            // ɾ���û���ɫ��Ϣ
            memberShipDao.deleteByUserID(userID);

            // ɾ���û���Ϣ
            userInfoDao.deleteById(userID);
        } catch (PersistenceException e) {
            throw new UserInfoServiceException(e);
        }

    }

    /**
     * ���һ���û��˻���Ϣ
     *
     * @param userInfoVO ��Ҫ��ӵ��û��˻���Ϣ
     */
    @Override
    public boolean insertUserInfo(UserInfoVO userInfoVO) throws UserInfoServiceException {
        if (userInfoVO == null)
            return false;

        // ��������Ƿ���Ч
        String  userID = userInfoVO.getUserID();
        String userName = userInfoVO.getUserName();
        String password = userInfoVO.getPassword();
        if (userName == null || password == null)
            return false;

        try {
            // ��������м���
            String tempStr = encryptingModel.MD5(password);
            String encryptPassword = encryptingModel.MD5(tempStr + userID);

            // �����û���Ϣ����ʵ��
            User user = new User();
            user.setId(userID);
            user.setFirstName(userName);
            user.setPassword(encryptPassword);

            // �־û��û���Ϣ
            userInfoDao.insert(user);

            // ��ȡ�û���ɫ��Ϣ
            List<String> groups = userInfoVO.getGroup();
            String groupID;

            // �־û��û���ɫ��Ϣ
            for (String group : groups) {
                groupID = groupDao.getGroupID(group);
                if (groupID != null)
                    memberShipDao.insert(userID, groupID);
                else
                    throw new UserInfoServiceException("The role of userInfo unavailable");
            }

            return true;

        } catch (NoSuchAlgorithmException | PersistenceException e) {
            throw new UserInfoServiceException(e);
        }
    }

    /**
     * ��װ UserInfoVO ���󣬰����û��˻���Ϣ�ͽ�ɫ��Ϣ
     *
     * @param user        �û��˻���Ϣ
     * @param groups      �û���ɫ��Ϣ
     * @param depts      �û�������Ϣ
     * @return ������װ���UserInfoVO
     */
    private UserInfoVO assembleUserInfo(User user, List<Group> groups, List<Dept> depts, List<Park> parks, List<Warehouse> warehouses) {
        UserInfoVO userInfoVO = null;
        if (user != null && groups != null) {
            userInfoVO = new UserInfoVO();
            userInfoVO.setUserID(user.getId());
            userInfoVO.setUserName(user.getFirstName() + user.getLastName());
            userInfoVO.setPassword(user.getPassword());
            userInfoVO.setStatus(user.getStatus());

            for (Group group : groups) {
                userInfoVO.getGroup().add(group.getId());
            }

            for (Dept dept : depts) {
                userInfoVO.getDept().add(dept.getDeptID());
            }
            for (Park park : parks) {
                userInfoVO.getPark().add(park.getParkID());
            }
            for (Warehouse warehouse : warehouses) {
                userInfoVO.getWarehouse().add(warehouse.getWhId());
            }
        }
        return userInfoVO;
    }

    /**
     * ��ȡ�û���Ȩ�޽�ɫ
     *
     * @param userID �û� ID
     * @return ����һ���������û���ɫ�� Set�������û�û���κν�ɫ���򷵻�һ���������κ�Ԫ�ص� Set
     */
    @Override
    public Set<String> getUserRoles(String  userID) throws UserInfoServiceException {
        // ��ȡ�û���Ϣ
        UserInfoVO userInfoVO = getUserInfo(userID);

        // �����û��Ľ�ɫ
        if (userInfoVO != null) {
            return new HashSet<>(userInfoVO.getGroup());
        } else {
            return new HashSet<>();
        }
    }


}
