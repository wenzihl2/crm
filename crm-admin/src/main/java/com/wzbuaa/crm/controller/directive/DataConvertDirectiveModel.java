package com.wzbuaa.crm.controller.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.wzbuaa.crm.plugin.SysDataConvert.AbstractDataConvertPlugin;
import com.wzbuaa.crm.plugin.SysDataConvert.DataConvertPluginBundle;
import com.wzbuaa.crm.plugin.SysDataConvert.DataType;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Repository
public class DataConvertDirectiveModel implements TemplateDirectiveModel {
	
	@Resource private DataConvertPluginBundle dataConvertPluginBundle;
	
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String convertType = DirectiveUtils.getString("convertType", params);
		String convertKey = DirectiveUtils.getString("convertKey", params);
		String value = DirectiveUtils.getString("value", params);
		
		if(StringUtils.isEmpty(convertType) || StringUtils.isEmpty(value)) {
			return;
		}
		
		AbstractDataConvertPlugin dataConvertPlugin = dataConvertPluginBundle.getPluginMap().get(DataType.valueOf(convertType));
		String val = dataConvertPlugin.convert(value, convertKey);
		env.getOut().write(val);
	}

}
