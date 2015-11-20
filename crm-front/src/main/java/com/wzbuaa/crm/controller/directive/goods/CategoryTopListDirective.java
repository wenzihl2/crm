package com.wzbuaa.crm.controller.directive.goods;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.controller.directive.BaseDirective;
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.service.base.DictionaryService;

import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 根据给定id获取上级目录
 * @author zhenglong
 */
@Repository
public class CategoryTopListDirective extends BaseDirective {

	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_GOODS_CATEGORY_ID = "categoryId";
	@Resource public DictionaryService dictionaryService;
	
	
	@SuppressWarnings({"rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
//		Boolean isVisible = DirectiveUtils.getBool("isVisible", params);
		Searchable searchable = Searchable.newSearchable().addSearchFilter("parentId", SearchOperator.eq, 0);
		searchable.addSearchFilter("type", SearchOperator.eq, DictionaryType.product);
		searchable.addSort(Direction.ASC, "priority");
		List<DictionaryDomain> list = dictionaryService.findAllWithSort(searchable);
		renderParamToBody("goodsCategories", list, params, env, body);
	}
}
