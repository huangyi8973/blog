package com.hy.core.viewrender;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.view.View;

public class JspViewRender extends ViewRender {

	public JspViewRender(HttpServletRequest req, HttpServletResponse resp) {
		super(req, resp);
	}

	@Override
	public void render(View view) {
		String viewPath = view.getViewPath();
		RequestDispatcher dispatcher = this.getRequest().getRequestDispatcher("/WEB-INF/view/"+viewPath);
		try {
			dispatcher.forward(this.getRequest(), this.getResponse());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
