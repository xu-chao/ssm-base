package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.RCheckoutListDao;
import com.java.activiti.model.erp.RCheckoutList;
import com.java.activiti.service.erp.RCheckoutListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("rCheckoutListService")
public class RCheckoutListServiceImpl implements RCheckoutListService {
    @Resource
    private RCheckoutListDao rCheckoutListDao;

    @Override
    public List<RCheckoutList> rCheckoutListPage(Map<String, Object> map) {
        return rCheckoutListDao.rCheckoutListPage(map);
    }

    @Override
    public int rCheckoutListCount(Map<String, Object> map) {
        return rCheckoutListDao.rCheckoutListCount(map);
    }

}
