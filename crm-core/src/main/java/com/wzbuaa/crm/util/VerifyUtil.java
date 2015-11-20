package com.wzbuaa.crm.util;

import static com.wzbuaa.crm.domain.Constants.VERIFY_CODE_KEY_PERIOD;
import static com.wzbuaa.crm.domain.Constants.VERIFY_CODE_KEY_SEPARATOR;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 验证工具类 
 *
 */
public class VerifyUtil {
	
	/**
	 * 验证身份证
	 * @param idCard 身份证号
	 * @param isNullable 是否可空
	 * @return
	 */
	public static boolean verifyIDCard(String idCard, boolean isNullable){
		if(isNullable && StringUtils.isBlank(idCard)){
			return true;
		}
		if(!Pattern.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$", idCard)
				&& !Pattern.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$", idCard)){
			return false;
		}
		return true;
	}
	
	/**
	 * 验证手机号码
	 * @param mobile 手机号码
	 * @param isNullable 是否可空
	 * @return
	 */
	public static boolean verifyMobile(String mobile, boolean isNullable){
		if(isNullable && StringUtils.isBlank(mobile)){
			return true;
		}
		if(!Pattern.matches("^[1][0-9]{10}$", mobile)){
			return false;
		}
		return true;
	}
	
	/**
	 * 验证邮箱
	 * @param email 邮箱
	 * @param isNullable 是否可空
	 * @return
	 */
	public static boolean verifyEmail(String email, boolean isNullable){
		if(isNullable && StringUtils.isBlank(email)){
			return true;
		}
		if(!Pattern.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$", email)){
			return false;
		}
		return true;
	}
	
	/**
	 * 创建手机验证码
	 * @param mobile  手机                  
	 * @param valCode  验证码
	 * @return    手机_时间_验证码
	 */
	public static String createMobileVerifyCodeKey(String mobile, String valCode){
		StringBuffer verifyCodeKey = new StringBuffer();
		verifyCodeKey.append(mobile);
		verifyCodeKey.append(VERIFY_CODE_KEY_SEPARATOR);
		verifyCodeKey.append(System.currentTimeMillis());
		verifyCodeKey.append(VERIFY_CODE_KEY_SEPARATOR);
		verifyCodeKey.append(valCode);
		return verifyCodeKey.toString();
	}
	
	/**
	 * 验证手机验证码
	 * @param mobile  手机
	 * @param oldKey  验证码加密串
	 * @param newKey  输入验证码
	 * @return
	 */
	public static String checkMobileVerify(String mobile, String oldKey, 
			String newKey, Integer expired){
		if(StringUtils.isBlank(oldKey)) {
			return "请先获取短信验证码！";
		}
		String[] verifyArr = StringUtils.split(oldKey, VERIFY_CODE_KEY_SEPARATOR);
		if(verifyArr.length != 3){
			return "短信验证码错误！";
		}
		
		String dbMobile = verifyArr[0];
		String dbTime = verifyArr[1];
		String dbCode = verifyArr[2];
		
		if(StringUtils.isBlank(mobile) || !StringUtils.equals(dbMobile, mobile)){
			return "短信验证码错误！";
		}
		long time = Long.valueOf(dbTime);
		Date verifyCodeKeyBuildDate = new Date(time);
		Date verifyCodeKeyExpiredDate = DateUtils.addMinutes(verifyCodeKeyBuildDate, expired);
		Date now = new Date();
		if (now.after(verifyCodeKeyExpiredDate)) {
			return "短信验证码已过期！";
		}
		if (!StringUtils.equalsIgnoreCase(dbCode, newKey)) {
			return "短信验证码错误！";
		}
		return null;
	}
	
	/**
	 * 创建邮箱验证码
	 * @param valCode
	 * @return 时间_验证码
	 */
	public static String createEmailVerifyCodeKey(String email, String valCode){
		StringBuffer verifyCodeKey = new StringBuffer();
		verifyCodeKey.append(email);
		verifyCodeKey.append(VERIFY_CODE_KEY_SEPARATOR);
		verifyCodeKey.append(System.currentTimeMillis());
		verifyCodeKey.append(VERIFY_CODE_KEY_SEPARATOR);
		verifyCodeKey.append(valCode);
		return verifyCodeKey.toString();
	}
	
	/**
	 * 验证邮箱验证码
	 * @param oldKey  验证码加密串
	 * @param newKey  输入验证码
	 * @return
	 */
	public static String checkEmailVerify(String oldKey, String newKey){
		String[] oldKeys = oldKey.split(VERIFY_CODE_KEY_SEPARATOR);
		if(!newKey.equals(oldKeys[2])){
			return "邮箱验证码已失效！";
		}
		Date recoverKeyBuildDate = new Date(Long.valueOf(oldKeys[1]));
		Date recoverKeyExpiredDate = DateUtils.addMinutes(recoverKeyBuildDate, VERIFY_CODE_KEY_PERIOD);
		Date now = new Date();
		if (now.after(recoverKeyExpiredDate)) {
			return "对不起，此验证码已过期！";
		}
		return null;
	}
	
	/**
	 * 根据加密串获取邮箱
	 * @param verifyCodeKey 数据库加密串
	 * @return
	 */
	public static String getEmailFromVerifyCodeKey(String verifyCodeKey){
		if(StringUtils.isBlank(verifyCodeKey)){
			return null;
		}
		String email = verifyCodeKey.split(VERIFY_CODE_KEY_SEPARATOR)[0];
		if(verifyEmail(email, false)){
			return email;
		}
		return null;
	}
	
}
