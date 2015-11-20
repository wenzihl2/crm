package com.wzbuaa.crm.controller.admin.sso;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wzbuaa.crm.bean.CompanyParamBean;
import com.wzbuaa.crm.controller.admin.BaseController;
import com.wzbuaa.crm.domain.sso.S_companyDomain;
import com.wzbuaa.crm.service.sso.CompanyService;
import com.wzbuaa.crm.util.JsonDataHelper;

import framework.Message;
import framework.Pager;
import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import framework.spring.mvc.bind.annotation.PageableDefaults;

/**
 * 后台Action类 - 企业设置
 */
@Controller
@RequestMapping("/admin/sso/sysparame")
public class CompanyController extends BaseController<S_companyDomain, Long> {

	@Resource private CompanyService companyService;
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	@ResponseBody
	public String search(Searchable searchable, String key) {
		if(StringUtils.isNotEmpty(key)){
			searchable.addSearchFilter("name", SearchOperator.eq, key);
		}
		List<S_companyDomain> companys = companyService.findAllWithNoPageNoSort(searchable);
		return JSON.toJSONString(JsonDataHelper.parseById("company_search_list", companys));
	}
	
	
	@RequestMapping(value="/system-parameter", method=RequestMethod.GET)
	public String systemParameter(){
		return viewName("edit");
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list() {
		return viewName("list");
	}
	
	//获取列表
	@RequestMapping(value="/queryData", method=RequestMethod.POST)
	@ResponseBody
	@PageableDefaults(sort = "id=desc")
	public Pager<S_companyDomain> queryData(Searchable searchable){
		Page<S_companyDomain> page = companyService.findAll(searchable);
		return JsonDataHelper.createJSONData(page);
    }
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(){
		return viewName("input");
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(S_companyDomain company, CompanyParamBean config){
		company.setConfig(JSON.toJSONString(config));
		companyService.save(company);
		return viewName("input");
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(Long id,  Model model){
		S_companyDomain company = companyService.findOne(id);
		model.addAttribute("company", company);
		CompanyParamBean config = JSON.parseObject(company.getConfig(), CompanyParamBean.class);
		model.addAttribute("config", config);
		return viewName("input");
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@ResponseBody
	public Message edit(S_companyDomain company, CompanyParamBean config){
		company.setConfig(JSON.toJSONString(config));
		companyService.update(company);
		return successMessage;
	}
	
	@RequestMapping(value="/setting", method=RequestMethod.GET)
	public String setting(Model model){
		S_companyDomain company = companyService.findOne(1L);
		model.addAttribute("company", company);
		CompanyParamBean config = JSON.parseObject(company.getConfig(), CompanyParamBean.class);
		model.addAttribute("config", config);
		return viewName("input");
	}
	
	@RequestMapping(value="/setting", method=RequestMethod.POST)
	@ResponseBody
	public Message setting(S_companyDomain company, CompanyParamBean config, HttpServletResponse response){
		company.setConfig(JSON.toJSONString(config));
		companyService.update(company);
		return successMessage;
	}
}