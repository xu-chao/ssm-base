package com.java.activiti.dao;

import com.java.activiti.model.Need;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NeedDao {

    List<Need> needPage(Map<String, Object> map);

    int needCount(Map<String, Object> map);

    int needCountByMonth();

    int addNeed(Need need);

    Need findById(String nId);

    Need findNeedById(String id);

    int updateNeed(Need need);

    int updateNeedByNId(Need need);

    Need getNeedByTaskId(String processInstanceId);

    List<Need> findNeed(Need need);

    List<Need> selectTaskByProcessID(List<String> processInstanceId);
}
