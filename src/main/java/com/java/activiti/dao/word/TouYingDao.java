package com.java.activiti.dao.word;

import com.java.activiti.model.word.TouYing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TouYingDao {
    /**
     * 新增  投影设备安装条件检查表
     * @return
     */
    int addTouYing(TouYing touYing);

    List<TouYing> touYingPage(Map<String, Object> map);

    int touYingCount(Map<String, Object> map);

    TouYing findById(String id);
}
