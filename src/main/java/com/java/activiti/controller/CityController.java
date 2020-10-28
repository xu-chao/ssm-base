package com.java.activiti.controller;

import com.java.activiti.model.City;
import com.java.activiti.model.PageInfo;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.CityService;
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
@RequestMapping("/city")
public class CityController {

    @Resource
    private CityService cityService;
    /**
     * ��ҳ��ѯ����
     * @return
     * @throws Exception
     */
    @RequestMapping("/cityPage")
    public String cityPage(HttpServletResponse response, String rows,
                                String page, String sort, String order, String cityName) throws Exception {
        PageInfo<City> cityPage = new PageInfo<City>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("cityName", cityName);
        Integer pageSize = Integer.parseInt(rows);
        cityPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        cityPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", cityPage.getPageIndex());
        map.put("pageSize", cityPage.getPageSize());
        int cityCount = cityService.cityCount(map);
        List<City> cityList = cityService.cityPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(cityList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", cityCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: searchCity
     * @Description: �������Զ���ȫ
     * @return
     */
    @RequestMapping(value = "/searchCity", method = RequestMethod.POST)
    @ResponseBody
    public List<City> searchCity() {
        List<City> cityName = cityService.findCityName();
        return cityName;
    }
    /**
     * ��������
     * @return
     * @throws Exception
     */
    @RequestMapping("/addCity")
    public String addCity(HttpServletResponse response,City city) throws Exception{
        int cityResult=cityService.addCity(city);
        JSONObject json=new JSONObject();
        if(cityResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * �޸ĳ���
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateCity")
    public String updateCity(HttpServletResponse response,City city) throws Exception{
        int cityResult=cityService.updateCity(city);
        JSONObject json=new JSONObject();
        if(cityResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: searchCity
     * @Description: ���ݳ���IDɾ������
     * @return
     */
    @RequestMapping(value = "/cityDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCityById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int cityResult=cityService.deleteCityById(list);
            if(cityResult>0){
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
    @RequestMapping("/existCity")
    public String existCity(HttpServletResponse response,
                            City city) throws Exception{
        List<City> cityResult = cityService.findCity(city);
        JSONObject json=new JSONObject();
        if(cityResult.size() > 0 ){
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
     * @param cityName ��װ������
     * @return
     */
    @RequestMapping(value = "cityExport", method = RequestMethod.POST)
    @ResponseBody
    public void cityExport(String cityName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "cityExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cityName", cityName);
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            cityService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * ����������Ϣ
//     *
//     * @param cityName   ���ҳ�������
//     * @param sort       ��������
//     * @param order      ����ʽ
//     * @param response   HttpServletResponse
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "cityExport", method = RequestMethod.GET)
//    public void exportGoods(@RequestParam("cityName") String cityName,
//                            @RequestParam("sort") String sort, @RequestParam("order") String order,
//                            HttpServletResponse response) throws CityManageServiceException, IOException {
//
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date=new Date();
//        String dateStr = sdf.format(date);
//        String fileName = "cityExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xlsx";
//
//        List<City> cityList = null;
//        Map<String, Object> queryResult = cityService.selectByCityName(cityName, sort, order);
//
//        if (queryResult != null) {
//            cityList = (List<City>) queryResult.get("data");
//        }
//
//        // ��ȡ���ɵ��ļ�
//        File file = cityService.export(cityList);
//
//        // д���ļ�
//        if (file != null) {
//            // ������Ӧͷ
//            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//
//            FileInputStream inputStream = new FileInputStream(file);
//            OutputStream outputStream = response.getOutputStream();
//            byte[] buffer = new byte[8192];
//
//            int len;
//            while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
//                outputStream.write(buffer, 0, len);
//                outputStream.flush();
//            }
//
//            inputStream.close();
//            outputStream.close();
//
//        }
//    }

    /**
     *
     * @Title: userdoImport
     * @Description: ���������Ϣexcel
     * @author: ��
     * @return
     */
    @RequestMapping(value = "/cityImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO cityImport(MultipartFile file) {
        try {
            cityService.cityImport(file.getInputStream());
            return new GlobalResultVO(200, "�ļ��ϴ��ɹ�", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "�ļ��ϴ�ʧ��", null);
        }
    }
}
