package com.wzbuaa.crm.plugin.SysDataConvert;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.wzbuaa.crm.service.base.PluginService;

import framework.plugin.AutoRegisterPlugin;

/**
 * 数据转换插件接口
 * @author zhenglong
 */
public abstract class AbstractDataConvertPlugin extends AutoRegisterPlugin {

	@Resource protected PluginService pluginService;
	@Resource protected DataConvertPluginBundle dataPluginBundle;
	/**
	 * 是否安装
	 * @return
	 */
	public boolean getIsInstalled() {
		return true;
	}
	
	public JSONObject getConfig(){
		return null;
	}
	
	public abstract String convert(String id, String key);

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
		AbstractDataConvertPlugin other = (AbstractDataConvertPlugin) obj;
		return getId().equals(other.getId());
	}
}