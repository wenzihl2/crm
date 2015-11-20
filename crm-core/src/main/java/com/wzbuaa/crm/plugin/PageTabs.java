package com.wzbuaa.crm.plugin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 商品维护时的选项卡上下文
 * @author zhenglong
 *
 */
public class PageTabs {
	
	private static final Log loger = LogFactory.getLog(PageTabs.class);
	private static Map<String,Map<Integer, String>> tabs;
	
	static{
		tabs = new HashMap<String, Map<Integer, String>>();
	}
	
	public static void addTab(String plugintype, Integer tabid, String tabname){
		Map<Integer, String> plugin_tab = tabs.get(plugintype) == null ? new LinkedHashMap<Integer, String>()
				: (Map<Integer, String>) tabs.get(plugintype);
		plugin_tab.put(tabid, tabname);
		
		tabs.put(plugintype, plugin_tab);
		if(loger.isDebugEnabled()){
			loger.debug("添加"+ plugintype  +"选项卡" +  tabid + " tabname is  " + tabname);
		}
	}
	
	public static Map<Integer, String> getTabs(String plugintype){
		return tabs.get(plugintype);
	}
	
}
