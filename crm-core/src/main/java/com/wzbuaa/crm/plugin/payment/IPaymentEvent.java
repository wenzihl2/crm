package com.wzbuaa.crm.plugin.payment;

import com.wzbuaa.crm.bean.OrderBean;
import com.wzbuaa.crm.domain.base.PluginDomain;

/**
 * PC端在线支付事件
 *
 */
public interface IPaymentEvent extends IOnlinePayEvent {
	
	/**
	 * 支付成功后异步通知事件的
	 * @param memberId 会员id
	 * @return 返回第三方支付需要的信息
	 */
	public String onCallBack(Long memberId) throws Exception;
	
	/**
	 * 支付成功后返回本站后激发此事件 
	 * @param memberId 会员id
	 * @return  处理结果
	 * @exception
	 */
	public String onReturn(Long memberId) throws Exception;
	
	/**
	 * 确认发货接口（用于支付宝担保交易）
	 * @param component 组件
	 * @param orderBean 订单信息
	 * @exception
	 */
	public String onShip(PluginDomain plugin, OrderBean orderBean) throws Exception;

	/**
	 * 退款成功后返回本站后激发此事件 
	 * @return  要求返回订单sn
	 * @exception
	 */
	public String onRefundReturn() throws Exception;
	
	/**
	 * 获取退款表单
	 * @return
	 */
	public String getRefundForm() throws Exception;

	/**
	 * 获取交易编号
	 * @return
	 */
	public String getTradeNo() throws Exception; 
	
}	
