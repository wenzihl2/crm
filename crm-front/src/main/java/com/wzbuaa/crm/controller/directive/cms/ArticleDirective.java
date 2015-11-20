package com.wzbuaa.crm.controller.directive.cms;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;
import static com.wzbuaa.crm.controller.directive.DirectiveUtils.OUT_LIST;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.controller.directive.DirectiveUtils;
import com.wzbuaa.crm.domain.cms.ArticleDomain;
import com.wzbuaa.crm.service.cms.ArticleService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 内容对象标签
 */
@Repository
public class ArticleDirective implements TemplateDirectiveModel {
	
	@Resource private ArticleService articleService;
	public static final String PARAM_IDS = "ids";
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_CHANNEL_ID = "categoryId";
	/**
	 * 输入参数，栏目名字。
	 */
	public static final String PARAM_CHANNEL_NAME = "categoryName";
	public static final String PARAM_ARTICLE_PU = "isPublication";
	public static final String PARAM_ARTICLE_RECOMMEND = "isRecommend";
	public static final String PARAM_ARTICLE_PRIORITY = "priority";
	public static final String PARAM_COUNT = "count";
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {	
		List<ArticleDomain> list= getList(params, env);			
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	
	}
	
	private List<ArticleDomain> getList(Map<String, TemplateModel> params,
			Environment env) throws TemplateException {
		Long[] ids = DirectiveUtils.getLongArray(PARAM_IDS, params);
		Long categoryId = DirectiveUtils.getLong(PARAM_CHANNEL_ID, params);
		Boolean isPublication = DirectiveUtils.getBool(PARAM_ARTICLE_PU, params);
		Boolean isRecommend = DirectiveUtils.getBool(PARAM_ARTICLE_RECOMMEND, params);
//		String orderBy = DirectiveUtils.getString(PARAM_ARTICLE_PRIORITY, params);
		Integer count = DirectiveUtils.getInt(PARAM_COUNT, params);
		if(count == null) {
			count = 20;
		}
		return articleService.findList(ids, categoryId, isPublication, isRecommend, count, null);
	}
}
