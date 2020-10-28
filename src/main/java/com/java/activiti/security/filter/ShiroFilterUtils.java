package com.java.activiti.security.filter;

import com.java.activiti.util.LoggerUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Map;

public class ShiroFilterUtils {

    final static Class<? extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class;
    //��¼ҳ��
    static final String LOGIN_URL = "/userLogin";
    //�߳���¼��ʾ
    final static String KICKED_OUT = "/open/kickedOut.shtml";
    //û��Ȩ������
    final static String UNAUTHORIZED = "/open/unauthorized.shtml";
    /**
     * �Ƿ���Ajax����
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request){
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    /**
     * response ���JSON
     * @param resultMap
     */
    public static void out(ServletResponse response, Map<String, String> resultMap){

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println(JSONObject.fromObject(resultMap).toString());
        } catch (Exception e) {
            LoggerUtils.fmtError(CLAZZ, e, "���JSON����");
        }finally{
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }
}
