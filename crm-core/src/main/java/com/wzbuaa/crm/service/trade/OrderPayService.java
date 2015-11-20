package com.wzbuaa.crm.service.trade;

import static com.wzbuaa.crm.domain.Constants.ORDER_EXPIRED_HOURS;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wzbuaa.crm.bean.ConsumeStatus;
import com.wzbuaa.crm.bean.PayStatus;
import com.wzbuaa.crm.domain.SnCreateType;
import com.wzbuaa.crm.domain.crm.MemberDomain;
import com.wzbuaa.crm.domain.trade.OrderDomain;
import com.wzbuaa.crm.domain.trade.OrderPayDomain;
import com.wzbuaa.crm.exception.ApplicationException;
import com.wzbuaa.crm.plugin.payment.PaymentPluginBundle;
import com.wzbuaa.crm.repository.trade.OrderPayRepository;
import com.wzbuaa.crm.service.BaseService;
import com.wzbuaa.crm.service.base.SnCreaterService;
import com.wzbuaa.crm.service.crm.CreditsDetailService;
import com.wzbuaa.crm.service.crm.MemberService;
import com.wzbuaa.crm.service.crm.PaymentLogService;
/**
 * 
 * @author fangchen
 *
 */
@Service
public class OrderPayService extends BaseService<OrderPayDomain, Long> {
	
	@Resource private OrderService orderService;
	@Resource private MemberService memberService;
	@Resource private PayDetailService paymentDetailService;
	@Resource private CreditsDetailService creditsDetailService;
	@Resource private SnCreaterService snCreaterService;
	@Resource private PaymentLogService paymentLogService;
	@Resource private PaymentPluginBundle paymentPluginBundle;
	@Resource private OrderPayRepository orderPayRepository;

	/**
	 * 根据交易编号和会员获取订单编号
	 * @param tradeNo
	 * @param memberId
	 * @return
	 */
	public String getOrderNoByTradeNo(String tradeNo, Long memberId) {
		OrderPayDomain orderPay = orderPayRepository.findByTradeNoAndMember(tradeNo, memberId);
		if(orderPay == null){
			return null;
		}
		return orderPay.getOrderNo();
	}

	/**
	 * 将订单的付款记录失效
	 * @param orderPay
	 * @param except
	 */
	public void expiryOrderPay(OrderDomain order){
		if(order == null){
			return;
		}
		OrderPayDomain orderPay = order.getOrderPay();
		if(order.getConsumeStatus() == ConsumeStatus.Create || orderPay != null) { 
			delete(orderPay);
		}
	}

	/**
	 * 创建新的付款记录(未关联订单)
	 * @param member 会员
	 * @return
	 */
	public OrderPayDomain createOrderPay(MemberDomain member){
		OrderPayDomain orderPay = new OrderPayDomain();
		orderPay.setTradeNo(snCreaterService.create(SnCreateType.ORDERPAY));
		orderPay.setExpired(DateUtils.addHours(new Date(), ORDER_EXPIRED_HOURS));
		orderPay.setMember(member);
		orderPay.setPayStatus(PayStatus.Create);
		orderPay.setIsPay(true);
		save(orderPay);
		return orderPay;
	}
	
