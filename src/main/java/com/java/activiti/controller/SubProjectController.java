package com.java.activiti.controller;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.Park;
import com.java.activiti.model.SubProject;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.SubProjectService;
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
@RequestMapping("/subProject")
public class SubProjectController {

    @Resource
    private SubProjectService subProjectService;
    /**
     * 分页查询项目
     * @return
     * @throws Exception
     */
    @RequestMapping("/subProjectPage")
    public String subProjectPage(HttpServletResponse response, String rows,
                           String page, String sort, String order, String subProjectName) throws Exception {
        PageInfo<SubProject> subProjectPage = new PageInfo<SubProject>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("subProjectName", subProjectName);
        Integer pageSize = Integer.parseInt(rows);
        subProjectPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        subProjectPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", subProjectPage.getPageIndex());
        map.put("pageSize", subProjectPage.getPageSize());
        int subProjectCount = subProjectService.subProjectCount(map);
        List<SubProject> subProjectList = subProjectService.subProjectPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(subProjectList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", subProjectCount);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping(value = "/searchAllSubProject", method = RequestMethod.POST)
    @ResponseBody
    public List<SubProject> searchAllSubProject() {
        List<SubProject> subProjectName = subProjectService.searchAllSubProject();
        return subProjectName;
    }
    /**
     *
     * @Title: searchProject
     * @Description: 城市名自动补全
     * @param q
     * @return
     */
    @RequestMapping(value = "/searchSubProject", method = RequestMethod.POST)
    @ResponseBody
    public List<SubProject> searchSubProject(String q) {
        List<SubProject> subProjectName = subProjectService.findSubProjectName(q);
        return subProjectName;
    }
    /**
     *
     * @Title: findParkByCityID
     * @Description: 城市名自动补全
     * @param cityID
     * @return
     */
    @RequestMapping(value = "/findParkByCityID", method = RequestMethod.POST)
    @ResponseBody
    public List<Park> findParkByCityID(String cityID) {
        List<Park> parkName = subProjectService.findParkByCityID(cityID);
        return parkName;
    }
    /**
     *
     * @Title: findProjectByParkID
     * @Description: 根据公园找项目
     * @param parkID
     * @return
     */
    @RequestMapping(value = "/findSubProjectByParkID", method = RequestMethod.POST)
    @ResponseBody
    public List<SubProject> findSubProjectByParkID(int parkID) {
        List<SubProject> subProjectName = subProjectService.findSubProjectByPark(parkID);
        return subProjectName;
    }

    /**
     * 新增城市
     * @return
     * @throws Exception
     */
    @RequestMapping("/addSubProject")
    public String addSubProject(HttpServletResponse response,SubProject subProject) throws Exception{
        int subProjectResult=subProjectService.addSubProject(subProject);
        JSONObject json=new JSONObject();
        if(subProjectResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }



    /**
     * 修改城市
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateSubProject")
    public String updateSubProject(HttpServletResponse response,SubProject subProject) throws Exception{
        int subProjectResult=subProjectService.updateSubProject(subProject);
        JSONObject json=new JSONObject();
        if(subProjectResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: searchProject
     * @Description: 根据城市ID删除城市
     * @return
     */
    @RequestMapping(value = "/subProjectDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSubProjectById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int subProjectResult=subProjectService.deleteSubProjectById(list);
            if(subProjectResult>0){
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
    @RequestMapping("/existSubProject")
    public String existSubProject(HttpServletResponse response,
                            SubProject subProject) throws Exception{
        List<SubProject> subProjectResult = subProjectService.findSubProject(subProject);
        JSONObject json=new JSONObject();
        if(subProjectResult.size() > 0 ){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: subProjectExport
     * @Description: 根据搜索条件，导出对应的数据
     * @param subProjectName 封装的条件
     * @return
     */
    @RequestMapping(value = "subProjectExport", method = RequestMethod.POST)
    @ResponseBody
    public void subProjectExport(String subProjectName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "subProjectExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subProjectName", subProjectName);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            subProjectService.export(response.getOutputStream(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @Title: projectImport
     * @Description: 导入城市信息excel
     * @author: 许超
     * @return
     */
    @RequestMapping(value = "/subProjectImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO projectImport(MultipartFile file) {
        try {
            subProjectService.subProjectImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }
}
