package com.java.activiti.service.word.impl;

import com.java.activiti.dao.word.TouYingDao;
import com.java.activiti.model.word.TouYing;
import com.java.activiti.service.word.TouYingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("touYingService")
public class TouYingServiceImpl implements TouYingService {

    @Resource
    private TouYingDao touYingDao;

  
    public int addTouYing(TouYing touYing) {
        return touYingDao.addTouYing(touYing);
    }

    public List<TouYing> touYingPage(Map<String, Object> map) {
        return touYingDao.touYingPage(map);
    }

    public int touYingCount(Map<String, Object> map) {
        return touYingDao.touYingCount(map);
    }

    public TouYing findById(String id) {
        return touYingDao.findById(id);
    }

}
