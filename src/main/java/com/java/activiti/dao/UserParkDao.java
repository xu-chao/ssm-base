package com.java.activiti.dao;

import com.java.activiti.model.Park;
import com.java.activiti.model.UserPark;

import java.util.List;
import java.util.Map;

public interface UserParkDao {

    int addParkAllocation(UserPark userPark);
//    int deleteUserDept(@Param("deptID") String deptID, @Param("userID") String userID);

    /**
     * 用户登入sessionDept的方法
     * @return
     */
    UserPark sessionPark(Map<String, Object> map);

    /**
     * 获取指定 userID 对应用户拥有的公园
     * @param userID 用户ID
     * @return 返回 userID 指定用户的公园
     */
    List<Park> selectUserPark(String userID);

    List<UserPark> findAllUser(Map<String, Object> map);
}
