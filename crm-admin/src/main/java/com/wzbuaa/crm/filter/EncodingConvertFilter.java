package com.wzbuaa.crm.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class EncodingConvertFilter extends OncePerRequestFilter {

	private String fromEncoding;
	private String toEncoding;

	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain){
		if (request.getMethod().equalsIgnoreCase("GET")) {
			for (Iterator<String[]> iterator = request.getParameterMap().values().iterator(); iterator.hasNext();) {
				String as[] = (String[])iterator.next();
				for (int i = 0; i < as.length; i++){
					try {
						as[i] = new String(as[i].getBytes(fromEncoding), toEncoding);
					} catch (UnsupportedEncodingException unsupportedencodingexception) {
						unsupportedencodingexception.printStackTrace();
					}
				}
			}
		}
		try {
			filterChain.doFilter(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public String getFromEncoding() {
		return fromEncoding;
	}

	public void setFromEncoding(String fromEncoding) {
		this.fromEncoding = fromEncoding;
	}

	public String getToEncoding() {
		return toEncoding;
	}

	public void setToEncoding(String toEncoding) {
		this.toEncoding = toEncoding;
	}
}
