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
     * ��ҳ��ѯ����
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
     * @Description: �������Զ���ȫ
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
     * @Description: �������Զ���ȫ
     * @return
     */
    @RequestMapping(value = "/searchAllSpuplier", method = RequestMethod.POST)
    @ResponseBody
    public List<Supplier> searchAllSpuplier() {
        List<Supplier> SUPPLIER_NAME = supplierService.findSupplierName();
        return SUPPLIER_NAME;
    }

    /**
     * ��������
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
     * �޸�����
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
     * @Description: ���ݳ���IDɾ������
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
     * �жϳ����Ƿ��Ѿ�����
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
     * @Description: ��������������������Ӧ������
     * @param SUPPLIER_NAME ��װ������
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
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
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
     * @Description: ���������Ϣexcel
     * @author: ��
     * @return
     */
    @RequestMapping(value = "/supplierImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO supplierImport(MultipartFile file) {
        try {
            supplierService.supplierImport(file.getInputStream());
            return new GlobalResultVO(200, "�ļ��ϴ��ɹ�", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "�ļ��ϴ�ʧ��", null);
        }
    }

    /**
     *
     * @Title: searchGoodsCodeLike
     * @Description: �ͺ�/ͼ�� �Զ���ȫ
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
     * ���� dsName
     * ���� datasetName
     * ���� parameters
     * @author xuchao
     * @description �������
     * @date 2019/12/29 14:22
     * @Version     1.0
     */
    public List<Map> loadReportData(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Supplier> list = supplierService.findAllSupplier();
        List<Map> result = list.stream().map(supplier -> {
            Map map = new HashMap();
            map.put("��Ӧ������", supplier.getSUPPLIER_NAME());
            map.put("��Ӧ�̵�ַ", supplier.getSUPPLIER_ADDRESS());
            map.put("��Ӧ�̸�����", supplier.getSUPPLIER_PERSON());
            map.put("��Ӧ����ϵ��ʽ", supplier.getSUPPLIER_TEL());
            return map;
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * ���� dsName
     * ���� datasetName
     * ���� parameters
     * @author xuchao 
     * @description �������
     * @date 2019/12/29 14:22 
     * @Version     1.0
     */
    public List<Supplier> buildReport(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Supplier> suppliers = supplierService.findSupplierName();
        return suppliers;
    }
}
