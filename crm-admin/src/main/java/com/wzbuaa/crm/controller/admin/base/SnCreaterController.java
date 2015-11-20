package com.wzbuaa.crm.controller.admin.base;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzbuaa.crm.controller.admin.BaseController;
import com.wzbuaa.crm.domain.base.SnCreaterDomain;
import com.wzbuaa.crm.service.base.SnCreaterService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.Pager;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;

/**
 * 数据字典 - 分类器
 */
@Controller
@RequestMapping("/admin/base/sncreater")
public class SnCreaterController extends BaseController<SnCreaterDomain, Long> {
	
	@Resource private SnCreaterService snCreaterService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(){
		return viewName("list");
	}
	
	//获取列表
	@RequestMapping(value="/queryData", method=RequestMethod.POST)
	@ResponseBody
	@PageableDefaults(sort = "id=desc")
	public Pager<SnCreaterDomain> queryData(Searchable searchable){
		Page<SnCreaterDomain> page = snCreaterService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
    }
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(){
		return viewName("add");
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Message add(SnCreaterDomain sncreater){
		snCreaterService.save(sncreater);
		return successMessage;
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable(value="id") Long id, Model model){
		SnCreaterDomain sncreater = snCreaterService.findOne(id);
		model.addAttribute("sncreater", sncreater);
		return viewName("add");
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.POST)
	@ResponseBody
	public Message edit(SnCreaterDomain sncreater){
		snCreaterService.update(sncreater);
		return successMessage;
	}
	
}