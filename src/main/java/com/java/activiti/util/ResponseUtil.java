package com.java.activiti.util;

import org.springframework.stereotype.Component;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

@Component
public class ResponseUtil {

	public static void write(HttpServletResponse response,Object o)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}

	/**
	 * ����һ�� Response ����
	 * @return response ����
	 */
	public Response newResponseInstance(){
		//�½�HashedMap����
		Response response = new Response();

		return response;
	}
}
