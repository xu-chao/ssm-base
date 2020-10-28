package com.java.activiti.service;

import com.java.activiti.model.Operate;
import com.java.activiti.model.RedisInfoDetail;

import java.util.List;
import java.util.Map;

public interface RedisService {

    //获取redis服务器信息
    List<RedisInfoDetail> getRedisInfo();

    //获取redis日志列表
    List<Operate> getLogs(long entries);

    //获取日志总数
    Long getLogLen();

    //清空日志
    String logEmpty();

    //获取当前数据库中key的数量
    Map<String, Object> getKeysSize();

    //获取当前redis使用内存大小情况
    Map<String, Object> getMemeryInfo();

    /**
     * 同步锁原子性递增流水号
     * LIUHD 20191127
     * 当期日期 + 当天从1自增
     * 如：201912011
     * @param  lengths 最小（10）,Name 名字开头（ERP_ID）,
     *                 dbindex (0-15)切换的DB缓存区,days 存储天数
     * @return
     */
    String getIncrementNum(int lengths,String name,int dbindex,int days);
}
