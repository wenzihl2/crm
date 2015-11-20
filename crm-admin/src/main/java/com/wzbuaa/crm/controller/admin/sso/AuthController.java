package com.wzbuaa.crm.controller.admin.sso;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.sso.AuthType;
import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.sso.Auth;
import com.wzbuaa.crm.service.sso.AuthService;
import com.wzbuaa.crm.service.sso.RoleService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.Pager;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;
import framework.spring.mvc.bind.annotation.SearchableDefaults;
import framework.util.RequestUtils;
import framework.util.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sso/auth")
public class AuthController extends BaseCRUDController<Auth, Long> {

    @Autowired private RoleService roleService;

    public AuthController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("sys:auth");
    }

    private AuthService getAuthService() {
        return (AuthService) baseService;
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("types", AuthType.values());

        Searchable searchable = Searchable.newSearchable();
//        searchable.addSearchFilter("show", SearchOperator.eq, true);
        List<Map<String, String>> jmap = JsonDataHelper.parse("[{name: 'id'}, {name: 'name'}]", roleService.findAllWithNoPageNoSort(searchable));
        model.addAttribute("roles", JSON.toJSONString(jmap));
    }
    
    @Override
    @RequestMapping(value = "discarded", method = RequestMethod.GET)
    public Pager<Auth> queryData(Searchable searchable) {
    	throw new RuntimeException("discard method");
    }
    
    //获取列表
  	@RequestMapping(method = RequestMethod.POST)
  	@ResponseBody
  	@PageableDefaults(sort = "id=desc")
  	@SearchableDefaults(value = "type_eq=user")
  	public Pager<Auth> queryData(Searchable searchable, Model model) {
  		String typeName = String.valueOf(searchable.getValue("type_eq"));
        model.addAttribute("type", AuthType.valueOf(typeName));
  		Page<Auth> page= baseService.findAll(searchable);
  		return JsonDataHelper.createJSONData(page);
  	}

    
    @Override
    @SearchableDefaults(value = "type_eq=user")
    public String list(Searchable searchable, Model model) {
    	String typeName = String.valueOf(searchable.getValue("type_eq"));
        model.addAttribute("type", AuthType.valueOf(typeName));
        return super.list(searchable, model);
    }

    @RequestMapping(value = "create/discarded", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        throw new RuntimeException("discard method");
    }

    @Override
    @RequestMapping(value = "create/discarded", method = RequestMethod.POST)
    public String create(Model model, Auth m, BindingResult result,
    		RedirectAttributes redirectAttributes, HttpServletRequest request,
    		HttpServletResponse response) {
    	throw new RuntimeException("discard method");
    }

    @RequestMapping(value = "{type}/create", method = RequestMethod.GET)
    public String showCreateFormWithType(@PathVariable("type") AuthType type, Model model) {
        model.addAttribute("type", type);
        return super.showCreateForm(model);
    }


    @RequestMapping(value = "{type}/create", method = RequestMethod.POST)
    public String createWithType(
            Model model,
            @RequestParam(value = "userIds", required = false) Long[] userIds,
            @RequestParam(value = "groupIds", required = false) Long[] groupIds,
            @RequestParam(value = "deptIds", required = false) Long[] deptIds,
            @RequestParam(value = "jobIds", required = false) Long[][] jobIds,
            @Valid @ModelAttribute("m") Auth m, BindingResult result,
            RedirectAttributes redirectAttributes, HttpServletRequest request,
    		HttpServletResponse response) {

        this.permissionList.assertHasCreatePermission();

        if (hasError(m, result)) {
            return showCreateForm(model);
        }

        if (m.getType() == AuthType.user) {
            getAuthService().addUserAuth(userIds, m);
        } else if (m.getType() == AuthType.user_group || m.getType() == AuthType.dept_group) {
            getAuthService().addGroupAuth(groupIds, m);
        } else if (m.getType() == AuthType.dept_job) {
            getAuthService().addDeptJobAuth(deptIds, jobIds, m);
        }
        if(RequestUtils.isAjaxRequest(request)) {
        	return ResponseUtils.renderJSON(Message.success("新增成功", null), response);
        } else {
        	redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "新增成功");
        	return redirectToUrl("/admin/sso/auth.jhtml?search.type_eq=" + m.getType());
        }
    }


}
