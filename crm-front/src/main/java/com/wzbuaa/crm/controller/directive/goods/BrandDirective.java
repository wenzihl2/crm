package com.wzbuaa.crm.controller.directive.goods;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.controller.directive.BaseDirective;
import com.wzbuaa.crm.controller.directive.DirectiveUtils;
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.service.base.DictionaryService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 品牌列表标签
 * @author zhenglong
 *
 */
@Repository
public class BrandDirective extends BaseDirective {
	
	public static final String IS_RECOMMEND = "isRecommend";
	public static final String CATEGORY_ID = "categoryId"; 
	public static final String COUNT = "count";
	@Resource private DictionaryService dictionaryService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		//是否推荐
		Boolean isRecommend = DirectiveUtils.getBool(IS_RECOMMEND, params);
		//条数
		int count = DirectiveUtils.getInt(COUNT, params);
		//类别
//		Long category_id = DirectiveUtils.getLong(CATEGORY_ID, params);
//		DictionaryDomain category = dictionaryService.findOne(category_id);
		List<DictionaryDomain> list = dictionaryService.findByTypeOrderByPriority(DictionaryType.brand);
		renderParamToBody("brands", list, params, env, body);
	}
	
}
