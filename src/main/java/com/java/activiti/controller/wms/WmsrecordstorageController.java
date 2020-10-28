package com.java.activiti.controller.wms;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.wms.Wmsrecordstorage;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.RedisService;
import com.java.activiti.service.wms.WmsrecordstorageService;
import com.java.activiti.service.wms.WmsrecordstorageService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/wmsrecordstorage")
public class WmsrecordstorageController {
    @Resource
    private WmsrecordstorageService wmsrecordstorageService;

    /**
     * 分页查询供应商
     * @return
     * @throws Exception
     */
    @RequestMapping("/wmsrecordstoragePage")
    public String wmsrecordstoragePage(HttpServletResponse response, String rows,
                            String page, String sort, String order,
                            Integer recordGoodId, Integer recordWarehouseId) throws Exception {
        PageInfo<Wmsrecordstorage> wmsrecordstoragePage = new PageInfo<Wmsrecordstorage>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("recordGoodId", recordGoodId);
        map.put("recordWarehouseId", recordWarehouseId);
        Integer pageSize = Integer.parseInt(rows);
        wmsrecordstoragePage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        wmsrecordstoragePage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", wmsrecordstoragePage.getPageIndex());
        map.put("pageSize", wmsrecordstoragePage.getPageSize());
        int wmsrecordstorageCount = wmsrecordstorageService.wmsrecordstorageCount(map);
        List<Wmsrecordstorage> wmsrecordstorageList = wmsrecordstorageService.wmsrecordstoragePage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(wmsrecordstorageList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", wmsrecordstorageCount);
        ResponseUtil.write(response, result);
        return null;
    }


}
