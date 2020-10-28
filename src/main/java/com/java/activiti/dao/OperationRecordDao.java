package com.java.activiti.dao;

import com.java.activiti.model.OperationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OperationRecordDao {

    /**
     * 选择指定用户ID，或时间范围的用户操作记录
     *
     * @param userID    指定的用户ID
     * @param startDate 记录的起始日期
     * @param endDate   记录的结束日期
     * @return 返回所有符合条件的记录
     */
    List<OperationRecord> selectUserOperationRecord(@Param("userID") Integer userID,
                                                    @Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate);

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
     * 插入用户操作记录
     *
     * @param operationRecord 用户操作记录
     */
    void insertOperationRecord(OperationRecord operationRecord);
}
