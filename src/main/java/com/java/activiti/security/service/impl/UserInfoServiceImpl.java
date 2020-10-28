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
     * 获取指定用户ID对应的用户账户信息
     *
     * @param userID 用户ID
     * @return 返回用户账户信息
     */
    @Override
    public UserInfoVO getUserInfo(String userID) throws UserInfoServiceException {
        if (userID == null)
            return null;

        try {
            // 获取用户信息
            User userInfo = userInfoDao.selectByUserID(userID);
            // 获取用户角色信息
            List<Group> groups = memberShipDao.selectUserGroup(userID);
            //获取用户部门信息
            List<Dept> depts = userDeptDao.selectUserDept(userID);
            //获取用户公园信息
            List<Park> parks = userParkDao.selectUserPark(userID);
            //获取用户公园信息
            List<Warehouse> warehouses = userWarehouseDao.selectUserWarehouse(userID);

            return assembleUserInfo(userInfo, groups, depts,parks,warehouses);
        } catch (PersistenceException e) {
            throw new UserInfoServiceException(e);
        }
    }

    /**
     * 获取指定 userName 对应的用户账户信息
     *
     * @param userName 用户名
     * @return 返回用户账户信息
     */
    @Override
    public UserInfoVO getUserNameInfo(String userName) throws UserInfoServiceException {
        if (userName == null)
            return null;

        try {
            // 获取用户信息
            User user = userInfoDao.selectByName(userName);
            // 获取用户角色信息
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
     * 获取所有用户账户信息
     *
     * @return 返回所有的用户账户信息
     */
    @Override
    public List<UserInfoVO> getAllUserInfo() throws UserInfoServiceException {
        // 保存所有用户的 UserInfoDTO 对象
        List<UserInfoVO> userInfoVOS = null;

        // 获取所有用户的账户信息（不包含角色信息）
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
                    // 获取用户对应的角色信息
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
     * 更新用户的账户信息
     *
     * @param userInfoVO 用户账户信息
     */
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) throws UserInfoServiceException {
        if (userInfoVO != null) {
            try {
                // 更新 User 对象信息
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

                // 更新角色信息
            } catch (PersistenceException e) {
                throw new UserInfoServiceException(e);
            }
        }

    }

    /**
     * 删除指定 userID 的用户账户信息
     *
     * @param userID 指定的用户ID
     */
    @Override
    public void deleteUserInfo(String userID) throws UserInfoServiceException {
        if (userID == null)
            return;

        try {
            // 删除用户角色信息
            memberShipDao.deleteByUserID(userID);

            // 删除用户信息
            userInfoDao.deleteById(userID);
        } catch (PersistenceException e) {
            throw new UserInfoServiceException(e);
        }

    }

    /**
     * 添加一条用户账户信息
     *
     * @param userInfoVO 需要添加的用户账户信息
     */
    @Override
    public boolean insertUserInfo(UserInfoVO userInfoVO) throws UserInfoServiceException {
        if (userInfoVO == null)
            return false;

        // 检查数据是否有效
        String  userID = userInfoVO.getUserID();
        String userName = userInfoVO.getUserName();
        String password = userInfoVO.getPassword();
        if (userName == null || password == null)
            return false;

        try {
            // 对密码进行加密
            String tempStr = encryptingModel.MD5(password);
            String encryptPassword = encryptingModel.MD5(tempStr + userID);

            // 创建用户信息数据实体
            User user = new User();
            user.setId(userID);
            user.setFirstName(userName);
            user.setPassword(encryptPassword);

            // 持久化用户信息
            userInfoDao.insert(user);

            // 获取用户角色信息
            List<String> groups = userInfoVO.getGroup();
            String groupID;

            // 持久化用户角色信息
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
     * 组装 UserInfoVO 对象，包括用户账户信息和角色信息
     *
     * @param user        用户账户信息
     * @param groups      用户角色信息
     * @param depts      用户部门信息
     * @return 返回组装后的UserInfoVO
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
     * 获取用户的权限角色
     *
     * @param userID 用户 ID
     * @return 返回一个保存有用户角色的 Set，若该用户没有任何角色，则返回一个不包含任何元素的 Set
     */
    @Override
    public Set<String> getUserRoles(String  userID) throws UserInfoServiceException {
        // 获取用户信息
        UserInfoVO userInfoVO = getUserInfo(userID);

        // 返回用户的角色
        if (userInfoVO != null) {
            return new HashSet<>(userInfoVO.getGroup());
        } else {
            return new HashSet<>();
        }
    }


}
