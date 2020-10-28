package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.UserParkDao;
import com.java.activiti.dao.wms.UserWarehouseDao;
import com.java.activiti.model.Park;
import com.java.activiti.model.UserPark;
import com.java.activiti.model.wms.Warehouse;
import com.java.activiti.pojo.wms.UserWarehouse;
import com.java.activiti.service.UserParkService;
import com.java.activiti.service.wms.UserWarehouseService;
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
@Service("userWarehouseService")
public class UserWarehouseServiceImpl implements UserWarehouseService {

    @Resource
    private UserWarehouseDao userWarehouseDao;

    /**
     * ���Ӳ����û�
     * @param userWarehouse
     * @return
     */
    public int addWarehouseAllocation(UserWarehouse userWarehouse) {
        return userWarehouseDao.addWarehouseAllocation(userWarehouse);
    }
    /**
     * �û�sessionPark�ķ���
     * @return
     */
    public UserWarehouse sessionWarehouse(Map<String, Object> map){
        return userWarehouseDao.sessionWarehouse(map);
    }

    @Override
    public List<UserWarehouse> findAllUser(Map<String, Object> map) {
        return userWarehouseDao.findAllUser(map);
    }

    @Override
    public List<Warehouse> findWarehouseByUser(String userId) {
        return userWarehouseDao.selectUserWarehouse(userId);
    }
}

