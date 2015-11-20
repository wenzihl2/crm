package com.wzbuaa.crm.plugin.payment.alipay;

import java.math.BigDecimal;
import java.util.Date;

import com.wzbuaa.crm.domain.base.PluginDomain;
import com.wzbuaa.crm.domain.trade.OrderDomain;
import com.wzbuaa.crm.domain.trade.OrderPayDomain;
import com.wzbuaa.crm.plugin.payment.AbstractPaymentPlugin;
import com.wzbuaa.crm.plugin.payment.IPaymentEvent;

import framework.util.AppContext;


/**
 * 支付宝即时到账插件 
 *
 */
public class AlipayDirectPlugin extends AbstractPaymentPlugin implements IPaymentEvent {

	@SuppressWarnings("unchecked")
	@Override
	public String onPay(PluginDomain plugin, OrderPayDomain orderPay, String bankInfo) throws Exception {
//		JSONObject jsonObject = plugin.getConfigJson("payConfig");
//		Map<String, String> params = (Map<String, String>) JSONObject.toBean(jsonObject, Map.class);
//
//		BigDecimal totalCost = orderPay.getCash();
//
//		params.put("out_trade_no", orderPay.getTradeNo());
//		params.put("body", orderPay.getBody());
//		params.put("_input_charset", AlipayConfig.input_charset);
//		params.put("partner", AlipayConfig.partner);
//        params.put("total_fee", totalCost.setScale(2).toString());
//        params.put("anti_phishing_key", AlipaySubmit.query_timestamp());
//        params.put("exter_invoke_ip", AppContext.getRemoteIp());
//
//		MemberDomain member = orderPay.getMember();
//		params.put("return_url", PAY_SHOW_URL.replace("{memberId}", member.getId().toString()));
//		params.put("notify_url", PAY_NOTIFY_URL.replace("{memberId}", member.getId().toString()));
//
//        return AlipaySubmit.buildRequest(params, "get", "确定");
		return null;
	}
	
	@Override
	public String onCallBack(Long memberId) throws Exception {
//		HttpServletRequest request = AppContext.getRequest();
//		request.setCharacterEncoding("UTF-8");
//		String paymentOrderId = request.getParameter("trade_no");
//		String orderNo = request.getParameter("out_trade_no");
//		List<PaymentLogDomain> logs = paymentLogService.getList("dealId", paymentOrderId);
//		if(!StringUtils.isEmpty(paymentOrderId) 
//				&& !logs.isEmpty()){
//			for(PaymentLogDomain log : logs){
//				if(orderNo.equals(log.getOrderNo()) 
//						&& (log.getPaySuccess() == null || !log.getPaySuccess())){
//					return "error";
//				}else if(orderNo.equals(log.getOrderNo()) 
//						&& (log.getPaySuccess() != null && log.getPaySuccess())){
//					return "result";
//				}
//			}
//		}else if(!StringUtils.isEmpty(paymentOrderId)){
//			return onReturn(memberId);
//		}
		return null;
	} 

