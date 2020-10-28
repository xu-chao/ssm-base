package com.java.activiti.service.gaizao;


import com.java.activiti.model.Project;
import com.java.activiti.model.gaizao.GaiZao;

import java.util.List;
import java.util.Map;

public interface GaiZaoService {
	/**
	 * 分页总数
	 *
	 * @return 返回所有符合条件的记录总数
	 */
	int gaiZaoCount(List<Project> projects);

	/**
	 * 自定义分页对象
	 *
	 * @param map
	 * @return 返回所有符合条件的记录的分页对象
	 */
	List<GaiZao> gaiZaoPage(Map<String, Object> map, List<Project> projects);	/**
	 * 获取该公园下所有的记录
	 *
	 * @param map
	 * @return 返回所有符合条件的记录的分页对象
	 */
	List<GaiZao> gaiZaoDetail(Map<String, Object> map);

	/**
	 * 新增 改造记录
	 *
	 * @param gaiZao
	 * @return 返回 0 1
	 */
	int addGaiZao(GaiZao gaiZao);

	/**
	 * 更新 改造记录
	 *
	 * @param gaiZao
	 * @return 返回 0 1
	 */
	int updateGaiZao(GaiZao gaiZao);


	/**
	 * 批量删除改造
	 * @param id
	 * @return
	 */
	int deleteGaiZaoById(List<String> id);
}