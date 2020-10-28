package com.java.activiti.service;

import com.java.activiti.exception.SystemLogServiceException;
import com.java.activiti.model.AccessRecord;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface AccessRecordService {
    String ACCESS_TYPE_LOGIN = "登入";
    String ACCESS_TYPE_LOGOUT = "登出";

    /**
     * 插入用户登入登出记录
     *
     * @param userID     用户ID
     * @param userName   用户名
     * @param accessIP   登陆IP
     * @param accessType 记录类型
     */
    void insertAccessRecord(String userID, String userName, String accessIP, String accessType) throws SystemLogServiceException;

    /**
     * 查询指定用户ID、记录类型或日期范围的登入登出记录
     *
     * @param userID       用户ID
     * @param accessType   记录类型
     * @param startDateStr 记录起始日期
     * @param endDateStr   记录结束日期
     * @return 返回一个Map， 其中键值为 data 的值为所有符合条件的记录， 而键值为 total 的值为符合条件的记录总条数
     */
    Map<String, Object> selectAccessRecord(String userID, String accessType, String startDateStr, String endDateStr) throws SystemLogServiceException;

    /**
     * 分页查询指定用户ID、记录类型或日期范围的登入登出记录
     *
     * @param userID       用户ID
     * @param accessType   记录类型
     * @param startDateStr 记录起始日期
     * @param endDateStr   记录结束日期
     * @param offset       分页偏移值
     * @param limit        分页大小
     * @return 返回一个Map， 其中键值为 data 的值为所有符合条件的记录， 而键值为 total 的值为符合条件的记录总条数
     */
    Map<String, Object> selectAccessRecord(String userID, String accessType, String startDateStr, String endDateStr, int offset, int limit) throws SystemLogServiceException;

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<AccessRecord> accessRecordPage(Map<String,Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int accessRecordCount(Map<String,Object> map);

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
