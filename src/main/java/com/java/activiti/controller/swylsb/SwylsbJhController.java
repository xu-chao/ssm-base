package com.java.activiti.controller.swylsb;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.swylsb.SwylsbJd;
import com.java.activiti.model.swylsb.SwylsbJh;
import com.java.activiti.service.swylsb.SwylsbJhService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/swylsbJh")
public class SwylsbJhController {

    @Resource
    private SwylsbJhService swylsbJhService;

    /**
     * 分页查询物料
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/swylsbJhPage")
    public String swylsbJhPage(HttpServletResponse response, String rows,
                               String page, String searchType, String searchValue, String startDate, String endDate) throws Exception {
        PageInfo<SwylsbJh> swylsbJhPage = new PageInfo<SwylsbJh>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        swylsbJhPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        swylsbJhPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", swylsbJhPage.getPageIndex());
        map.put("pageSize", swylsbJhPage.getPageSize());
        int swylsbJhCount = swylsbJhService.swylsbJhCount(map);
        List<SwylsbJh> swylsbJhList = swylsbJhService.swylsbJhPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(swylsbJhList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", swylsbJhCount);
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
    public List<SwylsbJh> searchSwylsbJb(String q) {
        if (q == null) {
            return null;
        } else {
            List<SwylsbJh> swylsbJb = swylsbJhService.findSwylsbJhName(q);
            return swylsbJb;
        }
    }
    /**
     * find by id
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById")
    public List<SwylsbJh> findById(HttpServletResponse response,
                           int id) throws Exception{
        JsonConfig jsonConfig = new JsonConfig();
        List<SwylsbJh> swylsbJb = swylsbJhService.selectSwylsbJhById(id);
        JSONObject result = new JSONObject();
//        result.put("swylsbJb", JSONObject.fromObject(swylsbJb));
        JSONArray jsonArray = JSONArray.fromObject(swylsbJb, jsonConfig);
        result.put("total", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }
    /**
     * @return
     * @Title: searchCity
     * @Description: 城市名自动补全
     */
    @RequestMapping(value = "/searchAllSwylsbJh", method = RequestMethod.POST)
    @ResponseBody
    public List<SwylsbJh> searchAllSwylsbJh() {
        List<SwylsbJh> swylsbJh = swylsbJhService.findSwylsbJhName();
        return swylsbJh;
    }

    /**
     * 新增物料
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/addSwylsbJh")
    public String addSwylsbJh(HttpServletResponse response, SwylsbJh swylsbJh) throws Exception {
        int SwylsbJhResult = swylsbJhService.addSwylsbJh(swylsbJh);
        JSONObject json = new JSONObject();
        if (SwylsbJhResult > 0) {
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
    @RequestMapping("/updateSwylsbJh")
    public String updateSwylsbJh(HttpServletResponse response, HttpServletRequest request, int ID) throws Exception {
        List<SwylsbJh> swylsbJhs = new ArrayList<>();
        Date date;
        String ds = request.getParameter("postDataJh");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray json = JSONArray.fromObject(ds); //使用net.sf.json.JSONObject对象来解析json
        JSONObject jsonOne;
        Map<String, Object> map = null;
//        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();?
        SwylsbJd swylsbJd = new SwylsbJd();
        Field[] fields = swylsbJd.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {//SwylsbJd字段
            SwylsbJh swylsbJh = new SwylsbJh();
            for (int j = 0; j < json.size(); j++) { //postDataJh
                jsonOne = json.getJSONObject(j);
                if ((String) jsonOne.get("Value") != "" && !"".equals((String) jsonOne.get("Value"))) {
                    String str = (String) jsonOne.get("Key");
                    int index = 1;
                    if (str.contains("-")) {
                        index = str.indexOf("-");
                    }
                    String sub = str.substring(0, index);
                    String subs = fields[i].getName();
                    //判读 是否是某个字段 然后赋值
                    if (str.substring(0, index).equals(fields[i].getName())) {
                         swylsbJh.setID(ID);
                        swylsbJh.setJdField(str.substring(0, index));
                       String ss = str.substring(index+1);
                        if (str.substring(index+1).equals("jhStartDate")) {
                            date = sdf.parse((String) jsonOne.get("Value"));
                            swylsbJh.setJhStartDate(date);
                        } else if (str.substring(index+1).equals("jhEndDate") ) {
                            date = sdf.parse((String) jsonOne.get("Value"));
                            swylsbJh.setJhEndDate(date);
                        } else if (str.substring(index+1).equals("sjStartDate")) {
                            date = sdf.parse((String) jsonOne.get("Value"));
                            swylsbJh.setSjStartDate(date);
                        } else if (str.substring(index+1).equals("sjEndDate")) {
                            date = sdf.parse((String) jsonOne.get("Value"));
                            swylsbJh.setSjEndDate(date);
                        }

                    }
                }
            }
            if (swylsbJh.getID()!=null){
                swylsbJhs.add(swylsbJh);
            }
        }

        int swylsbJhResult = swylsbJhService.updateSwylsbJh(swylsbJhs);
        JSONObject json1 = new JSONObject();
        if (swylsbJhResult > 0) {
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
    @RequestMapping(value = "/swylsbJhDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSwylsbJhById(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String id = request.getParameter("ids");
        JSONObject json = new JSONObject();
        List<String> list = new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int swylsbJhResult = swylsbJhService.deleteSwylsbJhById(list);
            if (swylsbJhResult > 0) {
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
    @RequestMapping("/existSwylsbJh")
    public String existSwylsbJh(HttpServletResponse response,
                                SwylsbJh swylsbJh) throws Exception {
        List<SwylsbJh> swylsbJhResult = swylsbJhService.findSwylsbJh(swylsbJh);
        JSONObject json = new JSONObject();
        if (swylsbJhResult.size() > 0) {
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
//    @RequestMapping(value = "swylsbJhExport", method = RequestMethod.POST)
//    @ResponseBody
//    public void swylsbJhExport(String sbName, HttpServletResponse response) {
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