	@SuppressWarnings("unchecked")
	@Override
	public String onReturn(Long memberId) throws Exception {
		return null;
//		HttpServletRequest request = AppContext.getRequest();
//		//获取支付宝POST过来反馈信息
//		Map<String,String> params = new HashMap<String,String>();
//		Map<String, String[]> requestParams = request.getParameterMap();
//		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = iter.next();
//			String[] values = requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			}
//			params.put(name, valueStr);
//		}
//
//		String tradeNo = params.get("out_trade_no");
//		String trade_no = params.get("trade_no");
//
//		List<PaymentLogDomain> logs = paymentLogService.getList("dealId", trade_no);
//		for(PaymentLogDomain log : logs){
//			if(tradeNo.equals(log.getOrderNo()) 
//					&& log.getPaySuccess() != null
//					&& log.getPaySuccess()){
//				return "success";
//			}else if(tradeNo.equals(log.getOrderNo())){
//				return "fail";
//			}
//		}
//
//		String orderAmount = params.get("total_fee");
//		BigDecimal amount = new BigDecimal(0);
//		if(!StringUtils.isEmpty(orderAmount)){
//			amount = new BigDecimal(orderAmount);
//		}
//
//		if(memberId == null){
//			return "error";
//		}
//		MemberDomain member = memberService.findOne(memberId);
//		if(member == null){
//			return "error";
//		}
//		String notify_time = params.get("notify_time");
//		PluginDomain component = componentService.get("componentId", getId());
//		PaymentLogDomain paymentLog = new PaymentLogDomain();
//		paymentLog.setOrderAccount(amount);
//		paymentLog.setOrderNo(tradeNo);
//		paymentLog.setMemberName(member.getUsername());
//		paymentLog.setPayType(component.getPaymemtType());
//		paymentLog.setDealId(trade_no);
//		if(StringUtils.isEmpty(notify_time)){
//			paymentLog.setDealTime(new Date());
//		}else{
//			paymentLog.setDealTime(DateHelper.string2Date(notify_time, "yyyyMMddHHmmss"));
//		}
//		paymentLog.setPayAmount(amount);
//		paymentLogService.persist(paymentLog);//保存支付记录
//		
//		if(AlipayNotify.verify(params)){//验证成功
//			//交易状态
//			String trade_status = params.get("trade_status");
//			if("TRADE_FINISHED".equals(trade_status) 
//					|| "TRADE_SUCCESS".equals(trade_status)){
//				boolean isSuccess = memberService.recharge(tradeNo, component.getPaymemtType(), amount, trade_no, memberId);
//				paymentLog.setPaySuccess(isSuccess);
//				paymentLogService.update(paymentLog);//保存支付记录
//				if(isSuccess && !isRecharge(tradeNo)){
//					String error = orderPayService.pay(tradeNo, component.getPaymemtType(), trade_no);
//					if(StringUtils.isEmpty(error)){
//						paymentLog.setPaySuccess(true);
//						paymentLogService.update(paymentLog);//保存支付记录
//					}else{
//						paymentLog.setPaySuccess(false);
//						paymentLog.setErrorCode(error);
//						paymentLogService.update(paymentLog);//保存支付记录
//					}
//				}
//			}
//			return "success";
//		}else{//验证失败
//			paymentLog.setPaySuccess(false);
//			paymentLogService.update(paymentLog);
//			return "fail";
//		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String onRefund(PluginDomain plugin, OrderDomain order, BigDecimal total)
			throws Exception {
//		JSONObject jsonObject = plugin.getConfigJson("refundConfig");
//		if(jsonObject == null){
//			return "未配置退款参数!";
//		}
//		OrderDomain consumeOrder = order.getParent();
//		Finder finder = Finder.create("from PaymentDetailDomain bean where bean.detailType = :detailType and bean.orderNo = :orderNo");
//		finder.setParam("detailType", DetailType.pay);
//		finder.setParam("orderNo", consumeOrder.getOrderNo());
//		Object obj = finder.findUniqueByHQL();
//		if(obj == null){
//			return "获取退款信息失败";
//		}
//		PaymentDetailDomain detail = (PaymentDetailDomain) obj;
//		String reason = StringUtils.isNotBlank(order.getBody())?order.getBody() : "协商退款";
//		order.setRefundBatchNo(SerialNumberUtil.buildRefundBatchSn());
//		
//		Map<String, String> params = (Map<String, String>) JSONObject.toBean(jsonObject, Map.class);
//		params.put("partner", AlipayConfig.partner);
//		params.put("_input_charset", AlipayConfig.input_charset);
//		params.put("refund_date", DateHelper.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
//		params.put("batch_no", order.getRefundBatchNo());
//		params.put("batch_num", "1");
//		params.put("detail_data", detail.getDealId() + "^" + total + "^" + reason);
//		refundForm = AlipaySubmit.buildRequest(params,"get","确认");
//		order.setRefundOperator(ShopUtil.getUser().getName());
//		orderService.update(order);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String onRefundReturn() throws Exception {
//		HttpServletRequest request = AppContext.getRequest();
//		
//		//获取支付宝POST过来反馈信息
//		Map<String,String> params = new HashMap<String,String>();
//		Map<String, String[]> requestParams = request.getParameterMap();
//		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = iter.next();
//			String[] values = requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			}
//			params.put(name, valueStr);
//		}
//
//		//批量退款数据中的详细信息
//		String result_details = params.get("result_details");
//
//		if(AlipayNotify.verify(params)){//验证成功
//			String[] details = result_details.split("\\^");
//			if(details.length >= 2 && details[2].equals("SUCCESS")){
//				String batch_no = params.get("batch_no");
//				Finder finder = Finder.create("from OrderDomain bean where bean.refundBatchNo = :refundBatchNo");
//				finder.setParam("refundBatchNo", batch_no);
//				Object obj = finder.findUniqueByHQL();
//				if(obj == null){
//					return "fail";
//				}
//				orderService.finishRefund(((OrderDomain) obj).getOrderNo());
//				return "success";
//			}
//		}else{//验证失败
//			return "fail";
//		}
		return "fail";
	}

	@Override
	public void onBeforeCheck(Date startDate, Date endDate) {
		
	}
	
	@Override
	public void register() {
		
	}
	
	@Override
	public String getAuthor() {
		return "fangchen";
	}

	@Override
	public String getId() {
		return "alipayDirectPlugin";
	}

	@Override
	public String getName() {
		return "支付宝即时到帐接口";
	}

	@Override
	public String getType() {
		return "payment";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getTradeNo() throws Exception {
		return AppContext.getRequest().getParameter("out_trade_no");
	}

	@Override
	public void perform(Object... params) {
		// TODO Auto-generated method stub
		
	}

}
