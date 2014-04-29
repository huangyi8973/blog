package com.hy.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.annotations.web.At;
import com.hy.core.annotations.web.Controller;
import com.hy.core.modelview.ModelAndView;
import com.hy.core.view.JspView;
@Controller
public class HomeController {

	@At("/")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp){
		return new ModelAndView(new JspView("index.jsp"),null);
	}
	@At("/test.json")
	public List<String> test(HttpServletRequest req,HttpServletResponse resp){
		List<String> list = new ArrayList<String>();
		list.add("中文怎么样？");
		list.add("def");
		return list;
	}
}
