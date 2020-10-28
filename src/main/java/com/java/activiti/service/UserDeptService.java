package com.java.activiti.service;

import com.java.activiti.model.Dept;
import com.java.activiti.model.UserDept;
import com.java.activiti.pojo.GlobalResultVO;

import java.util.List;
import java.util.Map;


public interface UserDeptService {

    /**
     * 增加部门用户
     * @param userDept
     * @return
     */
    int addDeptAllocation(UserDept userDept);

    GlobalResultVO deleteUserDept(String deptID, String userID);

    UserDept sessionDept(Map<String, Object> map);

    /**
     * 获取指定 userID 对应用户拥有的部门
     * @param userID 用户ID
     * @return 返回 userID 指定用户的部门
     */
    List<Dept> selectUserDept(String userID);

}
