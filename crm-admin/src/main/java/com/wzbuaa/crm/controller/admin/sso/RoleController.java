package com.wzbuaa.crm.controller.admin.sso;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.sso.Role;
import com.wzbuaa.crm.domain.sso.RoleResourcePermission;
import com.wzbuaa.crm.domain.sso.user.S_userDomain;
import com.wzbuaa.crm.service.sso.RoleService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Pager;
import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.CurrentUser;
import framework.spring.mvc.bind.annotation.PageableDefaults;
import framework.validation.annotation.RequiredFieldValidator;
import framework.validation.annotation.Validations;

/**
 * 后台Action类 - 菜单
 */
@Controller
@RequestMapping("/admin/sso/role")
public class RoleController extends BaseCRUDController<Role, Long> {

	@Resource private RoleService roleService;
	
	public RoleController() {
        setResourceIdentity("sys:role");
    }
	
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
	public Pager<Role> queryData(@CurrentUser S_userDomain user, Searchable searchable, HttpServletResponse response) {
		searchable.addSearchFilter("isSystem", SearchOperator.eq, false);
		//searchable.addSearchFilter("company", SearchOperator.eq, user.getCompany());
		Page<Role> page = roleService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
	}
	
	// 获取模块树
	@RequestMapping(value="/roleTree", method=RequestMethod.POST)
	@ResponseBody
	public String roleTree(HttpServletRequest request,
			@CurrentUser(value="currUser") S_userDomain currUser,
            @RequestParam(value = "searchName", required = false) String searchName,
            @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
            @RequestParam(value = "type", required = false) String type,
            Searchable searchable, Model model){
//		List<Long> roleIds = null;
//		if(!user.getAdmin()){
//			roleIds = user.getRoleListIds();
//		}
//		List<Menu> menus = menuService.getMenuTree(roleIds);
//		List<Operate> operates = roleService.getOperate(roleIds, null, null, OPERATE_TYPE_AUTHORITY);
//		
//		Menu root = new Menu();
//		root.setId(0L);
//		root.setName("权限树");
//		
//		List<ZTree<Long>> trees = new ArrayList<ZTree<Long>>();
//		trees.add(root.toZTree());
//		if(menus != null || operates != null){
//			for (int i = 0; i < menus.size(); i++) {
//				ZTree<Long> jObj = menus.get(i).toZTree();
//				jObj.setNocheck(false);
//				jObj.setOpen(true);
//				trees.add(jObj);
//			}
//			
//			for (int i = 0; i < operates.size(); i++) {
//				ZTree<Long> jObj = operates.get(i).toZTree();
//				trees.add(jObj);
//			}
//		}
//		return trees;
		return null;
	}

	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "id", message = "id非法!")
		}
	)
	@RequestMapping("/priv")
	@ResponseBody
	public JSONObject priv(Long id){
//		List<Long> roleIds = new ArrayList<Long>();
//		roleIds.add(id);
//		List<Menu> menuList = menuService.getMenuTree(roleIds);
//		List<Operate> operateList = roleService.getOperate(roleIds, null, null, OPERATE_TYPE_AUTHORITY);
//		
//		List<Long> menuIds = Reflections.mapListProperties(menuList, "id", Long.class);
//		List<Long> operateIds = Reflections.mapListProperties(operateList, "id", Long.class);
//		JSONObject jobj = new JSONObject();
//		jobj.put("menuIds", menuIds);
//		jobj.put("operateIds", operateIds);
//		return jobj;
		return null;
	}
	

	private void fillResourcePermission(Role role, Long[] resourceIds, Long[][] permissionIds) {
        int resourceLength = resourceIds.length;
        if (resourceIds.length == 0) {
            return;
        }

        if (resourceLength == 1) { //如果长度为1  那么permissionIds就变成如[[0],[1],[2]]这种
            Set<Long> permissionIdSet = Sets.newHashSet();
            for (Long[] permissionId : permissionIds) {
                permissionIdSet.add(permissionId[0]);
            }
            role.addResourcePermission(
                    new RoleResourcePermission(resourceIds[0], permissionIdSet)
            );

        } else {
            for (int i = 0; i < resourceLength; i++) {
                role.addResourcePermission(
                        new RoleResourcePermission(resourceIds[i], Sets.newHashSet(permissionIds[i]))
                );
            }
        }

    }

}