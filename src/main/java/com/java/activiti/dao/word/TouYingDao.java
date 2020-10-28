package com.java.activiti.dao.word;

import com.java.activiti.model.word.TouYing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TouYingDao {
    /**
     * ����  ͶӰ�豸��װ��������
     * @return
     */
    int addTouYing(TouYing touYing);

    List<TouYing> touYingPage(Map<String, Object> map);

    int touYingCount(Map<String, Object> map);

    TouYing findById(String id);
}
