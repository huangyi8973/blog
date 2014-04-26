package com.hy.core.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.action.Action;
import com.hy.core.action.ActionFactory;
import com.hy.core.action.ActionMapper;
import com.hy.core.handle.ActionHandler;
import com.hy.core.handle.Handler;
import com.hy.core.handle.ResourceHandler;
import com.hy.core.utils.Utils;
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
	
	private Class<?>[] getHandle(){
		return new Class<?>[]{
				ActionHandler.class,
				ResourceHandler.class
		};
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			try {
				Handler handle = prepareHandleChain(req,resp);
				handle.handle();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * 准备处理器链
	 * @param req
	 * @param resp
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private Handler prepareHandleChain(HttpServletRequest req, HttpServletResponse resp) 
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?>[] handleCls = getHandle();
		Handler firstHandle = null;
		if(!Utils.isEmplyOrNull(handleCls)){
			Handler lastHandle = null;
			//把所有处理器都链接起来
			for(Class<?> cls : handleCls){
				// 初始化handle类
				Constructor<?> constructor =  cls.getConstructor(HttpServletRequest.class, HttpServletResponse.class);
				constructor.setAccessible(true);
				Handler handle = (Handler) constructor.newInstance(req,resp);
				
				if(firstHandle == null){
					firstHandle = handle;
				}
				if(lastHandle == null){
					lastHandle = handle;
				}else{
					lastHandle.setNextHandle(handle);
					lastHandle = handle;
				}
			}
			
			return firstHandle;
		}
		return null;
	}


	/**
	 * 初始化controller的扫描
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void initMapper() throws IOException, ClassNotFoundException {
		ActionMapper.getInstance().init();
	}

}
