package com.hy.core.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionHandle extends Handle {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	
	private void setHeader(HttpServletResponse resp){
		resp.setCharacterEncoding("UTF-8");//设置响应流的编码方式
		resp.setHeader("ContentType", "text/html;charset=UTF-8");//设置浏览器的编码方式
	}
}
