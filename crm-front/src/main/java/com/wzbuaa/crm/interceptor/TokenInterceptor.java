package com.wzbuaa.crm.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import com.wzbuaa.crm.util.RedisCache;
import com.wzbuaa.crm.util.TokenHelper;

/**
 * 
 * @see TokenHelper
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
    
    private static Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    private Object clock = new Object();
    @Autowired private RedisCache redisCache;
    
    /**
     * 拦截方法，添加or验证token
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			TokenAnnotation tokenAnnotation = handlerMethod.getMethodAnnotation(TokenAnnotation.class);
			if(tokenAnnotation == null){
				return true;
			}
	        if(tokenAnnotation.valid() == false) {
	            TokenHelper.setToken(request);
	            return true;
	        } else {
	            log.debug("Intercepting invocation to check for valid transaction token.");
	            return handleToken(request, response, handler);
	        }
        }
        return true;
    }
    
    protected boolean handleToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        synchronized(clock) {
            if(!TokenHelper.validToken(request)) {
                return handleInvalidToken(request, response, handler);
            }
        }
        return handleValidToken(request, response, handler);
    }
    
    /**
     * 当出现一个非法令牌时调用
     */
    protected boolean handleInvalidToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String , Object> data = new HashMap<String , Object>();
        data.put("flag", 0);
        data.put("msg", "请不要频繁操作！");
        writeMessageUtf8(response, data);
        return false;
    }
    
    /**
     * 当发现一个合法令牌时调用.
     */
    protected boolean handleValidToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    
    private void writeMessageUtf8(HttpServletResponse response, Map<String , Object> json) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSONObject.toJSONString(json));
        } finally {
            response.getWriter().close();
        }
    }
    
}
