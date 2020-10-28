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
//    private static final int WMS_INCREMENT_LENGTH = 15;//��ˮ�ų���
//    private static final int WMS_DBINDEX = 9;//redisDB������
//    private static final String WMS_INODD = "WMS_INODD";//redisDB������
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
     * ��ҳ��ѯ��Ӧ��
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
//     * ��������¼
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/addWmsIncoming")
//    public String addWmsIncoming(HttpServletResponse response,WmsIncoming wmsIncoming) throws Exception{
//        wmsIncoming.setUserId(UserUtil.getSubjectUserID());
//        wmsIncoming.setInDate(new Date());
//        wmsIncoming.setInAllPri(wmsIncoming.getInPrice()*wmsIncoming.getInAmount());
//        //����¼
//        int wmsIncomingResult=wmsIncomingService.addWmsIncoming(wmsIncoming);
//        //�ֿ����
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
    // model ���ڸ�ʽ�Ĵ���
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

//    /**
//     * �޸Ĺ�Ӧ��
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
//     * @Description: ���ݹ�Ӧ��IDɾ����Ӧ��
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
//     * �жϹ�Ӧ���Ƿ��Ѿ�����
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
//     * @Description: ��������������������Ӧ������
//     * @param wmsIncomingName ��װ������
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
//        // ��Ӧ����
//        try {
//            // ���������,ʵ�������ļ�
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
     * @Description: ���빩Ӧ����Ϣexcel
     * @author: LIUHD
     * @return
     */
    @RequestMapping(value = "/wmsIncomingImport", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> wmsIncomingImport(MultipartFile file) throws WmsIncomingManageServiceException {
        //  ��ʼ�� Response
        Response responseContent = responseUtil.newResponseInstance();
        String result = "";

        // ��ȡ�ļ�����
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
        // ���� Response
        responseContent.setResponseResult(result);
        responseContent.setResponseTotal(total);
        responseContent.setCustomerInfo("available", available);
        return responseContent.generateResponse();

    }
//
//
//    /**(1)��@ResponseBodyע��
//     (2)���Խ��ո��ֲ�����url��data(����ƥ���@RequestBody)
//     (3)����ֵ���;��ǻ�������*/
//    @ResponseBody
//    @RequestMapping(value = "getNewInOdd", method = RequestMethod.POST)
//    public String getNewInOdd() {
//       String inOdd = redisService.getIncrementNum(WMS_INCREMENT_LENGTH,WMS_INODD,WMS_DBINDEX,2);
//        //�������
//        return inOdd;
//    }
}
