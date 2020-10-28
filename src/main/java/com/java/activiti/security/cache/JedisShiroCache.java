package com.java.activiti.security.cache;

import com.java.activiti.util.LoggerUtils;
import com.java.activiti.util.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

public class JedisShiroCache<K, V> implements Cache<K, V> {

    /**
     * Ϊ�˲��������Ļ������������׷��ǰ׺��ʽ��������
     */
    private static final String REDIS_SHIRO_CACHE = "shiro-demo-cache:";
    /**
     * Redis ��Ƭ(����)��Ҳ�����������ļ�������
     */
    private static final int DB_INDEX = 1;

    private JedisManager jedisManager;

    private String name;


    static final Class<JedisShiroCache> SELF = JedisShiroCache.class;
    public JedisShiroCache(String name, JedisManager jedisManager) {
        this.name = name;
        this.jedisManager = jedisManager;
    }

    /**
     * �Զ���relm�е���Ȩ/��֤������������Ȩ/��֤Ӣ������
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public V get(K key) throws CacheException {
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(key));
        byte[] byteValue = new byte[0];
        try {
            byteValue = jedisManager.getValueByKey(DB_INDEX, byteKey);
        } catch (Exception e) {
            LoggerUtils.error(SELF, "get value by cache throw exception",e);
        }
        return (V) SerializeUtil.deserialize(byteValue);
    }

    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.saveValueByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(key)),
                    SerializeUtil.serialize(value), -1);
        } catch (Exception e) {
            LoggerUtils.error(SELF, "put cache throw exception",e);
        }
        return previos;
    }

    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.deleteByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(key)));
        } catch (Exception e) {
            LoggerUtils.error(SELF, "remove cache  throw exception",e);
        }
        return previos;
    }

    public void clear() throws CacheException {
        //TODO--
    }

    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    public Set<K> keys() {
        //TODO
        return null;
    }

    public Collection<V> values() {
        //TODO
        return null;
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }
}
