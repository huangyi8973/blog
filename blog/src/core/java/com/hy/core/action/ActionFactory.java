package com.hy.core.action;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ActionFactory {

	private static ActionFactory _instance;
	private static Object _lock = new Object();
	private ActionFactory(){}
	
	public static ActionFactory getInstance(){
		if(_instance == null){
			synchronized (_lock) {
				if(_instance == null){
					_instance = new ActionFactory();
				}
			}
		}
		return _instance;
	}
	
	public Action getAction(String url) throws ClassNotFoundException,NoSuchMethodException, SecurityException{
		String actionInfoStr = ActionMapper.getInstance().getActionInfoStr(url);
		if(actionInfoStr != null){
			String[] rs = actionInfoStr.split("#");
			Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass(rs[0]);
			Method method = cls.getMethod(rs[1], HttpServletRequest.class,HttpServletResponse.class);
			return new Action(url, cls, method);
		}
		return null;
	}
	
}
