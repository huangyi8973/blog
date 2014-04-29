package com.hy.core.action;

import java.lang.reflect.Method;

import com.hy.core.pub.HttpMethod;

public class Action {

	private String _url;
	private Class<?> _controllerCls;
	private Method _method;
	private HttpMethod _httpMethod;
	private String _clsAndMethod;
	
	public Action(String url, Class<?> controllerCls, Method method,HttpMethod httpMethod,String clsAndMethod) {
		super();
		this._url = url;
		this._controllerCls = controllerCls;
		this._method = method;
		this._httpMethod = httpMethod;
		this._clsAndMethod = clsAndMethod;
	}

	
	public String getClsAndMethod() {
		return _clsAndMethod;
	}


	public void setClsAndMethod(String _clsAndMethod) {
		this._clsAndMethod = _clsAndMethod;
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
		return "Action [_url=" + _url + ", _httpMethod=" + _httpMethod
				+ ", _clsAndMethod=" + _clsAndMethod + "]";
	}


	
}
