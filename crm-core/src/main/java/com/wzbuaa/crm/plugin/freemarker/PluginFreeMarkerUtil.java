package com.wzbuaa.crm.plugin.freemarker;

import java.io.File;
import java.io.IOException;
import framework.util.AppContext;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * freemarker工具
 */
public class PluginFreeMarkerUtil {
	
	private PluginFreeMarkerUtil() {
	}

	private static Configuration cfg;

	/**
	 * 获取servlet上下文件的Configuration
	 * 
	 * @param pageFolder
	 * @return
	 */
	public static Configuration getServletCfg(String pageFolder) {
		Configuration cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(AppContext.getRequest().getSession().getServletContext(), pageFolder);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		return cfg;
	}

	public static Configuration getCfg(){
		if (cfg == null) {
			cfg = new Configuration();
			cfg.setTemplateUpdateDelay(6000);
			cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));

			 DateFormateDirectiveModel df = new DateFormateDirectiveModel();  
			 cfg.setSharedVariable("dateFormat", df);
			 
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setDefaultEncoding("UTF-8");
			cfg.setLocale(java.util.Locale.CHINA);
			cfg.setEncoding(java.util.Locale.CHINA, "UTF-8");
		}
		return cfg;
	}
	
	public static Configuration getFolderCfg(String pageFolder) throws IOException {
		cfg =getCfg();
		cfg.setDirectoryForTemplateLoading(new File(pageFolder));
		return cfg;
	}
}