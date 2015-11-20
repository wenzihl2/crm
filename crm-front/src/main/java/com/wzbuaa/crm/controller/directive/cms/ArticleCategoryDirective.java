package com.wzbuaa.crm.controller.directive.cms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.controller.directive.BaseDirective;
import com.wzbuaa.crm.controller.directive.DirectiveUtils;
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.service.base.DictionaryService;

import framework.entity.search.Searchable;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Repository
public class ArticleCategoryDirective extends BaseDirective {
	
	@Resource private DictionaryService dictionaryService;

	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "article_category";
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_CHANNEL_ID = "categoryId";
	/**
	 * 输入参数，栏目名字。
	 */
	public static final String PARAM_CHANNEL_NAME = "categoryName";
	/**
	 * 输入参数，栏目名字。
	 */
	public static final String PARAM_PARENT_ID = "parent_id";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		List<DictionaryDomain> list = getList(params, env);
		renderParamToBody("articleCategorys", list, params, env, body);
	}

	private List<DictionaryDomain> getList(Map<String, TemplateModel> params, Environment env)
			throws TemplateException {
		Long[] categoryIds = DirectiveUtils.getLongArray(PARAM_CHANNEL_ID, params);
		String categoryName = DirectiveUtils.getString(PARAM_CHANNEL_NAME, params);
		Long parentId = DirectiveUtils.getLong(PARAM_PARENT_ID, params);
		if(parentId != null) {
			List<DictionaryDomain> parents = new ArrayList<DictionaryDomain>();
			DictionaryDomain parent = dictionaryService.findOne(parentId);
			parents.add(parent);
			Searchable.newSearchable().addSearchParam("type_eq", DictionaryType.article);
			return dictionaryService.findChildren(parents, Searchable.newSearchable());
		} else if(StringUtils.isNotEmpty(categoryName)) {
			List<DictionaryDomain> parents = new ArrayList<DictionaryDomain>();
			DictionaryDomain parent = dictionaryService.findByTypeAndName(DictionaryType.article, categoryName);
			parents.add(parent);
			Searchable.newSearchable().addSearchParam("type_eq", DictionaryType.article);
			return dictionaryService.findChildren(parents, Searchable.newSearchable());
		} else if(categoryIds != null && categoryIds.length > 0){
			return dictionaryService.findAll(Arrays.asList(categoryIds));
		} else {
			Searchable searchable = Searchable.newSearchable().addSearchParam("parentId_eq", 0);
			searchable.addSearchParam("type_eq", DictionaryType.article);
	        return dictionaryService.findAllWithSort(searchable);
		}
	}
}
