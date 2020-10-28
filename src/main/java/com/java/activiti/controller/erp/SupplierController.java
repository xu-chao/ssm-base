package com.java.activiti.controller.erp;

import com.java.activiti.model.City;
import com.java.activiti.model.Goods;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.Respository;
import com.java.activiti.model.erp.Supplier;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.erp.RespositoryService;
import com.java.activiti.service.erp.SupplierService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Component;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @Resource
    private SupplierService supplierService;
    /**
     * 分页查询物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/supplierPage")
    public String supplierPage(HttpServletResponse response, String rows,
                            String page, String sort, String order, String SUPPLIER_NAME,String SUPPLIER_PERSON) throws Exception {
        PageInfo<Supplier> supplierPage = new PageInfo<Supplier>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("SUPPLIER_NAME", SUPPLIER_NAME);
        map.put("SUPPLIER_PERSON", SUPPLIER_PERSON);
        Integer pageSize = Integer.parseInt(rows);
        supplierPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        supplierPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", supplierPage.getPageIndex());
        map.put("pageSize", supplierPage.getPageSize());
        int supplierCount = supplierService.supplierCount(map);
        List<Supplier> suppierList = supplierService.supplierPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(suppierList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", supplierCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: searchGoods
     * @Description: 物料名自动补全
     * @return
     */
    @RequestMapping(value = "/searchSupplier", method = RequestMethod.POST)
    @ResponseBody
    public List<Supplier> searchSupplier(String q) {
        if(q==null){
            return null;
        }else {
            List<Supplier> SUPPLIER_NAME = supplierService.findSupplierName(q);
            return SUPPLIER_NAME;
        }
    }

    /**
     *
     * @Title: searchCity
     * @Description: 城市名自动补全
     * @return
     */
    @RequestMapping(value = "/searchAllSpuplier", method = RequestMethod.POST)
    @ResponseBody
    public List<Supplier> searchAllSpuplier() {
        List<Supplier> SUPPLIER_NAME = supplierService.findSupplierName();
        return SUPPLIER_NAME;
    }

    /**
     * 新增物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/addSupplier")
    public String addSupplier(HttpServletResponse response,Supplier supplier) throws Exception{
        int supplierResult=supplierService.addSupplier(supplier);
        JSONObject json=new JSONObject();
        if(supplierResult>0){
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
    @RequestMapping("/updateSupplier")
    public String updateSupplier(HttpServletResponse response,Supplier supplier) throws Exception{
        int supplierResult=supplierService.updateSupplier(supplier);
        JSONObject json=new JSONObject();
        if(supplierResult>0){
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
    @RequestMapping(value = "/supplierDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSupplierById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int goodsResult=supplierService.deleteSupplierById(list);
            if(goodsResult>0){
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
    @RequestMapping("/existSupplier")
    public String existSupplier(HttpServletResponse response,
                            Supplier supplier) throws Exception{
        List<Supplier> supplierResult = supplierService.findSupplier(supplier);
        JSONObject json=new JSONObject();
        if(supplierResult.size() > 0 ){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: respositoryExport
     * @Description: 根据搜索条件，导出对应的数据
     * @param SUPPLIER_NAME 封装的条件
     * @return
     */
    @RequestMapping(value = "supplierExport", method = RequestMethod.POST)
    @ResponseBody
    public void supplierExport(String SUPPLIER_NAME, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "supplierExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SUPPLIER_NAME", SUPPLIER_NAME);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            supplierService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: goodsImport
     * @Description: 导入城市信息excel
     * @author: 许超
     * @return
     */
    @RequestMapping(value = "/supplierImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO supplierImport(MultipartFile file) {
        try {
            supplierService.supplierImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }

    /**
     *
     * @Title: searchGoodsCodeLike
     * @Description: 型号/图号 自动补全
     * @return
     */
    @RequestMapping(value = "/searchSUPPLIER_PERSONLike", method = RequestMethod.POST)
    @ResponseBody
    public List<Supplier> searchSUPPLIER_PERSONLike(String q) {
        if(q==null){
            return null;
        }else {
            List<Supplier> SUPPLIER_PERSON = supplierService.findSUPPLIER_PERSONLike(q);
            return SUPPLIER_PERSON;
        }
    }

    /**
     * 参数 dsName
     * 参数 datasetName
     * 参数 parameters
     * @author xuchao
     * @description 报表测试
     * @date 2019/12/29 14:22
     * @Version     1.0
     */
    public List<Map> loadReportData(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Supplier> list = supplierService.findAllSupplier();
        List<Map> result = list.stream().map(supplier -> {
            Map map = new HashMap();
            map.put("供应商名称", supplier.getSUPPLIER_NAME());
            map.put("供应商地址", supplier.getSUPPLIER_ADDRESS());
            map.put("供应商负责人", supplier.getSUPPLIER_PERSON());
            map.put("供应商联系方式", supplier.getSUPPLIER_TEL());
            return map;
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * 参数 dsName
     * 参数 datasetName
     * 参数 parameters
     * @author xuchao 
     * @description 报表测试
     * @date 2019/12/29 14:22 
     * @Version     1.0
     */
    public List<Supplier> buildReport(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Supplier> suppliers = supplierService.findSupplierName();
        return suppliers;
    }
}
