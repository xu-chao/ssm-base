package com.java.activiti.controller.wms;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.wms.Wmsrecordin;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.RedisService;
import com.java.activiti.service.wms.WmsrecordinService;
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
@RequestMapping("/wmsrecordin")
public class WmsrecordinController {
    private static final int WMS_INCREMENT_LENGTH = 15;//流水号长度
    private static final int WMS_DBINDEX = 9;//redisDB缓存区
    private static final String WMS_INODD = "WMS_INODD";//redisDB缓存区
    @Resource
    private WmsrecordinService wmsrecordinService;

    @Resource
    private WmsrecordstorageService wmsrecordstorageService;

    @Resource
    private RedisService redisService;
    /**
     * 分页查询供应商
     * @return
     * @throws Exception
     */
    @RequestMapping("/wmsrecordinPage")
    public String wmsrecordinPage(HttpServletResponse response, String rows,
                            String page, String sort, String order,String inOdd,
                            Integer goodId, Integer supplierId, Integer whId) throws Exception {
        PageInfo<Wmsrecordin> wmsrecordinPage = new PageInfo<Wmsrecordin>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("inOdd", inOdd);
        map.put("goodId", goodId);
        map.put("whId", whId);
        map.put("supplierName", supplierId);
        Integer pageSize = Integer.parseInt(rows);
        wmsrecordinPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        wmsrecordinPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", wmsrecordinPage.getPageIndex());
        map.put("pageSize", wmsrecordinPage.getPageSize());
        int wmsrecordinCount = wmsrecordinService.wmsrecordinCount(map);
        List<Wmsrecordin> wmsrecordinList = wmsrecordinService.wmsrecordinPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(wmsrecordinList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", wmsrecordinCount);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * 新增入库记录
     * @return
     * @throws Exception
     */
    @RequestMapping("/addWmsrecordin")
    public String addWmsrecordin(HttpServletResponse response,Wmsrecordin wmsrecordin) throws Exception{
        wmsrecordin.setUserId(UserUtil.getSubjectUserID());
        wmsrecordin.setInDate(new Date());
        wmsrecordin.setInAllPri(wmsrecordin.getInPrice()*wmsrecordin.getInAmount());
        //入库记录
        int wmsrecordinResult=wmsrecordinService.addWmsrecordin(wmsrecordin);
        //仓库计数
        boolean wmsrecordstorageResult=wmsrecordstorageService.storageIncrease(wmsrecordin.getGoodId(),
                wmsrecordin.getWhId(),wmsrecordin.getInAmount());
        JSONObject json=new JSONObject();
        if(wmsrecordinResult>0||wmsrecordstorageResult){
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
     * 修改供应商
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateWmsrecordin")
    public String updateWmsrecordin(HttpServletResponse response,Wmsrecordin wmsrecordin) throws Exception{
        int wmsrecordinResult=wmsrecordinService.updateWmsrecordin(wmsrecordin);
        JSONObject json=new JSONObject();
        if(wmsrecordinResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Description: 根据供应商ID删除供应商
     * @return
     */
    @RequestMapping(value = "/wmsrecordinDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteWmsrecordinById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int wmsrecordinResult=wmsrecordinService.deleteWmsrecordinById(list);
            if(wmsrecordinResult>0){
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

    /**
     * 判断供应商是否已经存在
     * @return
     * @throws Exception
     */
    @RequestMapping("/existWmsrecordin")
    public String existWmsrecordin(HttpServletResponse response,
                            Wmsrecordin wmsrecordin) throws Exception{
        List<Wmsrecordin> wmsrecordinResult = wmsrecordinService.findWmsrecordin(wmsrecordin);
        JSONObject json=new JSONObject();
        if(wmsrecordinResult.size() > 0 ){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: cityExport
     * @Description: 根据搜索条件，导出对应的数据
     * @param wmsrecordinName 封装的条件
     * @return
     */
    @RequestMapping(value = "wmsrecordinExport", method = RequestMethod.POST)
    @ResponseBody
    public void wmsrecordinExport(String wmsrecordinName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "wmsrecordinExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wmsrecordinName", wmsrecordinName);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            wmsrecordinService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: wmsrecordinImport
     * @Description: 导入供应商信息excel
     * @author: LIUHD
     * @return
     */
    @RequestMapping(value = "/wmsrecordinImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO wmsrecordinImport(MultipartFile file) {
        try {
            wmsrecordinService.wmsrecordinImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }


    /**(1)用@ResponseBody注解
     (2)可以接收各种参数，url，data(名称匹配或@RequestBody)
     (3)返回值类型就是基本类型*/
    @ResponseBody
    @RequestMapping(value = "getNewInOdd", method = RequestMethod.POST)
    public String getNewInOdd() {
       String inOdd = redisService.getIncrementNum(WMS_INCREMENT_LENGTH,WMS_INODD,WMS_DBINDEX,2);
        //处理参数
        return inOdd;
    }
}
