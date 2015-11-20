package com.wzbuaa.crm.controller.admin.sso;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzbuaa.crm.controller.admin.BaseController;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.service.sso.PasswordService;
import com.wzbuaa.crm.service.sso.RoleService;
import com.wzbuaa.crm.service.sso.S_userService;
import com.wzbuaa.crm.util.BeanUtils;
import com.wzbuaa.crm.util.JsonDataHelper;
import com.wzbuaa.crm.util.SysUtil;

import framework.Message;
import framework.Pager;
import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;
import framework.util.ResponseUtils;

/**
 * 账号管理：账号管理可以跟职员表绑定，也可以单独新增，根据企业自身的操作习惯
 */
@Controller
@RequestMapping("/admin/sso/user")
public class UserController extends BaseController<S_userDomain, Long> {
	
	@Resource private S_userService s_userService;
	@Resource private RoleService roleService;
	@Resource public PasswordService passwordService;
	
	@RequestMapping(value="/list_dialog", method=RequestMethod.GET)
	public String dialog_list(){
		return viewName("list_dialog");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(){
		return viewName("list");
	}
	
	//获取列表
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults(sort = "id=desc")
	public Pager<S_userDomain> queryData(Searchable searchable, HttpServletResponse response) {
		searchable.addSearchFilter("isManager", SearchOperator.eq, false);
		searchable.addSearchFilter("admin", SearchOperator.eq, false);
		Page<S_userDomain> page = s_userService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Model model, Long worker_id){
		model.addAttribute("allRoles", roleService.findAll());
		return viewName("input");
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Message add(S_userDomain user, Long worker_id, String repassword, Long[] roleIds) {
		if (StringUtils.isNotEmpty(user.getPassword())) {
			String passwordMd5 = passwordService.encryptPassword(user.getUsername(), 
					user.getPassword(), user.getSalt());
			user.setPassword(passwordMd5);
		}
		s_userService.save(user);
		return successMessage;
	}
	
	@RequestMapping(value = "{id}/update", method = RequestMethod.GET)
	public String update(@PathVariable Long id, Model model){
		S_userDomain s_user = s_userService.findOne(id);
		model.addAttribute("s_user", s_user);
		model.addAttribute("allRoles", roleService.findAll());
		return viewName("input");
	}
	
	@RequestMapping(value = "{id}/update", method = RequestMethod.POST)
	@ResponseBody
	public Message update(S_userDomain user, Long[] roleIds){
		S_userDomain persistent = s_userService.findOne(user.getId());
 		if (StringUtils.isNotEmpty(user.getPassword())) {
			String passwordMd5 = passwordService.encryptPassword(persistent.getUsername(), 
					user.getPassword(), persistent.getSalt());
			user.setPassword(passwordMd5);
		}
		if (persistent.getIsAccountLocked() && !user.getIsAccountLocked()) {
			user.setLoginFailureCount(0);
			user.setLockedDate(null);
		}
		BeanUtils.copyNotNullProperties(user, persistent);
		s_userService.update(persistent);
		return successMessage;
	}
	
	// 更新个人资料
	@RequestMapping(value="/updateProfile", method=RequestMethod.POST)
	@ResponseBody
	public Message updateProfile(String oldPassword, String currentPassword, HttpServletResponse response) {
		S_userDomain persistent = s_userService.findOne(SysUtil.getUserId());
		
		if (StringUtils.isNotEmpty(oldPassword)) {
			String oldPasswordMd5 = passwordService.encryptPassword(persistent.getUsername(), 
					oldPassword, persistent.getSalt());
			if (!StringUtils.equals(oldPasswordMd5, persistent.getPassword())) {
				return Message.error("当前密码不正确!", null);
			}
		}
		String newPasswordMd5 = passwordService.encryptPassword(persistent.getUsername(), 
				currentPassword, persistent.getSalt());
		persistent.setPassword(newPasswordMd5);
		s_userService.update(persistent);
		return successMessage;
	}
	
	// 是否已存在 ajax验证
	@RequestMapping(value="/checkUsername", method=RequestMethod.POST)
	@ResponseBody
	public String checkUsername(String username, String oldValue, HttpServletResponse response) {
		if (s_userService.isUnique("username", oldValue, username)) {
			return ResponseUtils.renderJSON("true", response);
		} else {
			return ResponseUtils.renderJSON("false", response);
		}
	}
	
	@RequestMapping(value="/addRole", method=RequestMethod.GET)
	public String addRole(){
		return viewName("addRole");
	}
}