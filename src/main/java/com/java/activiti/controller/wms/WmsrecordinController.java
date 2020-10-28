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
    private static final int WMS_INCREMENT_LENGTH = 15;//��ˮ�ų���
    private static final int WMS_DBINDEX = 9;//redisDB������
    private static final String WMS_INODD = "WMS_INODD";//redisDB������
    @Resource
    private WmsrecordinService wmsrecordinService;

    @Resource
    private WmsrecordstorageService wmsrecordstorageService;

    @Resource
    private RedisService redisService;
    /**
     * ��ҳ��ѯ��Ӧ��
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
     * ��������¼
     * @return
     * @throws Exception
     */
    @RequestMapping("/addWmsrecordin")
    public String addWmsrecordin(HttpServletResponse response,Wmsrecordin wmsrecordin) throws Exception{
        wmsrecordin.setUserId(UserUtil.getSubjectUserID());
        wmsrecordin.setInDate(new Date());
        wmsrecordin.setInAllPri(wmsrecordin.getInPrice()*wmsrecordin.getInAmount());
        //����¼
        int wmsrecordinResult=wmsrecordinService.addWmsrecordin(wmsrecordin);
        //�ֿ����
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
     * @Description: ���ݹ�Ӧ��IDɾ����Ӧ��
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
     * �жϹ�Ӧ���Ƿ��Ѿ�����
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
     * @Description: ��������������������Ӧ������
     * @param wmsrecordinName ��װ������
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
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
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
     * @Description: ���빩Ӧ����Ϣexcel
     * @author: LIUHD
     * @return
     */
    @RequestMapping(value = "/wmsrecordinImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO wmsrecordinImport(MultipartFile file) {
        try {
            wmsrecordinService.wmsrecordinImport(file.getInputStream());
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
    @RequestMapping(value = "getNewInOdd", method = RequestMethod.POST)
    public String getNewInOdd() {
       String inOdd = redisService.getIncrementNum(WMS_INCREMENT_LENGTH,WMS_INODD,WMS_DBINDEX,2);
        //�������
        return inOdd;
    }
}
