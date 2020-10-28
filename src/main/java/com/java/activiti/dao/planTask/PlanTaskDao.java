package com.java.activiti.dao.planTask;

import com.java.activiti.model.planTask.PlanTask;
import com.java.activiti.model.question.QuestionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanTaskDao {

    List<PlanTask> planTaskThoughtPage(Map<String, Object> map);


    int planTaskThoughtCount(Map<String, Object> map);

    int planTaskCount(Map<String, Object> map);

    List<PlanTask> planTaskPage(Map<String, Object> map);

    int addPlanTask(PlanTask planTask );

    PlanTask findById(String ptID);

    PlanTask findPlanTaskById(String ptID);

    int updatePlanTask(PlanTask planTask);

    PlanTask getPlanTaskByTaskId(String processInstanceId);
//
//    QuestionInfo getQuestionByTaskId(String processInstanceId);
////≈˙¡ø≤È—Ø by processInstanceId
//    List<QuestionInfo> selectTaskByProcessID(List<String> processInstanceId);
}
