package com.wzbuaa.crm.domain;


public class Constants {

    public static final String PATH_SEPARATOR = ",";// 树路径分隔符
	public static final String PLUGIN_GOODSSTANDARD_PATH = "/admin/plugin/goodsstandard/";
	/**  交易记录过期小时数 **/
	public static final int ORDER_EXPIRED_HOURS = 24;
	/** 存入数据库  验证码分隔符 **/
	public static final String VERIFY_CODE_KEY_SEPARATOR = "_";
	/** 验证码过期时间间隔 **/
	public static final int VERIFY_CODE_KEY_PERIOD = 30;
	/** 会员银行卡限制5张 **/
	public static final int ACCOUNT_LIMIT = 5;
	
	/** 注册Key有效时间（单位：分钟） **/
	public static final int REGISTER_KEY_PERIOD = 10;
	/** 注册Key再次发送间隔（单位：分钟） **/
	public static final int REGISTER_KEY_RESEND_PERIOD = 2;
	/** 用户名前缀  **/
	public static final String USERNAME_PREFIX = "SM_";
	/**
     * 消息key
     */
	public static final String MESSAGEKEY = "messageKey";
	/**
     * 消息key
     */
	public static final String FLASHMESSAGE = "flashMessage";
	/**
     * 当前登录的用户
     */
	public static final String CURRENT_USER = "user";
	public static final String CURRENT_USERNAME = "username";
	public static final String CURRENT_FRONT_USER = "front_user";
	public static final String CURRENT_FRONT_USERNAME = "front_username";
	/**
     * 操作名称
     */
	public static final String OP_NAME = "op";
    /**
     * 错误key
     */
	public static final String ERROR = "error";
	
	/**
     * 上个页面地址
     */
	public static final String BACK_URL = "BackURL";
	public static final String IGNORE_BACK_URL = "ignoreBackURL";
	public static final int DEFAULT_CHECKBOX_SIZE = 5;
}
