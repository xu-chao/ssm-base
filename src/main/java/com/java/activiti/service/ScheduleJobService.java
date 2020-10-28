package com.java.activiti.service;

import com.java.activiti.exception.ScheduleException;
import com.java.activiti.model.ScheduleJob;

import java.util.List;
import java.util.Map;

public interface ScheduleJobService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<ScheduleJob> scheduleJobPage(Map<String,Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int scheduleJobCount(Map<String,Object> map);

    /**
     * ��ʼ����ʱ����
     */
    public void initScheduleJob() throws ScheduleException;

    /**
     * ����
     *
     * @param scheduleJob
     * @return
     */
    public Long insert(ScheduleJob scheduleJob) throws ScheduleException;

    /**
     * ֱ���޸� ֻ���޸����е�ʱ�䣬������ͬ�첽���޷��޸�
     *
     * @param scheduleJob
     */
    public Long update(ScheduleJob scheduleJob) throws ScheduleException;

    /**
     * ɾ�����´�����ʽ
     *
     * @param scheduleJob
     */
    public void delUpdate(ScheduleJob scheduleJob) throws ScheduleException;

    /**
     * ɾ��
     *
     * @param scheduleJobId
     */
    public void delete(Long scheduleJobId) throws ScheduleException;

    /**
     * ����ɾ����ʱ����
     * @param id
     * @return
     */
    Long deleteScheduleJobById(List<Long> id) throws ScheduleException;

    /**
     * �ж϶�ʱ�����Ƿ��Ѿ�����
     * @param scheduleJob
     * @return
     */
    List<ScheduleJob> findScheduleJob(ScheduleJob scheduleJob);

    /**
     * ����һ������
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void runOnce(Long scheduleJobId) throws ScheduleException;

    /**
     * ��ͣ����
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void pauseJob(Long scheduleJobId) throws ScheduleException;

    /**
     * �ָ�����
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void resumeJob(Long scheduleJobId) throws ScheduleException;

    /**
     * ��ȡ�������
     *
     * @param scheduleJobId
     * @return
     */
    public ScheduleJob get(Long scheduleJobId);

    /**
     * ��ѯ�����б�
     *
     * @return
     */
    public List<ScheduleJob> queryList();

    /**
     * ��ȡ�����е������б�
     *
     * @return
     */
    public List<ScheduleJob> queryExecutingJobList(Map<String,Object> map);
}
