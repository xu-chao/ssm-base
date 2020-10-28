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
	 * 生成一个 Response 对象
	 * @return response 对象
	 */
	public Response newResponseInstance(){
		//新建HashedMap对象
		Response response = new Response();

		return response;
	}
}
