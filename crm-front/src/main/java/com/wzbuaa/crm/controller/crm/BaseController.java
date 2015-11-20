package com.wzbuaa.crm.controller.crm;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.bean.Setting;
import com.wzbuaa.crm.controller.directive.FlashMessageDirective;
import com.wzbuaa.crm.exception.ResourceNotFoundException;
import com.wzbuaa.crm.util.SettingUtils;

import framework.Message;
import framework.WebErrors;
import framework.util.AppContext;
import framework.util.RequestUtils;
import framework.util.ResponseUtils;

public class BaseController {

	protected static final String FRONT_ERROR_PAGE = "/error";
	protected static final String MEMBER_SUCCESS = "redirect:/member/success.jhtml";
	protected static final Message successMessage = Message.success("shop.message.success", new Object[0]);
	protected static final Message errorMessage = Message.error("shop.message.error", new Object[0]);
	private String viewPrefix;
	
	protected BaseController() {
        setViewPrefix(defaultViewPrefix());
    }
	
	/**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     *
     * @return
     */
    public String viewName(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        return getViewPrefix() + suffixName;
    }
	
	/**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型小写的简单类名
     */
    public void setViewPrefix(String viewPrefix) {
        if (viewPrefix.startsWith("/")) {
            viewPrefix = viewPrefix.substring(1);
        }
        this.viewPrefix = viewPrefix;
    }
	
	public String getViewPrefix() {
        return viewPrefix;
    }
	
	/**
     * @param backURL null 将重定向到默认getViewPrefix()
     * @return
     */
    protected String redirectToUrl(String backURL) {
        if (StringUtils.isEmpty(backURL)) {
            backURL = getViewPrefix();
        }
        if (!backURL.startsWith("/") && !backURL.startsWith("http")) {
            backURL = "/" + backURL;
        }
        return "redirect:" + backURL;
    }
	
	protected String defaultViewPrefix() {
		Setting setting = SettingUtils.get();
        String shopTheme = setting.getShopTheme();
        String currentViewPrefix = shopTheme;
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix += requestMapping.value()[0];
        }
        return currentViewPrefix;
    }
    
    protected void redirect(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null){
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	/**
	 * 清除缓存
	 */
	protected void clearCache(HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache"); 
		response.setHeader("Cache-Control", "no-store");  
		response.setDateHeader("Expires", 0);
	}
	
	protected void addActionError(String message){
		HttpServletRequest request = AppContext.getRequest();
		WebErrors errors = (WebErrors)request.getAttribute("WebErrors");
		if(errors == null){
			errors = WebErrors.create();
			request.setAttribute("WebErrors", errors);
		}
		errors.addErrorString(message);
	}

	protected void addActionError(String code, Object[] objects){
		HttpServletRequest request = AppContext.getRequest();
		WebErrors errors = (WebErrors)request.getAttribute("WebErrors");
		if(errors == null){
			errors = WebErrors.create();
			request.setAttribute("WebErrors", errors);
		}
		errors.addErrorCode(code, objects);
	}
	
	@ExceptionHandler
	public String exceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException{
		if(ex.getClass() == ResourceNotFoundException.class){
			if (RequestUtils.isAjaxRequest(request)) {
				return ResponseUtils.renderJSON(request.getRequestURI() + " is not found", response);
			}
			response.sendRedirect(AppContext.getContextPath() + "/html/error_page_404.jsp");
			return null;
		} else {
			if (RequestUtils.isAjaxRequest(request)) {
				return ResponseUtils.renderJSON(Message.error(ex.getMessage(), null), response);
			}
			ex.printStackTrace();
			response.sendRedirect(AppContext.getContextPath() + "/html/error_page_403.jsp");
			return null;
		}
	}
}
