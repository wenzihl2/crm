package com.wzbuaa.crm.controller.directive.cms;

import static com.wzbuaa.crm.controller.directive.DirectiveUtils.OUT_LIST;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.controller.directive.BaseDirective;
import com.wzbuaa.crm.controller.directive.DirectiveUtils;
import com.wzbuaa.crm.domain.cms.AdvertDomain;
import com.wzbuaa.crm.service.cms.AdvertService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 广告标签
 * @author zhenglong
 */
@Repository
public class AdvertDirective extends BaseDirective {
	
	@Resource private AdvertService advertService;
	
	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "advert";
	/**
	 * 输入参数，广告个数。
	 */
	public static final String PARAM_COUNT = "count";
	//广告位ID
	public static final String ADVERTPOSITION_ID = "position_id";
	//广告ID
	public static final String ADVERT_ID = "advertIds";
	//广告位置
	public static final String ADVERTPOSITION_CODE = "position_code";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {	
		List<AdvertDomain> list= getList(params, env);			
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}	
	
	private List<AdvertDomain> getList(Map<String, TemplateModel> params,
			Environment env) throws TemplateException {		
		Long position_id = DirectiveUtils.getLong(ADVERTPOSITION_ID, params);
		Long[] advert_ids = DirectiveUtils.getLongArray(ADVERT_ID, params);
		String position_code = DirectiveUtils.getString(ADVERTPOSITION_CODE, params);
		Integer count = DirectiveUtils.getInt(PARAM_COUNT, params);
		
		if(count == null) {
			count = 20;
		}
		List<Order> orderList = getOrderList(params, null);
		return advertService.findList(advert_ids, position_id, position_code, count, orderList);
	}
}
