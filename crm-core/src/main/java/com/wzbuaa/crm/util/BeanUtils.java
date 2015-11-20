package com.wzbuaa.crm.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import framework.CommonAttributes;
import framework.util.MyConvertUtils;

public class BeanUtils extends org.springframework.beans.BeanUtils {

	public static final BeanUtilsBean beanUtilsBean;
	
	/**
	 * 功能 : 只复制source对象的非空属性到target对象上
	 * */
	public static void copyNotNullProperties(Object source, Object target, String... ignoreProperties)
			throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
//							Method writeMethod = targetPd.getWriteMethod();
//							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
//								writeMethod.setAccessible(true);
//							}
//							writeMethod.invoke(target, value);
							beanUtilsBean.setProperty(target, sourcePd.getName(), value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

	static {
		MyConvertUtils myConvertUtils = new MyConvertUtils();
		DateConverter dateconverter = new DateConverter();
		dateconverter.setPatterns(CommonAttributes.DATE_PATTERNS);
		myConvertUtils.register(dateconverter, Date.class);
		beanUtilsBean = new BeanUtilsBean(myConvertUtils);
	}
	
}
