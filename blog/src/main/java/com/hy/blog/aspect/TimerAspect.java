package com.hy.blog.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.core.annotations.aspect.Aspect;
import com.hy.core.aspect.AbstractAdvice;
import com.hy.core.aspect.Pointcut;

@Aspect
public class TimerAspect extends AbstractAdvice {

	private long start;
	
	@Override
	public void before(Pointcut pointcut, HttpServletRequest req,
			HttpServletResponse resp) {
		start = System.currentTimeMillis();
		System.out.println("start : "+start);
	}

	@Override
	public void after(Pointcut pointcut, HttpServletRequest req,
			HttpServletResponse resp) {
		System.out.println("end : "+ System.currentTimeMillis());
		System.out.println(String.format("%s#%s, spend %dms", pointcut.getTargetControllerName(),pointcut.getTargetMethodName(),System.currentTimeMillis() - start));
	}

	
}
