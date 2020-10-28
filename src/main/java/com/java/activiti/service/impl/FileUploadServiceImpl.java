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

    private final static String SERVER_URL_PREFIX = "http://localhost:8080/Activiti_LFP_war_exploded/";//�����Ƿ���URL
    private final static String FILE_STORE_PATH = "UploadFile";

    @Override
    public ResponseJson upload(MultipartFile file, HttpServletRequest request) {
        // �������ļ�����ֹ����
        String filename = getRandomUUID();
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        String fileSize = FileUtils.getFormatSize(file.getSize());
        // ��ȡ�ļ��ĺ�׺��
        if (originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        filename = filename + suffix;
        String prefix = request.getSession().getServletContext().getRealPath("/") + FILE_STORE_PATH;

        System.out.println("�ļ��洢·��Ϊ:" + prefix + "/" + filename);
        Path filePath = Paths.get(prefix, filename);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseJson().error("�ļ��ϴ���������");
        }
        return new ResponseJson().success()
                .setData("originalFilename", originalFilename)
                .setData("fileSize", fileSize)
                .setData("fileUrl", SERVER_URL_PREFIX + FILE_STORE_PATH + "/" + filename);
    }

    /**
     * UUID�����¼����ֵ���ϣ�
     * ��1����ǰ���ں�ʱ�䣬UUID�ĵ�һ��������ʱ���йأ������������һ��UUID֮�󣬹�����������һ��UUID�����һ�����ֲ�ͬ��������ͬ��
     * ��2��ʱ�����С�
     * ��3��ȫ��Ψһ��IEEE����ʶ��ţ������������������MAC��ַ��ã�û��������������ʽ��á�
     *
     * @return
     */
    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
