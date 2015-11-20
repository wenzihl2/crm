package com.wzbuaa.crm.controller.admin.base;

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
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.service.base.DictionaryService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.Pager;
import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;

/**
 * 数据字典 - 自定义参数
 */
@Controller
@RequestMapping("/admin/base/dictionary")
public class DictionaryController extends BaseTreeableController<DictionaryDomain, Long> {
	
	@Resource private DictionaryService dictionaryService;
	
	//获取列表
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@PageableDefaults(sort = "priority=asc")
	public Pager<DictionaryDomain> queryData(Searchable searchable) {
		Page<DictionaryDomain> page= baseService.findAll(searchable);
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
	public Message create(DictionaryDomain child, Long parentId, HttpServletResponse response){
		// 获取该分类的最大排序
		if(child.getPriority() == null) {
			int maxPriority = dictionaryService.findMaxPriority(child.getType(), child.getParentId());
			child.setPriority(maxPriority + 1);
		}
		
		DictionaryDomain parentModel = baseService.findOne(parentId);
		if(parentModel != null) {
	        baseService.appendChild(parentModel, child);
		} else {
			child.setParentId(0L);
			dictionaryService.save(child);
		}
		return successMessage;
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable("id")Long id, Model model){
		DictionaryDomain m = baseService.findOne(id);
		model.addAttribute("dictionary", m);
		return viewName("input");
	}
	
	
	@Override
	@RequestMapping(value = "{id}/edit/discard", method = RequestMethod.POST)
	public String edit(Model model, DictionaryDomain m, BindingResult result,
			RedirectAttributes redirectAttributes) {
		throw new RuntimeException("discarded method");
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.POST)
	@ResponseBody
	public Message update(DictionaryDomain dictionary, Long parentId){
		if(dictionary.getPriority() == null) {
			int maxPriority = dictionaryService.findMaxPriority(dictionary.getType(), dictionary.getParentId());
			dictionary.setPriority(maxPriority + 1);
		}
		DictionaryDomain parentModel = baseService.findOne(parentId);
		if(parentModel != null) {
	        baseService.appendChild(parentModel, dictionary);
		} else {
			dictionary.setParentId(0L);
			dictionaryService.save(dictionary);
		}
		dictionaryService.update(dictionary);
		return successMessage;
	}
	
	@RequestMapping(value="{type}/tree", method=RequestMethod.POST)
	@ResponseBody
	public List<ZTree<Long>> treeData(@PathVariable(value="type") String type, HttpServletRequest request){
		Searchable searchable = Searchable.newSearchable().addSearchFilter("type", SearchOperator.eq, type);
		List<DictionaryDomain> categorys = dictionaryService.findAllWithNoPageNoSort(searchable);
		return convertToZtreeList(
                request.getContextPath(),
                categorys,
                false,
                true);
	}
	
	/**
	 * 地区树
	 */
	@RequestMapping(value = "region/tree", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "priority=asc"})
	public String tree(HttpServletRequest request, String searchName,
			boolean async, String type, Searchable searchable, Model model) {
		searchable.addSearchParam("type_eq", DictionaryType.region);
		return super.tree(request, searchName, async, type, searchable, model);
	}
}