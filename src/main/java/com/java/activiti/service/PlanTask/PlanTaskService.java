package com.java.activiti.service.PlanTask;


import com.java.activiti.model.gaizao.GaiZao;
import com.java.activiti.model.planTask.PlanTask;
import com.java.activiti.model.question.QuestionInfo;

import java.util.List;
import java.util.Map;

public interface PlanTaskService {
	/**
	 * 分页总数
	 *
	 * @return 返回所有符合条件的记录总数
	 */
	int planTaskThoughtCount(Map<String, Object> map);

	/**
	 * 自定义分页对象
	 *
	 * @param map
	 * @return 返回所有符合条件的记录的分页对象
	 */
	List<PlanTask> planTaskThoughtPage(Map<String, Object> map);

	/**
	 * 分页总数
	 *
	 * @return 返回所有符合条件的记录总数
	 */
	int planTaskCount(Map<String, Object> map);

	/**
	 * 自定义分页对象
	 *
	 * @param map
	 * @return 返回所有符合条件的记录的分页对象
	 */
	List<PlanTask> planTaskPage(Map<String, Object> map);
//	/**
//	 * 获取该公园下所有的记录
//	 *
//	 * @param map
//	 * @return 返回所有符合条件的记录的分页对象
//	 */
//	List<GaiZao> gaiZaoDetail(Map<String, Object> map);
//
	/**
	 * 新增 计划任务
	 *
	 * @param planTask
	 * @return 返回 0 1
	 */
	int addPlanTask(PlanTask planTask);

	/**
	 * 更新 计划任务
	 *
	 * @param planTask
	 * @return 返回 0 1
	 */
	int updatePlanTask(PlanTask planTask);

	/**
	 * 查找 计划任务
	 * @param ptID
	 * @return
	 */
	PlanTask findById(String ptID);

	PlanTask findPlanTaskById(String ptID);

	PlanTask getPlanTaskByTaskId(String processInstanceId);
//  /**
//	 * 批量删除 计划任务
//	 * @param id
//	 * @return
//	 */
//	int deletePlanTaskById(List<String> id);
}