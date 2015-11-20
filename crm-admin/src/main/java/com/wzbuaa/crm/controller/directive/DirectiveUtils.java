package com.wzbuaa.crm.controller.directive;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.RequestContext;

import com.wzbuaa.crm.controller.directive.exception.MustBooleanException;
import com.wzbuaa.crm.controller.directive.exception.MustDateException;
import com.wzbuaa.crm.controller.directive.exception.MustNumberException;
import com.wzbuaa.crm.controller.directive.exception.MustStringException;

import framework.CommonAttributes;
import framework.util.MyConvertUtils;
import freemarker.core.Environment;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.utility.DeepUnwrap;

/**
 * Freemarker标签工具类
 * 
 * @author zhenglong
 * 
 */
public abstract class DirectiveUtils {
	/**
	 * 输出参数：对象数据
	 */
	public static final String OUT_BEAN = "tag_bean";
	/**
	 * 输出参数：列表数据
	 */
	public static final String OUT_LIST = "tag_list";
	/**
	 * 输出参数：分页数据
	 */
	public static final String OUT_PAGE = "tag_page";
	/**
	 * 输出参数：分页数据
	 */
	public static final String OUT_PAGINATION = "tag_pagination";
	/**
	 * 参数：是否调用模板。
	 */
	public static final String PARAM_TPL = "tpl";
	/**
	 * 参数：次级模板名称
	 */
	public static final String PARAM_TPL_SUB = "tplSub";
	private static final ConvertUtilsBean convertUtilsBean;
	
	public static final String SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE = "springMacroRequestContext"; 
	
	static  {
		convertUtilsBean = new MyConvertUtils();
		DateConverter dateconverter = new DateConverter();
		dateconverter.setPatterns(CommonAttributes.DATE_PATTERNS);
		convertUtilsBean.register(dateconverter, Date.class);
	}
	/**
	 * 将params的值复制到variable中
	 * 
	 * @param env
	 * @param params
	 * @return 原Variable中的值
	 * @throws TemplateException
	 */
	public static Map<String, TemplateModel> addParamsToVariable(
			Environment env, Map<String, TemplateModel> params)
			throws TemplateException {
		Map<String, TemplateModel> origMap = new HashMap<String, TemplateModel>();
		if (params.size() <= 0) {
			return origMap;
		}
		Set<Map.Entry<String, TemplateModel>> entrySet = params.entrySet();
		String key;
		TemplateModel value;
		for (Map.Entry<String, TemplateModel> entry : entrySet) {
			key = entry.getKey();
			value = env.getVariable(key);
			if (value != null) {
				origMap.put(key, value);
			}
			env.setVariable(key, entry.getValue());
		}
		return origMap;
	}

	/**
	 * 将variable中的params值移除
	 * 
	 * @param env
	 * @param params
	 * @param origMap
	 * @throws TemplateException
	 */
	public static void removeParamsFromVariable(Environment env,
			Map<String, TemplateModel> params,
			Map<String, TemplateModel> origMap) throws TemplateException {
		if (params.size() <= 0) {
			return;
		}
		for (String key : params.keySet()) {
			env.setVariable(key, origMap.get(key));
		}
	}

	/**
	 * 获得RequestContext
	 * 
	 * ViewResolver中的exposeSpringMacroHelpers必须为true
	 * 
	 * @param env
	 * @return
	 * @throws TemplateException
	 */
	public static RequestContext getContext(Environment env)
			throws TemplateException {
		TemplateModel ctx = env
				.getGlobalVariable(SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE);
		if (ctx instanceof AdapterTemplateModel) {
			return (RequestContext) ((AdapterTemplateModel) ctx)
					.getAdaptedObject(RequestContext.class);
		} else {
			throw new TemplateModelException("RequestContext '"
					+ SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE
					+ "' not found in DataModel.");
		}
	}

	public static String getString(String name,
			Map<String, TemplateModel> params) throws TemplateException {
		TemplateModel model = params.get(name);
		if (model == null) {
			return null;
		}
		if (model instanceof TemplateScalarModel) {
			return ((TemplateScalarModel) model).getAsString();
		} else if ((model instanceof TemplateNumberModel)) {
			return ((TemplateNumberModel) model).getAsNumber().toString();
		} else {
			throw new MustStringException(name);
		}
	}

