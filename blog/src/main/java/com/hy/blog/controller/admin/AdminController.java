package com.hy.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.annotations.At;
import com.hy.core.annotations.Controller;
import com.hy.core.view.View;

@Controller
public class AdminController {

	@At("/admin/index")
	public View index(HttpServletRequest req,HttpServletResponse resp){
		req.setAttribute("user", "jimmy");
		return new View("admin/index.jsp");
	}
}
