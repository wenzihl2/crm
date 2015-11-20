package com.wzbuaa.crm.plugin.freemarker;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import framework.util.AppContext;
import framework.util.SpringUtils;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * FreeMarker解析器
 * 
 * @author zhenglong
 */
public final class FreeMarkerPaser {

//	private static final Log log = LogFactory.getLog(FreeMarkerPaser.class);
	private static ThreadLocal<FreeMarkerPaser> managerLocal = new ThreadLocal<FreeMarkerPaser>();
	//freemarker data model 通过putData方法设置模板中的值
	private Map<String, Object> data;
	private Configuration cfg;
	//模板路径前缀 默认为"" 可以通过 {@link #setPathPrefix(String)} 设置
	private String pathPrefix;
	//模板文件的名字，默认为与插件类同名
	private String pageName;
	//模板页面的扩展名，默认为.html
	private String pageExt;
	private boolean wrapPath = true;
	//页面所在文件夹 默认为插件类所在文件夹
	private String pageFolder;
	private Class clazz;
	
	public FreeMarkerPaser(){ 
		data = new HashMap<String, Object>();
		this.pageFolder =null;
	}
	
	public FreeMarkerPaser(Class clz){
		this.clazz = clz;
		data = new HashMap<String, Object>();
		this.pageFolder =null;
	}
	
	public FreeMarkerPaser(String folder){
		data = new HashMap<String, Object>();
		this.pageFolder = folder;
	}
	
	/**
	 * 获取当前线程的 fremarkManager
	 * @return
	 */
	public final static FreeMarkerPaser getInstance(){
		if( managerLocal.get() == null ){
			throw new RuntimeException("freemarker paser is null");
		}
		FreeMarkerPaser fmp = managerLocal.get();
		
		fmp.setPageFolder(null);
		fmp.setWrapPath(true);
		return fmp;
	}
	
	public final static FreeMarkerPaser getCurrInstance(){
		if( managerLocal.get() == null ){
			throw new RuntimeException("freemarker paser is null");
		}
		FreeMarkerPaser fmp = managerLocal.get();
		return fmp;
	}
	
	public final static void set(FreeMarkerPaser fp){
		 managerLocal.set(fp);
	}
	
	public final static void remove(){
		 managerLocal.remove();
	} 

	
	/**
	 * 设置挂件模板的变量
	 * 
	 * @param key
	 * @param value
	 */
	public void putData(String key, Object value) {
		if (key != null && value != null)
			data.put(key, value);
	}
	
	public void putData(Map  map){
		if(map!=null)
			data.putAll(map);
	}
	
	public Object getData(String key){
		if(key==null) return null;
		
		return data.get(key);
	}
	
	public void setWrapPath(boolean wp){
		wrapPath = wp;
	}

	public String process(String template) {
		if (template == null)
			return null;
		
		StringWriter stringwriter = new StringWriter();
		try {
			Configuration cfg = this.getConfiguration();
			new Template("template", new StringReader(template), cfg).process(data, stringwriter);
		} catch (TemplateException templateexception) {
			templateexception.printStackTrace();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		return stringwriter.toString();
	}
	
	public String getContentByPageName(String pageName, String pagerFolder){
		if(StringUtils.isEmpty(pageName)){
			return "";
		}
		if(StringUtils.isEmpty(pagerFolder)){
			return "";
		}
		setPageFolder(pagerFolder);
		setPageName(pageName);
		String content = proessPageContent();
		FreeMarkerPaser.remove();
		return content;
	}
	
	/**
	 * 解析webapp页面，插件页面在webapp下
	 * @return
	 */
	public String proessPageContent() { 
		
		String template_path = null;
		pageExt = pageExt == null ? ".ftl" : pageExt;
		if(this.clazz != null) {
			String name = this.clazz.getSimpleName();
			template_path = name + pageExt;
		} else {
			template_path = pageFolder + pageName + pageExt;
		}
		Configuration cfg = this.getConfiguration();
		String content = null;
		try {
			Template template = cfg.getTemplate(template_path);
			content = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 解析class路径下的页面，插件页面在插件包里
	 * @return
	 */
	public String proessClassPageContent() {
		String name = this.clazz.getSimpleName();
		pageExt = pageExt == null ? ".ftl" : pageExt;
		name = this.pageName == null ? name : pageName;
		
		try {
			Configuration cfg = this.getCfg();
			cfg.setNumberFormat("0.##");
			Template template = cfg.getTemplate(name + pageExt);
			StringWriter result = new StringWriter();
			template.process(data, result);
			return result.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return
	 * @throws TemplateException 
	 */
	public Configuration getConfiguration() {
		FreeMarkerConfigurer FreeMarkerConfigurer = (FreeMarkerConfigurer) SpringUtils.getBean("freeMarkerConfigurer");
		return FreeMarkerConfigurer.getConfiguration();
	}
	
	private Configuration getCfg(){
		
		if(cfg == null){
			 cfg = PluginFreeMarkerUtil.getCfg();		
		}
		pathPrefix = pathPrefix == null ? "" : pathPrefix;
		if(pageFolder == null) {//默认使用挂件所在文件夹
			cfg.setClassForTemplateLoading(this.clazz, pathPrefix);
		} else {
			cfg.setServletContextForTemplateLoading(AppContext.getRequest().getSession().getServletContext(), pageFolder);
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());			
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(java.util.Locale.CHINA);
		cfg.setEncoding(java.util.Locale.CHINA, "UTF-8");
		return cfg;
	}

	/**
	 * 设置模板路径前缀
	 * @param path
	 */
	public void setPathPrefix(String path) {
		this.pathPrefix = path;
	}

	/**
	 * 设置模板文件的名称
	 * @param pageName
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * 设置模板页面扩展名
	 * @param pageExt
	 */
	public void setPageExt(String pageExt) {
		this.pageExt = pageExt;
	}

	public void setPageFolder(String pageFolder) {
		this.pageFolder = pageFolder;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
	
//	public static void main(String[] args) {
//		FreeMarkerPaser f = new FreeMarkerPaser();
//		FreeMarkerPaser.set(f);
//			FreeMarkerPaser fp = FreeMarkerPaser.getInstance();
//			fp.setClazz(ProductSpecPlugin2.class);
//			fp.setPageName("spec2");
//			String s = fp.proessClassPageContent();
//			System.out.println(s);
//
//	}

}
