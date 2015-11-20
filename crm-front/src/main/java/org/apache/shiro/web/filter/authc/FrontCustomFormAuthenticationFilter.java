/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.web.filter.authc;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.realm.FrontUserRealm.ShiroUser;
import org.springframework.beans.factory.annotation.Autowired;

import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.service.crm.MemberService;

/**
 * 基于几点修改：
 * 1、onLoginFailure 时 把异常添加到request attribute中 而不是异常类名
 * 2、登录成功时：成功页面重定向：
 * 2.1、如果前一个页面是登录页面，-->2.3
 * 2.2、如果有SavedRequest 则返回到SavedRequest
 * 2.3、否则根据当前登录的用户决定返回到管理员首页/前台首页
 * <p/>
 * <p>User: zhenglong
 * <p>Date: 13-3-19 下午2:11
 * <p>Version: 1.0
 */
public class FrontCustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Autowired MemberService memberService;

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    /**
     * 默认的成功地址
     */
    private String defaultSuccessUrl;
    /**
     * 管理员默认的成功地址
     */
    private String frontDefaultSuccessUrl;

    public void setDefaultSuccessUrl(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }

    public String getDefaultSuccessUrl() {
        return defaultSuccessUrl;
    }

    public String getFrontDefaultSuccessUrl() {
		return frontDefaultSuccessUrl;
	}

	public void setFrontDefaultSuccessUrl(String frontDefaultSuccessUrl) {
		this.frontDefaultSuccessUrl = frontDefaultSuccessUrl;
	}

	/**
     * 根据用户选择成功地址
     *
     * @return
     */
    @Override
    public String getSuccessUrl() {
    	ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        MemberDomain user = memberService.findByUsername(shiroUser.loginName);
        if (user != null) {
            return getDefaultSuccessUrl();
        }
        return getFrontDefaultSuccessUrl();
    }
}
