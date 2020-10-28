package com.java.activiti.service.impl;

import com.java.activiti.dao.UserDeptDao;
import com.java.activiti.dao.UserParkDao;
import com.java.activiti.model.Park;
import com.java.activiti.model.UserDept;
import com.java.activiti.model.UserPark;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.UserDeptService;
import com.java.activiti.service.UserParkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserDeptServiceImpl
 * @Description: 部门用户相关的操作
 * @author: 许超
 * @date: 2019年8月9日
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service("userParkService")
public class UserParkServiceImpl implements UserParkService {

    @Resource
    private UserParkDao userParkDao;

    /**
     * 增加部门用户
     * @param userPark
     * @return
     */
    public int addParkAllocation(UserPark userPark) {
        return userParkDao.addParkAllocation(userPark);
    }
//    public GlobalResultVO deleteUserDept(String deptID,String userID) {
//        Integer deleteCount = userDeptDao.deleteUserDept(deptID,userID);
//        if (deleteCount != null && deleteCount > 0) {
//            return new GlobalResultVO(200, "数据删除成功", null);
//        } else {
//            return new GlobalResultVO(400, "数据删除失败", null);
//        }
//    }
//
    /**
     * 用户sessionPark的方法
     * @return
     */
    public UserPark sessionPark(Map<String, Object> map){
        return userParkDao.sessionPark(map);
    }

    @Override
    public List<UserPark> findAllUser(Map<String, Object> map) {
        return userParkDao.findAllUser(map);
    }

    @Override
    public List<Park> findParkByUser(String userID) {
        return userParkDao.selectUserPark(userID);
    }
}

