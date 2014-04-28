package com.hy.core.config;

import com.hy.core.handle.ActionHandler;
import com.hy.core.handle.ExceptionHandler;
import com.hy.core.handle.ResourceHandler;

public class JHelloConfig {

	public static Class<?>[] getHandles(){
		return new Class<?>[]{
				ExceptionHandler.class,
				ActionHandler.class,
				ResourceHandler.class
		};
	}
}
