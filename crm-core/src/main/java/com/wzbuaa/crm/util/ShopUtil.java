package com.wzbuaa.crm.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.FrontUserRealm.ShiroUser;
import org.apache.shiro.subject.Subject;

/**
 * 提供一些系统中使用到的共用方法
 * 比如获得当前用户信息
 * @author zhenglong
 */
public class ShopUtil {

	/**
	 * 获得用户
	 * @param request
	 * @return
	 */
	public static ShiroUser getUser() {
		Subject subject = SecurityUtils.getSubject();
		if(subject.getPrincipal() instanceof ShiroUser) {
			final ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
			return shiroUser;
		}
		return null;
	}
	
	/**
	 * 获得用户ID
	 * 
	 * @param request
	 * @return
	 */
	public static Long getUserId() {
		ShiroUser frontShiroUser = getUser();
		if (frontShiroUser != null) {
			return frontShiroUser.id;
		} else {
			return null;
		}
	}
}
