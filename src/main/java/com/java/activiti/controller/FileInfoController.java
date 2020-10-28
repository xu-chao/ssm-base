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
 * @Description:枚举
 * @author: LIUHD
 * @date: 2019年11月14日
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
     * 根据path 路径删除文件[修改状态]
     *
     * @param filePath 主键
     * @return
     * @author LIUHD
     * @date 2019年11月14日
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
//        判断是文件还是图片
        boolean flag = filePath.contains("uploadImage");
//        删除数据文件ID
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
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                resultState = 1;//删除成功
                manager.commit(transactionStatus);
            } else {
                resultState = 0;
                manager.rollback(transactionStatus);
            }
        } else {
               //文件不存在
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
     * 查找该派工单下所有文件
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
     * 上传文件
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
//上传成功

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
     * 下载文件
     */

    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    @ResponseBody
    public String downloadFile(HttpServletResponse response, @RequestParam("path") String path) throws IOException {
//		String firstName =  path.substring(0,path.lastIndexOf("\\") + 1);
//        String basePath = request.getServletContext().getRealPath("\\"); // 获取基本路径
//        String basePath = "D:\\fangteFile\\"; // 获取基本路径
        String basePath = FANGTE_FILE_REALPATH; // 获取基本路径
        /* 第一步:根据文件路径获取文件 */
//		, @RequestParam("path")String path

        java.io.File file = new java.io.File(basePath + path);
        if (file.exists()) { // 文件存在
            /* 第二步：根据已存在的文件，创建文件输入流 */
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            /* 第三步：创建缓冲区，大小为流的最大字符数 */
            byte[] buffer = new byte[inputStream.available()]; // int available() 返回值为流中尚未读取的字节的数量
            /* 第四步：从文件输入流读字节流到缓冲区 */
            inputStream.read(buffer);
            /* 第五步： 关闭输入流 */
            inputStream.close();

            String fileName = file.getName();// 获取文件名
            response.reset();
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
            response.addHeader("Content-Length", "" + file.length());

            /* 第六步：创建文件输出流 */
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            /* 第七步：把缓冲区的内容写入文件输出流 */
            outputStream.write(buffer);
            /* 第八步：刷空输出流，并输出所有被缓存的字节 */
            outputStream.flush();
            /* 第九步：关闭输出流 */
            outputStream.close();

        }

        return null;
    }

}
