package com.java.activiti.controller.swylsb;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.swylsb.Swylsb;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.swylsb.SwylsbService;
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
@RequestMapping("/swylsb")
public class SwylsbController {

    @Resource
    private SwylsbService swylsbService;
    /**
     * 分页查询物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/swylsbPage")
    public String swylsbPage(HttpServletResponse response, String rows, Integer projectID,
                                  String page, String searchType, String searchValue, String startDate, String endDate) throws Exception {
        PageInfo<Swylsb> swylsbPage = new PageInfo<Swylsb>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectID", projectID);
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        swylsbPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        swylsbPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", swylsbPage.getPageIndex());
        map.put("pageSize", swylsbPage.getPageSize());
        int swylsbCount = swylsbService.swylsbCount(map);
        List<Swylsb> swylsbList = swylsbService.swylsbPage(map);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(swylsbList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", swylsbCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: searchGoods
     * @Description: 物料名自动补全
     * @return
     */
    @RequestMapping(value = "/searchSwylsb", method = RequestMethod.POST)
    @ResponseBody
    public List<Swylsb> searchSwylsb(String q) {
        if(q==null){
            return null;
        }else {
            List<Swylsb> swylsb = swylsbService.findSwylsbName(q);
            return swylsb;
        }
    }

    /**
     *
     * @Title: searchCity
     * @Description: 城市名自动补全
     * @return
     */
    @RequestMapping(value = "/searchAllSwylsb", method = RequestMethod.POST)
    @ResponseBody
    public List<Swylsb> searchAllSwylsb() {
        List<Swylsb> swylsb = swylsbService.findSwylsbName();
        return swylsb;
    }

    /**
     * 新增物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/addSwylsb")
    public String addSwylsb(HttpServletResponse response,Swylsb swylsb) throws Exception{
        int SwylsbResult=swylsbService.addSwylsb(swylsb);
        JSONObject json=new JSONObject();
        if(SwylsbResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 修改物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateSwylsb")
    public String updateSwylsb(HttpServletResponse response,Swylsb swylsb) throws Exception{
        swylsb.setSbDate(new Date());
        int swylsbResult=swylsbService.updateSwylsb(swylsb);
        JSONObject json=new JSONObject();
        if(swylsbResult>0){
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
    @RequestMapping(value = "/swylsbDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSwylsbById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int swylsbResult=swylsbService.deleteSwylsbById(list);
            if(swylsbResult>0){
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
    @RequestMapping("/existSwylsb")
    public String existSwylsb(HttpServletResponse response,
                              Swylsb swylsb) throws Exception{
        List<Swylsb> swylsbResult = swylsbService.findSwylsb(swylsb);
        JSONObject json=new JSONObject();
        if(swylsbResult.size() > 0 ){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * find by id
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById")
    public Swylsb findById(HttpServletResponse response,
                              int id) throws Exception{
        Swylsb swylsb = swylsbService.findById(id);
        JSONObject result = new JSONObject();
        result.put("ylsb", JSONObject.fromObject(swylsb));
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: respositoryExport
     * @Description: 根据搜索条件，导出对应的数据
     * @param sbName 封装的条件
     * @return
     */
    @RequestMapping(value = "swylsbExport", method = RequestMethod.POST)
    @ResponseBody
    public void swylsbExport(String sbName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "swylsbExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sbName", sbName);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            swylsbService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: goodsImport
     * @Description: 导入城市信息excel
     * @author: 许超
     * @return
     */
    @RequestMapping(value = "/swylsbImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO supplierImport(MultipartFile file) {
        try {
            swylsbService.swylsbImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }
}
