package com.java.activiti.controller.wms;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.wms.Wmssupplier;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.wms.WmssupplierService;
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
@RequestMapping("/wmssupplier")
public class WmssupplierController {

    @Resource
    private WmssupplierService wmssupplierService;
    /**
     * ��ҳ��ѯ��Ӧ��
     * @return
     * @throws Exception
     */
    @RequestMapping("/wmssupplierPage")
    public String wmssupplierPage(HttpServletResponse response, String rows,
                            String page, String sort, String order,
                            String supplierName) throws Exception {
        PageInfo<Wmssupplier> wmssupplierPage = new PageInfo<Wmssupplier>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("supplierName", supplierName);
        Integer pageSize = Integer.parseInt(rows);
        wmssupplierPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        wmssupplierPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", wmssupplierPage.getPageIndex());
        map.put("pageSize", wmssupplierPage.getPageSize());
        int wmssupplierCount = wmssupplierService.wmssupplierCount(map);
        List<Wmssupplier> wmssupplierList = wmssupplierService.wmssupplierPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(wmssupplierList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", wmssupplierCount);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * ������Ӧ��
     * @return
     * @throws Exception
     */
    @RequestMapping("/addWmssupplier")
    public String addWmssupplier(HttpServletResponse response,Wmssupplier wmssupplier) throws Exception{
        int wmssupplierResult=wmssupplierService.addWmssupplier(wmssupplier);
        JSONObject json=new JSONObject();
        if(wmssupplierResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * �޸Ĺ�Ӧ��
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateWmssupplier")
    public String updateWmssupplier(HttpServletResponse response,Wmssupplier wmssupplier) throws Exception{
        int wmssupplierResult=wmssupplierService.updateWmssupplier(wmssupplier);
        JSONObject json=new JSONObject();
        if(wmssupplierResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Description: ���ݹ�Ӧ��IDɾ����Ӧ��
     * @return
     */
    @RequestMapping(value = "/wmssupplierDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteWmssupplierById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int wmssupplierResult=wmssupplierService.deleteWmssupplierById(list);
            if(wmssupplierResult>0){
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
     * �жϹ�Ӧ���Ƿ��Ѿ�����
     * @return
     * @throws Exception
     */
    @RequestMapping("/existWmssupplier")
    public String existWmssupplier(HttpServletResponse response,
                            Wmssupplier wmssupplier) throws Exception{
        List<Wmssupplier> wmssupplierResult = wmssupplierService.findWmssupplier(wmssupplier);
        JSONObject json=new JSONObject();
        if(wmssupplierResult.size() > 0 ){
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
     * @Description: ��������������������Ӧ������
     * @param wmssupplierName ��װ������
     * @return
     */
    @RequestMapping(value = "wmssupplierExport", method = RequestMethod.POST)
    @ResponseBody
    public void wmssupplierExport(String wmssupplierName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "wmssupplierExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wmssupplierName", wmssupplierName);
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            wmssupplierService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: wmssupplierImport
     * @Description: ���빩Ӧ����Ϣexcel
     * @author: LIUHD
     * @return
     */
    @RequestMapping(value = "/wmssupplierImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO wmssupplierImport(MultipartFile file) {
        try {
            wmssupplierService.wmssupplierImport(file.getInputStream());
            return new GlobalResultVO(200, "�ļ��ϴ��ɹ�", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "�ļ��ϴ�ʧ��", null);
        }
    }


    /**
     *
     * @Title: selectSupplierNameLike
     * @Description: ��Ӧ�̱��� �Զ���ȫ
     * @return
     */
    @RequestMapping(value = "/selectSupplierNameLike", method = RequestMethod.POST)
    @ResponseBody
    public List<Wmssupplier> selectSupplierNameLike(String q) {
        if(q==null){
            return null;
        }else {
            List<Wmssupplier> supplierName = wmssupplierService.selectSupplierNameLike(q);
            return supplierName;
        }
    }
    /**
     * @return
     * @Title: searchAllWmssupplier
     * @Description: �������Զ���ȫ
     */
    @RequestMapping(value = "/searchAllWmssupplier", method = RequestMethod.POST)
    @ResponseBody
    public List<Wmssupplier> searchAllWmssupplier() {
        List<Wmssupplier> wmssuppliers = wmssupplierService.findALL();
        return wmssuppliers;
    }
}
