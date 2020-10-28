package com.java.activiti.controller.erp;

import com.java.activiti.dao.erp.NotQualifiedDao;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.NotQualified;
import com.java.activiti.model.erp.RCheckout;
import com.java.activiti.service.erp.NotQualifiedService;
import com.java.activiti.service.erp.RCheckoutService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import com.java.activiti.util.UuidUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
@RequestMapping("/notQualified")
public class NotQualifiedController {
    @Resource
    private NotQualifiedService notQualifiedService;

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

    @RequestMapping("/notQualifiedPage")
    public String notQualifiedPage(HttpServletResponse response, String rows,
                           String page, String userId) throws Exception {
        PageInfo<NotQualified> notQualifiedPage = new PageInfo<NotQualified>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        notQualifiedPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        notQualifiedPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", notQualifiedPage.getPageIndex());
        map.put("pageSize", notQualifiedPage.getPageSize());
        int notQualifiedCount = notQualifiedService.notQualifiedCount(map);
        List<NotQualified> notQualifiedList = notQualifiedService.notQualifiedPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(notQualifiedList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", notQualifiedCount);
        ResponseUtil.write(response, result);
        return null;
    }

//    /**
//     * 添加Erp单
//     *
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/addNotQualified")
//    public String addNotQualified(HttpServletResponse response, RCheckout rCheckout,
//                              @RequestParam(value = "NotQuaId") String NotQuaId) throws Exception{
//        rCheckout.setRCheckoutId("R" + UuidUtil.uuidUtil());
//        int checkoutResult=notQualifiedService.addRCheckout(rCheckout);
//        NotQualified notQualified = notQualifiedDao.findById(NotQuaId);
//        notQualified.setRCheckoutId(rCheckout.getRCheckoutId());
//        notQualifiedDao.updateNotQualified(notQualified);
//        JSONObject json=new JSONObject();
//        if(checkoutResult>0){
//            json.put("success", true);
//        }else{
//            json.put("success", false);
//        }
//        ResponseUtil.write(response, json);
//        return null;
//    }

    // model 日期格式的处理
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    /**
     * 修改采购表
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateNotQualified")
    public String updatenNotQualified(HttpServletResponse response,NotQualified notQualified) throws Exception{
        int notQualifiedResult=notQualifiedService.updateNotQualified(notQualified);
        JSONObject json=new JSONObject();
        if(notQualifiedResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

}
