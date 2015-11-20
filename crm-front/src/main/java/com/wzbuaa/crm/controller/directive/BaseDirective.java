package com.wzbuaa.crm.controller.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import framework.util.FreemarkerUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

public abstract class BaseDirective implements TemplateDirectiveModel {
	
	private static final String UseCache = "useCache";
	private static final String CacheRegion = "cacheRegion";
	private static final String ID = "id";
	private static final String COUNT = "count";
	private static final String PRIORITY = "priority";
	private static final String MidTrim = "\\s*,\\s*";
	private static final String LRTrim = "\\s+";
	
	protected boolean getUseCache(Environment environment, Map map) throws TemplateModelException {
		Boolean useCache = (Boolean)FreemarkerUtils.getParameter(UseCache, Boolean.class, map);
		return useCache == null ? true : useCache.booleanValue();
	}

	protected String getCacheRegion(Environment environment, Map map) throws TemplateModelException {
		String cacheRegion = (String)FreemarkerUtils.getParameter(CacheRegion, String.class, map);
		return cacheRegion == null ? environment.getTemplate().getName() : cacheRegion;
	}
	
	protected Long getId(Map map) throws TemplateModelException {
		return (Long)FreemarkerUtils.getParameter(ID, Long.class, map);
	}

	protected Integer getCount(Map map) throws TemplateModelException {
		return (Integer)FreemarkerUtils.getParameter(COUNT, Integer.class, map);
	}

	protected List<Order> getOrderList(Map map, String excludeProperty[]) throws TemplateModelException {
		String orderByStr = StringUtils.trim((String)DirectiveUtils.getParameter(PRIORITY, String.class, map));
		List<Order> orderList = new ArrayList<Order>();
		if (StringUtils.isNotEmpty(orderByStr)) {
			String orders[] = orderByStr.split(MidTrim);
			for (int i = 0; i < orders.length; i++) {
				String order = orders[i];
				if (!StringUtils.isNotEmpty(order)){
					continue;
				}
				String fieldName = null;
				Direction direction = null;
				String orderStrs[] = order.split(LRTrim);
				if (orderStrs.length == 1) {
					fieldName = orderStrs[0];
				} else {
					if (orderStrs.length < 2)
						continue;
					fieldName = orderStrs[0];
					try {
						direction = Direction.valueOf(orderStrs[1]);
					} catch (IllegalArgumentException illegalargumentexception) {
						continue;
					}
				}
				if (!ArrayUtils.contains(excludeProperty, fieldName)){
					orderList.add(new Order(direction, fieldName));
				}
			}

		}
		return orderList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void renderParamToBody(Map map, Environment environment, 
			TemplateDirectiveBody templatedirectivebody) throws TemplateException, IOException {
		HashMap hashmap = new HashMap();
		String s;
		freemarker.template.TemplateModel templatemodel;
		for (Iterator<String> iterator = map.keySet().iterator(); 
			iterator.hasNext(); hashmap.put(s, templatemodel)){
			s = (String)iterator.next();
			templatemodel = FreemarkerUtils.getVariable(s, environment);
		}

		FreemarkerUtils.setVariables(map, environment);
		templatedirectivebody.render(environment.getOut());
		FreemarkerUtils.setVariables(hashmap, environment);
	}
	
	@SuppressWarnings({"rawtypes"})
	protected void renderParamToBody(String viriableName, Object obj, Map params,
			Environment environment, TemplateDirectiveBody templatedirectivebody) {
		freemarker.template.TemplateModel templatemodel;
		try {
			templatemodel = DirectiveUtils.getVariable(viriableName, environment);
			DirectiveUtils.setVariable(viriableName, obj, environment);
			templatedirectivebody.render(environment.getOut());
			DirectiveUtils.setVariable(viriableName, templatemodel, environment);
		} catch (TemplateModelException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
