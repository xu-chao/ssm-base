package com.java.activiti.controller.wms;

import com.java.activiti.exception.WmsIncomingManageServiceException;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.wms.WmsIncoming;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.RedisService;
import com.java.activiti.service.wms.WmsIncomingService;
import com.java.activiti.service.wms.WmsrecordstorageService;
import com.java.activiti.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/wmsIncoming")
public class WmsIncomingController {
//    private static final int WMS_INCREMENT_LENGTH = 15;//流水号长度
//    private static final int WMS_DBINDEX = 9;//redisDB缓存区
//    private static final String WMS_INODD = "WMS_INODD";//redisDB缓存区
    @Resource
    private WmsIncomingService wmsIncomingService;

    @Autowired
    private ResponseUtil responseUtil;

    @Resource
    private WmsrecordstorageService wmsrecordstorageService;

//
//    @Resource
//    private RedisService redisService;
    /**
     * 分页查询供应商
     * @return
     * @throws Exception
     */
    @RequestMapping("/wmsIncomingPage")
    public String wmsIncomingPage(HttpServletResponse response, String rows,
                            String page, String sort, String order,String inOdd,
                            Integer goodId, Integer supplierId, Integer whId) throws Exception {
        PageInfo<WmsIncoming> wmsIncomingPage = new PageInfo<WmsIncoming>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("inOdd", inOdd);
        map.put("goodId", goodId);
        map.put("whId", whId);
        map.put("supplierName", supplierId);
        Integer pageSize = Integer.parseInt(rows);
        wmsIncomingPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        wmsIncomingPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", wmsIncomingPage.getPageIndex());
        map.put("pageSize", wmsIncomingPage.getPageSize());
        int wmsIncomingCount = wmsIncomingService.wmsIncomingCount(map);
        List<WmsIncoming> wmsIncomingList = wmsIncomingService.wmsIncomingPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(wmsIncomingList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", wmsIncomingCount);
        ResponseUtil.write(response, result);
        return null;
    }

//
//    /**
//     * 新增入库记录
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/addWmsIncoming")
//    public String addWmsIncoming(HttpServletResponse response,WmsIncoming wmsIncoming) throws Exception{
//        wmsIncoming.setUserId(UserUtil.getSubjectUserID());
//        wmsIncoming.setInDate(new Date());
//        wmsIncoming.setInAllPri(wmsIncoming.getInPrice()*wmsIncoming.getInAmount());
//        //入库记录
//        int wmsIncomingResult=wmsIncomingService.addWmsIncoming(wmsIncoming);
//        //仓库计数
//        boolean wmsrecordstorageResult=wmsrecordstorageService.storageIncrease(wmsIncoming.getGoodId(),
//                wmsIncoming.getWhId(),wmsIncoming.getInAmount());
//        JSONObject json=new JSONObject();
//        if(wmsIncomingResult>0||wmsrecordstorageResult){
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

//    /**
//     * 修改供应商
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/updateWmsIncoming")
//    public String updateWmsIncoming(HttpServletResponse response,WmsIncoming wmsIncoming) throws Exception{
//        int wmsIncomingResult=wmsIncomingService.updateWmsIncoming(wmsIncoming);
//        JSONObject json=new JSONObject();
//        if(wmsIncomingResult>0){
//            json.put("success", true);
//        }else{
//            json.put("success", false);
//        }
//        ResponseUtil.write(response, json);
//        return null;
//    }
//
//    /**
//     *
//     * @Description: 根据供应商ID删除供应商
//     * @return
//     */
//    @RequestMapping(value = "/wmsIncomingDelete", method = RequestMethod.POST)
//    @ResponseBody
//    public String deleteWmsIncomingById(HttpServletResponse response, HttpServletRequest request) throws Exception{
//        String id=request.getParameter("ids");
//        JSONObject json=new JSONObject();
//        List<String> list=new ArrayList<String>();
//        String[] strs = id.split(",");
//        for (String str : strs) {
//            list.add(str);
//        }
//        try {
//            int wmsIncomingResult=wmsIncomingService.deleteWmsIncomingById(list);
//            if(wmsIncomingResult>0){
//                json.put("success", true);
//            }else{
//                json.put("success", false);
//            }
//        } catch (Exception e) {
//            json.put("success", false);
//            e.printStackTrace();
//        }
//        ResponseUtil.write(response, json);
//        return null;
//    }
//
//    /**
//     * 判断供应商是否已经存在
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/existWmsIncoming")
//    public String existWmsIncoming(HttpServletResponse response,
//                            WmsIncoming wmsIncoming) throws Exception{
//        List<WmsIncoming> wmsIncomingResult = wmsIncomingService.findWmsIncoming(wmsIncoming);
//        JSONObject json=new JSONObject();
//        if(wmsIncomingResult.size() > 0 ){
//            json.put("success", true);
//        }else{
//            json.put("success", false);
//        }
//        ResponseUtil.write(response, json);
//        return null;
//    }
//
//    /**
//     *
//     * @Title: cityExport
//     * @Description: 根据搜索条件，导出对应的数据
//     * @param wmsIncomingName 封装的条件
//     * @return
//     */
//    @RequestMapping(value = "wmsIncomingExport", method = RequestMethod.POST)
//    @ResponseBody
//    public void wmsIncomingExport(String wmsIncomingName, HttpServletResponse response) {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date=new Date();
//        String dateStr = sdf.format(date);
//        String filename = "wmsIncomingExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("wmsIncomingName", wmsIncomingName);
//        // 响应对象
//        try {
//            // 设置输出流,实现下载文件
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));
//
//            wmsIncomingService.export(response.getOutputStream(), map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     *
     * @Title: wmsIncomingImport
     * @Description: 导入供应商信息excel
     * @author: LIUHD
     * @return
     */
    @RequestMapping(value = "/wmsIncomingImport", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> wmsIncomingImport(MultipartFile file) throws WmsIncomingManageServiceException {
        //  初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = "";

        // 读取文件内容
        int total = 0;
        int available = 0;
        int index = 0; //add
        if (file != null) {
            Map<String, Object> importInfo = wmsIncomingService.wmsIncomingImport(file);
            if (importInfo != null) {
                index = (int) importInfo.get("false");//add
                if (index != -1) { //add
                    total = (int) importInfo.get("total");
                    available = (int) importInfo.get("available");
                    result = "success";
                } else {//add
                    total = (int) importInfo.get("total");//add
                    responseContent.setCustomerInfo("false", index);//add
                    result = "error"; //add
                }


            }
        }
        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseTotal(total);
        responseContent.setCustomerInfo("available", available);
        return responseContent.generateResponse();

    }
//
//
//    /**(1)用@ResponseBody注解
//     (2)可以接收各种参数，url，data(名称匹配或@RequestBody)
//     (3)返回值类型就是基本类型*/
//    @ResponseBody
//    @RequestMapping(value = "getNewInOdd", method = RequestMethod.POST)
//    public String getNewInOdd() {
//       String inOdd = redisService.getIncrementNum(WMS_INCREMENT_LENGTH,WMS_INODD,WMS_DBINDEX,2);
//        //处理参数
//        return inOdd;
//    }
}
