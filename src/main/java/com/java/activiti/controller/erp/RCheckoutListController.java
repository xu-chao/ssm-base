package com.java.activiti.controller.erp;

import com.java.activiti.dao.erp.NotQualifiedDao;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.NotQualified;
import com.java.activiti.model.erp.RCheckout;
import com.java.activiti.model.erp.RCheckoutList;
import com.java.activiti.service.erp.RCheckoutListService;
import com.java.activiti.service.erp.RCheckoutService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import com.java.activiti.util.UuidUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务处理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/rCheckoutList")
public class RCheckoutListController {
    @Resource
    private RCheckoutListService rCheckoutListService;

    /**
     * 分页查询业务
     *
     * @param response
     * @param rows
     * @param page
     * @param userId
     * @return
     * @throws Exception
     */

    @RequestMapping("/rCheckoutListPage")
    public String rCheckoutListPage(HttpServletResponse response, String rows,
                                    String page, String userId, @Param("RCheckoutId") String RCheckoutId) throws Exception {
        PageInfo<RCheckoutList> rCheckoutListPage = new PageInfo<RCheckoutList>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        map.put("RCheckoutId",RCheckoutId);
        Integer pageSize = Integer.parseInt(rows);
        rCheckoutListPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        rCheckoutListPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", rCheckoutListPage.getPageIndex());
        map.put("pageSize", rCheckoutListPage.getPageSize());
        int rCheckoutListCount = rCheckoutListService.rCheckoutListCount(map);
        List<RCheckoutList> rCheckoutListList = rCheckoutListService.rCheckoutListPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(rCheckoutListList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", rCheckoutListCount);
        ResponseUtil.write(response, result);
        return null;
    }
}
