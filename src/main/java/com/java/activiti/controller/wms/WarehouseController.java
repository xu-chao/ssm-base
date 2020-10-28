package com.java.activiti.controller.wms;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.wms.Warehouse;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.wms.WarehouseService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/warehouse")
public class WarehouseController {

    @Resource
    private WarehouseService warehouseService;
    /**
     * 分页查询物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/warehousePage")
    public String warehousePage(HttpServletResponse response, String rows,
                            String page, String sort, String order,
                            String whName, String whAddress) throws Exception {
        PageInfo<Warehouse> warehousePage = new PageInfo<Warehouse>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("whName", whName);
        map.put("whAddress", whAddress);
        Integer pageSize = Integer.parseInt(rows);
        warehousePage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        warehousePage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", warehousePage.getPageIndex());
        map.put("pageSize", warehousePage.getPageSize());
        int warehouseCount = warehouseService.warehouseCount(map);
        List<Warehouse> warehouseList = warehouseService.warehousePage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(warehouseList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", warehouseCount);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * 新增物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/addWarehouse")
    public String addWarehouse(HttpServletResponse response,Warehouse warehouse) throws Exception{
        int warehouseResult=warehouseService.addWarehouse(warehouse);
        JSONObject json=new JSONObject();
        if(warehouseResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 修改物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateWarehouse")
    public String updateWarehouse(HttpServletResponse response,Warehouse warehouse) throws Exception{
        int warehouseResult=warehouseService.updateWarehouse(warehouse);
        JSONObject json=new JSONObject();
        if(warehouseResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Description: 根据城市ID删除城市
     * @return
     */
    @RequestMapping(value = "/warehouseDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteWarehouseById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int warehouseResult=warehouseService.deleteWarehouseById(list);
            if(warehouseResult>0){
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
     * 判断城市是否已经存在
     * @return
     * @throws Exception
     */
    @RequestMapping("/existWarehouse")
    public String existWarehouse(HttpServletResponse response,
                            Warehouse warehouse) throws Exception{
//        List<Warehouse> warehouseResult = warehouseService.findWarehouse(warehouse);
        JSONObject json=new JSONObject();
//        if(warehouseResult.size() > 0 ){
//            json.put("success", true);
//        }else{
            json.put("success", false);
//        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: cityExport
     * @Description: 根据搜索条件，导出对应的数据
     * @param warehouseName 封装的条件
     * @return
     */
    @RequestMapping(value = "warehouseExport", method = RequestMethod.POST)
    @ResponseBody
    public void warehouseExport(String warehouseName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "warehouseExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("warehouseName", warehouseName);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            warehouseService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: warehouseImport
     * @Description: 导入城市信息excel
     * @author: 许超
     * @return
     */
    @RequestMapping(value = "/warehouseImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO warehouseImport(MultipartFile file) {
        try {
            warehouseService.warehouseImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }

    /**
     * @return
     * @Title: searchAllWarehouse
     * @Description: 城市名自动补全
     */
    @RequestMapping(value = "/searchAllWarehouse", method = RequestMethod.POST)
    @ResponseBody
    public List<Warehouse> searchAllWarehouse() {
        List<Warehouse> warehouses = warehouseService.findALL();
        return warehouses;
    }

    /**
     *
     * @Title: searchGoods
     * @Description: 物料名自动补全
     * @return
     */
    @RequestMapping(value = "/searchWarehouse", method = RequestMethod.POST)
    @ResponseBody
    public List<Warehouse> searchWarehouse(String q,String whType) {
        if(q==null || whType==null){
            return null;
        }else {
            List<Warehouse> WarehouseName = warehouseService.findWarehouseName(q,whType);
            return WarehouseName;
        }
    }

    /**
     *
     * @Title: searchCity
     * @Description: 城市名自动补全
     * @return
     */
    @RequestMapping(value = "/searchAllWarehouseByWhType", method = RequestMethod.POST)
    @ResponseBody
    public List<Warehouse> searchAllWarehouseByWhType(String whType) {
        if(whType==null){
            return null;
        }else{
            List<Warehouse> WarehouseName = warehouseService.findWarehouseNameByWhType(whType);
            return WarehouseName;
        }
    }
}
