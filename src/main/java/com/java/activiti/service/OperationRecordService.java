package com.java.activiti.service;

import com.java.activiti.exception.SystemLogServiceException;
import com.java.activiti.model.FileInfo;
import com.java.activiti.model.Leave;
import com.java.activiti.model.OperationRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface OperationRecordService {
	/**
	 * 插入用户操作记录
	 *
	 * @param userID          执行操作的用户ID
	 * @param userName        执行操作的用户名
	 * @param operationName   操作的名称
	 * @param operationResult 操作的记录
	 */
	void insertOperationRecord(String userID, String userName, String operationName, String operationResult) throws SystemLogServiceException;

	/**
	 * 查询指定用户ID或日期范围的用户操作记录
	 *
	 * @param userID       用户ID
	 * @param startDateStr 记录的起始日期
	 * @param endDateStr   记录的结束日期
	 * @return 返回一个Map， 其中键值为 data 的值为所有符合条件的记录， 而键值为 total 的值为符合条件的记录总条数
	 */
	Map<String, Object> selectUserOperationRecord(String userID, String startDateStr, String endDateStr) throws SystemLogServiceException;

	/**
	 * 分页查询指定用户ID或日期范围的用户操作记录
	 *
	 * @param userID       用户ID
	 * @param startDateStr 记录的起始日期
	 * @param endDateStr   记录的结束日期
	 * @param offset       分页的偏移值
	 * @param limit        分页的大小
	 * @return 返回一个Map， 其中键值为 data 的值为所有符合条件的记录， 而键值为 total 的值为符合条件的记录总条数
	 */
	Map<String, Object> selectUserOperationRecord(String userID, String startDateStr, String endDateStr, int offset, int limit) throws SystemLogServiceException;

	/**
	 * 自定义分页对象
	 *
	 * @param map
	 * @return 返回所有符合条件的记录的分页对象
	 */
	List<OperationRecord> operationRecordPage(Map<String,Object> map);

	/**
	 * 分页总数
	 *
	 * @param map
	 * @return 返回所有符合条件的记录总数
	 */
	int operationRecordCount(Map<String,Object> map);

	/**
	 *
	 * @Title: export
	 * @Description: 导出excel
	 * @author: 许超
	 * @param os
	 * @param map
	 */
	void export(OutputStream os, Map<String,Object> map);
}
