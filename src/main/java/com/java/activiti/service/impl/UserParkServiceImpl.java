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
 * @Description: �����û���صĲ���
 * @author: ��
 * @date: 2019��8��9��
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service("userParkService")
public class UserParkServiceImpl implements UserParkService {

    @Resource
    private UserParkDao userParkDao;

    /**
     * ���Ӳ����û�
     * @param userPark
     * @return
     */
    public int addParkAllocation(UserPark userPark) {
        return userParkDao.addParkAllocation(userPark);
    }
//    public GlobalResultVO deleteUserDept(String deptID,String userID) {
//        Integer deleteCount = userDeptDao.deleteUserDept(deptID,userID);
//        if (deleteCount != null && deleteCount > 0) {
//            return new GlobalResultVO(200, "����ɾ���ɹ�", null);
//        } else {
//            return new GlobalResultVO(400, "����ɾ��ʧ��", null);
//        }
//    }
//
    /**
     * �û�sessionPark�ķ���
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

