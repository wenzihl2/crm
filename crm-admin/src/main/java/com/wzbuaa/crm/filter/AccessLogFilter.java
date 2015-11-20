package com.wzbuaa.crm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.wzbuaa.crm.util.LogUtils;

/**
 * 记录访问日志
 */
public class AccessLogFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public final void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		LogUtils.logAccess(httpRequest);
        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}


}
