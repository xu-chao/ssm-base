package com.java.activiti.controller;

import com.java.activiti.service.FileService;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @ClassName: MenuController
 * @Description:ö��
 * @author: LIUHD
 * @date: 2019��11��14��
 */
@Controller
@RequestMapping("/fileInfo")
@PropertySource("classpath:config.properties")
public class FileInfoController {

    @Autowired
    private DataSourceTransactionManager manager;

    @Resource
    FileService fileService;

    @Value("${fangte.file.realpath}")
    private String FANGTE_FILE_REALPATH;

    /**
     * ����path ·��ɾ���ļ�[�޸�״̬]
     *
     * @param filePath ����
     * @return
     * @author LIUHD
     * @date 2019��11��14��
     */
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public String deleteFile(
            @RequestParam("fileID") String fileID,
            @RequestParam("filePath") String filePath,
            HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();

        TransactionStatus transactionStatus = null;
        transactionStatus = manager.getTransaction(new DefaultTransactionDefinition());

        int resultState = 0;
//        �ж����ļ�����ͼƬ
        boolean flag = filePath.contains("uploadImage");
//        ɾ�������ļ�ID
        if (flag){
            resultState = fileService.deleteImagebyID(fileID);
        }else{
            resultState = fileService.deleteFilebyID(fileID);
        }

//        String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
//        String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
        String webappRoot = FANGTE_FILE_REALPATH;
        String Path = webappRoot + filePath;
        File file = new File(Path);
        // ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                resultState = 1;//ɾ���ɹ�
                manager.commit(transactionStatus);
            } else {
                resultState = 0;
                manager.rollback(transactionStatus);
            }
        } else {
               //�ļ�������
                manager.commit(transactionStatus);
        }
         if (resultState > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;

    }
    /**
     * ���Ҹ��ɹ����������ļ�
     */
    @RequestMapping(value = "findFiles", method = RequestMethod.POST)
    public String findFiles(@RequestParam("fileID") String fileID, HttpServletResponse response) throws Exception {

        List<com.java.activiti.model.File> files = fileService.findFilesByRepairID(fileID);
        JSONArray jsonArray = new JSONArray();
        JSONArray rows = JSONArray.fromObject(files);
        jsonArray.addAll(rows);
        ResponseUtil.write(response, jsonArray);
        return null;
    }


    /**
     * �ϴ��ļ�
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadFile")
    public String uploadFile(
            @RequestParam("fileID") String fileID,
            @RequestParam("file") CommonsMultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {


        int out = fileService.addFileImage(file, request, fileID);
//�ϴ��ɹ�

        JSONObject result = new JSONObject();
        if (out > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }
    /*
     * �����ļ�
     */

    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    @ResponseBody
    public String downloadFile(HttpServletResponse response, @RequestParam("path") String path) throws IOException {
//		String firstName =  path.substring(0,path.lastIndexOf("\\") + 1);
//        String basePath = request.getServletContext().getRealPath("\\"); // ��ȡ����·��
//        String basePath = "D:\\fangteFile\\"; // ��ȡ����·��
        String basePath = FANGTE_FILE_REALPATH; // ��ȡ����·��
        /* ��һ��:�����ļ�·����ȡ�ļ� */
//		, @RequestParam("path")String path

        java.io.File file = new java.io.File(basePath + path);
        if (file.exists()) { // �ļ�����
            /* �ڶ����������Ѵ��ڵ��ļ��������ļ������� */
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            /* ����������������������СΪ��������ַ��� */
            byte[] buffer = new byte[inputStream.available()]; // int available() ����ֵΪ������δ��ȡ���ֽڵ�����
            /* ���Ĳ������ļ����������ֽ����������� */
            inputStream.read(buffer);
            /* ���岽�� �ر������� */
            inputStream.close();

            String fileName = file.getName();// ��ȡ�ļ���
            response.reset();
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
            response.addHeader("Content-Length", "" + file.length());

            /* �������������ļ������ */
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            /* ���߲����ѻ�����������д���ļ������ */
            outputStream.write(buffer);
            /* �ڰ˲���ˢ�����������������б�������ֽ� */
            outputStream.flush();
            /* �ھŲ����ر������ */
            outputStream.close();

        }

        return null;
    }

}
