package com.wzbuaa.crm.controller.admin.cms;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wzbuaa.crm.bean.ZTree;
import com.wzbuaa.crm.controller.admin.BaseTreeableController;
import com.wzbuaa.crm.domain.Constants;
import com.wzbuaa.crm.domain.cms.NavigationDomain;
import com.wzbuaa.crm.service.cms.NavigationService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.Pager;
import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;

@Controller
@RequestMapping("/admin/cms/navigation")
public class NavigationController extends BaseTreeableController<NavigationDomain, Long>  {

	@Resource private NavigationService navigationService;
	
	//获取列表
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults(sort = "priority=asc")
	public Pager<NavigationDomain> queryData(Searchable searchable) {
		Page<NavigationDomain> page = baseService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "新增");
        return viewName("input");
    }
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	@ResponseBody
	public Message create(NavigationDomain child, Long parentId, HttpServletResponse response){
		// 获取该分类的最大排序
		if(child.getPriority() == null) {
			int maxPriority = navigationService.findMaxPriority(child.getPosition(), child.getParentId());
			child.setPriority(maxPriority + 1);
		}
		
		NavigationDomain parentModel = navigationService.findOne(parentId);
		if(parentModel != null) {
			navigationService.appendChild(parentModel, child);
		} else {
			child.setParentId(0L);
			navigationService.save(child);
		}
		return successMessage;
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable("id")Long id, Model model){
		NavigationDomain m = navigationService.findOne(id);
		NavigationDomain parent = navigationService.findOne(m.getParentId());
		model.addAttribute("m", m);
		model.addAttribute("parent", parent);
		return viewName("input");
	}
	
	
	@Override
	@RequestMapping(value = "{id}/edit/discard", method = RequestMethod.POST)
	public String edit(Model model, NavigationDomain m, BindingResult result,
			RedirectAttributes redirectAttributes) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.POST)
	@ResponseBody
	public Message update(NavigationDomain m, Long parentId){
		if(m.getPriority() == null) {
			int maxPriority = navigationService.findMaxPriority(m.getPosition(), m.getParentId());
			m.setPriority(maxPriority + 1);
		}
		NavigationDomain parentModel = baseService.findOne(parentId);
		if(parentModel != null) {
	        baseService.appendChild(parentModel, m);
		} else {
			m.setParentId(0L);
			navigationService.save(m);
		}
		navigationService.update(m);
		return successMessage;
	}
	
	@RequestMapping(value="{position}/tree", method=RequestMethod.POST)
	@ResponseBody
	public List<ZTree<Long>> treeData(@PathVariable(value="position") String position, HttpServletRequest request){
		Searchable searchable = Searchable.newSearchable().addSearchFilter("position", SearchOperator.eq, position);
		List<NavigationDomain> categorys = navigationService.findAllWithNoPageNoSort(searchable);
		return convertToZtreeList(
                request.getContextPath(),
                categorys,
                false,
                true);
	}
}
