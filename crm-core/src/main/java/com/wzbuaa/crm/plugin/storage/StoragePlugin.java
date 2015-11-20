package com.wzbuaa.crm.plugin.storage;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import com.wzbuaa.crm.bean.FileInfo;
import com.wzbuaa.crm.domain.base.PluginDomain;
import com.wzbuaa.crm.repository.hibernate.type.JsonMap;
import com.wzbuaa.crm.service.base.PluginService;

import framework.plugin.AutoRegisterPlugin;

/**
 * 存储插件接口
 * @author zhenglong
 */
public abstract class StoragePlugin extends AutoRegisterPlugin {

	@Resource protected PluginService pluginService;
	@Resource protected StoragePluginBundle storagePluginBundle;
	/**
	 * 是否安装
	 * @return
	 */
	public boolean getIsInstalled() {
		return true;
	}
	
	public JsonMap<String,Object> getConfig(){
		PluginDomain plugin = pluginService.findEnableComponent(getName());
		JsonMap<String,Object> obj = plugin.getConfigparam();
		return obj;
	}
	
	public abstract void upload(String path, File file, String contentType);

	public abstract String getUrl(String path);

	public abstract List<FileInfo> browser(String path);
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoragePlugin other = (StoragePlugin) obj;
		return getId().equals(other.getId());
	}
}
