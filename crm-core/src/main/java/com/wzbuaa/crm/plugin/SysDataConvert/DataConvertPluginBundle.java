package com.wzbuaa.crm.plugin.SysDataConvert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.util.Collections3;

import framework.plugin.AutoRegisterPluginsBundle;
import framework.plugin.IPlugin;

/**
 * 存储插件桩
 */
public class DataConvertPluginBundle extends AutoRegisterPluginsBundle {
	
	public String getName() {
		return "数据转换插件桩";
	}
	
	public List<IPlugin> getPluginList(){
		return this.plugins;
	}
	
	public Map<DataType, AbstractDataConvertPlugin> getPluginMap(){
		if(Collections3.isNotEmpty(this.plugins)){
			Map<DataType, AbstractDataConvertPlugin> map = new HashMap<DataType, AbstractDataConvertPlugin>();
			for(int i=0, len = this.plugins.size(); i<len; i++){
				AbstractDataConvertPlugin dataConvertPlugin = (AbstractDataConvertPlugin)this.plugins.get(i);
				map.put(DataType.valueOf(dataConvertPlugin.getName()), dataConvertPlugin);
			}
			return map;
		}
		return null;
	}
}
