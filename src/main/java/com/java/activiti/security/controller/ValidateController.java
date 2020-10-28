package com.java.activiti.security.controller;
import com.java.activiti.security.imgCode.VerifyImageUtil;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 1. Created by yansheng on 2014/6/29.
 */
@Controller
@RequestMapping("/validate")
public class ValidateController {
    private static Logger log = Logger.getLogger("application");
    @Resource
    private JedisPool jedisPool;

//    @Resource
//        @RequestMapping(value = "codeInit", method = RequestMethod.GET)
    /**
     * 生成验证码
     *
     * @return
     */
    @RequestMapping("/codeInit")
    public String codeInit(HttpServletResponse response,HttpServletRequest request) throws Exception {
        JSONObject object = new JSONObject();
        /*redis实现:使用base64编码转化成字符串处理*/
//        List<String> imgList = JedisUtils.getList(JedisConfig.KEY_VALIDATE_IMG);
//        List<String> tpllist = JedisUtils.getList(JedisConfig.KEY_VALIDATE_TPL);
//        if (null == imgList || imgList.size() < 1 || tpllist == null || tpllist.size() < 1) {
//            imgList = new ArrayList<String>();
//            tpllist = new ArrayList<String>();
//            initValidateResources(imgList,tpllist);
//            JedisUtils.setList(JedisConfig.KEY_VALIDATE_IMG,imgList,JedisConfig.JEDIS_EXPIRE*3);
//            JedisUtils.setList(JedisConfig.KEY_VALIDATE_TPL,tpllist,JedisConfig.JEDIS_EXPIRE*3);
//        }
        /*本地缓存实现*/
        List<byte[]> imgList = null;//底图
        List<byte[]> tpllist = null;//抠图模版
//        List<byte[]> tpllist = ValidateCache.get(JedisConfig.KEY_VALIDATE_TPL);
        if (null == imgList || imgList.size() < 1 || tpllist == null || tpllist.size() < 1) {
            imgList = new ArrayList<byte[]>();
            tpllist = new ArrayList<byte[]>();
            initValidateResources(imgList,tpllist);
//            ValidateCache.set(JedisConfig.KEY_VALIDATE_IMG,imgList);
//            ValidateCache.set(JedisConfig.KEY_VALIDATE_TPL,tpllist);
        }

        byte[] targetIS = null;
        byte[] templateIS = null;
        Random ra = new Random();
        if (null != imgList){
            int rd = ra.nextInt(imgList.size());
            targetIS  = imgList.get(rd);
        }
        if (null != tpllist){
            int rd = ra.nextInt(tpllist.size());
            templateIS  = tpllist.get(rd);//抠图模版
        }

        Map<String, Object> pictureMap = null;
        try {
            pictureMap = VerifyImageUtil.pictureTemplatesCut(templateIS,targetIS , "png", "png");
            String newImage = Base64Utils.encodeToString((byte[]) pictureMap.get("newImage"));
            String sourceImage = Base64Utils.encodeToString((byte[]) pictureMap.get("oriCopyImage"));
            int X = (int) pictureMap.get("X");
            int Y = (int) pictureMap.get("Y");
            object.put("newImage", newImage);
            object.put("sourceImage", sourceImage);
            //object.put("X", X);
            object.put("Y", Y);


            String token = UUID.randomUUID().toString().replaceAll("-", "");
            Map<String, Object> tokenObj = new HashMap<String, Object>();
            tokenObj.put("token", token);
            tokenObj.put("X", X);
            tokenObj.put("Y", Y);
            //token 保存2分钟
            // 从缓存中读取数据
            HttpSession session = request.getSession();
            session.setAttribute(token, tokenObj);
//            JedisUtils.setObjectMap(JedisConfig.KEY_VALIDATE_TOKEN + ":" + token, tokenObj, 120000);
            object.put("token", token);
        } catch (Exception e) {
            log.error("",e);
        }
        ResponseUtil.write(response, object);
        return null;
    }

    /**
     * 初始化验证图形生成资源
     * @param imgList
     * @param tpllist
     */
    private void initValidateResources(List<byte[]> imgList, List<byte[]> tpllist) throws IOException {
        /*加载验证原图*/
        String target = URLDecoder.decode(ValidateController.class.getClassLoader().getResource("../../static/Validate/codeimg").getPath(),"UTF-8");
        byte[] targetIS = null;
        byte[] templateIS = null;
        if (target.indexOf("!/") != -1) {//jar包
            String jarPath = "jar:" + target;
            log.debug(jarPath);
            URL jarURL = new URL(jarPath);
            JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
            JarFile jarFile = jarCon.getJarFile();
            Enumeration<JarEntry> jarEntrys = jarFile.entries();
            while (jarEntrys.hasMoreElements()) {
                JarEntry entry = jarEntrys.nextElement();
                String name = entry.getName();
                if (name.startsWith("static/Validate/codeimg") && !name.equals("static/Validate/codeimg/") && (name.endsWith(".jpg") || name.endsWith(".png"))) {
                    log.debug("targets=" + name);
                    InputStream isTemplates = jarFile.getInputStream(entry);
                    targetIS = IOUtils.toByteArray(jarFile.getInputStream(entry));
                    imgList.add(targetIS);

                } else if (name.startsWith("static/Validate/codeimgMask") && !name.equals("static/Validate/codeimgMask/")  && (name.endsWith(".jpg") || name.endsWith(".png"))) {
                    log.debug("templates=" + name);
                    InputStream isTemplates = jarFile.getInputStream(entry);
                    templateIS = IOUtils.toByteArray(jarFile.getInputStream(entry));
                    tpllist.add(templateIS);
                }
            }
        } else {
            File targetBaseFile = new File(target);
            if (null != targetBaseFile) {
                File[] fs = targetBaseFile.listFiles();
//                Random ra = new Random();
//                if (null != fs && fs.length > 0) {
//                    int random = ra.nextInt(fs.length);
//                    targetIS = IOUtils.toByteArray(new FileInputStream(fs[random]));
//                }
                for (File f : fs){
                    targetIS = IOUtils.toByteArray(new FileInputStream(f));
                    imgList.add(targetIS);
                }
            }
            /*加载切图模板*/
            String template = URLDecoder.decode(ValidateController.class.getClassLoader().getResource("../../static/Validate/codeimgMask").getFile(),"UTF-8");
            File templateBaseFile = new File(template);
            if (null != templateBaseFile) {
                File[] fs = templateBaseFile.listFiles();
//                Random ra = new Random();
//                if (null != fs && fs.length > 0) {
//                    int random = ra.nextInt(fs.length);
//                    templateIS = IOUtils.toByteArray(new FileInputStream(fs[random]));
//                }
                for (File f : fs){
                    templateIS = IOUtils.toByteArray(new FileInputStream(f));
                    tpllist.add(templateIS);
                }
            }
        }
        log.info("initValidateResources:template size:" + tpllist.size() + "target size:" + imgList.size());
    }
}
