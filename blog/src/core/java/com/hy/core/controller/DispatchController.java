package com.hy.core.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.action.ActionMapper;
import com.hy.core.aspect.AdviceMapper;
import com.hy.core.config.JHelloConfig;
import com.hy.core.handle.ActionHandler;
import com.hy.core.handle.Handler;
import com.hy.core.handle.ResourceHandler;
import com.hy.core.utils.Utils;

public class DispatchController extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		try {
			initHandlesMapper();
			initAspectMapper();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initAspectMapper() throws Exception {
		AdviceMapper.getInstance().init();
		
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
		Class<?>[] handleCls =JHelloConfig.getHandles();
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
	public void initHandlesMapper() throws IOException, ClassNotFoundException {
		ActionMapper.getInstance().init();
	}

}
