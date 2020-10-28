package com.java.activiti.controller;

import com.java.activiti.model.Need;
import com.java.activiti.model.NeedGoods;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.service.FileService;
import com.java.activiti.service.NeedGoodsService;
import com.java.activiti.service.NeedService;
import com.java.activiti.service.question.QuestionService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成word文档
 *
 * @author LIUHD
 */
@Controller
@RequestMapping("/word")
@Api(tags = "word文档，每个word不一样")
public class WordController {
    @Resource
    private QuestionService questionService;
    @Resource
    private NeedService needService;
    @Resource
    private NeedGoodsService needGoodsService;
    @Resource
    private TaskService taskService;
    @Resource
    private FileService fileService;

    /**
     * 生成 问题反馈系统的 word文档
     *
     * @param taskId 流程实例ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getQuestionWord", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "流程实例ID", notes = "流程实例ID必须填")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    void getQuestionWord(HttpServletResponse response, HttpServletRequest request,
                         @RequestParam(value = "taskId", required = false) String taskId,
                         @RequestParam(value = "processId", required = false) String processId ) throws IOException, TemplateException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        QuestionInfo question = null;
        if(taskId!=null){
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
             question = questionService.getQuestionByTaskId(task.getProcessInstanceId());

        }else {
            if (processId!=null){
                question = questionService.getQuestionByTaskId(processId);

            }
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //编号
        dataMap.put("parkName", question.getPark().getParkName());
        dataMap.put("projectName", question.getProject().getProjectName());
        dataMap.put("deptName", question.getSubjectID());
        dataMap.put("questionID", question.getQuestionID());
        dataMap.put("problemText", question.getProblemText());
        dataMap.put("userName", question.getUser().getAllName());
        String path = request.getServletContext().getRealPath("/");
        String filePath = null;
        try {
            filePath = fileService.getImageStr(path, question.getFileBeforeID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath == null) {
            filePath = "1";
        }
        dataMap.put("imgStr", filePath);
        //日期
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataMap.put("date", sdf.format(d));


        //Configuration 用于读取ftl文件
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        //指定路径的第二种方式，我的路径是C：/a.ftl
        try {
            configuration.setDirectoryForTemplateLoading(new File(path + "/static/word/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = question.getQuestionID() + "问题反馈.doc";
        File file = new File(name);
        // 调用工具类的createDoc方法生成Word文档
        //以utf-8的编码读取ftl文件
        Template template = null;
        try {
            template = configuration.getTemplate("questionWord.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            template.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
            out.close();

            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(name.getBytes(), "ISO-8859-1"));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());

            byte[] buff = new byte[10240];
            int bytesRead;
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成 ERP系统的 word文档
     *
     * @param id 流程实例ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getErpWord", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "流程实例ID", notes = "流程实例ID必须填")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    void getErpWord(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "id") String id) throws IOException, TemplateException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //Need need = needService.getNeedByTaskId(task.getProcessInstanceId());
        Need need = needService.findNeedById(id);
        List<NeedGoods> needGoodsList = needGoodsService.selectNeedGoods(need.getNID());

        Map<String, Object> dataMap = new HashMap<String, Object>();

        String path = request.getServletContext().getRealPath("/");

        //编号
        dataMap.put("NID", need.getNID());
        //日期
        Date EApplicantData = need.getEApplicantData();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dataMap.put("EApplicantData", sdf.format(EApplicantData));

        dataMap.put("city", need.getSubProject().getCity().getCityName());
        dataMap.put("park", need.getSubProject().getPark().getParkName());
        dataMap.put("sysName", need.getESysName());
        dataMap.put("ESubProjectNameElse", need.getESubProjectNameElse());
        dataMap.put("subProject", need.getSubProject().getSubProjectName());
        dataMap.put("EType", need.getEType());

        Date endDate = need.getEndDate();
        if(endDate==null){
            endDate = new Date();
        }
        dataMap.put("endDate", sdf.format(endDate));

        dataMap.put("needGoodsList", needGoodsList);

        //Configuration 用于读取ftl文件
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        //指定路径的第二种方式，我的路径是C：/a.ftl
        try {
            configuration.setDirectoryForTemplateLoading(new File(path + "/static/word/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = need.getNID() + "03-（智能）试制物料需求表--外购.xls";
        File file = new File(name);
        // 调用工具类的createDoc方法生成Word文档
        //以utf-8的编码读取ftl文件
        Template template = null;
        try {
            template = configuration.getTemplate("erpWord.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            template.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
            out.close();

            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(name.getBytes(), "ISO-8859-1"));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());

            byte[] buff = new byte[10240];
            int bytesRead;
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成 ERP系统的 word文档
     *
     * @param purchaseId 流程实例ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getPurchaseWord", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "流程实例ID", notes = "流程实例ID必须填")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    void getPurchaseWord(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "purchaseId") String purchaseId) throws IOException, TemplateException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
//        Task task = taskService.createTaskQuery().taskId(purchaseId).singleResult();
//        Need need = needService.getNeedByTaskId(task.getProcessInstanceId());
//        List<NeedGoods> needGoodsList = needGoodsService.selectNeedGoods(need.getNID());
        NeedGoods needGoods = needGoodsService.findNeedGoodsByPurchaseId(purchaseId);

        Map<String, Object> dataMap = new HashMap<String, Object>();

        String path = request.getServletContext().getRealPath("/");

        //编号
        dataMap.put("need", needGoods.getNeed());
        dataMap.put("storage", needGoods.getStorage());
        dataMap.put("purchase", needGoods.getPurchase());
        dataMap.put("goods", needGoods.getGoods());
        //日期
        Date purchaseDate = needGoods.getPurchase().getPurchaseDate();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dataMap.put("purchaseDate", sdf.format(purchaseDate));

        //Configuration 用于读取ftl文件
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        //指定路径的第二种方式，我的路径是C：/a.ftl
        try {
            configuration.setDirectoryForTemplateLoading(new File(path + "/static/word/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = needGoods.getPurchase().getPurchaseId() + "进料验收单.doc";
        File file = new File(name);
        // 调用工具类的createDoc方法生成Word文档
        // 以utf-8的编码读取ftl文件
        Template template = null;
        try {
            template = configuration.getTemplate("purchase.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            template.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
            out.close();

            // 设置输出流,实现下载文件
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(name.getBytes(), "ISO-8859-1"));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());

            byte[] buff = new byte[10240];
            int bytesRead;
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
