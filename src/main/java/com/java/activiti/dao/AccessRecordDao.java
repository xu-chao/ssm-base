package com.java.activiti.dao;

import com.java.activiti.model.AccessRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccessRecordDao {

    /**
     * 插入一条用户用户登入登出记录
     *
     * @param accessRecord 用户登入登出记录
     */
    void insertAccessRecord(AccessRecord accessRecord);

    /**
     * 选择指定用户ID、记录类型、时间范围的登入登出记录
     *
     * @param userID     用户ID
     * @param accessType 记录类型（登入、登出或所有）
     * @param startDate  记录的起始日期
     * @param endDate    记录的结束日期
     * @return 返回所有符合条件的记录
     */
    List<AccessRecord> selectAccessRecords(@Param("userID") String userID,
                                           @Param("accessType") String accessType,
                                           @Param("startDate") Date startDate,
                                           @Param("endDate") Date endDate);

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

}
