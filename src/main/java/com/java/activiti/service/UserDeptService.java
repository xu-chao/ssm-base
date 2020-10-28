package com.java.activiti.service;

import com.java.activiti.model.Dept;
import com.java.activiti.model.UserDept;
import com.java.activiti.pojo.GlobalResultVO;

import java.util.List;
import java.util.Map;


public interface UserDeptService {

    /**
     * ���Ӳ����û�
     * @param userDept
     * @return
     */
    int addDeptAllocation(UserDept userDept);

    GlobalResultVO deleteUserDept(String deptID, String userID);

    UserDept sessionDept(Map<String, Object> map);

    /**
     * ��ȡָ�� userID ��Ӧ�û�ӵ�еĲ���
     * @param userID �û�ID
     * @return ���� userID ָ���û��Ĳ���
     */
    List<Dept> selectUserDept(String userID);

}
