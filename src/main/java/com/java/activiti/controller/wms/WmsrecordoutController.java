package com.java.activiti.controller.wms;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.wms.Wmsrecordout;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.RedisService;
import com.java.activiti.service.wms.WmsrecordoutService;
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

@Controller
@RequestMapping("/wmsrecordout")
public class WmsrecordoutController {

    private static final int WMS_outCREMENT_LENGTH = 15;//��ˮ�ų���
    private static final int WMS_DBoutDEX = 2;//redisDB������
    private static final String WMS_outODD = "WMS_outODD";//redisDB������
    @Resource
    private WmsrecordoutService wmsrecordoutService;

    @Resource
    private RedisService redisService;

    @Resource
    private WmsrecordstorageService wmsrecordstorageService;
    /**
     * ��ҳ��ѯ��Ӧ��
     * @return
     * @throws Exception
     */

    @RequestMapping("/wmsrecordoutThroughPage")
    public String wmsrecordoutThroughPage(HttpServletResponse response, String rows,
                                  String page, String searchType, String searchValue, String startDate, String endDate) throws Exception {
        PageInfo<Wmsrecordout> wmsrecordoutPage = new PageInfo<Wmsrecordout>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        wmsrecordoutPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        wmsrecordoutPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", wmsrecordoutPage.getPageIndex());
        map.put("pageSize", wmsrecordoutPage.getPageSize());
        int wmsrecordoutCount = wmsrecordoutService.wmsrecordoutCount(map);
        List<Wmsrecordout> wmsrecordoutList = wmsrecordoutService.wmsrecordoutPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(wmsrecordoutList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", wmsrecordoutCount);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/wmsrecordoutPage")
    public String wmsrecordoutPage(HttpServletResponse response, String rows,
                                    String page, String sort, String order,String outOdd,
                                    Integer goodId, Integer projectName) throws Exception {
        PageInfo<Wmsrecordout> wmsrecordoutPage = new PageInfo<Wmsrecordout>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("outOdd", outOdd);
        map.put("goodId", goodId);
        map.put("projectName", projectName);
        Integer pageSize = Integer.parseInt(rows);
        wmsrecordoutPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        wmsrecordoutPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageoutdex", wmsrecordoutPage.getPageIndex());
        map.put("pageSize", wmsrecordoutPage.getPageSize());
        int wmsrecordoutCount = wmsrecordoutService.wmsrecordoutCount(map);
        List<Wmsrecordout> wmsrecordoutList = wmsrecordoutService.wmsrecordoutPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(wmsrecordoutList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", wmsrecordoutCount);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * ������Ӧ��
     * @return
     * @throws Exception
     */
    @RequestMapping("/addWmsrecordout")
    public String addWmsrecordout(HttpServletResponse response,Wmsrecordout wmsrecordout) throws Exception{
        wmsrecordout.setUserId(UserUtil.getSubjectUserID());
        wmsrecordout.setOutDate(new Date());
        wmsrecordout.setApproveId(1);
        if(!wmsrecordout.getOutTypeId().equals(85)){
            wmsrecordout.setInRespositoryId(2);
            wmsrecordstorageService.storageDecrease(wmsrecordout.getGoodId(),wmsrecordout.getInRespositoryId(),Integer.parseInt(wmsrecordout.getOutAmount()));
        }
        int wmsrecordoutResult=wmsrecordoutService.addWmsrecordout(wmsrecordout);
        JSONObject json=new JSONObject();
        if(wmsrecordoutResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }
    // model ���ڸ�ʽ�Ĵ���
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    /**
     * �޸Ĺ�Ӧ��
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateWmsrecordout")
    public String updateWmsrecordout(HttpServletResponse response,Wmsrecordout wmsrecordout) throws Exception{
        int wmsrecordoutResult=wmsrecordoutService.updateWmsrecordout(wmsrecordout);
        JSONObject json=new JSONObject();
        if(wmsrecordoutResult>0){
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
    @RequestMapping(value = "/wmsrecordoutDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteWmsrecordoutById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int wmsrecordoutResult=wmsrecordoutService.deleteWmsrecordoutById(list);
            if(wmsrecordoutResult>0){
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
    @RequestMapping("/existWmsrecordout")
    public String existWmsrecordout(HttpServletResponse response,
                                     Wmsrecordout wmsrecordout) throws Exception{
        List<Wmsrecordout> wmsrecordoutResult = wmsrecordoutService.foutdWmsrecordout(wmsrecordout);
        JSONObject json=new JSONObject();
        if(wmsrecordoutResult.size() > 0 ){
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
     * @param wmsrecordoutName ��װ������
     * @return
     */
    @RequestMapping(value = "wmsrecordoutExport", method = RequestMethod.POST)
    @ResponseBody
    public void wmsrecordoutExport(String wmsrecordoutName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "wmsrecordoutExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("wmsrecordoutName", wmsrecordoutName);
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            wmsrecordoutService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: wmsrecordoutImport
     * @Description: ���빩Ӧ����Ϣexcel
     * @author: LIUHD
     * @return
     */
    @RequestMapping(value = "/wmsrecordoutImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO wmsrecordoutImport(MultipartFile file) {
        try {
            wmsrecordoutService.wmsrecordoutImport(file.getInputStream());
            return new GlobalResultVO(200, "�ļ��ϴ��ɹ�", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "�ļ��ϴ�ʧ��", null);
        }
    }


    /**(1)��@ResponseBodyע��
     (2)���Խ��ո��ֲ�����url��data(����ƥ���@RequestBody)
     (3)����ֵ���;��ǻ�������*/
    @ResponseBody
    @RequestMapping(value = "getNewoutOdd", method = RequestMethod.POST)
    public String getNewoutOdd() {
        String outOdd = redisService.getIncrementNum(WMS_outCREMENT_LENGTH,WMS_outODD,WMS_DBoutDEX,2);
        //�������
        return outOdd;
    }

    @RequestMapping("/updateStroageAndApprovelId")
    public String updateStroageAndApprovelId(HttpServletResponse response, Integer outId, String goodId, Integer inRespositoryId, Integer outAmount, Integer approveId) throws Exception{
        JSONObject json=new JSONObject();
        Wmsrecordout wmsrecordout = new Wmsrecordout();
        wmsrecordout.setOutId(outId);
        wmsrecordout.setApproveId(approveId + 1);
        Boolean wmsrecordstorageResult = wmsrecordstorageService.storageDecrease(goodId,inRespositoryId,outAmount);
        if(wmsrecordstorageResult){
            int wmsrecordoutResult=wmsrecordoutService.updateWmsrecordout(wmsrecordout);
            if(wmsrecordoutResult>0){
                json.put("success", true);
            }else{
                json.put("success", false);
            }
            ResponseUtil.write(response, json);
        }else {
            json.put("success", false);
            ResponseUtil.write(response, json);
        }
        return null;
    }

    @RequestMapping("/updateApproveId")
    public String updateApprovelId(HttpServletResponse response, Integer outId, Integer approveId) throws Exception{
        Wmsrecordout wmsrecordout = new Wmsrecordout();
        wmsrecordout.setOutId(outId);
        wmsrecordout.setApproveId(approveId + 1);
        int wmsrecordoutResult=wmsrecordoutService.updateWmsrecordout(wmsrecordout);
        JSONObject json=new JSONObject();
        if(wmsrecordoutResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }
}
