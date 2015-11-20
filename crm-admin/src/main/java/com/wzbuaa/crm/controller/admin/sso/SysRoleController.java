package com.wzbuaa.crm.controller.admin.sso;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.sso.Role;
import com.wzbuaa.crm.service.sso.RoleService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Pager;
import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;

/**
 * 后台Action类 - 菜单
 */
@Controller
@RequestMapping("/admin/sso/sysrole")
public class SysRoleController extends BaseCRUDController<Role, Long> {

	@Resource private RoleService roleService;
	
	//获取列表
	@RequestMapping(value="/discard", method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults
	public Pager<Role> queryData(Searchable searchable) {
		throw new RuntimeException("discard method");
	}
	
	//获取列表
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults
	public Pager<Role> queryData(Searchable searchable, HttpServletResponse response) {
		searchable.addSearchFilter("isSystem", SearchOperator.eq, true);
		Page<Role> page = roleService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
	}

}