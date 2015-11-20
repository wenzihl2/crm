package com.wzbuaa.crm.util;

import framework.util.AppContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class ViewFreemarker implements InitializingBean {
	
	static final Log logger = LogFactory.getLog(ViewFreemarker.class);
	private static Configuration _tplConfig = new Configuration();
  
	static {
	  	_tplConfig.setClassForTemplateLoading(ViewFreemarker.class, "/content");
	}

	private static String parseTemplate(String tplName, String encoding, Map<String, Object> paras) {
		try {
			StringWriter swriter = new StringWriter();
			Template mytpl = null;
			mytpl = _tplConfig.getTemplate(tplName, encoding);
			mytpl.process(paras, swriter);
			return swriter.toString();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return e.toString();
    	}
	}

	public static void view(HttpServletResponse response, String template) throws Exception {
		view(response, template, null);
	}

	public static void view(HttpServletResponse response, String template, Map<String, Object> paras)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Freemarker loading：" + template);
		}
		HttpServletRequest request = AppContext.getRequest();
		if (paras == null) {
			paras = new HashMap();
		}
		paras.put("request", request);
		paras.put("response", response);
		String content = parseTemplate(template, "UTF-8", paras);
		outputToPage(request, response, content);
	}

	private static void outputToPage(HttpServletRequest request, HttpServletResponse response, String content)
			throws Exception {
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter writer = response.getWriter();
			writer.println(content);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
	public void afterPropertiesSet() throws Exception
	{}
}