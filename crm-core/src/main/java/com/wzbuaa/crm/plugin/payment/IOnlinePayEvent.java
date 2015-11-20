package com.wzbuaa.crm.plugin.payment;

import java.math.BigDecimal;
import java.util.Date;

import com.wzbuaa.crm.domain.base.PluginDomain;
import com.wzbuaa.crm.domain.trade.OrderDomain;
import com.wzbuaa.crm.domain.trade.OrderPayDomain;

/**
 * 在线支付事件
 *
 */
public interface IOnlinePayEvent {
	
	/**
	 * 生成跳转至第三方支付平台支付的html和脚本
	 * @param payment
	 * @param order
	 * @param obj 业务系统订单详情
	 * @param bankInfo 银行信息
	 * @return 跳转到第三方支付平台支付的html和脚本
	 * @exception
	 */
	public String onPay(PluginDomain plugin, OrderPayDomain orderPay, String bankInfo) throws Exception;

	/**
	 * 生成跳转至第三方支付平台退款的html和脚本
	 * @param component
	 * @param order
	 * @param total
	 * @return 跳转到第三方支付平台退款的html和脚本
	 * @exception
	 */
	public String onRefund(PluginDomain plugin, OrderDomain order, BigDecimal total) throws Exception;

	/**
	 * 导入对账数据
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 */
	public void onBeforeCheck(Date startDate, Date endDate);
	
	/**
	 * 跳转方式
	 * @return
	 */
	public String getRequstType();
	
	/**
	 * 获取编码格式
	 * @return
	 */
	public String getEncode();
	
}	
