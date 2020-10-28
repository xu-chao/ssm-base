package com.java.activiti.service.gaizao;

import com.java.activiti.model.gaizao.Huiyishi;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface HuiyishiService {

    List<Huiyishi> huiyishiPage(Map<String, Object> map);

    List<Huiyishi> huiyishiThrough(Map<String, Object> map);

    int huiyishiCount(Map<String, Object> map);

    int huiyishiThoughtCount(Map<String, Object> map);

    int addHuiyishi(Huiyishi huiyishi);


    Huiyishi findById(String id);

    int updateHuiyishi(Huiyishi huiyishi);

    Huiyishi getHuiyishiByTaskId(String processInstanceId);
//批量获取 问题单 通过 流程id
    List<Huiyishi> selectTaskByProcessID(List<String> map);

    void export(OutputStream os, HttpServletRequest request, Map<String, Object> map) throws IOException;
}
