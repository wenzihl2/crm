package com.wzbuaa.crm.controller.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.springframework.stereotype.Component;
import framework.Message;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class FlashMessageDirective extends BaseDirective {

	public static final String FLASH_MESSAGE_ATTRIBUTE_NAME = (new StringBuilder(FlashMessageDirective.class.getName())).append(".FLASH_MESSAGE").toString();
	private static final String flashMessage = "flashMessage";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel loopVars[], TemplateDirectiveBody body) throws TemplateException {
		Map<String, Object> model = DirectiveUtils.getContext(env).getModel();
		if (model != null) {
			Message message = (Message)model.get(FLASH_MESSAGE_ATTRIBUTE_NAME);
			if (body != null){
				renderParamToBody(flashMessage, message, env, body);
			} else if (message != null) {
				Writer writer = env.getOut();
				try {
					writer.write("<span class='" + message.getType() + "'>" + message.getContent() + "</span>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
