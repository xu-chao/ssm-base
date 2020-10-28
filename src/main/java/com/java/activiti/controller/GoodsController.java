package com.java.activiti.controller;

import com.java.activiti.model.Goods;
import com.java.activiti.model.PageInfo;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.GoodsService;
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
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    /**
     * 分页查询物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/goodsPage")
    public String goodsPage(HttpServletResponse response, String rows,
                            String page, String sort, String order,
                            String goodsName, String goodsType,
                            String goodsSysName,String goodsCode) throws Exception {
        PageInfo<Goods> goodsPage = new PageInfo<Goods>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sort", sort);
        map.put("order", order);
        map.put("goodsName", goodsName);
        map.put("goodsType", goodsType);
        map.put("goodsSysName", goodsSysName);
        map.put("goodsCode", goodsCode);
        Integer pageSize = Integer.parseInt(rows);
        goodsPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        goodsPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", goodsPage.getPageIndex());
        map.put("pageSize", goodsPage.getPageSize());
        int goodsCount = goodsService.goodsCount(map);
        List<Goods> goodsList = goodsService.goodsPage(map);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(goodsList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", goodsCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: searchGoodsNameLike
     * @Description: 物料名自动补全
     * @return
     */
    @RequestMapping(value = "/searchGoodsNameLike", method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> searchGoodsNameLike(String q) {
        if(q==null){
            return null;
        }else {
            List<Goods> goodsName = goodsService.findGoodsNameLike(q);
            return goodsName;
        }
    }

    /**
     *
     * @Title: searchGoodsCodeLike
     * @Description: 型号/图号 自动补全
     * @return
     */
    @RequestMapping(value = "/searchGoodsCodeLike", method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> searchGoodsCodeLike(String q) {
        if(q==null){
            return null;
        }else {
            List<Goods> goodsCode = goodsService.findGoodsCodeLike(q);
            return goodsCode;
        }
    }

    /**
     *
     * @Title: searchGoodsTypeLike
     * @Description: 类型 自动补全
     * @return
     */
    @RequestMapping(value = "/searchGoodsTypeLike", method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> searchGoodsTypeLike(String q) {
        if(q==null){
            return null;
        }else {
            List<Goods> goodsType = goodsService.findGoodsTypeLike(q);
            return goodsType;
        }
    }

    /**
     *
     * @Title: selectGoodsType
     * @Description: 物料类型自动补全
     * @return
     */
    @RequestMapping(value = "/selectGoodsType", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> selectGoodsType() {
        List<Tree> goodsTypes = goodsService.selectGoodsType();
        return goodsTypes;
    }

    /**
     * 新增物料
     * @return
     * @throws Exception
     */
    @RequestMapping("/addGoods")
    public String addGoods(HttpServletResponse response,Goods goods) throws Exception{
        int goodsResult=goodsService.addGoods(goods);
        JSONObject json=new JSONObject();
        if(goodsResult>0){
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
    @RequestMapping("/updateGoods")
    public String updateGoods(HttpServletResponse response,Goods goods) throws Exception{
        int goodsResult=goodsService.updateGoods(goods);
        JSONObject json=new JSONObject();
        if(goodsResult>0){
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
    @RequestMapping(value = "/goodsDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGoodsById(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String id=request.getParameter("ids");
        JSONObject json=new JSONObject();
        List<String> list=new ArrayList<String>();
        String[] strs = id.split(",");
        for (String str : strs) {
            list.add(str);
        }
        try {
            int goodsResult=goodsService.deleteGoodsById(list);
            if(goodsResult>0){
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
    @RequestMapping("/existGoods")
    public String existGoods(HttpServletResponse response,
                            Goods goods) throws Exception{
        List<Goods> goodsResult = goodsService.findGoods(goods);
        JSONObject json=new JSONObject();
        if(goodsResult.size() > 0 ){
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
     * @Description: 根据搜索条件，导出对应的数据
     * @param goodsName 封装的条件
     * @return
     */
    @RequestMapping(value = "goodsExport", method = RequestMethod.POST)
    @ResponseBody
    public void goodsExport(String goodsName, HttpServletResponse response) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateStr = sdf.format(date);
        String filename = "goodsExportBy" + UserUtil.getSubjectUserID() + "(" + dateStr + ").xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsName", goodsName);
        // 响应对象
        try {
            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));

            goodsService.export(response.getOutputStream(), map);
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
    @RequestMapping(value = "/goodsImport", method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO goodsImport(MultipartFile file) {
        try {
            goodsService.goodsImport(file.getInputStream());
            return new GlobalResultVO(200, "文件上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return new GlobalResultVO(400, "文件上传失败", null);
        }
    }
}
