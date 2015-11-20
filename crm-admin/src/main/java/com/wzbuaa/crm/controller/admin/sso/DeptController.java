package com.wzbuaa.crm.controller.admin.sso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.wzbuaa.crm.controller.admin.BaseTreeableController;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.sso.user.DeptDomain;
import com.wzbuaa.crm.service.sso.DeptService;
import com.wzbuaa.crm.service.sso.S_userService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.entity.search.Searchable;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月25日
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/sso/dept")
public class DeptController extends BaseTreeableController<DeptDomain, Long> {
	
	@Resource private DeptService s_deptService;
	@Resource private S_userService s_userService;
	
	public DeptController() {
        setResourceIdentity("sys:dept");
    }
	
	@RequestMapping(value="/workerList", method=RequestMethod.POST)
	@ResponseBody
	public String workerList(Long dept_id) {
		DeptDomain dept = baseService.findOne(dept_id);
		List<DeptDomain> parents = new ArrayList<DeptDomain>();
		parents.add(dept);
		List<DeptDomain> children = baseService.findChildren(parents, Searchable.newSearchable());
		children.add(dept);
		//List<S_userDomain> users = s_userService.findUserDept(children); 
		return JSON.toJSONString(JsonDataHelper.parseById("json_user_list", null));
	}
	
	@RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids,
            RedirectAttributes redirectAttributes
    ) {

        this.permissionList.assertHasUpdatePermission();

        for (Long id : ids) {
        	DeptDomain organization = baseService.findOne(id);
            organization.setShow(newStatus);
            baseService.update(organization);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGEKEY, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }
}