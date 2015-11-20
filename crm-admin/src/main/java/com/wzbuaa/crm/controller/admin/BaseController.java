package com.wzbuaa.crm.controller.admin;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.controller.directive.FlashMessageDirective;
import com.wzbuaa.crm.domain.AbstractEntity;

import framework.DateEditor;
import framework.Message;
import framework.WebErrors;
import framework.util.AppContext;
import framework.util.Reflections;

/**
 * 基础控制器
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-23 下午3:56
 * <p>Version: 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class BaseController<M extends AbstractEntity, ID extends Serializable> {

	protected static final Message errorMessage = Message.error("message.error", new Object[0]);
	protected static final Message successMessage = Message.success("message.success", new Object[0]);
    /**
     * 实体类型
     */
    protected final Class<M> entityClass;

    private String viewPrefix;

    @InitBinder
	protected void initBinder(WebDataBinder webdatabinder) {
		webdatabinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		webdatabinder.registerCustomEditor(Date.class, new DateEditor(true));
	}
    
    @SuppressWarnings("unchecked")
	protected BaseController() {
        this.entityClass = Reflections.getClassGenricType(getClass(), 0);
        setViewPrefix(defaultViewPrefix());
    }

    /**
     * 设置通用数据
     *
     * @param model
     */
    protected void setCommonData(Model model) {
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

    protected M newModel() {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("can not instantiated model : " + this.entityClass, e);
        }
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
     * 共享的验证规则
     * 验证失败返回true
     *
     * @param m
     * @param result
     * @return
     */
    protected boolean hasError(M m, BindingResult result) {
        Assert.notNull(m);
        return result.hasErrors();
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
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if (StringUtils.isEmpty(currentViewPrefix)) {
            currentViewPrefix = this.entityClass.getSimpleName();
        }

        return currentViewPrefix;
    }
    
    protected void redirect(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null){
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
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
	
	protected WebErrors getErrors(){
		HttpServletRequest request = AppContext.getRequest();
		WebErrors errors = (WebErrors)request.getAttribute("WebErrors");
		return errors;
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
}
