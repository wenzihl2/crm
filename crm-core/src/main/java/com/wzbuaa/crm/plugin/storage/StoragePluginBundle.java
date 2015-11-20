package com.wzbuaa.crm.plugin.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.util.Collections3;

import framework.plugin.AutoRegisterPluginsBundle;
import framework.plugin.IPlugin;

/**
 * 存储插件桩
 */
public class StoragePluginBundle extends AutoRegisterPluginsBundle {
	
	public String getName() {
		return "存储插件桩";
	}
	
	public List<IPlugin> getPluginList(){
		return this.plugins;
	}
	
	public Map<StorageType, StoragePlugin> getPluginMap(){
		if(Collections3.isNotEmpty(this.plugins)){
			Map<StorageType, StoragePlugin> map = new HashMap<StorageType, StoragePlugin>();
			for(int i=0, len = this.plugins.size(); i<len; i++){
				StoragePlugin storageplugin = (StoragePlugin)this.plugins.get(i);
				map.put(StorageType.valueOf(storageplugin.getId()), storageplugin);
			}
			return map;
		}
		return null;
	}
}
