package com.wzbuaa.crm.controller.admin.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzbuaa.crm.bean.KeyValue;
import com.wzbuaa.crm.controller.admin.BaseCRUDController;
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.domain.base.TableDomain;
import com.wzbuaa.crm.domain.base.TableFieldDomain;
import com.wzbuaa.crm.domain.base.TableFieldDomain.FieldType;
import com.wzbuaa.crm.repository.RepositoryHelper;
import com.wzbuaa.crm.service.base.DictionaryService;
import com.wzbuaa.crm.service.base.TableFieldService;
import com.wzbuaa.crm.service.base.TableService;
import com.wzbuaa.crm.util.BeanUtils;

import framework.entity.search.SearchOperator;
import framework.spring.mvc.bind.annotation.RequestJsonParam;
import framework.util.Collections3;
import framework.util.EnumUtils;
import framework.util.ResponseUtils;

/**
 * <p>User: zhenglong
 * <p>Date: 2015年5月27日
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/base/table")
public class TableController extends BaseCRUDController<TableDomain, Long> {
	
	@Resource private TableFieldService tableFieldService;
	@Resource private TableService tableService;
	@Resource private DictionaryService dictionaryService;
	
    private TableService getTableService() {
        return (TableService) baseService;
    }

    public TableController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("maintain:table");
    }
    
    @RequestMapping(value = "{id}/fieldList", method = RequestMethod.GET)
	public String fieldList(Model model, @PathVariable("id") Long id) {
		TableDomain table = getTableService().findOne(id);
		Map<String, String> fieldTypeMap = EnumUtils.enumProp2I18nMap(FieldType.class);
		Map<String, String> operatorTypeMap = EnumUtils.enumProp2NameMap(SearchOperator.class, "info");
		JSONArray fieldArr = new JSONArray();
		for(Map.Entry<String, String> entry : fieldTypeMap.entrySet()){
			JSONObject obj = new JSONObject();
			obj.put("value", entry.getKey());
			obj.put("text", entry.getValue());
			fieldArr.add(obj);
		}
		
		JSONArray operatorArr = new JSONArray();
		for(Map.Entry<String, String> entry : operatorTypeMap.entrySet()){
			JSONObject obj = new JSONObject();
			obj.put("value", entry.getKey());
			obj.put("text", entry.getValue());
			operatorArr.add(obj);
		}
		model.addAttribute("fieldArr", fieldArr);
		model.addAttribute("operatorArr", operatorArr);
		model.addAttribute("table", table);
		return viewName("fieldList");
	}
    
	@RequestMapping(value = "{id}/fieldList", method = RequestMethod.POST)
	public String fieldList(@PathVariable("id") Long id, HttpServletResponse response) {
		TableDomain table = getTableService().findOne(id);
		if(Collections3.isEmpty(table.getFields())){
			Class clazz = null;
	    	try {
				clazz = Class.forName(table.getClassPath());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				addActionError(table.getClassPath() + "类路径错误，请检查!");
	    		return null;
			}
	    	EntityManager entityManager = RepositoryHelper.getEntityManager();
	    	HibernateEntityManagerFactory entityManagerFactory = (HibernateEntityManagerFactory)entityManager.getEntityManagerFactory();
			ClassMetadata classMetadata = entityManagerFactory.getSessionFactory().getClassMetadata(clazz);
			String[] propertyNames = classMetadata.getPropertyNames();
			
			List<TableFieldDomain> fields = new ArrayList<TableFieldDomain>();
			for(String name : propertyNames) {
				TableFieldDomain field = new TableFieldDomain();
				field.setName(name);
				fields.add(field);
			}
			table.setFields(fields);
		}
		return ResponseUtils.renderJSON(table.getFields(), response);
	}
	
	@Override
	@RequestMapping(value = "{id}/update/discard", method = RequestMethod.POST)
	public String update(Model model, TableDomain m, BindingResult result,
			String backURL, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		throw new RuntimeException("discard method");
	}
	
	@RequestMapping(value = "{id}/update", method = RequestMethod.POST)
	public String update(TableDomain m, HttpServletResponse response) {
		TableDomain persist = tableService.findOne(m.getId());
		BeanUtils.copyNotNullProperties(m, persist);
		tableService.update(persist);
		return ResponseUtils.renderJSON(successMessage, response);
	}
    
	@Override
	@RequestMapping(value = "create/discard", method = RequestMethod.POST)
	public String create(
			Model model,
			@Valid @ModelAttribute("m") TableDomain m,
			BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		throw new RuntimeException("discard method");
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute("m") TableDomain m,
			HttpServletResponse response) {
		if (StringUtils.isBlank(m.getClassPath())) {
			addActionError("类路径不能为空！");
			return ResponseUtils.renderJSON(errorMessage, response);
		}
		try {
			Class clazz = Class.forName(m.getClassPath());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			addActionError(m.getClassPath() + "类路径不存在！");
			return ResponseUtils.renderJSON(errorMessage, response);
		}
		getTableService().save(m);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	@RequestMapping(value = "{id}/saveField", method = RequestMethod.POST)
	public String saveField(@PathVariable("id") Long id, @RequestJsonParam(value="fieldList") List<TableFieldDomain> tableFieldList, HttpServletResponse response){
		TableDomain tableDomain = getTableService().findOne(id);
		tableDomain.addFields(tableFieldList);
		getTableService().update(tableDomain);
		return ResponseUtils.renderJSON(successMessage, response);
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public String query(String url, HttpServletResponse response, HttpServletRequest request){
		if (StringUtils.isEmpty(url)){
			addActionError("参数url不能为空");
			return null;
		}
		if(url.indexOf(".") != -1) {
			url = StringUtils.substringBefore(url, ".");
		}
		TableDomain table = getTableService().getByClassPath(url);
		if(table == null) {
			addActionError("url:" + url + "还未配置!");
			return null;
		}
		
		Class clazz = null;
    	try {
			clazz = Class.forName(table.getClassPath());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			addActionError(table.getClassPath() + "类路径错误，请检查!");
    		return null;
		}
    	
    	List<TableFieldDomain> listTableField = table.getFields();
    	List<TableFieldDomain> usedList = new ArrayList<TableFieldDomain>();
		for (TableFieldDomain tableField : listTableField ){
			if(!tableField.getUsed()) {
				continue;
			}
			usedList.add(tableField);
			if (tableField.getType() == FieldType.enumSelect) {
		    	EntityManager entityManager = RepositoryHelper.getEntityManager();
		    	HibernateEntityManagerFactory entityManagerFactory = (HibernateEntityManagerFactory)entityManager.getEntityManagerFactory();
				ClassMetadata classMetadata = entityManagerFactory.getSessionFactory().getClassMetadata(clazz);
				Type  propertyType = null;
				try {
				   propertyType = classMetadata.getPropertyType(tableField.getName());
				} catch (Exception e) {
					tableFieldService.delete(tableField);
					listTableField.remove(tableField);
				}
				if(propertyType.getReturnedClass().isEnum()) {
					Map<String, String> map = EnumUtils.enumProp2I18nMap(propertyType.getReturnedClass());
					List<KeyValue> list = new ArrayList<KeyValue>();
					Set<String> keys = map.keySet();
			        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			            String key =  it.next();				            
			        	list.add(new KeyValue(key, map.get(key)));	       
			        }
					tableField.setData(JSON.toJSONString(list));
				}
			} else if (tableField.getType() == FieldType.categorySelect) {
				EntityManager entityManager = RepositoryHelper.getEntityManager();
		    	HibernateEntityManagerFactory entityManagerFactory = (HibernateEntityManagerFactory)entityManager.getEntityManagerFactory();
				ClassMetadata classMetadata = entityManagerFactory.getSessionFactory().getClassMetadata(clazz);
				Type  propertyType = null;
				try {
				   propertyType = classMetadata.getPropertyType(tableField.getName());
				} catch (Exception e) {
					tableFieldService.delete(tableField);
					listTableField.remove(tableField);
				}
				if(propertyType.getReturnedClass() == DictionaryDomain.class) {
					List<DictionaryDomain> categorys = dictionaryService.findByTypeOrderByPriority(DictionaryType.valueOf(tableField.getName()));
					List<KeyValue> list = new ArrayList<KeyValue>();
			        for (Iterator<DictionaryDomain> it = categorys.iterator(); it.hasNext();) {
			        	DictionaryDomain cate =  it.next();				            
			        	list.add(new KeyValue(cate.getId().toString(), cate.getName()));	       
			        }
					tableField.setData(JSON.toJSONString(list));
				}
			}
		}
		return ResponseUtils.renderJSON(usedList, response);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(Long[] ids, HttpServletResponse response){
		getTableService().delete(ids);
    	return ResponseUtils.renderJSON(successMessage, response);
    }
		
}
