package com.java.activiti.controller.word;

import com.java.activiti.model.PageInfo;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.model.word.TouYing;
import com.java.activiti.service.word.TouYingService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UuidUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.task.Task;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ͶӰ�豸��װ��������
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/touYing")
public class TouYingController {
    @Resource
    private TouYingService touYingService;

    /**
     * ���� ͶӰ�豸
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/addTouYing")
    @ResponseBody
    public String addTouYing(TouYing touYing,
                             HttpServletResponse response, HttpServletRequest request
    ) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (touYing.getTouYing_date() == null) {
            touYing.setTouYing_date(new Date());
        }
        //���uuid
        String touYingID = UuidUtil.uuidUtil();
        touYing.setId(touYingID);
        resultTotal = touYingService.addTouYing(touYing);

        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    // model ���ڸ�ʽ�Ĵ���
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    /**
     * ��ҳ��ѯ����ͨ���ļ���
     *
     * @param response
     * @param rows
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "touYingPage", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "��������", notes = "�������ͱ�����")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    String questionPage(HttpServletResponse response, String rows,
                        String page, String searchType, String searchValue, String startDate, String endDate) throws Exception {
        PageInfo<TouYing> questionPage = new PageInfo<TouYing>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer pageSize = Integer.parseInt(rows);
        questionPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        questionPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", questionPage.getPageIndex());
        map.put("pageSize", questionPage.getPageSize());
        int questionCount = touYingService.touYingCount(map);
        List<TouYing> questionList = touYingService.touYingPage(map);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(questionList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", questionCount);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * ���� ���� word�ĵ�
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getTouYingWord", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "id", notes = "id������")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    void getTouYingWord(HttpServletResponse response, HttpServletRequest request,
                         @RequestParam(value = "id", required = false) String id) throws  NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        TouYing touYing = null;

        if (id != null) {
            touYing = touYingService.findById(id);

        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", touYing.getId());
        dataMap.put("project_name", touYing.getProject_name());
        dataMap.put("entry_name", touYing.getEntry_name());
        dataMap.put("userName", touYing.getUser().getAllName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dataMap.put("touYing_date", sdf.format(touYing.getTouYing_date()));
        //�������
        Class<? extends TouYing> touYingClass = touYing.getClass();
        for (int i = 1; i <= 23; i++) {
            String flag = "getN"+i+"_flag";
            String text = "getN"+i+"_text";
            // ����get����
            Method flagMethod = touYingClass.getMethod(flag);
            Method textMethod = touYingClass.getMethod(text);
            // ����get����
            char equal = (char)flagMethod.invoke(touYing);
            if ( equal=='1') {
                dataMap.put("n"+i+"_y", "\u2611");
                dataMap.put("n"+i+"_n", "��");
            } else {
                dataMap.put("n"+i+"_y", "��");
                dataMap.put("n"+i+"_n", "\u2611");
            }
            dataMap.put("n"+i+"_text", textMethod.invoke(touYing));
        }
        dataMap.put("jieLun_text", touYing.getJieLun_text());
        dataMap.put("other_text", touYing.getOther_text());

        String path = request.getServletContext().getRealPath("/");
        //Configuration ���ڶ�ȡftl�ļ�
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");
        //ָ��·���ĵڶ��ַ�ʽ���ҵ�·����C��/a.ftl
        try {
            configuration.setDirectoryForTemplateLoading(new File(path + "/static/word/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = touYing.getId() + "����.doc";
        File file = new File(name);
        // ���ù������createDoc��������Word�ĵ�
        //��utf-8�ı����ȡftl�ļ�
        Template template = null;
        try {
            template = configuration.getTemplate("touYing.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            template.process(dataMap, out); //�������������ģ���ļ��������Ŀ���ļ�
            out.close();

            // ���������,ʵ�������ļ�
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(name.getBytes(), "ISO-8859-1"));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());

            byte[] buff = new byte[10240];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ѯ������Ϣ
     *
     * @param response
     * @param id   ����ʵ��ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getTouYingById", method = RequestMethod.POST)
    public
    @ResponseBody
    @ApiOperation(value = "id", notes = "id������")
    @ApiResponses({
            @ApiResponse(code = 400, message = "�������û���"),
            @ApiResponse(code = 404, message = "����·��û�л�ҳ����ת·������")
    })
    String getTouYingById(HttpServletResponse response, String id) throws Exception {
        //�ȸ�������ID��ѯ
        TouYing touYing = touYingService.findById(id);
        JSONObject result = new JSONObject();
        result.put("touYing", JSONObject.fromObject(touYing));
        ResponseUtil.write(response, result);
        return null;
    }
}
