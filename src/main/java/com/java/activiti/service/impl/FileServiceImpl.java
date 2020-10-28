package com.java.activiti.service.impl;

import com.java.activiti.dao.FileDao;
import com.java.activiti.model.Images;
import com.java.activiti.model.Repair;
import com.java.activiti.service.FileService;
import com.java.activiti.service.RedisService;
import com.java.activiti.util.RedisUtil;
import com.java.activiti.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;


@Service("fileService")
@PropertySource("classpath:config.properties")
public class FileServiceImpl implements FileService {
    @Resource
    private FileDao fileDao;

    @Autowired
    private DataSourceTransactionManager manager;

    @Resource
    private RedisService redisService;

    @Value("${fangte.file.realpath}")
    private String FANGTE_FILE_REALPATH;

    @Override
    public int addFileImage(MultipartFile file, HttpServletRequest request, String fileID) throws IOException {
//form表单提交的参数测试为String类型
        boolean flag = true;
        String realPath;
//        ServletContext servletContext = request.getServletContext();//获取ServletContext的对象 代表当前WEB应用
//        String userID = UserUtil.getSubjectUserID();
//        String fileName = UuidUtil.uuidUtil() + file.getOriginalFilename().replace(" ", "");
        String fileName = redisService.getIncrementNum(15,"FILE_ID",9,2)
                + file.getOriginalFilename().replace(" ", "");
        String saveName;
        String endName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        // 获取文件后缀名bmp,jpg,png,tif,gif,
        if ("png".equals(endName) || "jpg".equals(endName) || "gif".equals(endName)
                || "bmp".equals(endName) || "tif".equals(endName)) {
//            realPath = servletContext.getRealPath("/uploadImage");//得到文件上传目的位置的真实路径
//            realPath = "D:\fangteFile";//得到文件上传目的位置的真实路径
            realPath = FANGTE_FILE_REALPATH+"uploadImage";//得到文件上传目的位置的真实路径
            saveName = "\\\\uploadImage\\\\" + fileName;
            flag = false;
        } else {
//            realPath = servletContext.getRealPath("/uploadFile");//得到文件上传目的位置的真实路径
            realPath = FANGTE_FILE_REALPATH+"uploadFile";//得到文件上传目的位置的真实路径
            saveName = "\\\\uploadFile\\\\" + fileName;
        }

        System.out.println("realPath :" + realPath);
        java.io.File file1 = new java.io.File(realPath);
        if (!file1.exists()) {
            file1.mkdir(); //如果该目录不存在，就创建此抽象路径名指定的目录。
        }
        InputStream in = file.getInputStream();//声明输入输出流

        String filePath = realPath + "\\" + fileName;//路径
        OutputStream out = new FileOutputStream(new java.io.File(filePath));//指定输出流的位置;
        byte[] buffer = new byte[1024];
        int len = 0;
            while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
            out.flush();                //类似于文件复制，将文件存储到输入流，再通过输出流写入到上传位置
        }                               //这段代码也可以用IOUtils.copy(in, out)工具类的copy方法完成
        out.close();
        in.close();
        int resultTotal;
        if (flag) {//文件
            com.java.activiti.model.File fileInfo = new com.java.activiti.model.File();
            fileInfo.setRepairID(fileID);
            fileInfo.setCreate_time(new Date());
//              fileInfo.setPath(filePath);
            fileInfo.setPath(saveName);
            fileInfo.setRemark(fileName);
            fileInfo.setIs_deleted(0);
//              fileDao.insertFile(repair);
            resultTotal = fileDao.addFiles(fileInfo);
        } else {
            Images images = new Images();
            images.setRepairID(fileID);
            images.setCreate_time(new Date());
            images.setPath(saveName);
            images.setRemark(fileName);
            images.setIs_deleted(0);
            resultTotal = fileDao.addImages(images);
        }

        return resultTotal;

    }

    @Override
    public List<com.java.activiti.model.File> findFilesByRepairID(String repairFileID) {
        List<com.java.activiti.model.File> files = fileDao.findFilesByRepairID(repairFileID);
        List<Images> images = fileDao.findImageByRepairID(repairFileID);
        for (Images image : images) {
            com.java.activiti.model.File file = new com.java.activiti.model.File();
            file.setFileID(image.getImagesID());
            file.setPath(image.getPath());
            file.setCreate_time(image.getCreate_time());
            file.setRemark(image.getRemark());
            files.add(file);
        }

        return files;
    }

    @Override
    public List<Images> findImagesByRepairID(String repairFileID) {
        List<Images> images = fileDao.findImageByRepairID(repairFileID);
        return images;
    }

    public HttpServletResponse downloadFile(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public String getImageStr(String path, String fileID) {
        List<Images> images = fileDao.findImageByRepairID(fileID);
        String imgFile = null;
        if (images.size() > 0) {
            imgFile = images.get(0).getPath();
        }
        try {
            InputStream in = null;
            byte[] data = null;
            in = new FileInputStream(path + imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int deleteFilebyID(String id) {

        int resultState;

        resultState = fileDao.deleteFilebyID(id);

        return resultState;
    }

    @Override
    public int deleteImagebyID(String id) {

        int resultState;

        resultState = fileDao.deleteImagebyID(id);

        return resultState;
    }

    //-----------------------------------------------------------------------------------------
    //批量查找 文件和图片 数据库信息
    public List<com.java.activiti.model.File> findFileandImage(List<String> repairFileID) {
        List<com.java.activiti.model.File> files = fileDao.selectFileList(repairFileID);
        List<Images> images = fileDao.selectImageList(repairFileID);
        for (Images image : images) {
            com.java.activiti.model.File file = new com.java.activiti.model.File();
            file.setFileID(image.getImagesID());
            file.setPath(image.getPath());
            file.setCreate_time(image.getCreate_time());
            file.setRemark(image.getRemark());
            files.add(file);
        }

        return files;
    }

    //批量删除项目中 文件
    public int deleteFileByPath(List<com.java.activiti.model.File> files) {
        String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
        int resultState = 0;

        for (com.java.activiti.model.File file : files) {
            String Path = webappRoot + file.getPath();
            File fileKo = new File(Path);
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            if (fileKo.exists() && fileKo.isFile()) {
                if (fileKo.delete()) {
                    resultState = 1;//删除成功
                } else {
                    return 0;
                }
            }
        }

        return resultState;
    }

    public int deleteFileByFileID(List<String> fileIDs) {

        // 手动回滚数据库
        TransactionStatus transactionStatus = null;
        transactionStatus = manager.getTransaction(new DefaultTransactionDefinition());
        int resultFile = 0;
        int resultImage = 0;
        //先根据 文件ID 找出 所有文件和图片的数据库信息
        List<com.java.activiti.model.File> lists = findFileandImage(fileIDs);
        try {
        //然后批量删除 数据库 文件信息
            resultFile = fileDao.deleteListFile(fileIDs);
            resultImage = fileDao.deleteListImage(fileIDs);
        if (resultFile > 0||resultImage>0) {
            //         批量删除文件
            int state = deleteFileByPath(lists);
            if (state > 0) {//文件删除成功
                manager.commit(transactionStatus);
                return 1;
            } else {
                manager.rollback(transactionStatus);
                return 0;
            }
        } else {
            manager.rollback(transactionStatus);
            return 0;
        }
        } catch (Exception e) {
            manager.rollback(transactionStatus);
            return 0;
        }

    }
}
