/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.realm;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.exception.user.UserBlockedException;
import com.wzbuaa.crm.exception.user.UserException;
import com.wzbuaa.crm.exception.user.UserNotExistsException;
import com.wzbuaa.crm.exception.user.UserPasswordNotMatchException;
import com.wzbuaa.crm.exception.user.UserPasswordRetryLimitExceedException;
import com.wzbuaa.crm.repository.support.SimpleBaseRepositoryFactoryBean;
import com.wzbuaa.crm.service.crm.MemberService;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-12 下午9:05
 * <p>Version: 1.0
 */
public class FrontUserRealm extends AuthorizingRealm {

    private MemberService memberService;

    private static final Logger log = LoggerFactory.getLogger("es-error");

    @Autowired
    public FrontUserRealm(ApplicationContext ctx) {
        super();
        //不能注入 因为获取bean依赖顺序问题造成可能拿不到某些bean报错
        //why？
        //因为spring在查找findAutowireCandidates时对FactoryBean做了优化，即只获取Bean，但不会autowire属性，
        //所以如果我们的bean在依赖它的bean之前初始化，那么就得不到ObjectType（永远是Repository）
        //所以此处我们先getBean一下 就没有问题了
        ctx.getBeansOfType(SimpleBaseRepositoryFactoryBean.class);
    }

    @Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

    	UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername().trim();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        MemberDomain member = null;
        try {
        	member = memberService.login(username, password);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            log.error("login error", e);
            throw new AuthenticationException(new UserException("user.unknown.error", null));
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
        		new ShiroUser(member.getId(), member.getUsername(), member.getName(), member.getCompany()),
        		password.toCharArray(), getName());
        return info;
    }
    
    /**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	//final ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        //S_userDomain user = s_userService.findOne(shiroUser.id);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(userAuthService.findStringRoles(user));
//        authorizationInfo.setStringPermissions(userAuthService.findStringPermissions(user));

        return authorizationInfo;
    }

    private static final String OR_OPERATOR = " or ";
    private static final String AND_OPERATOR = " and ";
    private static final String NOT_OPERATOR = "not ";

    /**
     * 支持or and not 关键词  不支持and or混用
     *
     * @param principals
     * @param permission
     * @return
     */
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (permission.contains(OR_OPERATOR)) {
            String[] permissions = permission.split(OR_OPERATOR);
            for (String orPermission : permissions) {
                if (isPermittedWithNotOperator(principals, orPermission)) {
                    return true;
                }
            }
            return false;
        } else if (permission.contains(AND_OPERATOR)) {
            String[] permissions = permission.split(AND_OPERATOR);
            for (String orPermission : permissions) {
                if (!isPermittedWithNotOperator(principals, orPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            return isPermittedWithNotOperator(principals, permission);
        }
    }

    private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
        if (permission.startsWith(NOT_OPERATOR)) {
            return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
        } else {
            return super.isPermitted(principals, permission);
        }
    }

    /**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;
		public Long companyId;

		public ShiroUser(final Long id, final String loginName,
				final String name, final Long companyId) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
			this.companyId = companyId;
		}

		public Long getCompanyId() {
			return companyId;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		public Long getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this, "loginName");
		}

		/**
		 * 重载equals,只比较loginName
		 */
		@Override
		public boolean equals(final Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj, "loginName");
		}
	}
}
