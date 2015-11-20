package com.wzbuaa.crm.plugin.payment;

import java.util.Map;

/**
 * 手机端在线支付事件
 *
 */
public interface IWapPaymentEvent extends IOnlinePayEvent {
	
	/**
	 * 支付成功后通知事件的
	 * @param params 回调参数
	 * @param memberId 会员id
	 * @return 返回第三方支付需要的信息
	 */
	public String onCallBack(Map<String, String> params, Long memberId) throws Exception;

	/**
	 * 获取交易编号
	 * @param params 回调参数
	 * @return
	 */
	public String getTradeNo(Map<String, String> params); 
}	
