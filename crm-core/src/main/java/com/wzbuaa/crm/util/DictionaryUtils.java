package com.wzbuaa.crm.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import com.wzbuaa.crm.domain.DictionaryType;
import com.wzbuaa.crm.domain.base.DictionaryDomain;
import com.wzbuaa.crm.service.base.DictionaryService;
import framework.util.Collections3;
import framework.util.PropertiesHelper;
import framework.util.SpringUtils;

public class DictionaryUtils {
	
	/**
	 * 获取系统参数
	 * @return
	 */
	public static Map<Long, String> category(String type) {
		DictionaryService dictionaryService = (DictionaryService)SpringUtils.getBean(DictionaryService.class);
		List<DictionaryDomain> categorys = dictionaryService.findByTypeOrderByPriority(DictionaryType.valueOf(type));
		Map<Long, String> retMap = new LinkedHashMap<Long, String>();
		if(Collections3.isNotEmpty(categorys)) {
			for(DictionaryDomain category : categorys){
				retMap.put(category.getId(), category.getName());
			}
		}
		return retMap;
	}
	
	public static Map<String, String> getDictionaryMap(String prefix){
		Properties properties = PropertiesHelper.getPropertiesByPrefix("config", prefix);
		Map<String, String> retMap = new HashMap<String, String>();
		if(properties != null && properties.size() > 0){
			for(Object key : properties.keySet()){
				retMap.put(StringUtils.split((String)key, ".")[1], (String)properties.get(key));
			}
		}
		return retMap;
	}
}