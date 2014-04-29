package com.hy.blog.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.annotations.aspect.Aspect;
import com.hy.core.aspect.Pointcut;

@Aspect
public class TimerAspect {

	
	public void timer(Pointcut pointcut,HttpServletRequest req, HttpServletResponse resp){
		
	}
}
