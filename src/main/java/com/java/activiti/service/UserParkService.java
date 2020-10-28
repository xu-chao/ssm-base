package com.java.activiti.service;

import com.java.activiti.model.Park;
import com.java.activiti.model.UserDept;
import com.java.activiti.model.UserPark;
import com.java.activiti.pojo.GlobalResultVO;

import java.util.List;
import java.util.Map;


public interface UserParkService {

    /**
     * 增加部门用户
     * @param userPark
     * @return
     */
    int addParkAllocation(UserPark userPark);
//    GlobalResultVO deleteUserDept(String deptID, String userID);

    UserPark sessionPark(Map<String, Object> map);

     List<UserPark> findAllUser(Map<String, Object> map);

     List<Park> findParkByUser(String userID);
}
