package com.wzbuaa.crm.util;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestHelper {

	public static BigDecimal[] getBigDecimalArray(HttpServletRequest request,
			String paraName) {
		String tempStrArray[] = request.getParameterValues(paraName);
		if (tempStrArray == null || tempStrArray.length == 0) {
			return null;
		}
		BigDecimal[] arr = new BigDecimal[tempStrArray.length];
		for (int i = 0; i < tempStrArray.length; i++) {
			if (StringUtils.isBlank(tempStrArray[i]) || !StringUtils.isNumeric(tempStrArray[i])) {
				arr[i] = new BigDecimal(0);
			} else {
				arr[i] = new BigDecimal(tempStrArray[i].trim());
			}
		}
		return arr;
	}
}
