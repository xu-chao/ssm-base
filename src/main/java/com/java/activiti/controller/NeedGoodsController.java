package com.java.activiti.controller;

import com.java.activiti.model.*;
import com.java.activiti.service.MemberShipService;
import com.java.activiti.service.NeedGoodsService;
import com.java.activiti.service.NeedService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.StringUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/needGoods")
public class NeedGoodsController {

    @Resource
    private NeedGoodsService needGoodsService;
    @Resource
    private NeedService needService;
    @Resource
    private TaskService taskService;


    /**
     * 分页查询业务
     *
     * @param response
     * @param rows
     * @param page
     * @return
     * @throws Exception
     */

    @RequestMapping("/needGoodsThroughPage")
    public String needGoodsThroughPage(HttpServletResponse response, String userId, String rows, String flag,
                                  String page, String searchType, String searchValue, String startDate, String endDate) throws Exception {
        PageInfo<NeedGoods> needGoodsPage = new PageInfo<NeedGoods>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", flag);
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        needGoodsPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        needGoodsPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", needGoodsPage.getPageIndex());
        map.put("pageSize", needGoodsPage.getPageSize());
        int needGoodsCount = needGoodsService.needGoodsThroughCount(map);
        LinkedList<NeedGoods> needGoodsList = needGoodsService.needGoodsThroughPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(needGoodsList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", needGoodsCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * @return
     * @Title: pojectExport
     * @Description: 根据搜索条件，导出对应的数据
     */
    @RequestMapping(value = "needGoodsThroughExport", method = RequestMethod.POST)
    @ResponseBody
    public void needGoodsThroughExport(HttpServletResponse response,String flag, String searchType, String searchValue, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);
        String filename = flag + "ExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", flag);
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            if(!flag.isEmpty()){
                needGoodsService.needGoodsExport(response.getOutputStream(), map, flag);
            }else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/needGoodsPage")
    public String needGoodsPage(HttpServletResponse response, String rows,
                                String page, String taskId) throws Exception {
        PageInfo<NeedGoods> needGoodsPage = new PageInfo<NeedGoods>();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Need need = needService.getNeedByTaskId(task.getProcessInstanceId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nId", need.getNID());
        Integer pageSize = Integer.parseInt(rows);
        needGoodsPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        needGoodsPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", needGoodsPage.getPageIndex());
        map.put("pageSize", needGoodsPage.getPageSize());
        int needGoodsCount = needGoodsService.needGoodsCount(map);
        List<NeedGoods> needGoodsList = needGoodsService.needGoodsPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(needGoodsList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", needGoodsCount);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/updateNeedGoods")
    public String updateNeedGoods(HttpServletResponse response, String nId, String goodsIds) throws Exception {
        //刪除全部NeedGoods
        needGoodsService.deleteAllGoodsByNId(nId);

        if (StringUtil.isNotEmpty(goodsIds)) {
            //分割字符串，以，分割
            String idsArr[] = goodsIds.split(",");
            for (String goodsId : idsArr) {
                Need need = new Need();
                need.setId(nId);
                Goods goods = new Goods();
                goods.setGoodsId(goodsId);
                NeedGoods needGoods = new NeedGoods();
                needGoods.setNeed(need);
                needGoods.setGoods(goods);
                needGoodsService.addNeedGoods(needGoods);
            }
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/updateGoods")
    public String updateGoods(HttpServletResponse response,String nId_tt, String goodsId_tt, String goodsId) throws Exception {
        JSONObject result = new JSONObject();
        int updateGoodsResult = needGoodsService.updateGoods(nId_tt,goodsId_tt,goodsId);
        if(updateGoodsResult >0){
            result.put("success", true);
        }else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }
}
