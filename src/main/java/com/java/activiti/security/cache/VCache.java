package com.java.activiti.security.cache;

import com.java.activiti.util.SerializeUtil;
import com.java.activiti.util.SpringContextUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class VCache {

    final static JedisManager J = SpringContextUtil.getBean("jedisManager", JedisManager.class);
    private VCache() {}

    /**
     * �򵥵�Get
     * @param <T>
     * @param key
     * @param requiredType
     * @return
     */
    public static <T> T get(String key , Class<T>...requiredType){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] skey = SerializeUtil.serialize(key);
            return SerializeUtil.deserialize(jds.get(skey),requiredType);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }
    /**
     * �򵥵�set
     * @param key
     * @param value
     */
    public static void set(Object key ,Object value){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] skey = SerializeUtil.serialize(key);
            byte[] svalue = SerializeUtil.serialize(value);
            jds.set(skey, svalue);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
    }
    /**
     * ����ʱ���
     * @param key
     * @param value
     * @param timer ���룩
     */
    public static void setex(Object key, Object value, int timer) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] skey = SerializeUtil.serialize(key);
            byte[] svalue = SerializeUtil.serialize(value);
            jds.setex(skey, timer, svalue);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }

    }
    /**
     *
     * @param <T>
     * @param mapkey map
     * @param key	 map���key
     * @param requiredType value�ķ�������
     * @return
     */
    public static <T> T getVByMap(String mapkey,String key , Class<T> requiredType){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] mkey = SerializeUtil.serialize(mapkey);
            byte[] skey = SerializeUtil.serialize(key);
            List<byte[]> result = jds.hmget(mkey, skey);
            if(null != result && result.size() > 0 ){
                byte[] x = result.get(0);
                T resultObj = SerializeUtil.deserialize(x, requiredType);
                return resultObj;
            }

        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }
    /**
     *
     * @param mapkey map
     * @param key	 map���key
     * @param value   map���value
     */
    public static void setVByMap(String mapkey,String key ,Object value){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] mkey = SerializeUtil.serialize(mapkey);
            byte[] skey = SerializeUtil.serialize(key);
            byte[] svalue = SerializeUtil.serialize(value);
            jds.hset(mkey, skey,svalue);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }

    }
    /**
     * ɾ��Map���ֵ
     * @param mapKey
     * @param dkey
     * @return
     */
    public static Object delByMapKey(String mapKey ,String...dkey){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[][] dx = new byte[dkey.length][];
            for (int i = 0; i < dkey.length; i++) {
                dx[i] = SerializeUtil.serialize(dkey[i]);
            }
            byte[] mkey = SerializeUtil.serialize(mapKey);
            Long result = jds.hdel(mkey, dx);
            return result;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return new Long(0);
    }

    /**
     * ��redis��ȡset��������
     *
     * @param <T>
     * @param setKey
     * @param requiredType
     * @return
     */
    public static <T> Set<T> getVByList(String setKey, Class<T> requiredType){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] lkey = SerializeUtil.serialize(setKey);
            Set<T> set = new TreeSet<T>();
            Set<byte[]> xx = jds.smembers(lkey);
            for (byte[] bs : xx) {
                T t = SerializeUtil.deserialize(bs, requiredType);
                set.add(t);
            }
            return set;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }
    /**
     * ��ȡSet����
     * @param setKey
     * @return
     */
    public static Long getLenBySet(String setKey){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            Long result = jds.scard(setKey);
            return result;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }
    /**
     * ɾ��Set
     * @param dkey
     * @return
     */
    public static Long delSetByKey(String key,String...dkey){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            Long result = 0L;
            if(null == dkey){
                result = jds.srem(key);
            }else{
                result = jds.del(key);
            }
            return result;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return new Long(0);
    }
    /**
     * ��� Set �е�һ��ֵ
     * @param key
     * @return
     */
    public static String srandmember(String key){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            String result = jds.srandmember(key);
            return result;
        } catch (Exception e){
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }
    /**
     * ��redis���Set
     * @param setKey
     * @param value
     */
    public static void setVBySet(String setKey,String value){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            jds.sadd(setKey, value);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
    }
    /**
     * ȡset
     * @param key
     * @return
     */
    public static Set<String> getSetByKey(String key){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            Set<String> result = jds.smembers(key);
            return result;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;

    }


    /**
     * ��redis���List
     * @param listKey
     * @param value
     */
    public static void setVByList(String listKey,Object value){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] lkey = SerializeUtil.serialize(listKey);
            byte[] svalue = SerializeUtil.serialize(value);
            jds.rpush(lkey, svalue);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
    }
    /**
     * ��redis��ȡlist
     *
     * @param <T>
     * @param listKey
     * @param start
     * @param end
     * @param requiredType
     * @return
     */
    public static <T> List<T> getVByList(String listKey,int start,int end,Class<T> requiredType){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] lkey = SerializeUtil.serialize(listKey);
            List<T> list = new ArrayList<T>();
            List<byte[]> xx = jds.lrange(lkey,start,end);
            for (byte[] bs : xx) {
                T t = SerializeUtil.deserialize(bs, requiredType);
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }
    /**
     * ��ȡlist����
     * @param listKey
     * @return
     */
    public static Long getLenByList(String listKey){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] lkey = SerializeUtil.serialize(listKey);
            Long result = jds.llen(lkey);
            return result;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }
    /**
     * ɾ��
     * @param dkey
     * @return
     */
    public static Long delByKey(String...dkey){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[][] dx = new byte[dkey.length][];
            for (int i = 0; i < dkey.length; i++) {
                dx[i] = SerializeUtil.serialize(dkey[i]);
            }
            Long result = jds.del(dx);
            return result;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return new Long(0);
    }
    /**
     * �ж��Ƿ����
     * @param existskey
     * @return
     */
    public static boolean exists(String existskey){
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(0);
            byte[] lkey = SerializeUtil.serialize(existskey);
            return jds.exists(lkey);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return false;
    }
    /**
     * �ͷ�
     * @param jedis
     * @param isBroken
     */
    public static void returnResource(Jedis jedis, boolean isBroken) {
        if (jedis == null)
            return;
        if (isBroken)
            J.getJedisPool().returnBrokenResource(jedis);
        else
            J.getJedisPool().returnResource(jedis);
//        �汾����
//        jedis.close();
    }
}
