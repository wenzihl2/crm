package com.wzbuaa.crm.controller.directive.goods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.controller.directive.BaseDirective;
import com.wzbuaa.crm.controller.directive.DirectiveUtils;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.service.base.DictionaryService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 商品树状结构标签
 * @author zhenglong
 */
@Repository
public class CategoryTreeDirective extends BaseDirective {
	
	@Resource DictionaryService dictionaryService;
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_ID = "parentId";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Long parentId = (Long)DirectiveUtils.getLong(PARAM_ID, params);
		DictionaryDomain parent = dictionaryService.findOne(parentId);
		List<DictionaryDomain> parents = new ArrayList<DictionaryDomain>();
		parents.add(parent);
		List<DictionaryDomain> categorys= dictionaryService.findChildren(parents, null);	
		renderParamToBody("categories", categorys, params, env, body);
	}

}
