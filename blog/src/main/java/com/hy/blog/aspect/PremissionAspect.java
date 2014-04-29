package com.hy.blog.aspect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hy.core.annotations.aspect.Aspect;
import com.hy.core.annotations.aspect.BeforeAdvice;
import com.hy.core.aspect.Pointcut;

@Aspect
public class PremissionAspect {

	private final static Logger logger = LoggerFactory.getLogger(PremissionAspect.class);
	
	@BeforeAdvice("com.hy.blog.controller..*#test")
	public void checkPremission(Pointcut pointcut,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		logger.debug("------------checkPremission,go to /");
		resp.sendRedirect(req.getServletContext().getContextPath() + "/login/");
	}
}
