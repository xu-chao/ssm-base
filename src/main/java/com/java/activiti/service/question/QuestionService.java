package com.java.activiti.service.question;

import com.java.activiti.model.question.QuestionInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface QuestionService {

    List<QuestionInfo> questionPage(Map<String, Object> map);

    List<QuestionInfo> questionThrough(Map<String, Object> map);

    int questionCount(Map<String, Object> map);

    int questionThoughtCount(Map<String, Object> map);

    int addQuestion(QuestionInfo question);


    QuestionInfo findById(String id);

    int updateQuestion(QuestionInfo question);

    QuestionInfo getQuestionByTaskId(String processInstanceId);
//批量获取 问题单 通过 流程id
    List<QuestionInfo> selectTaskByProcessID(List<String>  map);

    void export(OutputStream os, HttpServletRequest request, Map<String, Object> map) throws IOException;
}
