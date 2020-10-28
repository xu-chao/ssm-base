package com.java.activiti.controller.gaizao;

import com.java.activiti.dao.ParkDao;
import com.java.activiti.dao.ProjectDao;
import com.java.activiti.model.File;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.Project;
import com.java.activiti.model.User;
import com.java.activiti.model.gaizao.GaiZao;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.service.FileService;
import com.java.activiti.service.MailService;
import com.java.activiti.service.UserService;
import com.java.activiti.service.gaizao.GaiZaoService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 业务处理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/gaizao")
public class GaiZaoController {

    @Resource
    private GaiZaoService gaiZaoService;
    @Resource
    private ProjectDao projectDao;
    @Resource
    private ParkDao parkDao;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private UserService userService;
    @Resource
    private FileService fileService;
    @Resource
    private MailService mailService;

    /**
     * 分页查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/gaizaoPage")
    public String gaizaoPage(HttpServletResponse response, String rows,
                             String page, String sort, String order,
                             String parkID) throws Exception {
        PageInfo<GaiZao> pageInfo = new PageInfo<GaiZao>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("sort", sort);
        map.put("order", order);
        map.put("typeCode", "gzlx");

        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", pageInfo.getPageIndex());
        map.put("pageSize", pageInfo.getPageSize());
        List<Project> projects = projectDao.findProjectByParkID(parkID);//公园ID

        List<GaiZao> gaiZaos = new ArrayList<>();
        int gaiZaoCount = 0;
        if (projects.size()>0){
             gaiZaoCount = gaiZaoService.gaiZaoCount(projects);
            gaiZaos = gaiZaoService.gaiZaoPage(map, projects);
        }else {

        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(gaiZaos, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", gaiZaoCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 获取该公园下所有的记录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/gaiZaoDetail")
    public String gaiZaoDetail(HttpServletResponse response, String rows,
                             String page, String sort, String order,
                             String projectID) throws Exception {
        PageInfo<GaiZao> pageInfo = new PageInfo<GaiZao>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("sort", sort);
        map.put("order", order);
        map.put("projectID", projectID);
        map.put("typeCode", "gzlx");

        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", pageInfo.getPageIndex());
        map.put("pageSize", pageInfo.getPageSize());

        List<GaiZao> gaiZaos = new ArrayList<>();
//        int gaiZaoCount = 0;

//            gaiZaoCount = gaiZaoService.gaiZaoCount(projects);
            gaiZaos = gaiZaoService.gaiZaoDetail(map);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(gaiZaos, jsonConfig);
        result.put("rows", jsonArray);
//        result.put("total", gaiZaoCount);
        ResponseUtil.write(response, result);
        return null;
    }
    /**
     * 添加 改造
     *
     * @param gaiZao
     * @param response
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/addGaiZao", method = RequestMethod.POST)
    @RequestMapping("/addGaiZao")
    @ResponseBody
    public String addGaiZao(GaiZao gaiZao, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        //添加uuid
        resultTotal = gaiZaoService.addGaiZao(gaiZao);

        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }

        ResponseUtil.write(response, result);
        return null;
    }

    // model 日期格式的处理
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    /**
     * 修改物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateGaiZao")
    public String updateGaiZao(HttpServletResponse response,GaiZao gaiZao) throws Exception{
        int gaizaoResult=gaiZaoService.updateGaiZao(gaiZao);
        JSONObject json=new JSONObject();
        if(gaizaoResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     *
     * @Description: 根据ID删除改造单
     * @return
     */
    @RequestMapping(value = "/gaizaoDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGaiZaoById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("str");
        String files=request.getParameter("fileID");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        List<String> listFiles=new ArrayList<String>();
        String[] strs = id.split(",");
        String[] strFiles = files.split(",");
        for (String str : strs) {
            list.add(str);
        }
        for (String str : strFiles) {
            listFiles.add(str);
        }
        try {
            int goodsResult=gaiZaoService.deleteGaiZaoById(list);

            if(goodsResult>0){
                    int state = fileService.deleteFileByFileID(listFiles);//批量删除对应文件
                if (state>0){
                    json.put("success", true);
                }else{
                    json.put("success", true);
                }
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
}
