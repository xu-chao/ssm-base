package com.java.activiti.controller.swylsb;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.swylsb.Swylsb;
import com.java.activiti.model.swylsb.SwylsbJd;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.swylsb.SwylsbJdService;
import com.java.activiti.service.swylsb.SwylsbService;
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
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/swylsbJd")
public class SwylsbJdController {

    @Resource
    private SwylsbJdService swylsbJdService;
    @Resource
    private SwylsbService swylsbService;
    /**
     * 分页查询物料
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/swylsbJdPage")
    public String swylsbJdPage(HttpServletResponse response, String rows,
                               String page, String searchType, String searchValue, String startDate, String endDate) throws Exception {
        PageInfo<SwylsbJd> swylsbJdPage = new PageInfo<SwylsbJd>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        swylsbJdPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        swylsbJdPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", swylsbJdPage.getPageIndex());
        map.put("pageSize", swylsbJdPage.getPageSize());
        int swylsbJdCount = swylsbJdService.swylsbJdCount(map);
        List<SwylsbJd> swylsbJdList = swylsbJdService.swylsbJdPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(swylsbJdList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", swylsbJdCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * @return
     * @Title: searchGoods
     * @Description: 物料名自动补全
     */
    @RequestMapping(value = "/searchSwylsbJb", method = RequestMethod.POST)
    @ResponseBody
    public List<SwylsbJd> searchSwylsbJb(String q) {
        if (q == null) {
            return null;
        } else {
            List<SwylsbJd> swylsbJb = swylsbJdService.findSwylsbJdName(q);
            return swylsbJb;
        }
    }

    /**
     * @return
     * @Title: searchCity
     * @Description: 城市名自动补全
     */
    @RequestMapping(value = "/searchAllSwylsbJd", method = RequestMethod.POST)
    @ResponseBody
    public List<SwylsbJd> searchAllSwylsbJd() {
        List<SwylsbJd> swylsbJd = swylsbJdService.findSwylsbJdName();
        return swylsbJd;
    }

    /**
     * 新增物料
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/addSwylsbJd")
    public String addSwylsbJd(HttpServletResponse response, SwylsbJd swylsbJd) throws Exception {
        int SwylsbJdResult = swylsbJdService.addSwylsbJd(swylsbJd);
        JSONObject json = new JSONObject();
        if (SwylsbJdResult > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 修改物料
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateSwylsbJd")
    public String updateSwylsbJd(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String ds = request.getParameter("postDataJd");
        SwylsbJd swylsbJd = new SwylsbJd();//要赋值的对象
        JSONArray json = JSONArray.fromObject(ds); //使用net.sf.json.JSONObject对象来解析json
        JSONObject jsonOne;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = null;
//        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();?
        for (int i = 0; i < json.size(); i++) {
            jsonOne = json.getJSONObject(i);
//只保留不为空的 键值对
            if ((String) jsonOne.get("Value") != "" && !"".equals((String) jsonOne.get("Value"))) {

                Field[] fields = swylsbJd.getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {//SwylsbJd字段
                    String name = fields[j].getName();
                    if (((String)jsonOne.get("Key")).equals(fields[j].getName()) ) {
                        //使用反射给对象赋值
                        Field f = swylsbJd.getClass().getDeclaredField(fields[j].getName());
                        f.setAccessible(true);
                        String type = f.getType().toString();
                        if (type.contains("Date")) {
                            Date date = sdf.parse((String) jsonOne.get("Value"));
                            f.set(swylsbJd, date);
                            break ;
                        } else {
                            Integer value = Integer.parseInt((String) jsonOne.get("Value"));
                            f.set(swylsbJd, value);
                            break ;
                        }

                    }
                }
            }
        }

        int swylsbJdResult = swylsbJdService.updateSwylsbJd(swylsbJd);
        Swylsb swylsb = new Swylsb();
        swylsb.setID(swylsbJd.getID());
        swylsb.setJdId(swylsbJd.getID());
        swylsb.setXqId(swylsbJd.getID());
        int swylsbResult=swylsbService.updateSwylsb(swylsb);
        JSONObject json1 = new JSONObject();
        if (swylsbJdResult > 0) {
            json1.put("success", true);
        } else {
            json1.put("success", false);
        }
        ResponseUtil.write(response, json1);
        return null;
    }

    /**
     * @return
     * @Description: 根据城市ID删除城市
     */
    @RequestMapping(value = "/swylsbJdDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSwylsbJdById(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String id = request.getParameter("ids");
        JSONObject json = new JSONObject();
        List<String> list = new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int swylsbJdResult = swylsbJdService.deleteSwylsbJdById(list);
            if (swylsbJdResult > 0) {
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
     * 判断城市是否已经存在
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/existSwylsbJd")
    public String existSwylsbJd(HttpServletResponse response,
                                SwylsbJd swylsbJd) throws Exception {
        List<SwylsbJd> swylsbJdResult = swylsbJdService.findSwylsbJd(swylsbJd);
        JSONObject json = new JSONObject();
        if (swylsbJdResult.size() > 0) {
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: respositoryExport
     * @Description: 根据搜索条件，导出对应的数据
     * @param sbName 封装的条件
     * @return
     */
//    @RequestMapping(value = "swylsbJdExport", method = RequestMethod.POST)
//    @ResponseBody
//    public void swylsbJdExport(String sbName, HttpServletResponse response) {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date=new Date();
//        String dateStr = sdf.format(date);
//        String filename = "swylsbExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("sbName", sbName);
//        // 响应对象
//        try {
//            // 设置输出流,实现下载文件
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));
//
//            swylsbService.export(response.getOutputStream(), map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     *
     * @Title: goodsImport
     * @Description: 导入城市信息excel
     * @author: 许超
     * @return
     */
//    @RequestMapping(value = "/swylsbImport", method = RequestMethod.POST)
//    @ResponseBody
//    public GlobalResultVO supplierImport(MultipartFile file) {
//        try {
//            swylsbService.swylsbImport(file.getInputStream());
//            return new GlobalResultVO(200, "文件上传成功", null);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new GlobalResultVO(400, "文件上传失败", null);
//        }
//    }
}
