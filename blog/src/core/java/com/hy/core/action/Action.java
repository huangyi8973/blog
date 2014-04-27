package com.hy.core.action;

import java.lang.reflect.Method;

import com.hy.core.pub.HttpMethod;

public class Action {

	private String _url;
	private Class<?> _controllerCls;
	private Method _method;
	private HttpMethod _httpMethod;
	
	public Action(String url, Class<?> controllerCls, Method method,HttpMethod httpMethod) {
		super();
		this._url = url;
		this._controllerCls = controllerCls;
		this._method = method;
		this._httpMethod = httpMethod;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		this._url = url;
	}

	public Class<?> getControllerCls() {
		return _controllerCls;
	}

	public void setControllerCls(Class<?> controllerCls) {
		this._controllerCls = controllerCls;
	}

	public Method getMethod() {
		return _method;
	}

	public void setMethod(Method method) {
		this._method = method;
	}

	@Override
	public String toString() {
		return "Action [_url=" + _url + ", _controllerCls=" + _controllerCls
				+ ", _method=" + _method + ", _httpMethod=" + _httpMethod + "]";
	}

	
}
