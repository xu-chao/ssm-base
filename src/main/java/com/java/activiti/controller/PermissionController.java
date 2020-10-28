package com.java.activiti.controller;

import com.java.activiti.model.OperationRecord;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.Permission;
import com.java.activiti.service.PermissionService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 分页查询权限管理
     * @return
     * @throws Exception
     */
    @RequestMapping("/permissionPage")
    public String permissionPage(HttpServletResponse response, String rows,
                                String page, String name) throws Exception {
        PageInfo<OperationRecord> operationRecordPage = new PageInfo<OperationRecord>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        Integer pageSize = Integer.parseInt(rows);
        operationRecordPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        operationRecordPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", operationRecordPage.getPageIndex());
        map.put("pageSize", operationRecordPage.getPageSize());
        int operationRecordCount = permissionService.permissionCount(map);
        List<Permission> permissionList = permissionService.permissionPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(permissionList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", operationRecordCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 新增权限
     * @return
     * @throws Exception
     */
    @RequestMapping("/permissionSave")
    public String permissionSave(HttpServletResponse response, Permission permission) throws Exception{
        int permissionResult=permissionService.addPermission(permission);
        JSONObject json=new JSONObject();
        if(permissionResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 判断权限是否已经存在
     * @return
     * @throws Exception
     */
    @RequestMapping("/existPermissionID")
    public String existPermission(HttpServletResponse response,String permissionName) throws Exception{
        Permission result=permissionService.findById(permissionName);
        JSONObject json=new JSONObject();
        if(result != null){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 修改权限
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePermission")
    public String updatePermission(HttpServletResponse response,Permission permission) throws Exception{
        int result=permissionService.updatePermission(permission);
        JSONObject json=new JSONObject();
        if(result>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 批量刪除用戶权限信息
     * @param response
     * @param request
     * @return String
     * @throws Exception
     */
    @RequestMapping("/deletePermission")
    public String deletePermission(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int permissionResult=permissionService.deletePermission(list);
            if(permissionResult>0){
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
}
