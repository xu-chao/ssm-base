package com.java.activiti.controller;

import com.java.activiti.dao.ProjectDao;
import com.java.activiti.model.City;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.Park;
import com.java.activiti.model.Project;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.ProjectService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.StringUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectDao projectDao;
    /**
     * 分页查询项目
     * @return
     * @throws Exception
     */
    @RequestMapping("/projectPage")
    public String projectPage(HttpServletResponse response, String rows,
                           String page, String sort, String order, String projectName) throws Exception {
        PageInfo<Project> projectPage = new PageInfo<Project>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("projectName", projectName);
        Integer pageSize = Integer.parseInt(rows);
        projectPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        projectPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", projectPage.getPageIndex());
        map.put("pageSize", projectPage.getPageSize());
        int projectCount = projectService.projectCount(map);
        List<Project> projectList = projectService.projectPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(projectList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", projectCount);
        ResponseUtil.write(response, result);
        return null;
    }
    @RequestMapping(value = "/searchAllProject", method = RequestMethod.POST)
    @ResponseBody
    public List<Project> searchAllProject() {
        List<Project> projectName = projectService.searchAllProject();
        return projectName;
    }
    /**
     *
     * @Title: searchProject
     * @Description: 城市名自动补全
     * @param q
     * @return
     */
    @RequestMapping(value = "/searchProject", method = RequestMethod.POST)
    @ResponseBody
    public List<Project> searchProject(String q) {
        List<Project> projectName = projectService.findProjectName(q);
        return projectName;
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
        List<Park> parkName = projectService.findParkByCityID(cityID);
        return parkName;
    }
    /**
     *
     * @Title: findProjectByParkID
     * @Description: 根据公园找项目
     * @param parkID
     * @return
     */
    @RequestMapping(value = "/findProjectByParkID", method = RequestMethod.POST)
    @ResponseBody
    public List<Project> findProjectByParkID(String parkID) {
        List<Project> projectName = projectService.findProjectByParkID(parkID);
        return projectName;
    }

    /**
     * 新增城市
     * @return
     * @throws Exception
     */
    @RequestMapping("/addProject")
    public String addProject(HttpServletResponse response,Project project) throws Exception{
        int projectResult=projectService.addProject(project);
        JSONObject json=new JSONObject();
        if(projectResult>0){
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
    @RequestMapping("/updateProject")
    public String updateProject(HttpServletResponse response,Project project) throws Exception{
        int projectResult=projectService.updateProject(project);
        JSONObject json=new JSONObject();
        if(projectResult>0){
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
    @RequestMapping(value = "/projectDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteProjectById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int projectResult=projectService.deleteProjectById(list);
            if(projectResult>0){
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
    @RequestMapping("/existProject")
    public String existProject(HttpServletResponse response,
                            Project project) throws Exception{
        List<Project> projectResult = projectService.findProject(project);
        JSONObject json=new JSONObject();
        if(projectResult.size() > 0 ){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Title: pojectExport
     * @Description: 根据搜索条件，导出对应的数据
     * @param projectName 封装的条件
     * @return
     */
    @RequestMapping(value = "projectExport", method = RequestMethod.POST)
    @ResponseBody
    public void parkExport(String projectName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "projectExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectName", projectName);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            projectService.export(response.getOutputStream(), map);
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
    @RequestMapping(value = "/projectImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO projectImport(MultipartFile file) {
        try {
            projectService.projectImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }
}
