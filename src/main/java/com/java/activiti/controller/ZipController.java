package com.java.activiti.controller;

import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.service.FileService;
import com.java.activiti.service.RedisService;
import com.java.activiti.service.question.QuestionService;
import com.java.activiti.util.FileUtil;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.StringUtils;
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
@RequestMapping("/zipFile")
public class ZipController {
    @Resource
    private RedisService redisService;
    @Resource
    private FileService fileService;
    @Resource
    private QuestionService questionService;

    /**
     * ���� ZIP
     *
     * @param questionID ���ⵥID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDownFileZip", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "questionID", notes = "questionID������")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    void getDownZip(HttpServletResponse response,
                    HttpServletRequest request,
                    @RequestParam(value = "questionID", required = true) String questionID) throws IOException, TemplateException {
        QuestionInfo question = null;
        /** �ļ���  */
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateName = redisService.getIncrementNum(10,"FILE_ID",9,2);
        String dateTime = sdf2.format(new Date());
        String zipName1 = "���ⵥ" + dateName;
        /** ����� ���ص��ļ���  */
        String zipName =  new String(zipName1.getBytes(),"ISO8859-1");
        /** .������ʱ�ļ���  */
//        String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        /** ��Ŀ�е����·�� */
        String webappRoot = request.getRealPath("/");
//        String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
        /** ��ʱĿ¼  */
        String Path = webappRoot + "temporary/" + dateName;

        /** .��ȡ�ļ�·�� ���������  */
        question = questionService.findById(questionID);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        //���
        dataMap.put("parkName", question.getPark().getParkName());
        dataMap.put("projectName", question.getProject().getProjectName());
        dataMap.put("deptName", question.getSubjectID());
        dataMap.put("questionID", question.getQuestionID());
        dataMap.put("problemText", question.getProblemText());
        dataMap.put("userName", question.getUser().getAllName());
        dataMap.put("imgStr", "");
        dataMap.put("date", dateTime);
        //Configuration ���ڶ�ȡftl�ļ�
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");

        configuration.setDirectoryForTemplateLoading(new File(webappRoot + "static/word/"));

        /** ����ĵ�·��������   ��ʱĿ¼*/
        File outWordFile = new File(Path);
        if (!outWordFile.exists()) {
            outWordFile.mkdirs();
        }
        File outWord = new File(Path + "/" + zipName1 + ".doc");
        //��utf-8�ı����ȡftl�ļ�
        Template template = configuration.getTemplate("questionWord.ftl", "utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outWord), "utf-8"), 10240);
        template.process(dataMap, out);
        out.close();


        File temDir = new File(Path);
        if (!temDir.exists()) {
            temDir.mkdirs();
        }
        List<com.java.activiti.model.File> files = fileService.findFilesByRepairID(question.getFileBeforeID());
        for (com.java.activiti.model.File file : files) {
            /** .������Ҫ���ص��ļ����������ʱ�ļ����� */
            File f = new File(webappRoot + file.getPath());
            try {
                FileUtil.copy(f, temDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter writer;
        /** .����response��header */
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName + ".zip");
        /** .���ù����࣬����zipѹ���� */
        // �������ǲ���Ҫ����Ŀ¼�ṹ
        try {
            FileUtil.toZip(temDir.getPath(), response.getOutputStream(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** .ɾ����ʱ�ļ����ļ��� */
        // ������ûд�ݹ飬ֱ�Ӿ�����ɾ����
        File[] listFiles = temDir.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            listFiles[i].delete();
        }
        temDir.delete();

    }
}
