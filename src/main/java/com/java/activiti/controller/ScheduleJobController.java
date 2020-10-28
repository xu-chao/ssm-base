package com.java.activiti.controller;

import com.java.activiti.exception.ScheduleException;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.ScheduleJob;
import com.java.activiti.service.ScheduleJobService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/scheduleJob")
public class ScheduleJobController {

    /** job service */
    @Resource
    private ScheduleJobService scheduleJobService;

    /**
     * 分页查询定时任务
     * @return
     * @throws Exception
     */
    @RequestMapping("/scheduleJobPage")
    public String scheduleJobPage(HttpServletResponse response, String rows,
                           String page, String sort, String order, String jobName) throws Exception {
        PageInfo<ScheduleJob> scheduleJobPage = new PageInfo<ScheduleJob>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("jobName", jobName);
        Integer pageSize = Integer.parseInt(rows);
        scheduleJobPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        scheduleJobPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", scheduleJobPage.getPageIndex());
        map.put("pageSize", scheduleJobPage.getPageSize());
        int scheduleJobCount = scheduleJobService.scheduleJobCount(map);
        List<ScheduleJob> sheduleJobList = scheduleJobService.scheduleJobPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(sheduleJobList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", scheduleJobCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 分页查询定时任务2
     * @return
     * @throws Exception
     */
    @RequestMapping("/scheduleJobPage2")
    public String scheduleJobPage2(HttpServletResponse response, String rows,
                                  String page, String sort, String order, String jobName) throws Exception {
        PageInfo<ScheduleJob> scheduleJobPage = new PageInfo<ScheduleJob>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("jobName", jobName);
        Integer pageSize = Integer.parseInt(rows);
        scheduleJobPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        scheduleJobPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", scheduleJobPage.getPageIndex());
        map.put("pageSize", scheduleJobPage.getPageSize());
        int scheduleJobCount = scheduleJobService.scheduleJobCount(map);
        List<ScheduleJob> sheduleJobList = scheduleJobService.queryExecutingJobList(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(sheduleJobList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", scheduleJobCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 删除任务
     *
     * @return
     */
    @RequestMapping(value = "deleteScheduleJob", method = RequestMethod.GET)
    public String deleteScheduleJob(Long scheduleJobId) throws ScheduleException {

        scheduleJobService.delete(scheduleJobId);

        return null;
    }

    /**
     * 运行一次
     *
     * @return
     */
    @RequestMapping(value = "runOnceScheduleJob", method = RequestMethod.GET)
    public String runOnceScheduleJob(Long scheduleJobId) throws ScheduleException {

        scheduleJobService.runOnce(scheduleJobId);

        return null;
    }

    /**
     * 暂停
     *
     * @return
     */
    @RequestMapping(value = "pauseScheduleJob", method = RequestMethod.GET)
    public String pauseScheduleJob(Long scheduleJobId) throws ScheduleException {
        scheduleJobService.pauseJob(scheduleJobId);
        return null;
    }

    /**
     * 恢复
     *
     * @return
     */
    @RequestMapping(value = "resumeScheduleJob", method = RequestMethod.GET)
    public String resumeScheduleJob(Long scheduleJobId) throws ScheduleException {
        scheduleJobService.resumeJob(scheduleJobId);
        return null;
    }

    /**
     * 新增定时任务
     * @return
     * @throws Exception
     */
    @RequestMapping("/addScheduleJob")
    public String addScheduleJob(HttpServletResponse response, ScheduleJob scheduleJob) throws Exception{
        Long scheduleJobResult=scheduleJobService.insert(scheduleJob);
        JSONObject json=new JSONObject();
        if(scheduleJobResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 修改定时任务
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateScheduleJob")
    public String updateScheduleJob(HttpServletResponse response,ScheduleJob scheduleJob) throws Exception{
        Long scheduleJobResult=scheduleJobService.update(scheduleJob);
        JSONObject json=new JSONObject();
        if(scheduleJobResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: searchScheduleJob
     * @Description: 根据城市ID删除定时任务
     * @return
     */
    @RequestMapping(value = "/scheduleJobDelete", method = RequestMethod.POST)
    @ResponseBody
    public String scheduleJobDelete(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<Long> list=new ArrayList<Long>();
        String[] strs = id.split(",");
        long[] strArrNum = (long[]) ConvertUtils.convert(strs,long.class);
        for (Long str : strArrNum) {
            list.add(str);
        }
        try {
            Long scheduleJobResult=scheduleJobService.deleteScheduleJobById(list);
            if(scheduleJobResult>0){
                json.put("success", true);
            }else{
                json.put("success", false);
            }
        } catch (Exception e) {
            json.put("success", false);
            e.printStackTrace();
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 判断定时任务是否已经存在
     * @return
     * @throws Exception
     */
    @RequestMapping("/existScheduleJob")
    public String existScheduleJob(HttpServletResponse response,
                                   ScheduleJob scheduleJob) throws Exception{
        List<ScheduleJob> scheduleJobResult = scheduleJobService.findScheduleJob(scheduleJob);
        JSONObject json=new JSONObject();
        if(scheduleJobResult.size() > 0 ){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 保存任务
     *
     * @param scheduleJob
     * @return
     */
    @RequestMapping(value = "saveScheduleJob", method = RequestMethod.POST)
    public String saveScheduleJob(ScheduleJob scheduleJob) throws ScheduleException {

        //测试用，设个状态
        scheduleJob.setStatus("1");

        if (scheduleJob.getScheduleJobId() == null) {
            scheduleJobService.insert(scheduleJob);
        } else if (false){
            scheduleJobService.delUpdate(scheduleJob);
        }else {
            scheduleJobService.update(scheduleJob);
        }
        return null;
    }

    /**
     * 任务定时测试
     *
     * @return
     */
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public void test() {
        //UDPClient.UDPClient();
        System.out.println("test");
    }
}
