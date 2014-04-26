package com.hy.core.handle;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.action.Action;
import com.hy.core.action.ActionFactory;
import com.hy.core.view.View;
import com.hy.core.viewrender.JspViewRender;

/**
 * Action处理器
 * @author Huangyi
 *
 */
public class ActionHandler extends Handler {

	public ActionHandler(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	
	/**
	 * 设置响应头
	 */
	private void setHeader(){
		this.getResponse().setCharacterEncoding("UTF-8");//设置响应流的编码方式
		this.getResponse().setHeader("ContentType", "text/html;charset=UTF-8");//设置浏览器的编码方式
	}

	@Override
	public void handle() throws Exception {
		System.out.println("============ActionHandle");
		this.setHeader();
		
		String url = this.getRequest().getRequestURI().substring(this.getRequest().getContextPath().length());
		Action action = ActionFactory.getInstance().getAction(url);
		
		System.out.println(String.format("url:%s",url));
		System.out.println(String.format("获得url映射:%s",action));
		
		if(action != null){
			Object controller = action.getControllerCls().newInstance();
			Method method = action.getMethod();
			Object result = method.invoke(controller, this.getRequest(),this.getResponse());
			if(result != null){
				if(result instanceof View){
					//返回视图
					JspViewRender jsp = new JspViewRender(this.getRequest(), this.getResponse());
					jsp.render((View) result);
				}
			}
		}else{
			this.nextHandle();
		}
	}


}
