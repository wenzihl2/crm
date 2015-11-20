package com.wzbuaa.crm.exception.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wzbuaa.crm.exception.ResourceNotFoundException;

import framework.WebErrors;
import framework.util.AppContext;
import framework.util.RequestUtils;
import framework.util.ResponseUtils;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-7 下午3:38
 * <p>Version: 1.0
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 没有权限 异常
     * <p/>
     * 后续根据不同的需求定制即可
     * @throws IOException 
     */
	@ExceptionHandler
    public String exceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
		if(ex.getClass() == ResourceNotFoundException.class){
			if (RequestUtils.isAjaxRequest(request)) {
				return ResponseUtils.renderJSON(request.getRequestURI() + " is not found", response);
			}
			response.sendRedirect(AppContext.getContextPath() + "/html/error_page_404.jsp");
			return null;
		}else if(ex.getClass() != ApplicationException.class) {
			ex.printStackTrace();
		}
		
		String message = ex.getMessage();
		if(message == null) {
			message = "系统错误";
		}
		WebErrors errors = WebErrors.create();
		errors.addError(message);
		if (RequestUtils.isAjaxRequest(request)) {
			return errors.showErrorAjax(response);
		} else {
			return errors.showErrorPage(request);
		}
    }
}
