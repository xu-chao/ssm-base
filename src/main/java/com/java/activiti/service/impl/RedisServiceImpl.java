package com.java.activiti.service.impl;

import com.alibaba.fastjson.JSON;
import com.java.activiti.model.Operate;
import com.java.activiti.model.RedisInfoDetail;
import com.java.activiti.service.RedisService;
import com.java.activiti.util.RedisUtil;
import org.springframework.stereotype.Service;
import redis.clients.util.Slowlog;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<RedisInfoDetail> getRedisInfo() {
        //获取redis服务器信息
        String info = redisUtil.getRedisInfo();
        List<RedisInfoDetail> ridList = new ArrayList<RedisInfoDetail>();
        String[] strs = info.split("\n");
        RedisInfoDetail rif = null;
        if (strs != null && strs.length > 0) {
            for (int i = 0; i < strs.length; i++) {
                rif = new RedisInfoDetail();
                String s = strs[i];
                String[] str = s.split(":");
                if (str != null && str.length > 1) {
                    String key = str[0];
                    String value = str[1];
                    rif.setKey(key);
                    rif.setValue(value);
                    ridList.add(rif);
                }
            }
        }
        return ridList;
    }

    @Override
    public List<Operate> getLogs(long entries) {
        List<Slowlog> list = redisUtil.getLogs(entries);
        List<Operate> opList = null;
        Operate op  = null;
        boolean flag = false;
        if (list != null && list.size() > 0) {
            opList = new LinkedList<Operate>();
            for (Slowlog sl : list) {
                String args = JSON.toJSONString(sl.getArgs());
                if (args.equals("[\"PING\"]") || args.equals("[\"SLOWLOG\",\"get\"]") || args.equals("[\"DBSIZE\"]") || args.equals("[\"INFO\"]")) {
                    continue;
                }
                op = new Operate();
                flag = true;
                op.setId(sl.getId());
                op.setExecuteTime(getDateStr(sl.getTimeStamp() * 1000));
                op.setUsedTime(sl.getExecutionTime()/1000.0 + "ms");
                op.setArgs(args);
                opList.add(op);
            }
        }
        if (flag)
            return opList;
        else
            return null;
    }

    @Override
    public Long getLogLen() {
        return redisUtil.getLogsLen();
    }

    @Override
    public String logEmpty() {
        return redisUtil.logEmpty();
    }

    @Override
    public Map<String, Object> getKeysSize() {
        long dbSize = redisUtil.dbSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("create_time", new Date().getTime());
        map.put("dbSize", dbSize);
        return map;
    }

    @Override
    public Map<String, Object> getMemeryInfo() {
        String[] strs = redisUtil.getRedisInfo().split("\n");
        Map<String, Object> map = null;
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            String[] detail = s.split(":");
            if (detail[0].equals("used_memory")) {
                map = new HashMap<String, Object>();
                map.put("used_memory",detail[1].substring(0, detail[1].length() - 1));
                map.put("create_time", new Date().getTime());
                break;
            }
        }
        return map;
    }

    /**
     * 同步锁原子性递增流水号
     * LIUHD
     * 当期日期 + 当天从1自增
     * 如：201912011
     * @param  lengths 最小（10）,Name 名字开头（ERP_ID）,
     *                 dbindex (0-15)切换的DB缓存区,days 存储天数
     * @return
     */
    public String  getIncrementNum(int lengths,String Name,int dbindex,int days) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String newDate = sdf.format(new Date());
        long ERP_ID = redisUtil.getIncrementNum(Name + newDate,dbindex,days);

        String str = String.valueOf(ERP_ID);
        str = newDate + str;//20191102 + 1
        int len = str.length();//日期加生成流水号 长度
        if (len>=lengths){
            return str;
        }
        int rest = lengths - len;
        StringBuilder sb = new StringBuilder();
        sb.append(newDate);//20191102
        for(int i =0;i<rest;i++){
            sb.append('0');
        }
        sb.append(ERP_ID);//20191102 + 0000 + 1
        return sb.toString();
    }

    private String getDateStr(long timeStmp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(timeStmp));
    }
}
