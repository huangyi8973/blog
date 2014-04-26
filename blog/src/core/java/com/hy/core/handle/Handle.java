package com.hy.core.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Handle {

	public abstract void handle(HttpServletRequest req,HttpServletResponse resp);
}
