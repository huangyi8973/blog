package com.hy.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hy.core.annotations.web.At;

public abstract class AbstractController {
	
	private Logger logger  = LoggerFactory.getLogger(AbstractController.class);
	
	@At("/save")
	public Map<String,String> save(HttpServletRequest req,HttpServletResponse resp){
		Map<String,String> result = new HashMap<String, String>();
		logger.debug("do save");
		return result;
	}
}
