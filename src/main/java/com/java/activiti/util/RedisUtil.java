package com.java.activiti.util;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Slowlog;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private JedisPool jedisPool;

    // ��ȡredis ��������Ϣ
    public String getRedisInfo() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Client client = jedis.getClient();
            client.info();
            String info = client.getBulkReply();
            return info;
        } finally {
            // ���������ӳ�
            jedis.close();
        }
    }

    // ��ȡ��־�б�
    public List<Slowlog> getLogs(long entries) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<Slowlog> logList = jedis.slowlogGet(entries);
            return logList;
        } finally {
            // ���������ӳ�
            jedis.close();
        }
    }

    // ��ȡ��־����
    public Long getLogsLen() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long logLen = jedis.slowlogLen();
            return logLen;
        } finally {
            // ���������ӳ�
            jedis.close();
        }
    }

    // �����־
    public String logEmpty() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.slowlogReset();
        } finally {
            // ���������ӳ�
            jedis.close();
        }
    }

    // ��ȡռ���ڴ��С
    public Long dbSize() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // TODO ����redis������Ϣ
            Client client = jedis.getClient();
            client.dbSize();
            return client.getIntegerReply();
        } finally {
            // ���������ӳ�
            jedis.close();
        }
    }

    public Long getIncrementNum(String key, int dbindex, int days) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dbindex);
            result = jedis.incr(key);
            if (days != 0) {
                jedis.expire(key, days*24*60*60);
            }
        } finally {
            // ���������ӳ�
            jedis.close();
            return result;
        }

    }
}
