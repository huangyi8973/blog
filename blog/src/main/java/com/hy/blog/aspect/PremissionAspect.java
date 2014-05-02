package com.hy.blog.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hy.core.annotations.aspect.Aspect;
import com.hy.core.aspect.AbstractAdvice;
import com.hy.core.aspect.Pointcut;

@Aspect(joincutExpression = "com.hy.blog.controller..*#test")
public class PremissionAspect extends AbstractAdvice {

	private final static Logger logger = LoggerFactory.getLogger(PremissionAspect.class);

	@Override
	public void before(Pointcut pointcut, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		logger.debug("------------checkPremission,go to /");
		resp.sendRedirect(req.getServletContext().getContextPath() + "/login/");
		
	}

	@Override
	public void after(Pointcut pointcut, HttpServletRequest req,
			HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
}
