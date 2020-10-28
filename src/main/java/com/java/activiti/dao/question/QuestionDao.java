package com.java.activiti.dao.question;

import com.java.activiti.model.question.QuestionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuestionDao {

    List<QuestionInfo> questionPage(Map<String, Object> map);

    List<QuestionInfo> questionThrough(Map<String, Object> map);

    int questionCount(Map<String, Object> map);

    int questionThoughtCount(Map<String, Object> map);

    int addQuestion(QuestionInfo question);

    QuestionInfo findById(String id);

    int updateQuestion(QuestionInfo question);

    QuestionInfo getQuestionByTaskId(String processInstanceId);
//≈˙¡ø≤È—Ø by processInstanceId
    List<QuestionInfo> selectTaskByProcessID(List<String> processInstanceId);
}
