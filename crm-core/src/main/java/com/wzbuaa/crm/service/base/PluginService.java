package com.wzbuaa.crm.service.base;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.base.PluginDomain;
import com.wzbuaa.crm.repository.base.PluginRepository;
import com.wzbuaa.crm.service.BaseService;

/**
 * Service实现类 - 插件
 */
@Service
public class PluginService extends BaseService<PluginDomain, String> {

	private PluginRepository getPluginRepository() {
        return (PluginRepository) baseRepository;
    }
	
	public PluginDomain findEnableComponent(String componentId){
		return getPluginRepository().findEnableComponent(componentId);
	}
	
	public void install(String id) {
		PluginDomain plugin = baseRepository.findOne(id);
		plugin.setIsEnabled(true);
		this.update(plugin);
	}

	public void unInstall(String id) {
		PluginDomain plugin = baseRepository.findOne(id);
		plugin.setIsEnabled(false);
		this.update(plugin);
	}

	public void start(String id) {
//		PluginDomain plugin = baseRepository.findOne(id);
//		IPlugin component = (IPlugin) storagePluginBundle.getPluginMap().get(id);
//		PluginBundleDomain bundle = plugin.getPluginBundle();
//		if(component != null && bundle != null){
//			IPluginBundle pluginBundle = (IPluginBundle) SpringUtils.getBean(bundle.getBundleId());
//			if(pluginBundle != null){
//				pluginBundle.registerPlugin(component);
//			}
//		}
//		plugin.setEnableState("1");
//		this.update(plugin);
	}

	public void stop(String id) {
//		PluginDomain plugin = baseRepository.findOne(id);
//		IPlugin component = (IPlugin) storagePluginBundle.getPluginMap().get(id);
//		PluginBundleDomain bundle = plugin.getPluginBundle();
//		if(component != null && bundle != null){
//			IPluginBundle pluginBundle = (IPluginBundle) SpringUtils.getBean(bundle.getBundleId());
//			if(pluginBundle != null){
//				pluginBundle.unRegisterPlugin(component);
//			}
//		}
//		plugin.setEnableState("2");
//		this.update(plugin);
	}
}
