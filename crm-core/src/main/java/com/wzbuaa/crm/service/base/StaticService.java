package com.wzbuaa.crm.service.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import freemarker.template.Template;

@Service
public class StaticService implements ServletContextAware {

	private ServletContext servletContext;
	@Resource private TemplateService templateService;
	@Resource private FreeMarkerConfigurer freemarkerConfigurer;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Transactional(readOnly=true)
	public int build(String templatePath, String staticPath, Map<String,Object> model) throws Exception {
		FileOutputStream fileoutputstream= null;
		OutputStreamWriter outputstreamwriter= null;
		BufferedWriter bufferedwriter= null;
		Assert.hasText(templatePath);
		Assert.hasText(staticPath);
		Template template = null;
		try {
			//读取模板
			template = freemarkerConfigurer.getConfiguration().getTemplate(templatePath, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file = new File(servletContext.getRealPath(staticPath));
		File file1 = file.getParentFile();
		if (!file1.exists())
			file1.mkdirs();
		fileoutputstream = new FileOutputStream(file);
		outputstreamwriter = new OutputStreamWriter(fileoutputstream, "UTF-8");
		bufferedwriter = new BufferedWriter(outputstreamwriter);
		try {
			template.process(model, bufferedwriter);//生成静态化文件
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}finally{
			//关闭流
			bufferedwriter.flush();
			IOUtils.closeQuietly(bufferedwriter);
			IOUtils.closeQuietly(outputstreamwriter);
			IOUtils.closeQuietly(fileoutputstream);	
		}
		return 1;
	}

	@Transactional(readOnly=true)
	public int build(String templatePath, String staticPath) throws Exception {
		return build(templatePath, staticPath, null);
	}

	@Transactional(readOnly=true)
	public int buildOther() throws Exception{
		int i = 0;
		return i;
	}

	@Transactional(readOnly=true)
	public int delete(String staticPath){
		Assert.hasText(staticPath);
		
		File file = new File(servletContext.getRealPath(staticPath));
		if (file.exists()){
			file.delete();
			return 1;
		} else{
			return 0;
		}
	}
}