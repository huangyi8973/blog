package com.hy.core.handle;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hy.core.action.Action;
import com.hy.core.action.ActionFactory;
import com.hy.core.model.Model;
import com.hy.core.modelview.ModelAndView;
import com.hy.core.view.JsonView;
import com.hy.core.viewrender.JsonViewRender;
import com.hy.core.viewrender.ViewRender;
import com.hy.core.viewrender.ViewRenderFactory;

/**
 * Action处理器
 * @author Huangyi
 *
 */
public class ActionHandler extends Handler {

	private final static Logger logger = LoggerFactory.getLogger(ActionHandler.class);
	
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
		logger.debug("============ActionHandle");
		this.setHeader();
		
		String url = this.getRequest().getRequestURI().substring(this.getRequest().getContextPath().length());
		String httpMethod = this.getRequest().getMethod();
		Action action = ActionFactory.getInstance().getAction(url,httpMethod);
		
		logger.debug(String.format("url:%s",url));
		logger.debug(String.format("获得url映射:%s",action));
		
		if(action != null){
			Object controller = action.getControllerCls().newInstance();
			Method method = action.getMethod();
			Object result = method.invoke(controller, this.getRequest(),this.getResponse());
			if(result != null){
				if(result instanceof ModelAndView){
					//返回视图
					ModelAndView mv = (ModelAndView) result;
					ViewRender render = ViewRenderFactory.getInstance().createViewRender(mv.getView());
					render.setRequest(this.getRequest());
					render.setResponse(this.getResponse());
					render.render(mv.getView(),mv.getModel());
				}else{
					//不是返回ModelAndView的，全部看成是返回json
					ViewRender render = new JsonViewRender(this.getRequest(),this.getResponse());
					Model model = new Model();
					model.put(JsonView.JSON_KEY, result);
					render.render(null, model);
				}
			}
		}else{
			this.nextHandle();
		}
	}


}
