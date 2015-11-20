package com.wzbuaa.crm.plugin.payment.alipay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import com.wzbuaa.crm.bean.OrderBean;
import com.wzbuaa.crm.domain.base.PluginDomain;
import com.wzbuaa.crm.domain.trade.OrderDomain;
import com.wzbuaa.crm.domain.trade.OrderPayDomain;
import com.wzbuaa.crm.plugin.payment.AbstractPaymentPlugin;
import com.wzbuaa.crm.plugin.payment.IPaymentEvent;
import com.wzbuaa.crm.service.base.PluginService;

import framework.util.AppContext;


/**
 * 支付宝担保交易插件 
 *
 */
public class AlipayAssurePlugin extends AbstractPaymentPlugin implements IPaymentEvent {
	
	@Resource PluginService pluginService;

	@SuppressWarnings("unchecked")
	@Override
	public String onPay(PluginDomain plugin, OrderPayDomain orderPay, String bankInfo) throws Exception {
		return null;
//		if(!orderPay.isGroupOrder()){
//			return "error";
//		}
//		JSONObject jsonObject = JSONObject.parseObject(plugin.getConfig());//.getConfigJson("payConfig");
//		Map<String, String> params = (Map<String, String>) JSONObject.toJavaObject(jsonObject, Map.class);
//		
//		BigDecimal totalCost = orderPay.getCash();
//		params.put("out_trade_no", orderPay.getTradeNo());
//		params.put("price", totalCost.setScale(2).toString());
//		params.put("quantity", "1");
//		params.put("body", orderPay.getBody());
//		params.put("show_url", orderPay.getDetailUrl());
//		params.put("_input_charset", AlipayConfig.input_charset);
//		params.put("partner", AlipayConfig.partner);
//        params.put("anti_phishing_key", AlipaySubmit.query_timestamp());
//        params.put("exter_invoke_ip", RequestUtils.getIpAddr(AppContext.getRequest()));
//
//		MemberDomain member = orderPay.getMember();
//		params.put("return_url", PAY_SHOW_URL.replace("{memberId}", member.getId().toString()));
//		params.put("notify_url", PAY_NOTIFY_URL.replace("{memberId}", member.getId().toString()));
//		
//		params.put("logistics_fee", "0.00");
//		params.put("logistics_type", "EXPRESS");
//		params.put("logistics_payment", "SELLER_PAY");
//        return AlipaySubmit.buildRequest(params, "get", "确定");
	}
	