	/**
	 * 修改付款记录
	 * @param orderPay
	 * @return
	 */
	private OrderPayDomain modifyOrderPay(OrderPayDomain orderPay){
		if(orderPay == null){
			return null;
		}
		orderPay.setTradeNo(snCreaterService.create(SnCreateType.ORDERPAY));
		orderPay.setPayStatus(PayStatus.Create);
		orderPay.setExpired(DateUtils.addHours(new Date(), ORDER_EXPIRED_HOURS));
		update(orderPay);
		return orderPay;
	}

//	public OrderPayDomain getOrderPayByOrderNo(String orderNo, Long memberId, boolean isIgnoreFreeze) {
//		OrderDomain order = orderService.getOrderByOrderNo(orderNo, memberId);
//		if(order == null){
//			throw new ApplicationException("交易记录不存在!");
//		}
//		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);
//		MemberDomain member = memberService.findOne(memberId);
//		if(member == null){
//			throw new ApplicationException("会员不存在!");
//		}
//		OrderPayDomain orderPay = order.getOrderPay();
//		if(orderPay == null){//付款记录不存在，付款记录为合并支付记录，则生成新的付款记录
//			expiryOrderPay(orderPay, order);
//			orderPay = createOrderPay(member);
//			order.setOrderPay(orderPay);
//			orderDao.merge(order);
//			
//			Set<OrderDomain> orderSet = new HashSet<OrderDomain>();
//			orderSet.add(order);
//			orderPay.setOrderSet(orderSet);
//		}else if(OrderPayStatus.Freeze == orderPay.getOrderPayStatus() && !isIgnoreFreeze){//付款记录已冻结且不忽略冻结状态，则生成新的付款编码，更新付款记录
//			orderPay = modifyOrderPay(orderPay);
//		}
//		return orderPay;
//	}
//
//	public PayParams getOrderPayParams(String tradeNo, String orderNo, Long memberId) {
//		PayParams payParams = new PayParams();
//		payParams.setHadPay(false);
//		OrderPayDomain orderPay = getByMemberId(tradeNo, memberId);
//		if(orderPay == null){//通过付款记录编号，无法获取到付款记录
//			if(StringUtils.isNotBlank(orderNo)){//交易编号不为空，则根据交易编号获取付款记录，若交易记录关联付款记录已冻结，则生成新的付款记录
//				orderPay = getOrderPayByOrderNo(orderNo, memberId, false);
//			}
//			if(orderPay == null){
//				payParams.setError("交易记录不存在!");
//				return payParams;
//			}
//		}
//		if(orderPay.getOrderPayStatus() == OrderPayStatus.Finish){//付款记录已完成付款
//			payParams.setHadPay(true);
//			payParams.setTradeNo(orderPay.getTradeNo());
//			return payParams;
//		}else if(orderPay.getOrderPayStatus() == OrderPayStatus.Freeze){//付款记录已冻结，则生成新的付款编码，更新付款记录
//			orderPay = modifyOrderPay(orderPay);
//			payParams.setTradeNo(orderPay.getTradeNo());
//		}
//		BigDecimal totalAmount = BigDecimal.ZERO;
//		BigDecimal deliveryPrice = BigDecimal.ZERO;
//		String title = null;
//		Set<OrderDomain> orderSet = orderPay.getOrderSet();
//		if(orderSet == null || orderSet.isEmpty()){//若付款记录包含交易记录为空，则付款记录失效
//			expiryOrderPay(orderPay, null);
//			payParams.setError("交易记录不存在!");
//			return payParams;
//		}
//		boolean canUseCredits = true;
//		int num = 0;
//		JSONArray payInfo = new JSONArray();
//		for(OrderDomain order : orderSet){
//			orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);
//			if(title == null){
//				title = order.getTitle();
//			}
//			if((order.getExpired() != null && order.getExpired().compareTo(new Date()) < 0)
//					|| RecordType.refund == order.getRecordType()
//					|| PaymentStatus.onlinePayment != order.getPaymentStatus()
//					|| ConsumeStatus.Create != order.getConsumeStatus()){//将无效的交易记录移出付款记录
//				order.setOrderPay(null);
//				orderService.update(order);
//				continue;
//			}
//			if(order.getRecordType() == RecordType.fund){//我的钱包交易记录不能使用积分
//				canUseCredits = false;
//			}
//			totalAmount = totalAmount.add(order.getAmount());
//			if(order.getDeliveryPrice() != null){
//				deliveryPrice = deliveryPrice.add(order.getDeliveryPrice());
//			}
//			num++;
//			payParams.setOrderNo(order.getOrderNo());
//			payInfo.addAll(order.getPayInfo());
//		}
//		if(totalAmount.signum() <= 0){//交易总额小于等于0，则无法进行交易
//			expiryOrderPay(orderPay, null);
//			payParams.setError("交易记录不存在或已过期!");
//			return payParams;
//		}
//		if(num > 1){//合并支付
//			title = title + "等";
//			payParams.setIsMerge(true);
//			payParams.setOrderNo(null);
//		}else{
//			payParams.setIsMerge(false);
//		}
//		Setting setting = SettingUtils.get();
//		BigDecimal goodsAmount = totalAmount.subtract(deliveryPrice);
//		payParams.setTradeNo(orderPay.getTradeNo());
//		payParams.setTitle(title);
//		payParams.setGoodsAmount(goodsAmount);
//		payParams.setDeliveryAmount(deliveryPrice);
//		payParams.setTotalAmount(totalAmount);
//		payParams.setBody(payInfo.toString());
//		payParams.setCredits(orderPay.getCredits());
//		payParams.setCreditsRate(setting.getCreditsRate());
//		payParams.setIsGroupOrder(orderPay.isGroupOrder());
//		payParams.setIsFundOrder(orderPay.isFundOrder());
//		if(canUseCredits){//积分使用规则：商品总额*积分使用比例*积分汇率
//			payParams.setCreditsLimit(goodsAmount.multiply(setting.getCreditsLimit()).multiply(new BigDecimal(setting.getCreditsRate())).intValue());
//		}else{
//			payParams.setCreditsLimit(0);
//		}
//		return payParams;
//	}
//
//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public JSONObject payOrder(PayInfo payInfo){
//		JSONObject jObj = new JSONObject();
//		OrderPayDomain orderPay = freezeOrderPay(payInfo);
//		if(orderPay.getOrderPayStatus() == OrderPayStatus.Finish){
//			jObj.put("isFinish", true);
//			return jObj;
//		}
//		jObj.put("tradeNo", orderPay.getTradeNo());
//		BigDecimal cash = orderPay.getCash();
//		if(cash.signum() > 0){//需要支付现金
//			String bankInfo = payInfo.getBankInfo();
//			if(StringUtils.isBlank(bankInfo)){
//				throw createWebApplicationException("请选择支付方式!");
//			}
//			int orderType = 0;
//			if(orderPay.isFundOrder()){
//				orderType = 3;
//			}else if(orderPay.isGroupOrder()){
//				orderType = 2;
//			}
//			if(!checkPayType(payInfo.getSystemId(), bankInfo, orderType)){
//				throw createWebApplicationException("支付方式不存在");
//			}
//			JSONObject config = SettingUtils.getDictionaryConfig(bankInfo);
//			if(config == null || config.get("payType") == null){
//				throw createWebApplicationException("支付方式配置有误!");
//			}
//			ComponentDomain component = componentService.get("componentId", config.getString("payType"));
//			if(component == null){
//				throw createWebApplicationException("交易记录无法支付!");
//			}
//			List<IPlugin> pluginList = null;
//			switch(payInfo.getSystemId()){
//				case WAP : pluginList = wapPaymentPluginBundle.getPluginList();break;
//				default : pluginList = paymentPluginBundle.getPluginList();
//				
//			}
//			for(IPlugin plugin : pluginList){
//				if (plugin.getId().equals(component.getComponentId())) {
//					IOnlinePayEvent pay = (IOnlinePayEvent) plugin;
//					String result;
//					try {
//						Object obj = config.get("code");
//						if(obj == null){
//							obj = "";
//						}
//						result = pay.onPay(component, orderPay, obj.toString());
//					} catch (Exception e) {
//						throw createWebApplicationException("交易记录无法支付!");
//					}
//					if("error".equals(result)){
//						throw createWebApplicationException("交易记录无法支付!");
//					}
//					jObj.put("form", result);
//					jObj.put("requestType", pay.getRequstType());
//					jObj.put("encode", pay.getEncode());
//					return jObj;
//				}
//			}
//			throw createWebApplicationException("交易记录无法完成支付!");
//		} else {//不需要现金支付或货到付款，则直接完成支付
//			String error = pay(payInfo.getTradeNo(), null, null);
//			if(StringUtils.isNotBlank(error)){
//				throw createWebApplicationException(error);
//			}
//			jObj.put("isFinish", true);
//			return jObj;
//		}
//	}
//
//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public OrderPayDomain freezeOrderPay(PayInfo payInfo) {
//		OrderPayDomain orderPay = null;
//		if(StringUtils.isNotBlank(payInfo.getTradeNo())){
//			orderPay = getByMemberId(payInfo.getTradeNo(), payInfo.getMemberId());
//		}
//		if(orderPay == null && StringUtils.isNotBlank(payInfo.getOrderNo())){
//			orderPay = getOrderPayByOrderNo(payInfo.getOrderNo(), payInfo.getMemberId(), true);
//		}
//		if(orderPay == null){
//			throw new ApplicationException("交易记录不存在!");
//		}
//		orderPayDao.lock(orderPay, LockModeType.PESSIMISTIC_WRITE);
//		if(orderPay.getOrderPayStatus() != OrderPayStatus.Create){
//			return orderPay;
//		}
//		payInfo.setTradeNo(orderPay.getTradeNo());
//		Set<OrderDomain> orderSet = orderPay.getOrderSet();
//		if(orderSet.isEmpty()){
//			throw new ApplicationException("交易记录不存在!");
//		}
//		BigDecimal total = BigDecimal.ZERO;
//		BigDecimal deliveryPrice = BigDecimal.ZERO;
//		for(OrderDomain order : orderSet){
//			if(order.getConsumeStatus() != ConsumeStatus.Create){
//				continue;
//			}
//			total = total.add(order.getAmount());
//			if(order.getDeliveryPrice() != null){
//				deliveryPrice = deliveryPrice.add(order.getDeliveryPrice());
//			}
//		}
//		total = total.subtract(deliveryPrice);
//		List<IPlugin> pluginList = orderPayPluginBundle.getPluginList();
//		for(IPlugin plugin : pluginList){
//			IOrderPayEvent orderPayEvent = (IOrderPayEvent) plugin;
//			total = orderPayEvent.beforePay(orderPay, orderPay.getMember(), total, total.subtract(deliveryPrice), deliveryPrice, payInfo);
//			if(total == null){
//				throw createWebApplicationException(orderPayEvent.getPayError());
//			}
//		}
//		orderPay.setCash(total);
//		orderPay.setOrderPayStatus(OrderPayStatus.Freeze);
//		update(orderPay);
//		return orderPay;
//	}
//
//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public String pay(String tradeNo, PaymentTypeDomain paymentType, String dealId) {
//		OrderPayDomain orderPay = get("tradeNo", tradeNo);
//		if(orderPay == null){
//			return "交易记录不存在!";
//		}
//		orderPayDao.lock(orderPay, LockModeType.PESSIMISTIC_WRITE);
//		if(orderPay.getOrderPayStatus() == OrderPayStatus.Finish){
//			return null;
//		}
//		List<OrderDomain> list = orderService.getList("orderPay.tradeNo", tradeNo);
//		if(list.isEmpty()){
//			return "交易信息有误!";
//		}
//		MemberDomain member = orderPay.getMember();
//		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
//		List<IPlugin> pluginList = orderPayPluginBundle.getPluginList();
//		for(IPlugin plugin : pluginList){
//			IOrderPayEvent orderPayEvent = (IOrderPayEvent) plugin;
//			String error = orderPayEvent.afterPay(list, orderPay, member);
//			if(StringUtils.isNotBlank(error)){
//				return error;
//			}
//		}
//		OrderBean[] orderBeans = new OrderBean[list.size()];
//		BigDecimal preDeposit = member.getDeposit().add(orderPay.getDeposit()).add(orderPay.getCash());
//		for(int i = 0;i < list.size();i++){
//			OrderDomain order = list.get(i);
//			orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);
//			if(order.getConsumeStatus() == ConsumeStatus.Create){
//				order.setExpired(null);
//				order.setConsumeStatus(ConsumeStatus.Over);
//				order.setTurnoverTime(new Date());
//				order.setTradeNo(tradeNo);
//				order.setOrderPay(orderPay);
//				orderService.update(order);
//				
//				BigDecimal total = BigDecimal.ZERO;
//				if(order.getCash() != null){
//					total = total.add(order.getCash());
//				}
//				if(order.getDeposit() != null){
//					total = total.add(order.getDeposit());
//				}
//				if(total.signum() > 0){
//					preDeposit = preDeposit.subtract(total);
//					PaymentDetailDomain paymentDetail = new PaymentDetailDomain();
//					paymentDetail.setPaymentNo(SerialNumberUtil.buildPaymentSn());
//					paymentDetail.setDetailType(DetailType.pay);
//					paymentDetail.setBalance(preDeposit);
//					paymentDetail.setAmount(total.negate());
//					paymentDetail.setOperator(member.getUsername());
//					paymentDetail.setOrder(order);
//					paymentDetail.setMember(member);
//					paymentDetail.setRemarks(order.getTitle());
//					if(paymentType != null){
//						order.setPaymentType(paymentType);
//						order.setPaymentTypeName(paymentType.getName());
//					}
//					paymentDetail.setDealId(dealId);
//					paymentDetail.setPaymentTypeName("余额付款");
//					order.setPaymentTypeName("余额付款");
//					paymentDetailService.persist(paymentDetail);
//				}
//				if(order.getRecordType() == RecordType.fund){
//					fundIncomeService.saveIncome(order, member);
//				}else{
//					OrderBean orderBean = new OrderBean();
//					orderBean.setOrderNo(order.getOrderNo());
//					orderBean.setAmount(order.getAmount());
//					orderBean.setCredits(order.getCredits());
//					orderBean.setDeposit(order.getDeposit());
//					orderBean.setCash(order.getCash());
//					orderBean.setMemberId(member.getId());
//					orderBean.setNotifyUrl(order.getNotifyUrl());
//					orderBean.setMobile(member.getMobile());
//					orderBeans[i] = orderBean;
//				}
//			}
//		}
//		memberService.update(member);
//		orderPay.setOrderPayStatus(OrderPayStatus.Finish);
//		orderPay.setExpired(null);
//		update(orderPay);
//		if(orderPay.getCredits() != null && orderPay.getCredits() > 0){
//			CreditsDetailDomain detail = new CreditsDetailDomain();
//			detail.setAmount(-orderPay.getCredits());
//			detail.setCreditsType(CreditsType.cost);
//			detail.setMember(member);
//			detail.setRemaind(member.getCredits());
//			detail.setRemark("购物抵用");
//			detail.setOperator(member.getUsername());
//			creditsDetailService.persist(detail);
//		}
//		for(OrderBean orderBean : orderBeans){
//			if(orderBean != null && StringUtils.isNotBlank(orderBean.getNotifyUrl())){
//				WebClientUtils.post(orderBean.getNotifyUrl(), orderBean, void.class);
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 发货，用于支付宝担保交易
//	 * @param tradeNo 交易编号
//	 * @param pluginId 支付插件id
//	 * @param pay 支付插件
//	 */
//	@Override
//	public void send(String tradeNo, String pluginId, IPaymentEvent pay) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>();
//		OrderPayDomain orderPay = get("tradeNo", tradeNo);
//		orderPayDao.lock(orderPay, LockModeType.PESSIMISTIC_WRITE);
//		if(orderPay.getIsSend() == null || !orderPay.getIsSend()){
//			map.put("orderNos", orderPay.getOrderNos());
//			Boolean flag = WebClientUtils.post(RestfulSysType.SHOP, "shop.isNeedShip", null, map, null, Boolean.class);
//			if(!flag){
//				ComponentDomain component = componentService.get("componentId", pluginId);
//				PaymentLogDomain log = paymentLogService.get("orderNo", tradeNo);
//				if(component != null && log != null){
//					OrderBean orderBean = new OrderBean();
//					orderBean.setOrderNo(log.getDealId());
//					pay.onShip(component, orderBean);
//				}
//			}
//		}
//	}
//
//	/**
//	 * 获取会员充值表单
//	 * @param payInfo 充值信息
//	 * @return
//	 */
//	public String getRechargeForm(PayInfo payInfo){
//		BigDecimal amount = payInfo.getCash();
//		if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
//			throw createWebApplicationException("充值金额错误!");
//		}
//		if(payInfo.getMemberId() == null){
//			throw createWebApplicationException("会员不存在!");
//		}
//		if(payInfo.getSystemId() == null){
//			throw createWebApplicationException("系统来源不存在!");
//		}
//		String bankInfo = payInfo.getBankInfo();
//		if(StringUtils.isBlank(bankInfo)){
//			throw createWebApplicationException("请选择支付方式!");
//		}
//		if(!checkPayType(payInfo.getSystemId(), bankInfo, 1)){
//			throw createWebApplicationException("支付方式不存在");
//		}
//		JSONObject config = SettingUtils.getDictionaryConfig(bankInfo);
//		if(config == null || config.get("payType") == null){
//			throw createWebApplicationException("支付方式配置有误!");
//		}
//		ComponentDomain component = componentService.get("componentId", config.getString("payType"));
//		if(component == null){
//			throw createWebApplicationException("当前无法进行会员充值!");
//		}
//		List<IPlugin> pluginList = null;
//		switch(payInfo.getSystemId()){
//			case WAP : pluginList = wapPaymentPluginBundle.getPluginList(); break;
//			default : pluginList = paymentPluginBundle.getPluginList();
//		}
//		for(IPlugin plugin : pluginList){
//			if (plugin.getId().equals(component.getComponentId())) {
//				MemberDomain member = memberService.find(payInfo.getMemberId());
//				IOnlinePayEvent pay = (IOnlinePayEvent) plugin;
//				OrderPayDomain orderPay = new OrderPayDomain();
//				orderPay.setTradeNo(SerialNumberUtil.buildRechargeSn());
//				orderPay.setMember(member);
//				orderPay.setCash(amount);
//				orderPay.setCreateDate(new Date());
//				String result;
//				try {
//					Object obj = config.get("code");
//					if(obj == null){
//						obj = "";
//					}
//					result = pay.onPay(component, orderPay, obj.toString());
//				} catch (Exception e) {
//					throw createWebApplicationException("当前无法进行会员充值!");
//				}
//				if("error".equals(result)){
//					throw createWebApplicationException("当前无法进行会员充值!");
//				}
//				JSONObject jObj = new JSONObject();
//				jObj.put("form", result);
//				jObj.put("requestType", pay.getRequstType());
//				jObj.put("encode", pay.getEncode());
//				return jObj.toString();
//			}
//		}
//		throw createWebApplicationException("当前无法进行会员充值!");
//	}
//	
//	/**
//	 * 校验付款方式是否正确
//	 * @param systemID 系统来源
//	 * @param payType 付款方式
//	 * @param orderType 订单类型：1.充值 2.团购 3.我的钱包
//	 * @return
//	 */
//	private boolean checkPayType(SystemID systemID, String payType, Integer orderType){
//		if(systemID == null){
//			return false;
//		}
//		switch(systemID){
//			case WAP :
//				if(StringUtils.isNotBlank(payType)
//						&& (payType.indexOf("100003") != 0 || payType.length() == 6)){
//					return false;
//				}
//				break;
//			default : 
//				if(StringUtils.isNotBlank(payType)
//						&& (payType.indexOf("100002") != 0 || payType.length() == 6)){
//					return false;
//				}
//		}
//		JSONObject jObj = SettingUtils.getDictionaryConfig(payType);
//		if(jObj == null){
//			return false;
//		}
//		switch(orderType){
//			case 1 : return jObj.get("canRecharge") != null && "true".equals(jObj.getString("canRecharge"));
//			case 2 : return jObj.get("canGroup") != null && "true".equals(jObj.getString("canGroup"));
//			case 3 : return jObj.get("canFund") != null && "true".equals(jObj.getString("canFund"));
//		}
//		return false;
//	}
//
//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public PayInfo checkOrderPayInfo(PayInfo payInfo, Boolean ignorePwd, Boolean ignoreOnline) {
//		if(payInfo.getMemberId() == null){
//			throw createWebApplicationException("会员不存在");
//		}
//		MemberDomain member = memberService.find(payInfo.getMemberId());
//		if(member == null){
//			throw createWebApplicationException("会员不存在");
//		}
//		if(StringUtils.isBlank(payInfo.getTradeNo())){
//			throw createWebApplicationException("交易记录不存在");
//		}
//		if(payInfo.getSystemId() == null){
//			throw createWebApplicationException("系统来源不能为空");
//		}
//		PayParams payParams = getOrderPayParams(payInfo.getTradeNo(), payInfo.getOrderNo(), payInfo.getMemberId());
//		if(StringUtils.isNotBlank(payParams.getError())){
//			throw createWebApplicationException(payParams.getError());
//		}
//		payInfo.setTradeNo(payParams.getTradeNo());
//		payInfo.setOrderNo(payInfo.getOrderNo());
//		BigDecimal total = payParams.getTotalAmount();
//		if(payInfo.getCredits() != null){
//			Integer limit = payParams.getCreditsLimit();
//			if(payInfo.getCredits() > limit){
//				throw createWebApplicationException("积分使用不正确，最多能使用" + limit);
//			}
//			if(member.getCredits() == null && payInfo.getCredits() > 0
//					|| member.getCredits() != null && member.getCredits() < payInfo.getCredits()){
//				throw createWebApplicationException("会员积分不足");
//			}
//			total = total.subtract(new BigDecimal(payInfo.getCredits()).divide(new BigDecimal(payParams.getCreditsRate()), 2, BigDecimal.ROUND_DOWN));
//			total = total.setScale(2, BigDecimal.ROUND_DOWN);
//		}
//		if(member.getDeposit() != null){
//			BigDecimal limit = member.getDeposit();
//			if(limit.compareTo(total) > 0){
//				limit = total;
//			}
//			payInfo.setDeposit(limit);
//			total = total.subtract(limit);
//			total = total.setScale(2, BigDecimal.ROUND_DOWN);
//		}
//		if(payInfo.needPwd() && !ignorePwd){
//			if(StringUtils.isBlank(payInfo.getPayPassword())
//					|| !DigestUtils.md5Hex(payInfo.getPayPassword()).equals(member.getPay_passwd())){
//				throw createWebApplicationException("支付密码错误");
//			}
//		}
//		payInfo.setCash(total);
//		if(!ignoreOnline){
//			int orderType = 0;
//			if(payParams.getIsFundOrder()){
//				orderType = 3;
//			}else if(payParams.getIsGroupOrder()){
//				orderType = 2;
//			}
//			if(total.signum() > 0 
//					&& (StringUtils.isBlank(payInfo.getBankInfo()) 
//							|| !checkPayType(payInfo.getSystemId(), payInfo.getBankInfo(), orderType))){
//				throw createWebApplicationException("请选择支付方式");
//			}
//		}
//		return payInfo;
//	}

