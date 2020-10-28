package com.java.activiti.controller.erp;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.CheckoutList;
import com.java.activiti.model.erp.PurchaseList;
import com.java.activiti.pojo.erp.Footer;
import com.java.activiti.service.erp.CheckoutListService;
import com.java.activiti.service.erp.CheckoutService;
import com.java.activiti.service.erp.PurchaseListService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.activemq.store.PList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 业务处理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/checkoutList")
public class CheckoutListController {
    @Resource
    private CheckoutListService checkoutListService;

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

    @RequestMapping("/checkoutListPage")
    public String checkoutListPage(HttpServletResponse response, String rows,
                                    String page, String userId, @Param("checkoutId") String checkoutId) throws Exception {
        PageInfo<CheckoutList> checkoutListPage = new PageInfo<CheckoutList>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        map.put("checkoutId",checkoutId);
        Integer pageSize = Integer.parseInt(rows);
        checkoutListPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        checkoutListPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", checkoutListPage.getPageIndex());
        map.put("pageSize", checkoutListPage.getPageSize());
        int checkoutListCount = checkoutListService.checkoutListCount(map);
        List<CheckoutList> checkoutListList = checkoutListService.checkoutListPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(checkoutListList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", checkoutListCount);
        ResponseUtil.write(response, result);
        return null;
    }
}