	@Override
	public String onCallBack(Long memberId) throws Exception {
//		HttpServletRequest request = AppContext.getRequest();
//		request.setCharacterEncoding("UTF-8");
//		String paymentOrderId = request.getParameter("trade_no");
//		String orderNo = request.getParameter("out_trade_no");
//		List<PaymentLogDomain> logs = paymentLogService.getList("dealId", paymentOrderId);
//		if(!StringUtils.isEmpty(paymentOrderId) && !logs.isEmpty()){
//			for(PaymentLogDomain log : logs){
//				if(orderNo.equals(log.getOrderNo()) && (log.getPaySuccess() == null || !log.getPaySuccess())){
//					return "error";
//				} else if(orderNo.equals(log.getOrderNo()) 
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
	public String onReturn(Long memberId) throws ParseException, Exception {
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
//		if(memberId == null){
//			return "error";
//		}
//		MemberDomain member = memberService.findOne(memberId);
//		if(member == null){
//			return "error";
//		}
//		String orderAmount = params.get("total_fee");
//		BigDecimal amount = new BigDecimal(0);
//		if(!StringUtils.isEmpty(orderAmount)){
//			amount = new BigDecimal(orderAmount);
//		}
//		String notify_time = params.get("notify_time");
//		PluginDomain component = pluginService.get("componentId", getId());
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
//		
//		if(AlipayNotify.verify(params)){//验证成功
//			String trade_status = params.get("trade_status");
//			if("WAIT_SELLER_SEND_GOODS".equals(trade_status)){
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
//				orderPayService.send(tradeNo, getId(), this);
//			}else if(trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")){//卖家发货
//				
//			}else if(trade_status.equals("TRADE_FINISHED")){//买家确认收货
//				
//			}
//			return "success";
//		}else{//验证失败
//			paymentLog.setPaySuccess(false);
//			paymentLogService.persist(paymentLog);
//			return "fail";
//		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String onShip(PluginDomain plugin, OrderBean orderBean) throws Exception {
//		JSONObject jsonObject = component.getConfigJson("shipConfig");
//		if(jsonObject == null){
//			return null;
//		}
//		Map<String, String> params = (Map<String, String>) JSONObject.toBean(jsonObject, Map.class);
//
//		params.put("_input_charset", AlipayConfig.input_charset);
//		params.put("partner", AlipayConfig.partner);
//		params.put("trade_no", orderBean.getOrderNo());
//		params.put("logistics_name", SettingUtils.get().getName());//TODO 快递名称
//		params.put("invoice_no", orderBean.getInvoice_no());
//		params.put("transport_type", "EXPRESS");
//		return AlipaySubmit.buildRequest("", "", params);
		return null;
	}

	@Override
	public String onRefund(PluginDomain plugin, OrderDomain order,
			BigDecimal total) throws Exception {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onBeforeCheck(Date startDate, Date endDate) {
//		ComponentDomain component = pluginService.get("componentId", getId());
//		if(component == null){
//			return;
//		}
//		JSONObject jObj = component.getConfigJson("checkConfig");
//		Map<String, String> params = (Map<String, String>) JSONObject.toBean(jObj, Map.class);
//		params.put("_input_charset", AlipayConfig.input_charset);
//		params.put("partner", AlipayConfig.partner);
//		long num = (endDate.getTime() - startDate.getTime())/1000/3600/24;
//		if(num >= 0){
//			PaymentTypeDomain paymentType = paymentTypeService.get("component.componentId", getId());
//			List<SettleDomain> settleList = new ArrayList<SettleDomain>();
//			for(int i = 0;i <= num;i++){
//				boolean hasNext = true;
//				Integer pageNo = 1;
//				Date settleDate = DateUtils.addDays(startDate, i);
//				params.put("gmt_start_time", DateHelper.date2String(DateHelper.toDayStart(settleDate), "yyyy-MM-dd HH:mm:ss"));
//				params.put("gmt_end_time", DateHelper.date2String(DateHelper.toDayEnd(settleDate), "yyyy-MM-dd HH:mm:ss"));
//				
//				try {
//					while(hasNext){
//						params.put("page_no", pageNo.toString());
//						String sHtmlText = AlipaySubmit.buildRequest("", "", params);
//						StringReader sr = new StringReader(sHtmlText);
//						SAXReader saxReader = new SAXReader();
//						Document doc = saxReader.read(sr);
//						List<Element> resultEleList = doc.selectNodes("alipay/is_success");
//						Element resultEle = resultEleList.get(0);
//						if("F".equals(resultEle.getStringValue())){
//							break;
//						}
//						resultEleList = doc.selectNodes("alipay/response/account_page_query_result/account_log_list/AccountQueryAccountLogVO");
//						for(Element ele : resultEleList){
//							Element subEle = ele.element("sub_trans_code_msg");
//							if(!"交易付款".equals(subEle.getStringValue())){
//								continue;
//							}
//							String tradeNo = ele.element("merchant_out_order_no").getStringValue();
//							Date tradeDate = DateHelper.string2Date(ele.element("trans_date").getStringValue(), "yyyy-MM-dd HH:mm:ss");
//							String dealId = ele.element("trade_no").getStringValue();
//							BigDecimal amount = new BigDecimal(ele.element("total_fee").getStringValue());
//							BigDecimal fee = amount.multiply(new BigDecimal(ele.element("rate").getStringValue())).setScale(2, BigDecimal.ROUND_DOWN);
//							BigDecimal settleAmount = amount.subtract(fee);
//							settleList.add(new SettleDomain(tradeNo, settleDate, tradeDate, dealId, amount, fee, settleAmount, paymentType));
//						}
//						resultEleList = doc.selectNodes("alipay/response/account_page_query_result/has_next_page");
//						resultEle = resultEleList.get(0);
//						hasNext = "T".equals(resultEle.getStringValue());
//						pageNo++;
//					}
//				} catch (Exception e) {
//					continue;
//				}
//			}
//			settleService.saveSettleList(settleList);
//		}
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
		return "alipayAssurePlugin";
	}

	@Override
	public String getName() {
		return "支付宝担保交易接口";
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
	public String getTradeNo() {
		return AppContext.getRequest().getParameter("out_trade_no");
	}

	@Override
	public void perform(Object... params) {
		// TODO Auto-generated method stub
		
	}

}
