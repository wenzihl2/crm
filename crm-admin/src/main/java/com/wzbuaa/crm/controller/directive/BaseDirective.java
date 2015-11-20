package com.wzbuaa.crm.controller.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	
	protected void renderParamToBody(String s, Object obj, Environment environment, TemplateDirectiveBody templatedirectivebody) {
		freemarker.template.TemplateModel templatemodel;
		try {
			templatemodel = FreemarkerUtils.getVariable(s, environment);
			FreemarkerUtils.setVariable(s, obj, environment);
			templatedirectivebody.render(environment.getOut());
			FreemarkerUtils.setVariable(s, templatemodel, environment);
		} catch (TemplateModelException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
