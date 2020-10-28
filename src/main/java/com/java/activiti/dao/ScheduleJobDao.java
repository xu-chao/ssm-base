package com.java.activiti.dao;

import com.java.activiti.model.City;
import com.java.activiti.model.ScheduleJob;

import java.util.List;
import java.util.Map;

public interface ScheduleJobDao {

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int scheduleJobCount(Map<String,Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<ScheduleJob> scheduleJobPage(Map<String,Object> map);

    /**
     * 新增
     *
     * @param scheduleJob
     * @return
     */
    public Long insert(ScheduleJob scheduleJob);

    /**
     * 直接修改 只能修改运行的时间，参数、同异步等无法修改
     *
     * @param scheduleJob
     */
    public Long update(ScheduleJob scheduleJob);

    /**
     * 删除重新创建方式
     *
     * @param scheduleJob
     */
//    public void delUpdate(ScheduleJob scheduleJob);

    /**
     * 删除
     *
     * @param scheduleJobId
     */
    public Long delete(Long scheduleJobId);

    /**
     * 运行一次任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
//    public void runOnce(Long scheduleJobId);

    /**
     * 暂停任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
//    public void pauseJob(Long scheduleJobId);

    /**
     * 恢复任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
//    public void resumeJob(Long scheduleJobId);

    /**
     * 获取任务对象
     *
     * @param scheduleJobId
     * @return
     */
    public ScheduleJob get(Long scheduleJobId);

    /**
     * 查询任务列表
     *
     * @return
     */
    public List<ScheduleJob> queryList();

    /**
     * 获取运行中的任务列表
     *
     * @return
     */
//    public List<ScheduleJob> queryExecutingJobList();

    List<ScheduleJob> findScheduleJob(ScheduleJob scheduleJob);
}
