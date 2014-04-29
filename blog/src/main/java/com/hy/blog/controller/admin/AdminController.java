package com.hy.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.annotations.web.At;
import com.hy.core.annotations.web.Controller;
import com.hy.core.model.Model;
import com.hy.core.modelview.ModelAndView;
import com.hy.core.view.JspView;

@Controller
public class AdminController {

	@At("/admin/index")
	public ModelAndView index(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mv = new ModelAndView();
		mv.setView(new  JspView("admin/index.jsp"));
		Model model = new Model();
		model.put("user", "jimmy");
		mv.setModel(model);
		return mv;
	}
}
