package com.wzbuaa.crm.controller.admin.sso;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.sso.Role;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.service.sso.PasswordService;
import com.wzbuaa.crm.service.sso.RoleService;
import com.wzbuaa.crm.service.sso.S_userService;
import com.wzbuaa.crm.util.BeanUtils;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.Pager;
import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;
import framework.util.Collections3;
import framework.util.ResponseUtils;

/**
 * 后台Action类 - 企业管理员管理
 */
@Controller
@RequestMapping("/admin/sso/manager")
public class ManagerController extends BaseCRUDController<S_userDomain, Long> {
	
	@Resource private S_userService s_userService;
	@Resource private RoleService roleService;
	@Resource public PasswordService passwordService;
	
	//获取列表
	@RequestMapping(value="/discard", method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults
	public Pager<S_userDomain> queryData(Searchable searchable) {
		throw new RuntimeException("discard method");
	}
	
	//获取列表
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults
	public Pager<S_userDomain> queryData(Searchable searchable, HttpServletResponse response) {
		searchable.addSearchFilter("isManager", SearchOperator.eq, true);
		Page<S_userDomain> page = s_userService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute("allRoles", roleService.findByIsSystem(true));
		return viewName("input");
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Message add(S_userDomain user, String repassword, Long[] roleIds, Long company_id,
			HttpServletResponse response) {
		user.setIsManager(true);
		String password = user.getPassword();
		if(StringUtils.isNotEmpty(password)){
			if(!password.equals(repassword)){
				this.addActionError("admin.user.save.passwordNotValid");
				return null;
			}
		}
		s_userService.save(user);
		return successMessage;
	}
	
	@RequestMapping(value = "{id}/update/discard", method = RequestMethod.GET)
	public String showUpdateForm(Long id, Model model) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{id}/update/discard", method = RequestMethod.POST)
    public String update(
            Model model, @Valid @ModelAttribute("user") S_userDomain user, BindingResult result,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes, 
            HttpServletRequest request, HttpServletResponse response){
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value = "{id}/update", method = RequestMethod.GET)
	public String update(@PathVariable Long id, Model model){
		S_userDomain s_user = s_userService.findOne(id);
		model.addAttribute("s_user", s_user);
		model.addAttribute("allRoles", roleService.findAll());
		return viewName("input");
	}
	
	@RequestMapping(value = "{id}/update", method = RequestMethod.POST)
	public String update(S_userDomain user, Long[] roleIds, HttpServletResponse response){
		S_userDomain persistent = s_userService.findOne(user.getId());
		if (StringUtils.isNotEmpty(user.getPassword())) {
			String passwordMd5 = passwordService.encryptPassword(persistent.getUsername(), 
					user.getPassword(), persistent.getSalt());
			user.setPassword(passwordMd5);
		}
		
		if (persistent.getIsAccountLocked() && !user.getIsAccountLocked()) {
			persistent.setLoginFailureCount(0);
			persistent.setLockedDate(null);
		}
		BeanUtils.copyNotNullProperties(user, persistent);
		s_userService.update(persistent);
		return ResponseUtils.renderJSON(successMessage, response);
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