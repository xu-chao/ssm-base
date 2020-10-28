package com.java.activiti.controller.erp;

import com.java.activiti.dao.NeedGoodsDao;
import com.java.activiti.dao.erp.NotQualifiedDao;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.Checkout;
import com.java.activiti.model.erp.NotQualified;
import com.java.activiti.model.erp.RCheckout;
import com.java.activiti.service.erp.CheckoutService;
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
@RequestMapping("/rCheckout")
public class RCheckoutController {
    @Resource
    private RCheckoutService rCheckoutService;
    @Resource
    private NotQualifiedDao notQualifiedDao;

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

    @RequestMapping("/rCheckoutPage")
    public String rCheckoutPage(HttpServletResponse response, String rows,
                           String page, String userId) throws Exception {
        PageInfo<RCheckout> rCheckoutPage = new PageInfo<RCheckout>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        rCheckoutPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        rCheckoutPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", rCheckoutPage.getPageIndex());
        map.put("pageSize", rCheckoutPage.getPageSize());
        int rCheckoutCount = rCheckoutService.rCheckoutCount(map);
        List<RCheckout> rCheckoutList = rCheckoutService.rCheckoutPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(rCheckoutList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", rCheckoutCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 添加Erp单
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/addRCheckout")
    public String addRCheckout(HttpServletResponse response, RCheckout rCheckout,
                              @RequestParam(value = "NotQuaId") String NotQuaId) throws Exception{
        rCheckout.setRCheckoutId("RC" + UuidUtil.uuidUtil());
        int checkoutResult=rCheckoutService.addRCheckout(rCheckout);
        NotQualified notQualified = notQualifiedDao.findById(NotQuaId);
        notQualified.setRCheckoutId(rCheckout.getRCheckoutId());
        notQualifiedDao.updateNotQualified(notQualified);
        JSONObject json=new JSONObject();
        if(checkoutResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

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
    @RequestMapping("/updateRCheckout")
    public String updateRCheckout(HttpServletResponse response,RCheckout rCheckout) throws Exception{
        int rCheckoutResult = 0;
        if(rCheckout.getRCheckoutFlag() == 1){
            rCheckoutResult=rCheckoutService.updateRCheckout(rCheckout);
        }else if(rCheckout.getRCheckoutFlag() == 2){
            rCheckout.setRCheckoutNum(rCheckout.getRCheckoutNum() + 1);
            rCheckoutResult=rCheckoutService.updateRCheckout(rCheckout);
        }
        JSONObject json=new JSONObject();
        if(rCheckoutResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

}
