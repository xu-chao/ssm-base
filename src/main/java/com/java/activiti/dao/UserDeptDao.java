package com.java.activiti.dao;

import com.java.activiti.model.Dept;
import com.java.activiti.model.UserDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDeptDao {

    int addDeptAllocation(UserDept userDept);

    int deleteUserDept(@Param("deptID") String deptID, @Param("userID") String userID);

    /**
     * 用户登入sessionDept的方法
     * @return
     */
    UserDept sessionDept(Map<String, Object> map);

    /**
     * 获取指定 userID 对应用户拥有的部门
     * @param userID 用户ID
     * @return 返回 userID 指定用户的部门
     */
    List<Dept> selectUserDept(String userID);
}
