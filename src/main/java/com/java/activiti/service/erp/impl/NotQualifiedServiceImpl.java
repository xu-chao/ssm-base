package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.NotQualifiedDao;
import com.java.activiti.dao.erp.RCheckoutDao;
import com.java.activiti.model.erp.NotQualified;
import com.java.activiti.model.erp.RCheckout;
import com.java.activiti.service.erp.NotQualifiedService;
import com.java.activiti.service.erp.RCheckoutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("notQualifiedService")
public class NotQualifiedServiceImpl implements NotQualifiedService {
    @Resource
    private NotQualifiedDao notQualifiedDao;

    @Override
    public List<NotQualified> notQualifiedPage(Map<String, Object> map) {
        return notQualifiedDao.notQualifiedPage(map);
    }

    @Override
    public int notQualifiedCount(Map<String, Object> map) {
        return notQualifiedDao.notQualifiedCount(map);
    }

    @Override
    public int addNotQualified(NotQualified notQualified) {
        return notQualifiedDao.addNotQualified(notQualified);
    }

    @Override
    public NotQualified findById(String id) {
        return notQualifiedDao.findById(id);
    }

    @Override
    public int updateNotQualified(NotQualified notQualified) {
        return notQualifiedDao.updateNotQualified(notQualified);
    }

    @Override
    public void export(OutputStream os, Map<String, Object> map) {
    }
}
