package com.java.activiti.controller;

import com.java.activiti.model.City;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.Park;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.CityService;
import com.java.activiti.service.ParkService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.StringUtil;
import com.java.activiti.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/park")
@Api(tags="��԰����Controller���漰��԰���t_park")
public class ParkController {

    @Resource
    private ParkService parkService;

    /**
     * ��ҳ��ѯ��԰
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/parkPage")
    public String parkPage(HttpServletResponse response, String rows,
                           String page, String sort, String order, String parkName) throws Exception {
        PageInfo<Park> parkPage = new PageInfo<Park>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("parkName", parkName);
        Integer pageSize = Integer.parseInt(rows);
        parkPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        parkPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", parkPage.getPageIndex());
        map.put("pageSize", parkPage.getPageSize());
        int parkCount = parkService.parkCount(map);
        List<Park> parkList = parkService.parkPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(parkList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", parkCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * @param q
     * @return
     * @Title: searchPark
     * @Description: �������Զ���ȫ
     */
    @RequestMapping(value = "/searchPark", method = RequestMethod.POST)
    @ResponseBody
    public List<Park> searchPark(String q) {
        List<Park> parkName = parkService.findParkName(q);
        return parkName;
    }
    /**
     * @param parkID
     * @return
     * @Title: findParkByID
     * @Description: ���ҹ�԰ ���� ID
     */
    @RequestMapping(value = "/findParkByID", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "���ҹ�԰",notes = "��԰id������")
    @ApiResponses({
            @ApiResponse(code=400,message="�������û���"),
            @ApiResponse(code=404,message="����·��û�л�ҳ����ת·������")
    })
    public Park findParkByID(int parkID) {
        Park parkName = parkService.findParkByID(parkID);
        return parkName;
    }
    /**
     * @return
     * @Title: searchPark
     * @Description: �������Զ���ȫ
     */
    @RequestMapping(value = "/searchAllPark", method = RequestMethod.POST)
    @ResponseBody
    public List<Park> searchAllPark() {
        List<Park> parkName = parkService.findALL();
        return parkName;
    }
    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/existPark")
    public String existPark(HttpServletResponse response,
                            Park park) throws Exception {
        List<Park> parkResult = parkService.findPark(park);
        JSONObject json = new JSONObject();
        if (parkResult.size() > 0) {

            json.put("success", true);
        } else {
            json.put("success", false);

        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * ��������
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/addPark")
    public String addPark(HttpServletResponse response, Park park) throws Exception {
        JSONObject json = new JSONObject();
        int cityResult;
        try {
            cityResult = parkService.addPark(park);
            if (cityResult > 0) {
                json.put("success", true);
            } else {
                json.put("success", false);
            }
//            }
        } catch (Exception e) {
            json.put("success", false);
        }


        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * �޸Ĺ�԰
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePark")
    public String updatePark(HttpServletResponse response, Park park) throws Exception {
        JSONObject json = new JSONObject();
        try {
            int cityResult = parkService.updatePark(park);
            if (cityResult > 0) {
                json.put("success", true);
            } else {
                json.put("success", false);
            }
        } catch (Exception e) {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * @return
     * @Title: searchPark
     * @Description: ���ݹ�԰IDɾ����԰
     */
    @RequestMapping(value = "/parkDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteParkById(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String id = request.getParameter("ids");
        JSONObject json = new JSONObject();
        List<String> list = new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int parkResult = parkService.deleteParkById(list);
            if (parkResult > 0) {
                json.put("success", true);
            } else {
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
     *
     * @Title: parkExport
     * @Description: ��������������������Ӧ������
     * @param parkName ��װ������
     * @return
     */
    @RequestMapping(value = "parkExport", method = RequestMethod.POST)
    @ResponseBody
    public void parkExport(String parkName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "parkExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parkName", parkName);
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            parkService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @Title: parkImport
     * @Description: ���������Ϣexcel
     * @author: ��
     * @return
     */
    @RequestMapping(value = "/parkImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO parkImport(MultipartFile file) {
        try {
            parkService.parkImport(file.getInputStream());
            return new GlobalResultVO(200, "�ļ��ϴ��ɹ�", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "�ļ��ϴ�ʧ��", null);
        }
    }
}
