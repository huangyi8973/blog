package com.hy.blog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.action.Params;
import com.hy.core.annotations.web.At;
import com.hy.core.annotations.web.Controller;
import com.hy.core.modelview.ModelAndView;
import com.hy.core.view.JspView;
@Controller
public class HomeController extends AbstractController {

	@At("/")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		return new ModelAndView(new JspView("index.jsp"),null);
	}
	@At("/test.json")
	public List<String> test(HttpServletRequest req,HttpServletResponse resp){
		List<String> list = new ArrayList<String>();
		list.add("中文怎么样？");
		list.add("def");
		return list;
	}
	
	@At("/testparams.json")
	public String testParams(Params p){
		return "ok";
	}
	
	@At("/data")
	public List<Map<String,String>> getGridData(HttpServletRequest req,HttpServletResponse resp){
		List<Map<String, String>> rs = new ArrayList<Map<String,String>>();
		for(int i=0;i<10;i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("pk_user", "pk_user_"+i);
			map.put("vusername", "userName"+i);
			rs.add(map);
		}
		return rs;
	}
}
