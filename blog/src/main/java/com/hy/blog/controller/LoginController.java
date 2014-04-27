package com.hy.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.annotations.At;
import com.hy.core.annotations.Controller;
import com.hy.core.modelview.ModelAndView;
import com.hy.core.pub.HttpMethod;
import com.hy.core.view.JspView;

@Controller
@At("/login")
public class LoginController {

	@At("/index")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp){
		return new ModelAndView(new JspView("index.jsp"),null);
	}
	
	@At(value = "/checkLogin", method = HttpMethod.POST)
	public boolean checkLogin(HttpServletRequest req,HttpServletResponse resp){
		return false;
	}
}
