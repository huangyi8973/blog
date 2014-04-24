package com.hy.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.annotations.At;
import com.hy.core.annotations.Controller;
import com.hy.core.view.View;
@Controller
public class HomeController {

	@At("/index")
	public View index(HttpServletRequest req, HttpServletResponse resp){
		return new View("index.jsp");
	}
}
