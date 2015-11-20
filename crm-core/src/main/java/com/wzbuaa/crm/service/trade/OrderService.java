package com.wzbuaa.crm.service.trade;

import org.springframework.stereotype.Service;

import com.wzbuaa.crm.domain.trade.OrderDomain;
import com.wzbuaa.crm.service.BaseService;
/**
 * Service实现类  账户消费记录
 *
 */
@Service
public class OrderService extends BaseService<OrderDomain, Long> {
	
//	@Resource private OrderPayPluginBundle orderPayPluginBundle;
//	@Resource private PaymentDetailService paymentDetailService;
//	@Resource private PaymentPluginBundle paymentPluginBundle;
//	@Resource private MemberService memberService;
//	@Resource private RemitService remitService;
//	@Resource private FundIncomeService fundIncomeService;
//	@Resource private CreditsDetailService creditsDetailService;
//	@Resource private PaymentLogService paymentLogService;
//	@Resource private ComponentService componentService;
//	@Resource private OrderPayService orderPayService;
//	
//	@Transactional(propagation=Propagation.REQUIRED)
//	private void createOrder(OrderBean orderBean, OrderPayDomain orderPay) {
//		if(StringUtils.isBlank(orderBean.getOrderNo())){
//			throw new ApplicationException("订单编号为空!");
//		}
//		if(orderBean.getSystemId() == null){
//			throw new ApplicationException("订单来源为空!");
//		}
//		if(orderBean.getMemberId() == null){
//			throw new ApplicationException("会员不存在!");
//		}
//		MemberDomain member = memberService.findOne(orderBean.getMemberId());
//		if(member == null){
//			throw new ApplicationException("会员不存在!");
//		}
//		if(isExist("orderNo", orderBean.getOrderNo())){
//			throw new ApplicationException("订单【" + orderBean.getOrderNo() + "】已存在!");
//		}
//		OrderDomain order = new OrderDomain(orderBean.getOrderNo(), orderBean.getSystemId(), null, member);
//		order.setAmount(orderBean.getAmount());
//		order.setTitle(orderBean.getTitle());
//		if(orderBean.getExpiredMinutes() != null){
//			order.setExpired(DateUtils.addMinutes(new Date(), orderBean.getExpiredMinutes()));
//		}
//		if(orderBean.getRecordType() == null){
//			throw new ApplicationException("订单类型为空!");
//		}
//		order.setRecordType(orderBean.getRecordType());
//		order.setPaymentStatus(orderBean.getPaymentStatus());
//		order.setSystemId(orderBean.getSystemId());
//		order.setMember(member);
//		order.setConsumeStatus(ConsumeStatus.Create);
//		order.setSupplier_id(orderBean.getSupplier_id());
//		order.setSupplier_name(orderBean.getSupplier_name());
//		order.setDeliveryPrice(orderBean.getDeliveryPrice());
//		order.setNotifyUrl(orderBean.getNotifyUrl());
//		order.setDetailUrl(orderBean.getDetailUrl());
//		order.setShowUrl(orderBean.getShowUrl());
//		order.setBody(orderBean.getBody());
//		order.setOrderPay(orderPay);
//		this.save(order);
//	}
//
//	@Override
//	public String createOrder(OrderBean[] orderBeans, Long member_id, Boolean isMergePay) {
//		MemberDomain member = memberService.findOne(member_id);
//		if(member == null){
//			throw new ApplicationException("会员不存在!");
//		}
//		//创建付款记录
//		OrderPayDomain orderPay = orderPayService.createOrderPay(member);
//		
//		for(int i = 0;i < orderBeans.length;i++){
//			OrderBean orderBean = orderBeans[i];
//			createOrder(orderBean, orderPay);
//		}
//		return orderPay.getTradeNo();
//	}
//
//	@Override
//	public void createRefundOrder(OrderBean orderBean) {
//		if(StringUtils.isBlank(orderBean.getGroupNo())){
//			throw new ApplicationException("消费记录不存在!");
//		}
//		OrderDomain consumeOrder = get("orderNo", orderBean.getGroupNo());
//		if(consumeOrder == null){
//			throw new ApplicationException("消费记录不存在!");
//		}
//		orderDao.lock(consumeOrder, LockModeType.PESSIMISTIC_WRITE);
//		BigDecimal refundAmount = orderBean.getAmount();//退款总额
//		BigDecimal deliveryPrice = orderBean.getDeliveryPrice();//退还运费
//		if(deliveryPrice != null){
//			refundAmount = refundAmount.subtract(deliveryPrice);
//		}
//		
//		OrderDomain order = get("orderNo", orderBean.getOrderNo());
//		if(order == null){
//			order = new OrderDomain(orderBean.getOrderNo(), orderBean.getSystemId(), RecordType.refund, consumeOrder.getMember());
//			order.setPaymentType(consumeOrder.getPaymentType());
//		}else{
//			orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);
//			if(order.getConsumeStatus() != ConsumeStatus.Create
//					&& order.getConsumeStatus() != ConsumeStatus.Close){
//				throw new ApplicationException("退款正在处理中!");
//			}
//		}
//		if(deliveryPrice != null){
//			order.setAmount(refundAmount.add(deliveryPrice));
//		}else{
//			order.setAmount(refundAmount);
//		}
//		order.setTitle("退款");
//		order.setConsumeStatus(ConsumeStatus.Create);
//		order.setParent(consumeOrder);
//		order.setNotifyUrl(orderBean.getNotifyUrl());
//		order.setDetailUrl(orderBean.getDetailUrl());
//		List<IPlugin> pluginList = orderPayPluginBundle.getPluginList();
//		for(IPlugin plugin : pluginList){
//			IOrderPayEvent orderPay = (IOrderPayEvent) plugin;
//			String error = orderPay.beforeRefund(consumeOrder, order, orderBean);
//			if(!StringUtils.isEmpty(error)){
//				throw new ApplicationException(error);
//			}
//		}
//
//		//生成退款单
//		BigDecimal cash = order.getCash();
//		if(cash != null && cash.signum() == 1){
//			RemitDomain remit = new RemitDomain();
//			remit.setAmount(order.getAmount());
//			remit.setRemitStatus(RemitStatus.apply);
//			remit.setOrder_num(order.getOrderNo());
//			remit.setRemitType(RemitType.refund);
//			remit.setReceiver(orderBean.getReceiver());
//			remit.setBank(orderBean.getBank());
//			remit.setCardNo(orderBean.getCardNo());
//			remitService.persist(remit);
//			order.setRemit(remit);
//		}
//		orderDao.merge(order);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("orderNo", orderBean.getGroupNo());
//		params.put("rejectNo", order.getOrderNo());
//		try {
//			oaNotifyService.sendMsg("reject_refund_notify", SystemID.CRM, params);
//		} catch (Exception e) {
//			log.info("OA通知失败!");
//		}
//	}
//
//	public String refund(OrderDomain order){
//		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);
//		if(order.getConsumeStatus() != ConsumeStatus.Create){
//			return null;
//		}
//		RemitDomain remit = order.getRemit();
//		BigDecimal cash = order.getCash();
//		if(remit == null 
//				&& cash != null 
//				&& cash.signum() > 0){
//			PaymentTypeDomain paymentType = order.getPaymentType();
//			if(paymentType != null 
//					&& paymentType.getPaymentStatus() == PaymentStatus.onlinePayment 
//					&& cash != null && cash.signum() == 1){
//				if(paymentType.getComponent() != null){
//					ComponentDomain component = paymentType.getComponent();
//					List<IPlugin> plugins = paymentPluginBundle.getPluginList();
//					for (IPlugin plugin : plugins) {
//						if (plugin.getId().equals(component.getComponentId())) {
//							IPaymentEvent pay = (IPaymentEvent) plugin;
//							try {
//								order.setPaymentTypeName(paymentType.getName());
//								String error = pay.onRefund(component, order, cash);
//								if(!StringUtils.isEmpty(error)){
//									throw createWebApplicationException(error);
//								}
//								return pay.getRefundForm();
//							} catch (Exception e) {
//								throw createWebApplicationException("网银退款失败!");
//							}
//						}
//					}
//				}else{
//					throw createWebApplicationException("网银退款失败!");
//				}
//			}
//		}
//		order.setRefundOperator(ShopUtil.getUser().getName());
//		update(order);
//		finishRefund(order.getOrderNo());
//		return null;
//	}
//
//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public void finishRefund(String orderNo) {
//		OrderDomain order = get("orderNo", orderNo);
//		if(order == null){
//			return;
//		}
//		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);
//		if(order.getConsumeStatus() != ConsumeStatus.Create){
//			return;
//		}
//		MemberDomain member = order.getMember();
//		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
//		List<IPlugin> pluginList = orderPayPluginBundle.getPluginList();
//		for(IPlugin plugin : pluginList){
//			IOrderPayEvent orderPay = (IOrderPayEvent) plugin;
//			String error = orderPay.afterRefund(order, member);
//			if(!StringUtils.isEmpty(error)){
//				throw createWebApplicationException(error);
//			}
//		}
//		if(order.getDeposit() != null 
//				&& order.getDeposit().compareTo(BigDecimal.ZERO) > 0){
//			//保存收支明细
//			PaymentDetailDomain paymentDetail = new PaymentDetailDomain();
//			paymentDetail.setPaymentNo(SerialNumberUtil.buildPaymentSn());
//			paymentDetail.setDetailType(DetailType.refund);
//			paymentDetail.setAmount(order.getDeposit());
//			paymentDetail.setBalance(member.getDeposit());
//			paymentDetail.setOrder(order);
//			paymentDetail.setOperator("系统");
//			paymentDetail.setMember(member);
//			paymentDetail.setRemarks("订单退款");
//			paymentDetailService.persist(paymentDetail);
//		}
//		
//		if(order.getCredits() != null 
//				&& order.getCredits() > 0){
//			CreditsDetailDomain detail = new CreditsDetailDomain();
//			detail.setAmount(order.getCredits());
//			detail.setCreditsType(CreditsType.refund);
//			detail.setMember(member);
//			detail.setRemaind(member.getCredits());
//			detail.setRemark("订单退款");
//			detail.setOperator(member.getUsername());
//			creditsDetailService.persist(detail);
//		}
//		
//		memberDao.merge(member);
//		order.setConsumeStatus(ConsumeStatus.Over);
//		OrderDomain consumeOrder = order.getParent();
//		consumeOrder.setIsRefund(true);
//		consumeOrder.setConsumeStatus(ConsumeStatus.Close);
//		update(order);
//		
//		if(StringUtils.isNotBlank(order.getNotifyUrl())){//通知业务系统
//			OrderBean orderBean = new OrderBean();
//			orderBean.setOrderNo(orderNo);
//			orderBean.setOperater(order.getRefundOperator());
//			WebClientUtils.post(order.getNotifyUrl(), orderBean, void.class);
//		}
//	}
//	
//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public void cancel(OrderBean orderBean)  {
//		String orderNo = orderBean.getOrderNo();
//		if(StringUtils.isBlank(orderNo)) {
//			throw createWebApplicationException("订单编号为空");
//		}
//		OrderDomain order = this.get("orderNo", orderNo);
//		if(order == null) {
//			throw createWebApplicationException("找不到编号为【" + orderNo  + "】的订单");
//		}
//		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);
//		//若是商城的订单要进行判断
//		if(order.getConsumeStatus() == ConsumeStatus.Over) {
//			throw createWebApplicationException("订单已完成交易");
//		}
//		if(order.getConsumeStatus() == ConsumeStatus.Freeze){
//			MemberDomain member = order.getMember();
//			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
//			List<IPlugin> pluginList = orderPayPluginBundle.getPluginList();
//			for(IPlugin plugin : pluginList){
//				IOrderPayEvent orderPay = (IOrderPayEvent) plugin;
//				String error = orderPay.beforeCancel(order, member);
//				if(StringUtils.isNotBlank(error)){
//					throw createWebApplicationException(error);
//				}
//			}
//			memberDao.merge(member);
//			if(order.getDeposit() != null && order.getDeposit().signum() > 0){//冻结余额
//				PaymentDetailDomain paymentDetail = new PaymentDetailDomain();
//				paymentDetail.setPaymentNo(SerialNumberUtil.buildPaymentSn());
//				paymentDetail.setDetailType(DetailType.recharge);
//				paymentDetail.setBalance(member.getDeposit());
//				paymentDetail.setAmount(order.getDeposit());
//				paymentDetail.setOperator(member == null?null : member.getUsername());
//				paymentDetail.setOrder(order);
//				paymentDetail.setMember(member);
//				paymentDetail.setRemarks(order.getTitle());
//				paymentDetail.setRemarks("取消订单");
//				paymentDetailService.persist(paymentDetail);
//			}
//			if(order.getCredits() != null && order.getCredits() > 0){
//				CreditsDetailDomain detail = new CreditsDetailDomain();
//				detail.setAmount(order.getCredits());
//				detail.setCreditsType(CreditsType.cancel);
//				detail.setMember(member);
//				detail.setRemaind(member.getCredits());
//				detail.setRemark("取消订单");
//				detail.setOperator(member.getUsername());
//				creditsDetailService.persist(detail);
//			}
//		}
//		orderPayService.expiryOrderPay(order.getOrderPay(), order);
//		
//		order.setConsumeStatus(ConsumeStatus.Close);
//		order.setOrderPay(null);
//		update(order);
//	}
//	
//	@Override
//	public OrderDomain getOrderByOrderNo(String orderNo, Long memberId) {
//		Finder finder = Finder.create("from OrderDomain bean where bean.orderNo = :orderNo and bean.member.id = :memberId");
//		finder.setParam("orderNo", orderNo);
//		finder.setParam("memberId", memberId);
//		Object obj = finder.findUniqueByHQL();
//		if(obj != null){
//			return (OrderDomain) obj;
//		}
//		return null;
//	}
//	
//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public void payment(OrderBean orderBean) {
//		if(StringUtils.isBlank(orderBean.getGroupNo())){
//			throw createWebApplicationException("订单号为空!");
//		}
//		OrderDomain order = this.get("orderNo", orderBean.getGroupNo());
//		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);
//		if(order == null){
//			throw createWebApplicationException("订单为不存在!");
//		}
//		if(order.getConsumeStatus() == ConsumeStatus.Close){
//			throw createWebApplicationException("订单已关闭，不能进行支付操作！");
//		}
//		MemberDomain member = order.getMember();
//		if(member == null){
//			throw createWebApplicationException("用户不存在！");
//		}
//		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
//		//余额
//		BigDecimal  deposit = orderBean.getDeposit();
//		if(deposit != null && deposit.signum() > 0){
//			BigDecimal mbrDeposit = member.getDeposit();
//			if(mbrDeposit.compareTo(deposit) < 0){
//				throw createWebApplicationException("可支付余额金额不足！");
//			}
//			member.setDeposit(mbrDeposit.subtract(deposit));
//			
//		}
//		
//		memberDao.merge(member);
//		
//		//支付记录
//		order.setConsumeStatus(ConsumeStatus.Over);
//		//现金
//		order.setCash(order.getCash() == null ? 
//				orderBean.getCash() : orderBean.getCash().add(order.getCash()));
//		//余额
//		order.setDeposit(order.getDeposit() == null ? deposit : order.getDeposit().add(deposit));
//		orderDao.merge(order);
//		
//		
//		//手动支付金额记录，编号为商城支付单号
//		OrderDomain recordOrder = new OrderDomain();
//		recordOrder.setOrderNo(orderBean.getOrderNo());
//		recordOrder.setAmount(orderBean.getAmount());
//		recordOrder.setSystemId(orderBean.getSystemId());
//		//TODO 修改
////		recordOrder.setRecordType(RecordType.shop);
//		recordOrder.setConsumeStatus(ConsumeStatus.Over);
//		recordOrder.setCash(orderBean.getCash());
////		recordOrder.setItemInfo(orderBean.getItemInfo());
//		recordOrder.setDeposit(deposit);
//		recordOrder.setMember(member);
//		this.persist(recordOrder);
//		
//		//收支明细记录
//		PaymentDetailDomain paymentDetail = new PaymentDetailDomain();
//		paymentDetail.setBalance(member.getDeposit());
//		paymentDetail.setAmount(orderBean.getAmount().negate());
//		paymentDetail.setDetailType(DetailType.pay);
//		paymentDetail.setMember(member);
//		paymentDetail.setOrder(order);
//		paymentDetail.setOrderNo(order.getOrderNo());
//		paymentDetail.setPaymentNo(SerialNumberUtil.buildPaymentSn());
//		paymentDetail.setRemarks("商城后台手动支付记录");
//		paymentDetail.setPaymentTypeName("商城后台手动支付");
//		paymentDetailService.persist(paymentDetail);
//		
//	}
//
//	@Override
//	@Transactional(propagation=Propagation.REQUIRED)
//	public void returnCredits(OrderBean[] orderBeans) {
//		for(OrderBean orderBean : orderBeans){
//			if(StringUtils.isBlank(orderBean.getOrderNo())){
//				throw createWebApplicationException("交易记录不存在!");
//			}
//			if(StringUtils.isBlank(orderBean.getBody())){
//				throw createWebApplicationException("团购券不存在!");
//			}
//			OrderDomain order = get("orderNo", orderBean.getOrderNo());
//			if(order == null){
//				throw createWebApplicationException("交易记录不存在!");
//			}
//			MemberDomain member = order.getMember();
//			if(member == null){
//				return;
//			}
//			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
//			CreditsSetDomain crditsSet = member.getCreditsSet();
//			if(crditsSet == null){
//				return;
//			}
//			if(crditsSet.getBuyCredits() == null
//					|| crditsSet.getBuyCredits() <= 0){
//				return;
//			}
//			BigDecimal amount = orderBean.getAmount();
//			if(amount == null){
//				throw createWebApplicationException("返现金额错误!");
//			}
//			Integer count = order.getCredits();
//			BigDecimal total = order.getTotalWithoutDelivery();
//			Setting setting = SettingUtils.get();
//			BigDecimal rate = new BigDecimal(setting.getCreditsRate());
//			BigDecimal part = new BigDecimal(count).multiply(amount).divide(total).divide(rate, 2, BigDecimal.ROUND_HALF_UP);
//			amount = amount.subtract(part);
//			count = amount.multiply(new BigDecimal(crditsSet.getBuyCredits())).intValue();
//			if(member.getCredits() == null){
//				member.setCredits(count);
//			}else{
//				member.setCredits(member.getCredits() + count);
//			}
//			memberService.update(member);
//
//			CreditsDetailDomain detail = new CreditsDetailDomain();
//			detail.setAmount(count);
//			detail.setCreditsType(CreditsType.shopping);
//			detail.setMember(member);
//			detail.setRemaind(member.getCredits());
//			if(StringUtils.isNotBlank(orderBean.getBody())){
//				detail.setRemark(orderBean.getBody() + "消费返现");
//			}else{
//				detail.setRemark("消费返现");
//			}
//			detail.setOperator(member.getUsername());
//			creditsDetailService.persist(detail);
//		}
//	}
//
//	@Override
//	public PagerBean getOrderList(PagerBean pager, Long memberId) {
//		Finder finder = Finder.create("select new com.crm.bean.OrderInfo(bean.orderNo,bean.createDate,bean.title,bean.recordType,bean.consumeStatus,bean.amount) from OrderDomain bean where bean.member.id=:memberId order by bean.createDate desc");
//		finder.setParam("memberId", memberId);
//		pager = findByPager(pager, finder, "bean");
//		pager.addDict(RecordType.class, DictionaryUtils.getRecordTypeDictionaryData());
//		pager.addDict(ConsumeStatus.class, DictionaryUtils.getConsumeStatusDictionaryData());
//		return pager;
//	}

}