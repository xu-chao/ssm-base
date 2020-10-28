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
 * @Description: �����û���صĲ���
 * @author: ��
 * @date: 2019��8��9��
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service("userDeptService")
public class UserDeptServiceImpl implements UserDeptService {

    @Resource
    private UserDeptDao userDeptDao;

    /**
     * ���Ӳ����û�
     * @param userDept
     * @return
     */
    public int addDeptAllocation(UserDept userDept) {
        return userDeptDao.addDeptAllocation(userDept);
    }
    public GlobalResultVO deleteUserDept(String deptID,String userID) {
        Integer deleteCount = userDeptDao.deleteUserDept(deptID,userID);
        if (deleteCount != null && deleteCount > 0) {
            return new GlobalResultVO(200, "����ɾ���ɹ�", null);
        } else {
            return new GlobalResultVO(400, "����ɾ��ʧ��", null);
        }
    }

    /**
     * �û�sessionDept�ķ���
     * @return
     */
    public UserDept sessionDept(Map<String, Object> map){
        return userDeptDao.sessionDept(map);
    }

    /**
     * ��ȡָ�� userID ��Ӧ�û�ӵ�еĲ���
     * @param userID �û�ID
     * @return ���� userID ָ���û��Ĳ���
     */
    public List<Dept> selectUserDept(String userID){
        return userDeptDao.selectUserDept(userID);
    }
}

