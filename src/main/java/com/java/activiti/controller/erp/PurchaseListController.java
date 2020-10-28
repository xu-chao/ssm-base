package com.java.activiti.controller.erp;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.PurchaseList;
import com.java.activiti.model.erp.RCheckoutList;
import com.java.activiti.pojo.erp.Footer;
import com.java.activiti.service.erp.PurchaseListService;
import com.java.activiti.service.erp.RCheckoutListService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
@RequestMapping("/purchaseList")
public class PurchaseListController {
    @Resource
    private PurchaseListService purchaseListService;

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

    @RequestMapping("/purchaseListPage")
    public String purchaseListPage(HttpServletResponse response, String rows,
                                    String page, String userId, @Param("purchaseId") String purchaseId) throws Exception {
        PageInfo<PurchaseList> purchaseListPage = new PageInfo<PurchaseList>();
        Footer footer = new Footer();
        int count = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        map.put("purchaseId",purchaseId);
        Integer pageSize = Integer.parseInt(rows);
        purchaseListPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        purchaseListPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", purchaseListPage.getPageIndex());
        map.put("pageSize", purchaseListPage.getPageSize());
        int purchaseListCount = purchaseListService.purchaseListCount(map);
        List<PurchaseList> purchaseListList = purchaseListService.purchaseListPage(map);
        footer.setCode("合计");
        for (PurchaseList purchaseList : purchaseListList){
            count += purchaseList.getPurchase().getPurchaseMount();
        }
        footer.setCount(count);
        footer.setTotal(purchaseListList.size());
        List<Footer> footerList = new ArrayList<>();
        footerList.add(footer);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(purchaseListList, jsonConfig);
        JSONArray jsonCountArray = JSONArray.fromObject(footerList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", purchaseListCount);
        result.put("footer", jsonCountArray);
        ResponseUtil.write(response, result);
        return null;
    }
}
