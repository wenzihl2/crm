package com.wzbuaa.crm.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

public class AccessDeniedFilter implements Filter {

	private static final String AccessDenied = "Access denied!";

	public void init(FilterConfig filterconfig) {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		try {
			response.sendError(403, AccessDenied);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
