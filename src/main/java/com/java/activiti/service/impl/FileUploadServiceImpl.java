package com.java.activiti.service.impl;

import com.java.activiti.pojo.ResponseJson;
import com.java.activiti.service.FileUploadService;
import com.java.activiti.util.web.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadServiceImpl implements FileUploadService {

    private final static String SERVER_URL_PREFIX = "http://localhost:8080/Activiti_LFP_war_exploded/";//这里是访问URL
    private final static String FILE_STORE_PATH = "UploadFile";

    @Override
    public ResponseJson upload(MultipartFile file, HttpServletRequest request) {
        // 重命名文件，防止重名
        String filename = getRandomUUID();
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        String fileSize = FileUtils.getFormatSize(file.getSize());
        // 截取文件的后缀名
        if (originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        filename = filename + suffix;
        String prefix = request.getSession().getServletContext().getRealPath("/") + FILE_STORE_PATH;

        System.out.println("文件存储路径为:" + prefix + "/" + filename);
        Path filePath = Paths.get(prefix, filename);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseJson().error("文件上传发生错误！");
        }
        return new ResponseJson().success()
                .setData("originalFilename", originalFilename)
                .setData("fileSize", fileSize)
                .setData("fileUrl", SERVER_URL_PREFIX + FILE_STORE_PATH + "/" + filename);
    }

    /**
     * UUID由以下几部分的组合：
     * （1）当前日期和时间，UUID的第一个部分与时间有关，如果你在生成一个UUID之后，过几秒又生成一个UUID，则第一个部分不同，其余相同。
     * （2）时钟序列。
     * （3）全局唯一的IEEE机器识别号，如果有网卡，从网卡MAC地址获得，没有网卡以其他方式获得。
     *
     * @return
     */
    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
