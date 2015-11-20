package com.wzbuaa.crm.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.UserRealm.ShiroUser;
import org.apache.shiro.subject.Subject;

import com.wzbuaa.crm.domain.sso.user.S_userDomain;

/**
 * 提供一些系统中使用到的共用方法
 * 比如获得当前用户信息
 * @author zhenglong
 */
public class SysUtil {
	
	/**
	 * 获得用户
	 * @param request
	 * @return
	 */
	public static Long getCurrentCompany() {
		Subject subject = SecurityUtils.getSubject();
		if(subject.getPrincipal() instanceof ShiroUser) {
			return SysUtil.getUser().companyId;
		} else if(subject.getPrincipal() instanceof org.apache.shiro.realm.FrontUserRealm.ShiroUser){
			return ShopUtil.getUser().companyId;
		}
		return null;
	}
	
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
		ShiroUser shiroUser = getUser();
		if (shiroUser != null) {
			return shiroUser.id;
		} else {
			return null;
		}
	}
	
	//系统管理员
	public static boolean isAdmin() {
		if(getUser() != null && getUser().companyId == null) {
			return true;
		}
		return false;
	}
	
	//企业管理员
	public static boolean isManager(S_userDomain user) {
		if(user.getIsManager() != null && user.getIsManager()) {
			return true;
		}
		return false;
	}
}
