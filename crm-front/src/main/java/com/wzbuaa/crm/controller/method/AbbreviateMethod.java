package com.wzbuaa.crm.controller.method;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 缩短字符串的方法
 * @author zhenglong
 *
 */
@Repository
public class AbbreviateMethod implements TemplateMethodModel {

	private static final Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5\\ufe30-\\uffa0]+$");

	public Object exec(List arguments) {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && 
				StringUtils.isNotEmpty(arguments.get(0).toString())){
			Integer integer = null;
			String s = null;
			if (arguments.size() == 2) {
				if (arguments.get(1) != null)
					integer = Integer.valueOf(arguments.get(1).toString());
			} else{
				if (arguments.size() > 2) {
					if (arguments.get(1) != null)
						integer = Integer.valueOf(arguments.get(1).toString());
					if (arguments.get(2) != null)
						s = arguments.get(2).toString();
				}
			}
			return new SimpleScalar(abbreviateString(arguments.get(0).toString(), integer, s));
		} else {
			return "";
		}
	}

	private String abbreviateString(String str, Integer len, String replace) {
		if (len != null) {
			int i = 0;
			int j = 0;
			for (; i < str.length(); i++) {
				j = pattern.matcher(String.valueOf(str.charAt(i))).find() ? j + 2 : j + 1;
				if (j >= len.intValue())
					break;
			}

			if (i < str.length()) {
				if (replace != null)
					return new StringBuilder(str.substring(0, i + 1)).append(replace).toString();
				else
					return str.substring(0, i + 1);
			} else {
				return str;
			}
		}
		if (replace != null){
			return new StringBuilder(str).append(replace).toString();
		} else {
			return str;
		}
	}

}
