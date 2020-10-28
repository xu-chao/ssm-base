package com.java.activiti.controller.erp;

import com.java.activiti.dao.NeedGoodsDao;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.Checkout;
import com.java.activiti.model.erp.Purchase;
import com.java.activiti.service.erp.CheckoutService;
import com.java.activiti.service.erp.PurchaseService;
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
@RequestMapping("/checkout")
public class CheckoutController {
    @Resource
    private CheckoutService checkoutService;
    @Resource
    private NeedGoodsDao needGoodsDao;

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

    @RequestMapping("/checkoutPage")
    public String checkoutPage(HttpServletResponse response, String rows,
                           String page, String userId) throws Exception {
        PageInfo<Checkout> checkoutPage = new PageInfo<Checkout>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        checkoutPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        checkoutPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", checkoutPage.getPageIndex());
        map.put("pageSize", checkoutPage.getPageSize());
        int checkoutCount = checkoutService.checkoutCount(map);
        List<Checkout> checkoutList = checkoutService.checkoutPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(checkoutList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", checkoutCount);
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
    @RequestMapping("/addCheckout")
    public String addCheckout(HttpServletResponse response, Checkout checkout,
                              @RequestParam(value = "NID") String NID, @RequestParam(value = "goodsId") String goodsId) throws Exception{
        checkout.setCheckoutId("C" + UuidUtil.uuidUtil());
        checkout.setCheckoutPerson(UserUtil.getSubjectUserID());
        checkout.setCheckoutFlag(1);
        checkout.setCheckoutNum(1);
        int checkoutResult=checkoutService.addCheckout(checkout);
        needGoodsDao.updateCheckoutId(NID,goodsId,checkout.getCheckoutId());
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
    @RequestMapping("/updateCheckout")
    public String updateCheckout(HttpServletResponse response,Checkout checkout) throws Exception{
        int checkoutResult = 0;
        if(checkout.getCheckoutFlag() == 1){
            checkoutResult=checkoutService.updateCheckout(checkout);
        }else if (checkout.getCheckoutFlag() == 2){
            checkout.setCheckoutNum(checkout.getCheckoutNum() + 1);
            checkoutResult=checkoutService.updateCheckout(checkout);
        }
        JSONObject json=new JSONObject();
        if(checkoutResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 修改采购表
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateCheckoutThrough")
    public String updateCheckoutThrough(HttpServletResponse response,Checkout checkout) throws Exception{
        int checkoutResult = 0;
        checkoutResult=checkoutService.updateCheckout(checkout);
        JSONObject json=new JSONObject();
        if(checkoutResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

}
