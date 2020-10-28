package com.java.activiti.service.gaizao;


import com.java.activiti.model.Project;
import com.java.activiti.model.gaizao.GaiZao;

import java.util.List;
import java.util.Map;

public interface GaiZaoService {
	/**
	 * ��ҳ����
	 *
	 * @return �������з��������ļ�¼����
	 */
	int gaiZaoCount(List<Project> projects);

	/**
	 * �Զ����ҳ����
	 *
	 * @param map
	 * @return �������з��������ļ�¼�ķ�ҳ����
	 */
	List<GaiZao> gaiZaoPage(Map<String, Object> map, List<Project> projects);	/**
	 * ��ȡ�ù�԰�����еļ�¼
	 *
	 * @param map
	 * @return �������з��������ļ�¼�ķ�ҳ����
	 */
	List<GaiZao> gaiZaoDetail(Map<String, Object> map);

	/**
	 * ���� �����¼
	 *
	 * @param gaiZao
	 * @return ���� 0 1
	 */
	int addGaiZao(GaiZao gaiZao);

	/**
	 * ���� �����¼
	 *
	 * @param gaiZao
	 * @return ���� 0 1
	 */
	int updateGaiZao(GaiZao gaiZao);


	/**
	 * ����ɾ������
	 * @param id
	 * @return
	 */
	int deleteGaiZaoById(List<String> id);
}