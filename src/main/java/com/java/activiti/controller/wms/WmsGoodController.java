package com.java.activiti.controller.wms;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.wms.WmsGood;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.wms.WmsGoodService;
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
@RequestMapping("/wmsGood")
public class WmsGoodController {

    @Resource
    private WmsGoodService wmsGoodService;
    /**
     * ��ҳ��ѯ����
     * @return
     * @throws Exception
     */
    @RequestMapping("/wmsGoodPage")
    public String wmsGoodPage(HttpServletResponse response, String rows,
                            String page, String sort, String order,
                            String goodEncoding, String goodName,
                            String goodModel) throws Exception {
        PageInfo<WmsGood> wmsGoodPage = new PageInfo<WmsGood>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("goodEncoding", goodEncoding);
        map.put("goodName", goodName);
        map.put("goodModel", goodModel);
        Integer pageSize = Integer.parseInt(rows);
        wmsGoodPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        wmsGoodPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", wmsGoodPage.getPageIndex());
        map.put("pageSize", wmsGoodPage.getPageSize());
        int wmsGoodCount = wmsGoodService.wmsGoodCount(map);
        List<WmsGood> wmsGoodList = wmsGoodService.wmsGoodPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(wmsGoodList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", wmsGoodCount);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * ��������
     * @return
     * @throws Exception
     */
    @RequestMapping("/addWmsGood")
    public String addWmsGood(HttpServletResponse response,WmsGood wmsGood) throws Exception{
        int wmsGoodResult=wmsGoodService.addWmsGood(wmsGood);
        JSONObject json=new JSONObject();
        if(wmsGoodResult>0){
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
    @RequestMapping("/updateWmsGood")
    public String updateWmsGood(HttpServletResponse response,WmsGood wmsGood) throws Exception{
        int wmsGoodResult=wmsGoodService.updateWmsGood(wmsGood);
        JSONObject json=new JSONObject();
        if(wmsGoodResult>0){
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
    @RequestMapping(value = "/wmsGoodDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteWmsGoodById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int wmsGoodResult=wmsGoodService.deleteWmsGoodById(list);
            if(wmsGoodResult>0){
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
    @RequestMapping("/existWmsGood")
    public String existWmsGood(HttpServletResponse response,
                            WmsGood wmsGood) throws Exception{
//        List<WmsGood> wmsGoodResult = wmsGoodService.findWmsGood(wmsGood);
        JSONObject json=new JSONObject();
//        if(wmsGoodResult.size() > 0 ){
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
     * @Description: ��������������������Ӧ������
     * @param wmsGoodName ��װ������
     * @return
     */
    @RequestMapping(value = "wmsGoodExport", method = RequestMethod.POST)
    @ResponseBody
    public void wmsGoodExport(String wmsGoodName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "wmsGoodExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wmsGoodName", wmsGoodName);
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            wmsGoodService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: wmsGoodImport
     * @Description: ���������Ϣexcel
     * @author: ��
     * @return
     */
    @RequestMapping(value = "/wmsGoodImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO wmsGoodImport(MultipartFile file) {
        try {
            wmsGoodService.wmsGoodImport(file.getInputStream());
            return new GlobalResultVO(200, "�ļ��ϴ��ɹ�", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "�ļ��ϴ�ʧ��", null);
        }
    }

    /**
     *
     * @Title: searchGoodsNameLike
     * @Description: ���ϱ��� �Զ���ȫ
     * @return
     */
    @RequestMapping(value = "/findGoodEncodingLike", method = RequestMethod.POST)
    @ResponseBody
    public List<WmsGood> findGoodEncodingLike(String q) {
        if(q==null){
            return null;
        }else {
            List<WmsGood> goodsName = wmsGoodService.findGoodEncodingLike(q);
            return goodsName;
        }
    }

    /**
     *
     * @Title: searchGoodsCodeLike
     * @Description: �������� �Զ���ȫ
     * @return
     */
    @RequestMapping(value = "/findGoodNameLike", method = RequestMethod.POST)
    @ResponseBody
    public List<WmsGood> findGoodNameLike(String q) {
        if(q==null){
            return null;
        }else {
            List<WmsGood> goodsCode = wmsGoodService.findGoodNameLike(q);
            return goodsCode;
        }
    }

    /**
     *
     * @Title: searchGoodsTypeLike
     * @Description: �����ͺ� �Զ���ȫ
     * @return
     */
    @RequestMapping(value = "/findGoodModelLike", method = RequestMethod.POST)
    @ResponseBody
    public List<WmsGood> findGoodModelLike(String q) {
        if(q==null){
            return null;
        }else {
            List<WmsGood> goodsType = wmsGoodService.findGoodModelLike(q);
            return goodsType;
        }
    }
    /**
     * @return
     * @Title: searchAllWmsGood
     * @Description: �������Զ���ȫ
     */
    @RequestMapping(value = "/searchAllWmsGood", method = RequestMethod.POST)
    @ResponseBody
    public List<WmsGood> searchAllWmsGood() {
        List<WmsGood> wmsGoods = wmsGoodService.findALL();
        return wmsGoods;
    }

}
