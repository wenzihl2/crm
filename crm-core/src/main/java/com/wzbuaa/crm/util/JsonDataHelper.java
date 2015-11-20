package com.wzbuaa.crm.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.wzbuaa.crm.plugin.SysDataConvert.AbstractDataConvertPlugin;
import com.wzbuaa.crm.plugin.SysDataConvert.DataConvertPluginBundle;
import com.wzbuaa.crm.plugin.SysDataConvert.DataType;

import framework.Pager;
import framework.util.AppContext;
import framework.util.PropertiesHelper;
import framework.util.SpringUtils;

@SuppressWarnings({"rawtypes","unchecked"})
public class JsonDataHelper {
	
	public static final String JSON_FILE_NAME = "com/wzbuaa/crm/bean/json.xml";
	public static final Map<String, List<Map<String,String>>> JSON_MAP = new HashMap<String, List<Map<String,String>>>(); 
	private static final BeanUtilsBean beanUtilsBean; 
	private static final DataConvertPluginBundle dataConvertPluginBundle;
	
	static {
		beanUtilsBean = BeanUtils.beanUtilsBean;
		dataConvertPluginBundle = SpringUtils.getBean(DataConvertPluginBundle.class);
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(JSON_FILE_NAME);
			Document document = (new SAXReader()).read(is);
			List<org.dom4j.Element> list = document.selectNodes("/jsonMap/json");
			for (Iterator<org.dom4j.Element> iterator = list.iterator(); iterator.hasNext();){
				org.dom4j.Element element1 = iterator.next();
				String id = element1.attributeValue("id");
				String val = element1.getTextTrim();
				List<Map<String,String>> beans = (List)JSON.parseObject(val, List.class);
				JSON_MAP.put(id, beans);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			File file = new ClassPathResource(JSON_FILE_NAME).getFile();
			Document document = (new SAXReader()).read(file);
			List<org.dom4j.Element> list = document.selectNodes("/jsonMap/json");
			for (Iterator<org.dom4j.Element> iterator = list.iterator(); iterator.hasNext();){
				org.dom4j.Element element1 = iterator.next();
				String id = element1.attributeValue("id");
				String val = element1.getTextTrim();
				List<Map<String,String>> beans = (List)JSON.parseObject(val, List.class);
				JSON_MAP.put(id, beans);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Map<String,String>> parseById(String jsonId, List dataList) {
		List<Map<String,String>> beans = JSON_MAP.get(jsonId);
		return parse(beans, dataList);
	}
	
	public static Map<String,String> parseById(String jsonId, Object data) {
		List<Map<String,String>> beans = JSON_MAP.get(jsonId);
		return parse(beans, data);
	}
	
	public static List<Map<String,String>> parse(String json, List dataList) {
		List<Map<String, String>> beans = (List)JSON.parseObject(json, List.class);
		return parse(beans, dataList);
	}

	public static List<Map<String,String>> parse(List<Map<String,String>> beans, List dataList) {
		if(beans == null || dataList == null) {
			return null;
		}
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(Object data: dataList){
			list.add(parse(beans, data));
		}
		return list;
	}
	
	public static Map<String,String> parse(String json, Object data) {
		List<Map<String, String>> beans = (List)JSON.parseArray(json, List.class);
		return parse(beans, data);
	}
	
	public static <T> List<T> parseById(String jsonId, Collection dataList, Class<T> clazz) {
		if(jsonId == null || dataList == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		for(Object data: dataList){
			list.add(parseById(jsonId, data, clazz));
		}
		return list;
	}
	
	public static <T> T parseById(String jsonId, Object data, Class<T> clazz) {
		List<Map<String,String>> beans = JSON_MAP.get(jsonId);
		try {
			return parse(beans, data, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String,String> parse(List<Map<String,String>> beans, Object data) {
		if(beans == null) {
			return null;
		}
		Map<String,String> keyValue = new HashMap<String,String>();
		for(Map<String,String> param : beans) {
			String i18n = param.get("i18n");
			String name = param.get("name");
			String vname = param.get("vname");
			String dict = param.get("dict");
			String dateFormat = param.get("dateFormat");
			if (StringUtils.isNotBlank(i18n)){
				String value = (String)getValueByOgnl(data, name, "");
				String propertiesValue = SpringUtils.getMessage(StringUtils.capitalize(i18n) + "." + value, null);
				keyValue.put(vname == null ? name : vname, propertiesValue);
				continue;
			} else if (StringUtils.isNotBlank(dict)){
				String value = (String)getValueByOgnl(data, name, "");
				String propertiesValue = PropertiesHelper.getPropertiesValue("config", dict + "." + value);
                keyValue.put(vname == null ? name : vname, propertiesValue);
                continue;
			}
			if (StringUtils.isNotBlank(dateFormat)){
				Date value = (Date)getValueByOgnl(data, name, "");
				if(value != null){
					String dateStr = DateHelper.date2String(value, dateFormat);
					keyValue.put(vname == null ? name : vname,  dateStr);
				}
				continue;
            }
			keyValue.put(vname == null ? name : vname,  (String)getValueByOgnl(data, name, ""));
		}
		return keyValue;
	}
	
	public static <T> T parse(List<Map<String,String>> beans, Object data, Class<T> clazz) throws Exception {
		if(beans == null) {
			return null;
		}
		T t = clazz.newInstance();
		for(Map<String,String> param : beans) {
			String i18n = param.get("i18n");
			String name = param.get("name");
			String vname = param.get("vname");
			String dict = param.get("dict");
			String dateFormat = param.get("dateFormat");
			Object value = getValueByOgnl(data, name, "");
			if(value == null) {
				continue;
			}
			if (StringUtils.isNotBlank(i18n)){
				String propertiesValue = SpringUtils.getMessage(StringUtils.capitalize(i18n) + "." + (String)value, null);
				beanUtilsBean.setProperty(t, vname == null ? name : vname, propertiesValue);
				continue;
			} else if (StringUtils.isNotBlank(dict)){
				String propertiesValue = PropertiesHelper.getPropertiesValue("config", dict + "." + (String)value);
				beanUtilsBean.setProperty(t, vname == null ? name : vname, propertiesValue);
                continue;
			}
			if (StringUtils.isNotBlank(dateFormat)){
				if(value != null){
					String dateStr = DateHelper.date2String((Date)value, dateFormat);
					beanUtilsBean.setProperty(t, vname == null ? name : vname, dateStr);
				}
				continue;
            }
			try {
				beanUtilsBean.setProperty(t, vname == null ? name : vname, getValueByOgnl(data, name, ""));
			} catch (Exception e) {
				System.out.println(name + " is not specfied");
				e.printStackTrace();
			}
		}
		return t;
	}
	
	public static Pager createJSONData(Page page){
		if(page == null){
			return null;
		}
		Pager pager = new Pager();
		pager.setList(createJSONData(page.iterator()));
		pager.setPageNumber(page.getNumber());
		pager.setPageCount(page.getSize());
		pager.setTotalCount(page.getTotalElements());
		pager.setList(createJSONData(page.iterator()));
		return pager;
	}
	
	public static Pager createJSONData(Pager pager){
		if(pager == null){
			return null;
		}
		pager.setList(createJSONData(pager.getList().iterator()));
		return pager;
	}
	
	/**
	 * 将page类转换成ligerUI Grid框架所需的JSON格式数据。
	 * @param data
	 * @return
	 */ 
	public static List<Map<String, String>> createJSONData(Iterator iterator) {
		HttpServletRequest request = AppContext.getRequest();
		String filedStr = request.getParameter("field");
		if(StringUtils.isEmpty(filedStr)){
			return null;
		}
		String[] field = filedStr.split(",");
		
		String[] dict = StringUtils.splitPreserveAllTokens(request.getParameter("dict"), ",");//配置文件
		String[] i18n = StringUtils.splitPreserveAllTokens(request.getParameter("i18n"), ",");//i18n文件
		String[] dateformat = StringUtils.splitPreserveAllTokens(request.getParameter("dateFormat"), ",");
		String[] convertType = StringUtils.splitPreserveAllTokens(request.getParameter("convertType"), ",");
		String[] convertKey = StringUtils.splitPreserveAllTokens(request.getParameter("convertKey"), ",");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		while(iterator.hasNext()) {
			Map<String, String> map = new HashMap<String, String>();
			Object obj = iterator.next();
			for(int i = 0; i < field.length;i++){
				Object value = getValueByOgnl(obj, field[i], "");
				if(value == null) {
					continue;
				}
				
				if (i18n != null && i18n[i].trim().length()>0){
					String propertiesValue = SpringUtils.getMessage(StringUtils.capitalize(i18n[i]) + "." + value, null);
					map.put(field[i], propertiesValue);
					continue;
				} else if (dict != null && dict[i].trim().length()>0) {
					String propertiesValue = PropertiesHelper.getPropertiesValue("config", dict[i] + "." + value);
					
					if(StringUtils.isNotEmpty(propertiesValue) && propertiesValue.contains("${base}")){
						propertiesValue = StringUtils.replace(propertiesValue, "${base}", request.getContextPath());
					}
					map.put(field[i], propertiesValue);
                    continue;
				} else if (convertType != null && convertType[i].trim().length()>0){
					AbstractDataConvertPlugin dataConvertPlugin = dataConvertPluginBundle.getPluginMap().get(DataType.valueOf(convertType[i]));
					String val = dataConvertPlugin.convert(value.toString(), convertKey[i]);
					map.put(field[i], val);
					continue;
                } else if (dateformat != null && dateformat[i].trim().length()>0){
					String dateStr = DateHelper.date2String((Date)value, dateformat[i]);
					map.put(field[i], dateStr);
					continue;
                }
				map.put(field[i],  value.toString());
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据数据源中所列的字段获取值,为NULL时 需要设置默认值(待完善)
	 * @param data
	 * @return
	 * @throws OgnlException 
	 */
	private static Object getValueByOgnl(Object target, String field, String defaultValue) {
		try {
			Class clazz = beanUtilsBean.getPropertyUtils().getPropertyType(target, field);
			if(clazz == Date.class) {
				return (Date)beanUtilsBean.getPropertyUtils().getProperty(target, field);
			} else if(Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
				String[] arr = beanUtilsBean.getArrayProperty(target, field);
				return arr == null ? null : StringUtils.join(arr, ",");
			} else {
				return beanUtilsBean.getProperty(target, field);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}
	
}
