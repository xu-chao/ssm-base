package com.java.activiti.controller.swylsb;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.swylsb.SwylsbXq;
import com.java.activiti.service.swylsb.SwylsbXqService;
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
import java.util.*;

@Controller
@RequestMapping("/swylsbXq")
public class SwylsbXqController {

    @Resource
    private SwylsbXqService swylsbXqService;
    /**
     * 分页查询物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/swylsbXqPage")
    public String swylsbXqPage(HttpServletResponse response, String rows,
                                  String page, String searchType, String searchValue, String startDate, String endDate) throws Exception {
        PageInfo<SwylsbXq> swylsbXqPage = new PageInfo<SwylsbXq>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        swylsbXqPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        swylsbXqPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", swylsbXqPage.getPageIndex());
        map.put("pageSize", swylsbXqPage.getPageSize());
        int swylsbXqCount = swylsbXqService.swylsbXqCount(map);
        List<SwylsbXq> swylsbXqList = swylsbXqService.swylsbXqPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(swylsbXqList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", swylsbXqCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: searchGoods
     * @Description: 物料名自动补全
     * @return
     */
    @RequestMapping(value = "/searchSwylsbJb", method = RequestMethod.POST)
    @ResponseBody
    public List<SwylsbXq> searchSwylsbJb(String q) {
        if(q==null){
            return null;
        }else {
            List<SwylsbXq> swylsbJb = swylsbXqService.findSwylsbXqName(q);
            return swylsbJb;
        }
    }

    /**
     *
     * @Title: searchCity
     * @Description: 城市名自动补全
     * @return
     */
    @RequestMapping(value = "/searchAllSwylsbXq", method = RequestMethod.POST)
    @ResponseBody
    public List<SwylsbXq> searchAllSwylsbXq() {
        List<SwylsbXq> swylsbXq = swylsbXqService.findSwylsbXqName();
        return swylsbXq;
    }

    /**
     * 新增物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/addSwylsbXq")
    public String addSwylsbXq(HttpServletResponse response,SwylsbXq swylsbXq) throws Exception{
        int SwylsbXqResult=swylsbXqService.addSwylsbXq(swylsbXq);
        JSONObject json=new JSONObject();
        if(SwylsbXqResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * 修改物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateSwylsbXq")
    public String updateSwylsbXq(HttpServletResponse response,SwylsbXq swylsbXq) throws Exception{
        int swylsbXqResult=swylsbXqService.updateSwylsbXq(swylsbXq);
        JSONObject json=new JSONObject();
        if(swylsbXqResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Description: 根据城市ID删除城市
     * @return
     */
    @RequestMapping(value = "/swylsbXqDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSwylsbXqById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int swylsbXqResult=swylsbXqService.deleteSwylsbXqById(list);
            if(swylsbXqResult>0){
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
     * 判断城市是否已经存在
     * @return
     * @throws Exception
     */
    @RequestMapping("/existSwylsbXq")
    public String existSwylsbXq(HttpServletResponse response,
                              SwylsbXq swylsbXq) throws Exception{
        List<SwylsbXq> swylsbXqResult = swylsbXqService.findSwylsbXq(swylsbXq);
        JSONObject json=new JSONObject();
        if(swylsbXqResult.size() > 0 ){
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
     * @Description: 根据搜索条件，导出对应的数据
     * @param sbName 封装的条件
     * @return
     */
//    @RequestMapping(value = "swylsbXqExport", method = RequestMethod.POST)
//    @ResponseBody
//    public void swylsbXqExport(String sbName, HttpServletResponse response) {
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
