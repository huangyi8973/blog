package com.hy.core.config;

import com.hy.core.handle.ActionHandler;
import com.hy.core.handle.ExceptionHandler;
import com.hy.core.handle.ResourceHandler;

public class JHelloConfig {

	/**
	 * 获取处理器，有序
	 * @return
	 */
	public static Class<?>[] getHandles(){
		return new Class<?>[]{
				ExceptionHandler.class,
				ActionHandler.class,
				ResourceHandler.class
		};
	}
	
	public static String getActionScanPackage(){
		return "com.hy.blog.controller";
	}
	
	public static String getAspectScanPackage(){
		return "com.hy.blog.aspect";
	}
}
