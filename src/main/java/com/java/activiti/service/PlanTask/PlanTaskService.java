package com.java.activiti.service.PlanTask;


import com.java.activiti.model.gaizao.GaiZao;
import com.java.activiti.model.planTask.PlanTask;
import com.java.activiti.model.question.QuestionInfo;

import java.util.List;
import java.util.Map;

public interface PlanTaskService {
	/**
	 * ��ҳ����
	 *
	 * @return �������з��������ļ�¼����
	 */
	int planTaskThoughtCount(Map<String, Object> map);

	/**
	 * �Զ����ҳ����
	 *
	 * @param map
	 * @return �������з��������ļ�¼�ķ�ҳ����
	 */
	List<PlanTask> planTaskThoughtPage(Map<String, Object> map);

	/**
	 * ��ҳ����
	 *
	 * @return �������з��������ļ�¼����
	 */
	int planTaskCount(Map<String, Object> map);

	/**
	 * �Զ����ҳ����
	 *
	 * @param map
	 * @return �������з��������ļ�¼�ķ�ҳ����
	 */
	List<PlanTask> planTaskPage(Map<String, Object> map);
//	/**
//	 * ��ȡ�ù�԰�����еļ�¼
//	 *
//	 * @param map
//	 * @return �������з��������ļ�¼�ķ�ҳ����
//	 */
//	List<GaiZao> gaiZaoDetail(Map<String, Object> map);
//
	/**
	 * ���� �ƻ�����
	 *
	 * @param planTask
	 * @return ���� 0 1
	 */
	int addPlanTask(PlanTask planTask);

	/**
	 * ���� �ƻ�����
	 *
	 * @param planTask
	 * @return ���� 0 1
	 */
	int updatePlanTask(PlanTask planTask);

	/**
	 * ���� �ƻ�����
	 * @param ptID
	 * @return
	 */
	PlanTask findById(String ptID);

	PlanTask findPlanTaskById(String ptID);

	PlanTask getPlanTaskByTaskId(String processInstanceId);
//  /**
//	 * ����ɾ�� �ƻ�����
//	 * @param id
//	 * @return
//	 */
//	int deletePlanTaskById(List<String> id);
}