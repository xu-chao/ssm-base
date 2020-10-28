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
     * ��ҳ��ѯ��Ŀ
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
     * @Description: �������Զ���ȫ
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
     * @Description: �������Զ���ȫ
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
     * @Description: ���ݹ�԰����Ŀ
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
     * ��������
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
     * �޸ĳ���
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
     * @Description: ���ݳ���IDɾ������
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
     * �жϳ����Ƿ��Ѿ�����
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
     * @Description: ��������������������Ӧ������
     * @param subProjectName ��װ������
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
        // ��Ӧ����
        try {
            // ���������,ʵ�������ļ�
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
     * @Description: ���������Ϣexcel
     * @author: ��
     * @return
     */
    @RequestMapping(value = "/subProjectImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO projectImport(MultipartFile file) {
        try {
            subProjectService.subProjectImport(file.getInputStream());
            return new GlobalResultVO(200, "�ļ��ϴ��ɹ�", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "�ļ��ϴ�ʧ��", null);
        }
    }
}
