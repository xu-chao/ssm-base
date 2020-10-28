package com.java.activiti.dao;

import com.java.activiti.model.AccessRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccessRecordDao {

    /**
     * ����һ���û��û�����ǳ���¼
     *
     * @param accessRecord �û�����ǳ���¼
     */
    void insertAccessRecord(AccessRecord accessRecord);

    /**
     * ѡ��ָ���û�ID����¼���͡�ʱ�䷶Χ�ĵ���ǳ���¼
     *
     * @param userID     �û�ID
     * @param accessType ��¼���ͣ����롢�ǳ������У�
     * @param startDate  ��¼����ʼ����
     * @param endDate    ��¼�Ľ�������
     * @return �������з��������ļ�¼
     */
    List<AccessRecord> selectAccessRecords(@Param("userID") String userID,
                                           @Param("accessType") String accessType,
                                           @Param("startDate") Date startDate,
                                           @Param("endDate") Date endDate);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<AccessRecord> accessRecordPage(Map<String,Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int accessRecordCount(Map<String,Object> map);

}
