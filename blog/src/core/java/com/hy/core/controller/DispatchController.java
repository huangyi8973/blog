package com.hy.core.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.action.Action;
import com.hy.core.action.ActionFactory;
import com.hy.core.action.ActionMapper;
import com.hy.core.view.View;
import com.hy.core.viewrender.JspViewRender;

public class DispatchController extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		try {
			initMapper();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String url = req.getRequestURI().substring(req.getContextPath().length());
		//执行controller
		try {
			execute(url,req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void execute(String url,HttpServletRequest req, HttpServletResponse resp) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, ServletException, IOException {
		Action action = ActionFactory.getInstance().getAction(url);
		System.out.println(String.format("url:%s",url));
		System.out.println(String.format("获得url映射:%s",action));
		if(action != null){
			Object controller = action.getControllerCls().newInstance();
			Method method = action.getMethod();
			Object result = method.invoke(controller, req,resp);
			if(result != null){
				if(result instanceof View){
					//返回视图
					JspViewRender jsp = new JspViewRender(req, resp);
					jsp.render((View) result);
				}
			}
		}else{
			resp.sendError(404);
		}
	}

	/**
	 * 初始化controller的扫描
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void initMapper() throws IOException, ClassNotFoundException {
		ActionMapper.getInstance().init();
	}
	
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		DispatchController controller = new DispatchController();
		controller.initMapper();
	}
}
