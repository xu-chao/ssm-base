package com.java.activiti.controller.erp;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.Respository;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.erp.RespositoryService;
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

@Controller
@RequestMapping("/respository")
public class RespositoryController {

    @Resource
    private RespositoryService respositoryService;
    /**
     * ��ҳ��ѯ����
     * @return
     * @throws Exception
     */
    @RequestMapping("/respositoryPage")
    public String respositoryPage(HttpServletResponse response, String rows,
                            String page, String sort, String order, String REPO_ADDRESS) throws Exception {
        PageInfo<Respository> respositoryPage = new PageInfo<Respository>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("REPO_ADDRESS", REPO_ADDRESS);
        Integer pageSize = Integer.parseInt(rows);
        respositoryPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        respositoryPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", respositoryPage.getPageIndex());
        map.put("pageSize", respositoryPage.getPageSize());
        int respositoryCount = respositoryService.respositoryCount(map);
        List<Respository> respositoryList = respositoryService.respositoryPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(respositoryList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", respositoryCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: searchGoods
     * @Description: �������Զ���ȫ
     * @return
     */
    @RequestMapping(value = "/searchRespository", method = RequestMethod.POST)
    @ResponseBody
    public List<Respository> searchRespository(String q) {
        if(q==null){
            return null;
        }else {
            List<Respository> REPO_ADDRESS = respositoryService.findRespositoryName(q);
            return REPO_ADDRESS;
        }
    }

    /**
     *
     * @Title: searchCity
     * @Description: �������Զ���ȫ
     * @return
     */
    @RequestMapping(value = "/searchAllRespository", method = RequestMethod.POST)
    @ResponseBody
    public List<Respository> searchAllRespository() {
        List<Respository> REPO_ADDRESS = respositoryService.findRespositoryName();
        return REPO_ADDRESS;
    }

    /**
     * ��������
     * @return
     * @throws Exception
     */
    @RequestMapping("/addRespository")
    public String addRespository(HttpServletResponse response,Respository respository) throws Exception{
        int respositoryResult=respositoryService.addRespository(respository);
        JSONObject json=new JSONObject();
        if(respositoryResult>0){
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
    @RequestMapping("/updateRespository")
    public String updateRespository(HttpServletResponse response,Respository respository) throws Exception{
        int respositoryResult=respositoryService.updateRespository(respository);
        JSONObject json=new JSONObject();
        if(respositoryResult>0){
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
    @RequestMapping(value = "/respositoryDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteRespositoryById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int goodsResult=respositoryService.deleteRespositoryById(list);
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
    @RequestMapping("/existGoods")
    public String existGoods(HttpServletResponse response,
                            Respository respository) throws Exception{
        List<Respository> respositoryResult = respositoryService.findRespository(respository);
        JSONObject json=new JSONObject();
        if(respositoryResult.size() > 0 ){
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
     * @param REPO_ADDRESS ��װ������
     * @return
     */
    @RequestMapping(value = "respositoryExport", method = RequestMethod.POST)
    @ResponseBody
    public void respositoryExport(String REPO_ADDRESS, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "respositoryExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("REPO_ADDRESS", REPO_ADDRESS);
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            respositoryService.export(response.getOutputStream(), map);
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
    @RequestMapping(value = "/respositoryImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO respositoryImport(MultipartFile file) {
        try {
            respositoryService.respositoryImport(file.getInputStream());
            return new GlobalResultVO(200, "�ļ��ϴ��ɹ�", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "�ļ��ϴ�ʧ��", null);
        }
    }
}
