package com.wzbuaa.crm.plugin.payment.alipay;


/**
 * 支付宝手机网站支付插件 
 *
 */
public class AlipayWapPlugin {
//extends AbstractPaymentPlugin implements IPaymentEvent {
//
//	@Override
//	public void register() {
//		
//	}
//
//	@Override
//	public String onCallBack() throws Exception {
//		HttpServletRequest request = AppContext.getRequest();
//		request.setCharacterEncoding("UTF-8");
//		String paymentOrderId = request.getParameter("trade_no");
//		String orderNo = request.getParameter("orderNo");
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
//			return onReturn();
//		}
//		return null;
//	} 
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public String onPay(ComponentDomain component, OrderDomain order, Map<String,Object> additionParams) throws Exception {
//		BigDecimal useDeposit = new BigDecimal(0);
//		JSONObject jsonObject = component.getConfigJson("payConfig");
//		Map<String, String> params = (Map<String, String>) JSONObject.toBean(jsonObject, Map.class);
//
//		BigDecimal totalCost = order != null?order.getCashAmount():new BigDecimal(StringUtil.getNullStr(additionParams.get("totalCost")));
//		if(additionParams.get("useDeposit") != null){
//			useDeposit = new BigDecimal(additionParams.get("useDeposit").toString());
//			totalCost = totalCost.subtract(useDeposit);
//		}
//		
//		if(order != null){
//			params.put("out_trade_no", order.getOrderNo());
//		}else{
//			params.put("out_trade_no", SerialNumberUtil.buildRechargeSn());
//		}
//		
//		String req_dataToken = "<direct_trade_create_req><notify_url>" 
//			+ params.get("notify_url") + "</notify_url><call_back_url>" 
//			+ params.get("call_back_url") + "</call_back_url><seller_account_name>" 
//			+ params.get("seller_account_name") + "</seller_account_name><out_trade_no>" 
//			+ params.get("out_trade_no") + "</out_trade_no><subject>" + params.get("subject") + "</subject><total_fee>" 
//			+ params.get("total_fee") + "</total_fee><merchant_url>" + params.get("merchant_url") + "</merchant_url></direct_trade_create_req>";
//
//        params.put("req_id", UtilDate.getOrderNum());
//        params.put("req_data", req_dataToken);
//        params.put("total_fee", totalCost.setScale(2).toString());
//        params.put("out_user", order.getMember().getId().toString());
//        
//        String url = params.remove("ALIPAY_GATEWAY_NEW");
//      //建立请求
//		String sHtmlTextToken = AlipaySubmit.buildRequest(url, "", "",params);
//		//URLDECODE返回的信息
//		sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,AlipayConfig.input_charset);
//		//获取token
//		String request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);
//		
//		String req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
//		
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//		sParaTemp.put("service", params.get("service"));
//		sParaTemp.put("partner", params.get("partner"));
//		sParaTemp.put("_input_charset", params.get("_input_charset"));
//		sParaTemp.put("sec_id", params.get("sec_id"));
//		sParaTemp.put("format", params.get("format"));
//		sParaTemp.put("v", params.get("v"));
//		sParaTemp.put("req_data", req_data);
//        
//        return AlipaySubmit.buildRequest(url, sParaTemp, "get", "确认");
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public String onReturn() throws ParseException, Exception {
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
//		ComponentDomain component = componentService.get("componentId", getId());
//		String orderNo = params.get("out_trade_no");
//		OrderDomain order = orderService.get("orderNo", orderNo);
//		if(order == null || order.getPayStatus() == PayStatus.pay){
//			return "success";
//		}
//		String orderAmount = getAmount();
//
//		Map<String, Object> addtionParams = new HashMap<String, Object>();
//		BigDecimal payAmount = null;
//		if(StringUtils.isEmpty(orderAmount)){
////			payAmount = order.getCashAmount()
//		}else{
//			payAmount = new BigDecimal(orderAmount);
//		}
//		addtionParams.put("realPay", payAmount);
//		
//		MemberDomain member = order.getMember();
//		return null;
//	}
//	
//	@Override
//	public String getAuthor() {
//		return "fangchen";
//	}
//
//	@Override
//	public String getId() {
//		return "alipayWapPlugin";
//	}
//
//	@Override
//	public String getName() {
//		return "支付宝即时到帐接口";
//	}
//
//	@Override
//	public String getType() {
//		return "payment";
//	}
//
//	@Override
//	public String getVersion() {
//		return "1.0";
//	}
//
//	@Override
//	public void perform(Object... params) {
//		
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public String onRefund(ComponentDomain component, RejectDomain reject)
//			throws Exception {
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public String onRefundReturn() throws Exception {
//		return "error";
//	}
//
//	@Override
//	public String getOrderNo() throws Exception {
//		return AppContext.getRequest().getParameter("out_trade_no");
//	}
//
//	@Override
//	public String getAmount() {
//		return AppContext.getRequest().getParameter("total_fee");
//	}
//
//	@Override
//	public Long getMemberId() {
//		return null;
//	}
//
//	@Override
//	public boolean isRecharge() {
//		return false;
//	}

}
