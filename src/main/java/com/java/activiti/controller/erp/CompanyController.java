package com.java.activiti.controller.erp;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.erp.Company;
import com.java.activiti.model.erp.Supplier;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.erp.CompanyService;
import com.java.activiti.service.erp.SupplierService;
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
@RequestMapping("/company")
public class CompanyController {

    @Resource
    private CompanyService companyService;
    /**
     * 分页查询物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/companyPage")
    public String companyPage(HttpServletResponse response, String rows,
                            String page, String sort, String order, String COMPANY_NAME) throws Exception {
        PageInfo<Company> companyPage = new PageInfo<Company>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("COMPANY_NAME", COMPANY_NAME);
        Integer pageSize = Integer.parseInt(rows);
        companyPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        companyPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", companyPage.getPageIndex());
        map.put("pageSize", companyPage.getPageSize());
        int companyCount = companyService.companyCount(map);
        List<Company> companyList = companyService.companyPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(companyList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", companyCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: searchCompany
     * @Description: 物料名自动补全
     * @return
     */
    @RequestMapping(value = "/searchCompany", method = RequestMethod.POST)
    @ResponseBody
    public List<Company> searchCompany(String q) {
        if(q==null){
            return null;
        }else {
            List<Company> COMPANY_NAME = companyService.findCompanyName(q);
            return COMPANY_NAME;
        }
    }

    /**
     *
     * @Title: searchAllCompany
     * @Description: 公司主体名自动补全
     * @return
     */
    @RequestMapping(value = "/searchAllCompany", method = RequestMethod.POST)
    @ResponseBody
    public List<Company> searchAllCompany() {
        List<Company> COMPANY_NAME = companyService.findCompanyName();
        return COMPANY_NAME;
    }

    /**
     * 新增物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/addCompany")
    public String addCompany(HttpServletResponse response,Company company) throws Exception{
        int companyResult=companyService.addCompany(company);
        JSONObject json=new JSONObject();
        if(companyResult>0){
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
    @RequestMapping("/updateCompany")
    public String updateCompany(HttpServletResponse response,Company company) throws Exception{
        int companyResult=companyService.updateCompany(company);
        JSONObject json=new JSONObject();
        if(companyResult>0){
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
    @RequestMapping(value = "/companyDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCompanyById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int companyResult=companyService.deleteCompanyById(list);
            if(companyResult>0){
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
    @RequestMapping("/existCompany")
    public String existCompany(HttpServletResponse response,
                            Company company) throws Exception{
        List<Company> companyResult = companyService.findCompany(company);
        JSONObject json=new JSONObject();
        if(companyResult.size() > 0 ){
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
     * @param COMPANY_NAME 封装的条件
     * @return
     */
    @RequestMapping(value = "companyExport", method = RequestMethod.POST)
    @ResponseBody
    public void companyExport(String COMPANY_NAME, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "companyExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("COMPANY_NAME", COMPANY_NAME);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            companyService.export(response.getOutputStream(), map);
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
    @RequestMapping(value = "/companyImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO companyImport(MultipartFile file) {
        try {
            companyService.companyImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }
}
