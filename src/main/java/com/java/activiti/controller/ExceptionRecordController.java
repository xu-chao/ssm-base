package com.java.activiti.controller;

import com.java.activiti.model.ExceptionInfo;
import com.java.activiti.model.PageInfo;
import com.java.activiti.service.ExceptionLogService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exceptionRecord")
public class ExceptionRecordController {

    @Resource
    private ExceptionLogService exceptionLogService;

    /**
     * 分页查询系统日志
     * @return
     * @throws Exception
     */
    @RequestMapping("/exceptionRecordPage")
    public String systemLogPage(HttpServletResponse response, String rows,
                            String page, String sort, String order, String userID, String startDate, String endDate) throws Exception {
        System.out.println(userID + startDate + endDate);
        PageInfo<ExceptionInfo> exceptionRecordPage = new PageInfo<ExceptionInfo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("userID", userID);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        exceptionRecordPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        exceptionRecordPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", exceptionRecordPage.getPageIndex());
        map.put("pageSize", exceptionRecordPage.getPageSize());
        int exceptionRecordCount = exceptionLogService.exceptionRecordCount(map);
        List<ExceptionInfo> exceptionRecordList = exceptionLogService.exceptionRecordPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(exceptionRecordList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", exceptionRecordCount);
        ResponseUtil.write(response, result);
        return null;
    }

//    /**
//     *
//     * @Title: exceptionRecordExport
//     * @Description: 根据搜索条件，导出对应的数据
//     * @param userID startDate endDate 封装的条件
//     * @return
//     */
//    @RequestMapping(value = "/exceptionRecordExport", method = RequestMethod.POST)
//    @ResponseBody
//    public void exceptionRecordExport(String userID, String startDate, String endDate, HttpServletResponse response) {
//        String filename = "exceptionRecordExportBy" + UserUtil.getSubjectUserID() + ".xls";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("userID", userID);
//        map.put("startDate", startDate);
//        map.put("endDate", endDate);
//        // 响应对象
//        try {
//            // 设置输出流,实现下载文件
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));
//
//            exceptionLogService.export(response.getOutputStream(), map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
