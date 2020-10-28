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

    //��ת�����ҳ��
    @RequestMapping(value="redisManage")
    public String redisManage(Model model) {
        //��ȡredis��info
        List<RedisInfoDetail> ridList = redisService.getRedisInfo();
        //��ȡredis����־��¼
        List<Operate> logList = redisService.getLogs(100);
        //��ȡ��־����
        long logLen = redisService.getLogLen();
        model.addAttribute("infoList", ridList);
        model.addAttribute("logList", logList);
        model.addAttribute("logLen", logLen);
        return "page/redisManage";
    }
    //�����־��ť
    @RequestMapping(value="logEmpty")
    @ResponseBody
    public String logEmpty(){
        return redisService.logEmpty();
    }

    //��ȡ��ǰ���ݿ���key������
    @RequestMapping(value="getKeysSize")
    @ResponseBody
    public String getKeysSize(){
        return JSON.toJSONString(redisService.getKeysSize());
    }

    //��ȡ��ǰ���ݿ��ڴ�ʹ�ô�С���
    @RequestMapping(value="getMemeryInfo")
    @ResponseBody
    public String getMemeryInfo(){
        return JSON.toJSONString(redisService.getMemeryInfo());
    }
}
