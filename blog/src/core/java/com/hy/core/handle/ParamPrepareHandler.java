package com.hy.core.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParamPrepareHandler extends Handler {

	public ParamPrepareHandler(HttpServletRequest req,
			HttpServletResponse resp) {
		super(req, resp);
	}

	@Override
	public void handle() throws Exception {
		String ctx = String.format("%s://%s:%d%s", this.getRequest()
				.getScheme(), this.getRequest().getServerName(), this
				.getRequest().getServerPort(), this.getRequest()
				.getContextPath());
		this.getRequest().setAttribute("ctx", ctx);
		this.nextHandle();
	}
}
