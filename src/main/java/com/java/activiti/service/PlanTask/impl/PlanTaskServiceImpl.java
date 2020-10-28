package com.java.activiti.service.PlanTask.impl;

import com.java.activiti.dao.planTask.PlanTaskDao;
import com.java.activiti.dao.question.QuestionDao;
import com.java.activiti.model.planTask.PlanTask;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.service.PlanTask.PlanTaskService;
import com.java.activiti.service.question.QuestionService;
import com.java.activiti.util.Excel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("planTaskService")
public class PlanTaskServiceImpl implements PlanTaskService {

    @Resource
    private PlanTaskDao planTaskDao;

    @Override
    public List<PlanTask> planTaskThoughtPage(Map<String, Object> map) {
        return planTaskDao.planTaskThoughtPage(map);
    }

    @Override
    public int planTaskThoughtCount(Map<String, Object> map) {
        return planTaskDao.planTaskThoughtCount(map);
    }

    @Override
    public List<PlanTask> planTaskPage(Map<String, Object> map) {
        return planTaskDao.planTaskPage(map);
    }

    @Override
    public int planTaskCount(Map<String, Object> map) {
        return planTaskDao.planTaskCount(map);
    }

    public int addPlanTask(PlanTask planTask) {
        return planTaskDao.addPlanTask(planTask);
    }

    public PlanTask findById(String ptID) {
        return planTaskDao.findById(ptID);
    }

    public PlanTask findPlanTaskById(String ptID) {
        return planTaskDao.findPlanTaskById(ptID);
    }

    public int updatePlanTask(PlanTask planTask) {
        return planTaskDao.updatePlanTask(planTask);
    }

//    @Override
//    public int deletePlanTaskById(List<String> id) {
//        return 0;
//    }
//
    public PlanTask getPlanTaskByTaskId(String processInstanceId) {
        return planTaskDao.getPlanTaskByTaskId(processInstanceId);
    }
//
//    public List<QuestionInfo> selectTaskByProcessID(List<String> map) {
//        return questionDao.selectTaskByProcessID(map);
//    }


}
