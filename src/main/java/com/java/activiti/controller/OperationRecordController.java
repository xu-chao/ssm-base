package com.java.activiti.controller;

import com.java.activiti.model.OperationRecord;
import com.java.activiti.model.PageInfo;
import com.java.activiti.service.OperationRecordService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/operationRecord")
public class OperationRecordController {

    @Resource
    private OperationRecordService operationRecordService;

    /**
     * ��ҳ��ѯϵͳ��־
     * @return
     * @throws Exception
     */
    @RequestMapping("/operationRecordPage")
    public String systemLogPage(HttpServletResponse response, String rows,
                            String page, String sort, String order, String userID, String startDate, String endDate) throws Exception {
        System.out.println(userID + startDate + endDate);
        PageInfo<OperationRecord> operationRecordPage = new PageInfo<OperationRecord>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("userID", userID);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        operationRecordPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        operationRecordPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", operationRecordPage.getPageIndex());
        map.put("pageSize", operationRecordPage.getPageSize());
        int operationRecordCount = operationRecordService.operationRecordCount(map);
        List<OperationRecord> operationRecordList = operationRecordService.operationRecordPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(operationRecordList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", operationRecordCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: operationRecordExport
     * @Description: ��������������������Ӧ������
     * @param userID startDate endDate ��װ������
     * @return
     */
    @RequestMapping(value = "/operationRecordExport", method = RequestMethod.POST)
    @ResponseBody
    public void operationRecordExport(String userID, String startDate, String endDate, HttpServletResponse response) {
        String filename = "operationRecordExportBy" + UserUtil.getSubjectUserID() + ".xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userID", userID);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            operationRecordService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
