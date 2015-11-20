package com.wzbuaa.crm.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AccountExpiredException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.service.sso.S_userService;
import com.wzbuaa.crm.util.CookieUtils;
import com.wzbuaa.crm.util.SysUtil;

import framework.spring.mvc.bind.annotation.CurrentUser;

/**
 * 后台Action类 - 首页
 */
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {
 
	public static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";// Spring security 最后登录异常Session名称
	@Resource private S_userService s_userService;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(@CurrentUser("user") S_userDomain user, HttpServletRequest request, HttpServletResponse response) {
		if(user != null){
			CookieUtils.addCookie(request, response, "username", user.getUsername());
		}
		return "/admin/common/index";
	}

	@RequestMapping(value="/middle", method=RequestMethod.GET)
	public String middle(){
		return "/admin/common/middle";
	}
	
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public String welcome(Model model) {
		S_userDomain user = s_userService.findOne(SysUtil.getUserId());
		model.addAttribute("user", user);
		return "/admin/common/welcome";
	}
}