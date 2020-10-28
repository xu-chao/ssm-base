package com.java.activiti.service.impl;

import com.java.activiti.dao.UserDeptDao;
import com.java.activiti.model.Dept;
import com.java.activiti.model.UserDept;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.UserDeptService;
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
@Service("userDeptService")
public class UserDeptServiceImpl implements UserDeptService {

    @Resource
    private UserDeptDao userDeptDao;

    /**
     * 增加部门用户
     * @param userDept
     * @return
     */
    public int addDeptAllocation(UserDept userDept) {
        return userDeptDao.addDeptAllocation(userDept);
    }
    public GlobalResultVO deleteUserDept(String deptID,String userID) {
        Integer deleteCount = userDeptDao.deleteUserDept(deptID,userID);
        if (deleteCount != null && deleteCount > 0) {
            return new GlobalResultVO(200, "数据删除成功", null);
        } else {
            return new GlobalResultVO(400, "数据删除失败", null);
        }
    }

    /**
     * 用户sessionDept的方法
     * @return
     */
    public UserDept sessionDept(Map<String, Object> map){
        return userDeptDao.sessionDept(map);
    }

    /**
     * 获取指定 userID 对应用户拥有的部门
     * @param userID 用户ID
     * @return 返回 userID 指定用户的部门
     */
    public List<Dept> selectUserDept(String userID){
        return userDeptDao.selectUserDept(userID);
    }
}