	public static Long getLong(String name, Map<String, TemplateModel> params)
			throws TemplateException {
		TemplateModel model = params.get(name);
		if (model == null) {
			return null;
		}
		if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isBlank(s)) {
				return null;
			}
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
				throw new MustNumberException(name);
			}
		} else if (model instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) model).getAsNumber().longValue();
		} else {
			throw new MustNumberException(name);
		}
	}

	public static Integer getInt(String name, Map<String, TemplateModel> params)
			throws TemplateException {
		TemplateModel model = params.get(name);
		if (model == null) {
			return null;
		}
		if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isBlank(s)) {
				return null;
			}
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				throw new MustNumberException(name);
			}
		} else if (model instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) model).getAsNumber().intValue();
		} else {
			throw new MustNumberException(name);
		}
	}
	
	public static Long[] getLongArray(String name,
			Map<String, TemplateModel> params) throws TemplateException {
		String str = DirectiveUtils.getString(name, params);
		if (StringUtils.isBlank(str)) {
			return null;
		}
		str = str.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		String[] arr = StringUtils.split(str, ',');
		if(arr == null){
			return null;
		}
		List<Long> arrLongList = new ArrayList<Long>();
		for(int i=0; i<arr.length; i++){
			if(StringUtils.isNotEmpty(arr[i])){
				arrLongList.add(Long.parseLong(arr[i]));
			}
		}
		return arrLongList.toArray(new Long[arrLongList.size()]);
	}

	public static String[] getStringArray(String name,
			Map<String, TemplateModel> params) throws TemplateException {
		String str = DirectiveUtils.getString(name, params);
		if (StringUtils.isBlank(str)) {
			return null;
		}
		str = str.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		String[] arr = StringUtils.split(str, ',');
		return arr;
	}

	public static Boolean getBool(String name, Map<String, TemplateModel> params)
			throws TemplateException {
		TemplateModel model = params.get(name);
		if (model == null) {
			return null;
		}
		if (model instanceof TemplateBooleanModel) {
			return ((TemplateBooleanModel) model).getAsBoolean();
		} else if (model instanceof TemplateNumberModel) {
			return !(((TemplateNumberModel) model).getAsNumber().intValue() == 0);
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			// 空串应该返回null还是true呢？
			if (!StringUtils.isBlank(s)) {
				return !(s.equals("0") || s.equalsIgnoreCase("false") || s
						.equalsIgnoreCase("f"));
			} else {
				return null;
			}
		} else {
			throw new MustBooleanException(name);
		}
	}

	public static Date getDate(String name, Map<String, TemplateModel> params)
			throws TemplateException {
		TemplateModel model = params.get(name);
		if (model == null) {
			return null;
		}
		if (model instanceof TemplateDateModel) {
			return ((TemplateDateModel) model).getAsDate();
		} else if (model instanceof TemplateScalarModel) {
			DateTypeEditor editor = new DateTypeEditor();
			editor.setAsText(((TemplateScalarModel) model).getAsString());
			return (Date) editor.getValue();
		} else {
			throw new MustDateException(name);
		}
	}

	/**
	 * 模板调用类型
	 * 
	 * @author zhenglong
	 */
	public enum InvokeType {
		body, custom, sysDefined, userDefined
	};

	/**
	 * 是否调用模板
	 * 
	 * 0：不调用，使用标签的body；1：调用自定义模板；2：调用系统预定义模板；3：调用用户预定义模板。默认：0。
	 * 
	 * @param params
	 * @return
	 * @throws TemplateException
	 */
	public static InvokeType getInvokeType(Map<String, TemplateModel> params)
			throws TemplateException {
		String tpl = getString(PARAM_TPL, params);
		if ("3".equals(tpl)) {
			return InvokeType.userDefined;
		} else if ("2".equals(tpl)) {
			return InvokeType.sysDefined;
		} else if ("1".equals(tpl)) {
			return InvokeType.custom;
		} else {
			return InvokeType.body;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Object getParameter(String name, Class type, Map params) throws TemplateModelException {
		Assert.hasText(name);
		Assert.notNull(type);
		Assert.notNull(params);
		TemplateModel templatemodel = (TemplateModel)params.get(name);
		if (templatemodel == null) {
			return null;
		} else {
			Object obj = DeepUnwrap.unwrap(templatemodel);
			return convertUtilsBean.convert(obj, type);
		}
		
	}

	public static TemplateModel getVariable(String name, Environment env) throws TemplateModelException {
		Assert.hasText(name);
		Assert.notNull(env);
		return env.getVariable(name);
	}
	
	public static void setVariable(String name, Object value, Environment env) throws TemplateModelException{
		Assert.hasText(name);
		Assert.notNull(env);
		if (value instanceof TemplateModel){
			env.setVariable(name, (TemplateModel)value);
		} else {
			env.setVariable(name, ObjectWrapper.BEANS_WRAPPER.wrap(value));
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void setVariables(Map variables, Environment env) throws TemplateModelException {
		Assert.notNull(variables);
		Assert.notNull(env);
		for (Iterator iterator = variables.entrySet().iterator(); iterator.hasNext();){
			Map.Entry entry = (Map.Entry)iterator.next();
			String s = (String)entry.getKey();
			Object obj = entry.getValue();
			if (obj instanceof TemplateModel){
				env.setVariable(s, (TemplateModel)obj);
			} else {
				env.setVariable(s, ObjectWrapper.BEANS_WRAPPER.wrap(obj));
			}
		}

	}
	
	
}