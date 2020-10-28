package com.java.activiti.security.controller;

import com.java.activiti.security.util.CheckCodeGenerator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/")
@Controller
public class CheckCodeController {

    private static Logger log = Logger.getLogger("application");
    @Resource
    private CheckCodeGenerator checkCodeGenerator;
    /**
     * ��ȡͼ����֤�� ������һ������4λ�ַ�����ĸ�����֣���ͼ����֤�룬���ҽ�ͼ����֤���ֵ���õ��û��� session ��
     *
     * @param time     ʱ���
     * @param response ���ص� HttpServletResponse ��Ӧ
     */
    @RequestMapping(value = "checkCode/{time}", method = RequestMethod.GET)
    public void getCheckCode(@PathVariable("time") String time, HttpServletResponse response, HttpServletRequest request) {

        BufferedImage checkCodeImage = null;
        String checkCodeString = null;

        // ��ȡͼ����֤�룬������util/CheckCodeGenerator
        Map<String, Object> checkCode = checkCodeGenerator.generlateCheckCode();

        if (checkCode != null) {
            checkCodeString = (String) checkCode.get("checkCodeString");
            checkCodeImage = (BufferedImage) checkCode.get("checkCodeImage");
        }

        if (checkCodeString != null && checkCodeImage != null) {
            //��ȡresponse.getOutputStream()
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                // ���� Session
                HttpSession session = request.getSession();
                session.setAttribute("checkCode", checkCodeString);

                // ����֤�����
                ImageIO.write(checkCodeImage, "png", outputStream);

                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                response.setContentType("image/png");
            } catch (IOException e) {
                log.error("fail to get the ServletOutputStream");
            }
        }
    }
}
