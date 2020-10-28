package com.java.activiti.dao.gaizao;

import com.java.activiti.model.gaizao.Huiyishi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HuiyishiDao {

    List<Huiyishi> huiyishiPage(Map<String, Object> map);

    List<Huiyishi> huiyishiThrough(Map<String, Object> map);

    int huiyishiCount(Map<String, Object> map);

    int huiyishiThoughtCount(Map<String, Object> map);

    int addHuiyishi(Huiyishi huiyishi);

    Huiyishi findById(String id);

    int updateHuiyishi(Huiyishi huiyishi);

    Huiyishi getHuiyishiByTaskId(String processInstanceId);
//≈˙¡ø≤È—Ø by processInstanceId
    List<Huiyishi> selectTaskByProcessID(List<String> processInstanceId);

}
