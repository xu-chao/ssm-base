package com.java.activiti.controller;

import com.alibaba.fastjson.JSON;
import com.java.activiti.model.Operate;
import com.java.activiti.model.RedisInfoDetail;
import com.java.activiti.service.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    //跳转到监控页面
    @RequestMapping(value="redisManage")
    public String redisManage(Model model) {
        //获取redis的info
        List<RedisInfoDetail> ridList = redisService.getRedisInfo();
        //获取redis的日志记录
        List<Operate> logList = redisService.getLogs(100);
        //获取日志总数
        long logLen = redisService.getLogLen();
        model.addAttribute("infoList", ridList);
        model.addAttribute("logList", logList);
        model.addAttribute("logLen", logLen);
        return "page/redisManage";
    }
    //清空日志按钮
    @RequestMapping(value="logEmpty")
    @ResponseBody
    public String logEmpty(){
        return redisService.logEmpty();
    }

    //获取当前数据库中key的数量
    @RequestMapping(value="getKeysSize")
    @ResponseBody
    public String getKeysSize(){
        return JSON.toJSONString(redisService.getKeysSize());
    }

    //获取当前数据库内存使用大小情况
    @RequestMapping(value="getMemeryInfo")
    @ResponseBody
    public String getMemeryInfo(){
        return JSON.toJSONString(redisService.getMemeryInfo());
    }
}
