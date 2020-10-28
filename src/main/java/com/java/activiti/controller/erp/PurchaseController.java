package com.java.activiti.controller.erp;

import com.java.activiti.dao.NeedGoodsDao;
import com.java.activiti.model.*;
import com.java.activiti.model.erp.Purchase;
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
import java.util.*;

/**
 * 业务处理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController {
    @Resource
    private PurchaseService purchaseService;
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

    @RequestMapping("/purchasePage")
    public String purchasePage(HttpServletResponse response, String rows,
                               String page, String userId) throws Exception {
        PageInfo<Purchase> purchasePage = new PageInfo<Purchase>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId == "" || "".equals(userId) || userId == null) {
            userId = UserUtil.getSubjectUserID();
        }
        map.put("userId", userId);
        Integer pageSize = Integer.parseInt(rows);
        purchasePage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        purchasePage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", purchasePage.getPageIndex());
        map.put("pageSize", purchasePage.getPageSize());
        int purchaseCount = purchaseService.purchaseCount(map);
        List<Purchase> purchaseList = purchaseService.purchasePage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(purchaseList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", purchaseCount);
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
    @RequestMapping("/addPurchase")
    public String addPurchase(HttpServletResponse response, Purchase purchase,
                              @RequestParam(value = "NID") String NID, @RequestParam(value = "goodsId") String goodsId,
                              @RequestParam(value = "mountStorage") int mountStorage) throws Exception {
        purchase.setPurchaseId("P" + UuidUtil.uuidUtil());
        purchase.setPurchasedId(purchase.getPurchaseId());
        purchase.setPurchaseGoodsPerson(UserUtil.getSubjectUserID());
        purchase.setPurchaseLeave(mountStorage - purchase.getPurchaseMount());
        purchase.setPurchaseNum(1);
        purchase.setPurchaseFlag(1);
        int purchaseResult = purchaseService.addPurchase(purchase);
        needGoodsDao.updatePurchaseId(NID, goodsId, purchase.getPurchaseId());
        JSONObject json = new JSONObject();
        if (purchaseResult > 0) {
            json.put("success", true);
        } else {
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
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePurchase")
    public String updatePurchase(HttpServletResponse response, Purchase purchase, int mountStorage) throws Exception {
        int purchaseResult = 0;
        Purchase purchaseOld = purchaseService.findById(purchase.getPurchaseId());
        Integer purchaseLeaveOld = new Integer(purchaseOld.getPurchaseMount());
        Integer purchaseMount = new Integer(purchase.getPurchaseMount());
        if (purchase.getPurchaseFlag() == 1) {
            if (purchaseLeaveOld == null && purchaseMount == null) {
                int purchaseCount = purchaseOld.getPurchaseLeave() - purchase.getPurchaseMount();
                purchase.setPurchaseLeave(purchaseCount);
            } else {
                int purchaseLeave = mountStorage - purchase.getPurchaseMount();
                purchase.setPurchaseLeave(purchaseLeave);
            }
        } else if (purchase.getPurchaseFlag() == 2) {
            purchase.setPurchaseNum(purchase.getPurchaseNum() + 1);
            if (purchaseLeaveOld == null && purchaseMount == null) {
                int purchaseCount = purchaseOld.getPurchaseLeave() - purchase.getPurchaseMount();
                purchase.setPurchaseLeave(purchaseCount);
            } else {
                int purchaseLeave = mountStorage - purchase.getPurchaseMount();
                purchase.setPurchaseLeave(purchaseLeave);
            }
        }
        purchaseResult = purchaseService.updatePurchase(purchase);
        JSONObject json = new JSONObject();
        if (purchaseResult > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 添加Erp单
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/addPurchaseThrough")
    public String addPurchaseThrough(HttpServletResponse response, Purchase purchase,
                                     @RequestParam(value = "NID") String NID, @RequestParam(value = "goodsId") String goodsId,
                                     @RequestParam(value = "mountStorage") int mountStorage) throws Exception {
        purchase.setPurchaseId("P" + UuidUtil.uuidUtil());
        purchase.setPurchasedId(purchase.getPurchaseId());
        purchase.setPurchaseGoodsPerson(UserUtil.getSubjectUserID());
        purchase.setPurchaseLeave(mountStorage - purchase.getPurchaseMount());
        purchase.setPurchaseNum(1);
        purchase.setPurchaseFlag(1);
        int purchaseResult = purchaseService.addPurchase(purchase);
        needGoodsDao.updatePurchaseId(NID, goodsId, purchase.getPurchaseId());
        JSONObject json = new JSONObject();
        if (purchaseResult > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    @RequestMapping("/updatePurchaseThrough")
    public String updatePurchaseThrough(HttpServletResponse response, Purchase purchase, int mountStorage) throws Exception {
        int purchaseResult = 0;
        JSONObject json = new JSONObject();
        int purchaseLeave = mountStorage - purchase.getPurchaseMount();
        purchase.setPurchaseLeave(purchaseLeave);
        purchaseResult = purchaseService.updatePurchase(purchase);
        if (purchaseResult > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }
}
