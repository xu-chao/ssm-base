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
 * ����word�ĵ�
 *
 * @author LIUHD
 */
@Controller
@RequestMapping("/word")
@Api(tags = "word�ĵ���ÿ��word��һ��")
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
     * ���� ���ⷴ��ϵͳ�� word�ĵ�
     *
     * @param taskId ����ʵ��ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getQuestionWord", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "����ʵ��ID", notes = "����ʵ��ID������")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
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
        //���
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
        //����
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataMap.put("date", sdf.format(d));


        //Configuration ���ڶ�ȡftl�ļ�
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        //ָ��·���ĵڶ��ַ�ʽ���ҵ�·����C��/a.ftl
        try {
            configuration.setDirectoryForTemplateLoading(new File(path + "/static/word/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = question.getQuestionID() + "���ⷴ��.doc";
        File file = new File(name);
        // ���ù������createDoc��������Word�ĵ�
        //��utf-8�ı����ȡftl�ļ�
        Template template = null;
        try {
            template = configuration.getTemplate("questionWord.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            template.process(dataMap, out); //�������������ģ���ļ��������Ŀ���ļ�
            out.close();

            // ���������,ʵ�������ļ�
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
     * ���� ERPϵͳ�� word�ĵ�
     *
     * @param id ����ʵ��ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getErpWord", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "����ʵ��ID", notes = "����ʵ��ID������")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
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

        //���
        dataMap.put("NID", need.getNID());
        //����
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

        //Configuration ���ڶ�ȡftl�ļ�
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        //ָ��·���ĵڶ��ַ�ʽ���ҵ�·����C��/a.ftl
        try {
            configuration.setDirectoryForTemplateLoading(new File(path + "/static/word/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = need.getNID() + "03-�����ܣ��������������--�⹺.xls";
        File file = new File(name);
        // ���ù������createDoc��������Word�ĵ�
        //��utf-8�ı����ȡftl�ļ�
        Template template = null;
        try {
            template = configuration.getTemplate("erpWord.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            template.process(dataMap, out); //�������������ģ���ļ��������Ŀ���ļ�
            out.close();

            // ���������,ʵ�������ļ�
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
     * ���� ERPϵͳ�� word�ĵ�
     *
     * @param purchaseId ����ʵ��ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getPurchaseWord", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "����ʵ��ID", notes = "����ʵ��ID������")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
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

        //���
        dataMap.put("need", needGoods.getNeed());
        dataMap.put("storage", needGoods.getStorage());
        dataMap.put("purchase", needGoods.getPurchase());
        dataMap.put("goods", needGoods.getGoods());
        //����
        Date purchaseDate = needGoods.getPurchase().getPurchaseDate();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dataMap.put("purchaseDate", sdf.format(purchaseDate));

        //Configuration ���ڶ�ȡftl�ļ�
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        //ָ��·���ĵڶ��ַ�ʽ���ҵ�·����C��/a.ftl
        try {
            configuration.setDirectoryForTemplateLoading(new File(path + "/static/word/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = needGoods.getPurchase().getPurchaseId() + "�������յ�.doc";
        File file = new File(name);
        // ���ù������createDoc��������Word�ĵ�
        // ��utf-8�ı����ȡftl�ļ�
        Template template = null;
        try {
            template = configuration.getTemplate("purchase.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            template.process(dataMap, out); //�������������ģ���ļ��������Ŀ���ļ�
            out.close();

            // ���������,ʵ�������ļ�
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
