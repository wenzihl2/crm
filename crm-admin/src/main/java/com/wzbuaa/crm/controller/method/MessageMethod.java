package com.wzbuaa.crm.controller.method;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import framework.util.SpringUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;

@Repository
public class MessageMethod implements TemplateMethodModel {

	public Object exec(List arguments) {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && 
				StringUtils.isNotEmpty(arguments.get(0).toString())){
			String s = null;
			String s1 = arguments.get(0).toString();
			if (arguments.size() > 1) {
				Object aobj[] = arguments.subList(1, arguments.size()).toArray();
				s = SpringUtils.getMessage(s1, aobj);
			} else {
				s = SpringUtils.getMessage(s1, new Object[]{});
			}
			return new SimpleScalar(s);
		} else {
			return null;
		}
	}
	
}
