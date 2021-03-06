package org.apache.shiro.web.filter.user;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.realm.FrontUserRealm.ShiroUser;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.domain.sso.user.UserStatus;
import com.wzbuaa.crm.service.crm.MemberService;

/**
 * 验证用户过滤器
 * 1、用户是否删除
 * 2、用户是否锁定
 */
public class FrontUserFilter extends AccessControlFilter {

    @Autowired private MemberService memberService;

    /**
     * 用户删除了后重定向的地址
     */
    private String userNotfoundUrl;
    /**
     * 用户锁定后重定向的地址
     */
    private String userBlockedUrl;
    /**
     * 未知错误
     */
    private String userUnknownErrorUrl;

    public String getUserNotfoundUrl() {
        return userNotfoundUrl;
    }

    public void setUserNotfoundUrl(String userNotfoundUrl) {
        this.userNotfoundUrl = userNotfoundUrl;
    }

    public String getUserBlockedUrl() {
        return userBlockedUrl;
    }

    public void setUserBlockedUrl(String userBlockedUrl) {
        this.userBlockedUrl = userBlockedUrl;
    }

    public String getUserUnknownErrorUrl() {
        return userUnknownErrorUrl;
    }

    public void setUserUnknownErrorUrl(String userUnknownErrorUrl) {
        this.userUnknownErrorUrl = userUnknownErrorUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject == null) {
            return true;
        }

        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        if(shiroUser != null) {
        	//此处注意缓存 防止大量的查询db
            MemberDomain member = memberService.findOne(shiroUser.id);
            //把当前用户放到session中
            request.setAttribute(Constants.CURRENT_FRONT_USER, member);
            //druid监控需要
            ((HttpServletRequest)request).getSession().setAttribute(Constants.CURRENT_FRONT_USERNAME, member.getUsername());
        }
        return true;
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	MemberDomain member = (MemberDomain) request.getAttribute(Constants.CURRENT_FRONT_USER);
        if (member == null) {
            return true;
        }

        if (Boolean.FALSE.equals(member.getIsAccountLocked()) || member.getStatus() == UserStatus.blocked) {
            getSubject(request, response).logout();
            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        getSubject(request, response).logout();
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
    	MemberDomain user = (MemberDomain) request.getAttribute(Constants.CURRENT_FRONT_USER);
        String url = null;
        if (Boolean.TRUE.equals(user.getIsAccountLocked())) {
            url = getUserNotfoundUrl();
        } else if (user.getStatus() == UserStatus.blocked) {
            url = getUserBlockedUrl();
        } else {
            url = getUserUnknownErrorUrl();
        }

        WebUtils.issueRedirect(request, response, url);
    }

}
