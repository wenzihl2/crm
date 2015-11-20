package com.wzbuaa.crm.controller.directive.goods;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.controller.directive.DirectiveUtils;
import com.wzbuaa.crm.util.UploadUtil;

import framework.util.AppContext;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Repository
public class GoodsPicDirectiveModel implements TemplateDirectiveModel {
	
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String pic = DirectiveUtils.getString("pic", params);
		String postfix = DirectiveUtils.getString("postfix", params);
		
		if(StringUtils.isEmpty(pic)) {
			if(StringUtils.isEmpty(postfix)){
				postfix = "_small";
			}
			StringBuilder sb = new StringBuilder();
			sb.append(AppContext.getContextPath()).append("/upload/image/")
			  .append("default").append(postfix).append(".gif");
			pic = sb.toString();
			env.getOut().write(pic);
		} else {
			pic = UploadUtil.replacePath(pic);
			env.getOut().write(UploadUtil.getImagePath(pic, postfix));
		}
	}

}
