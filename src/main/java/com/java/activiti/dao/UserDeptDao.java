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
     * �û�����sessionDept�ķ���
     * @return
     */
    UserDept sessionDept(Map<String, Object> map);

    /**
     * ��ȡָ�� userID ��Ӧ�û�ӵ�еĲ���
     * @param userID �û�ID
     * @return ���� userID ָ���û��Ĳ���
     */
    List<Dept> selectUserDept(String userID);
}
