package com.wzbuaa.crm.plugin.payment;

import java.util.List;

import framework.plugin.AutoRegisterPluginsBundle;
import framework.plugin.IPlugin;

/**
 * 支付插件桩
 *
 */
public class WapPaymentPluginBundle extends AutoRegisterPluginsBundle {

	
	public String getName() {
		return "Wap支付插件桩";
	}
	
	
	public List<IPlugin> getPluginList(){
		return this.plugins;
	}
	
	
}