	/**
	 * 根据交易编号获取订单信息
	 * @param memberId 会员id
	 * @param tradeNo 交易编号
	 * @return
	 */
	public String getOrderInfoByTradeNo(Long memberId, String tradeNo) {
		OrderPayDomain orderPay = orderPayRepository.findByTradeNoAndMemberAndPayStatus(tradeNo, memberId, PayStatus.Finish);
		if(orderPay == null){
			throw new ApplicationException("交易记录不存在");
		}
		OrderDomain order = orderPay.getOrder();
		BigDecimal total = order.getAmount();
		JSONObject jObj = new JSONObject();
		jObj.put("orderNo", order.getOrderNo());
		jObj.put("amount", order.getAmount());
		jObj.put("items", order.getPayInfo());
		
		JSONObject info = new JSONObject();
		info.put("detail", jObj);
		info.put("amount", total);
		if(orderPay.isShopOrder()){
			info.put("isShopOrder", true);
		} else {
			info.put("isRechargeOrder", true);
		}
		return info.toString();
	}

//	@Override
//	public String wapPayOrder(PayInfo payInfo) {
//		if(payInfo.getSystemId() == null){
//			throw new ApplicationException("系统来源不存在");
//		}
//		if(payInfo.getMemberId() == null){
//			throw createWebApplicationException("会员不存在");
//		}
//		MemberDomain member = memberService.find(payInfo.getMemberId());
//		if(member == null){
//			throw createWebApplicationException("会员不存在");
//		}
//		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
//		payInfo.setDeposit(member.getDeposit());
//		return payOrder(payInfo).toString();
//	}
}
