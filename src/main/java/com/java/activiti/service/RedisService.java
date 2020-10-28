package com.java.activiti.service;

import com.java.activiti.model.Operate;
import com.java.activiti.model.RedisInfoDetail;

import java.util.List;
import java.util.Map;

public interface RedisService {

    //��ȡredis��������Ϣ
    List<RedisInfoDetail> getRedisInfo();

    //��ȡredis��־�б�
    List<Operate> getLogs(long entries);

    //��ȡ��־����
    Long getLogLen();

    //�����־
    String logEmpty();

    //��ȡ��ǰ���ݿ���key������
    Map<String, Object> getKeysSize();

    //��ȡ��ǰredisʹ���ڴ��С���
    Map<String, Object> getMemeryInfo();

    /**
     * ͬ����ԭ���Ե�����ˮ��
     * LIUHD 20191127
     * �������� + �����1����
     * �磺201912011
     * @param  lengths ��С��10��,Name ���ֿ�ͷ��ERP_ID��,
     *                 dbindex (0-15)�л���DB������,days �洢����
     * @return
     */
    String getIncrementNum(int lengths,String name,int dbindex,int days);
}
