package com.wzbuaa.crm.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wzbuaa.crm.bean.Setting;
import com.wzbuaa.crm.util.SettingUtils;

@Component
public class SiteStatusFilter extends OncePerRequestFilter {

	private static AntPathMatcher antPathMatcher = new AntPathMatcher();
	private String[] ignoreUrlPatterns = {"/admin/**"};
	private String redirectUrl = "/common/site_close.jhtml";

	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain) {
		Setting setting = SettingUtils.get();
		if (setting.getIsSiteEnabled()) {
			try {
				filterChain.doFilter(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		} else {
			String path = request.getServletPath();
			if (path.equals(redirectUrl)) {
				try {
					filterChain.doFilter(request, response);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ServletException e) {
					e.printStackTrace();
				}
				return;
			}
			if (ignoreUrlPatterns != null) {
				for (int i = 0; i < ignoreUrlPatterns.length; i++) {
					String ignoreUrlPattern = ignoreUrlPatterns[i];
					if (antPathMatcher.match(ignoreUrlPattern, path)) {
						try {
							filterChain.doFilter(request, response);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ServletException e) {
							e.printStackTrace();
						}
						return;
					}
				}
			}
			try {
				response.sendRedirect(request.getContextPath() + redirectUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
