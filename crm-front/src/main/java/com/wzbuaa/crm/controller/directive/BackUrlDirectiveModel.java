package com.wzbuaa.crm.controller.directive;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.domain.Constants;

import framework.util.AppContext;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Repository
public class BackUrlDirectiveModel implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> model = DirectiveUtils.getContext(env).getModel();
		String ignoreBackUrl = (String)model.get(Constants.IGNORE_BACK_URL);
		if(!StringUtils.isEmpty(ignoreBackUrl)) {
			return;
		}
		String backURL = AppContext.getRequest().getParameter(Constants.BACK_URL);
		backURL = backURL == null ? "" : backURL;
		Boolean hiddenInput = DirectiveUtils.getBool("hiddenInput", params);
	    if(hiddenInput != null && hiddenInput.equals(Boolean.TRUE)) {
	    	env.getOut().write("<input type=\"hidden\" name=\"" + Constants.BACK_URL + "\" value=\"" + backURL + "\">");
	    } else {
	    	env.getOut().write(backURL);
	    }
	}

}
