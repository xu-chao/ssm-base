package com.java.activiti.dao;

import com.java.activiti.model.Park;
import com.java.activiti.model.UserPark;

import java.util.List;
import java.util.Map;

public interface UserParkDao {

    int addParkAllocation(UserPark userPark);
//    int deleteUserDept(@Param("deptID") String deptID, @Param("userID") String userID);

    /**
     * �û�����sessionDept�ķ���
     * @return
     */
    UserPark sessionPark(Map<String, Object> map);

    /**
     * ��ȡָ�� userID ��Ӧ�û�ӵ�еĹ�԰
     * @param userID �û�ID
     * @return ���� userID ָ���û��Ĺ�԰
     */
    List<Park> selectUserPark(String userID);

    List<UserPark> findAllUser(Map<String, Object> map);
}
