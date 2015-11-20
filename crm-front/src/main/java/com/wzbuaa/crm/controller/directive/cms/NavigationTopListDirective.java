package com.wzbuaa.crm.controller.directive.cms;

import static com.wzbuaa.crm.controller.directive.DirectiveUtils.OUT_LIST;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.controller.directive.DirectiveUtils;
import com.wzbuaa.crm.domain.cms.NavigationDomain;
import com.wzbuaa.crm.domain.cms.NavigationDomain.NavigationPosition;
import com.wzbuaa.crm.service.cms.NavigationService;

import framework.entity.search.SearchOperator;
import framework.entity.search.Searchable;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 导航列表标签
 * @author zhenglong
 */
@Repository
public class NavigationTopListDirective implements TemplateDirectiveModel {
	
	@Resource private NavigationService navigationService;
	/**
	 * 输入参数，导航个数。
	 */
	public static final String POSITION = "position";//导航栏位置

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {	
		List<NavigationDomain> list= getList(params, env);			
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}	
	
	private List<NavigationDomain> getList(Map<String, TemplateModel> params,
			Environment env) throws TemplateException {
		String position = DirectiveUtils.getString(POSITION, params);
		NavigationPosition nposition = Enum.valueOf(NavigationPosition.class, position);
		Searchable searchable = Searchable.newSearchable().addSearchFilter("position", SearchOperator.eq, nposition)
								  .addSearchFilter("parentId", SearchOperator.eq, 0)
								  .addSort(Direction.ASC, "priority");
		return navigationService.findAllWithNoPageNoSort(searchable);
	}
}
